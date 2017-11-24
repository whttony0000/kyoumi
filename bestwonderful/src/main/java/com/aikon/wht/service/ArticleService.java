package com.aikon.wht.service;

import com.aikon.wht.dao.extend.ArticleBookmarkExtendMapper;
import com.aikon.wht.dao.extend.ArticleExtendMapper;
import com.aikon.wht.dao.extend.ArticleLikeExtendMapper;
import com.aikon.wht.entity.*;
import com.aikon.wht.enums.ScoreEnum;
import com.aikon.wht.enums.StatisticsEnum;
import com.aikon.wht.enums.StatusEnum;
import com.aikon.wht.model.ArticleCreateEventModel;
import com.aikon.wht.model.ArticleModel;
import com.aikon.wht.model.Page;
import com.aikon.wht.model.Response;
import com.aikon.wht.event.ArticleCreateEvent;
import com.aikon.wht.event.NoticeCaller;
import com.aikon.wht.utils.Converter;
import com.aikon.wht.utils.IdEncrypter;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/7/15 0015.
 */
@Service
@Slf4j
public class ArticleService {

    @Autowired
    ArticleExtendMapper articleExtendMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    IndividualService individualService;

    @Autowired
    ArticleLikeExtendMapper articleLikeExtendMapper;

    @Autowired
    ArticleBookmarkExtendMapper articleBookmarkExtendMapper;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    NoticeCaller noticeCaller;

    @Autowired
    CommonService commonService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    public Article getArticle(Integer articleId) {
        ArticleExample example = new ArticleExample();
        example.or().andIdEqualTo(articleId).andStatusEqualTo(StatusEnum.VALID.getCode());
        List<Article> articleList = articleExtendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(articleList)) {
            return null;
        }
        return articleList.get(0);
    }

    public Response<String> saveArticle(Article article) {
        if (article.getId() == null || article.getId() == 0) {
            article.setStatus(StatusEnum.VALID.getCode());
            Boolean created = transactionTemplate.execute((TransactionStatus status) -> {
                try {
                    articleExtendMapper.insertSelectiveExt(article);
                    individualService.updateScore(article.getCreatorId(), ScoreEnum.CREATE_ARTICLE.getCode());
                } catch (Exception e) {
                    log.info("Error Creating Article {} {}", article.getCreatorId(), article.getTitle());
                    return false;
                }
                return true;
            });
            if (created) {
                noticeCaller.publishEvent(
                        new ArticleCreateEventModel(article.getId(), article.getCreatorId(), article.getTitle()),
                        ArticleCreateEvent.class
                );
            }
        } else {
            Article articleDB = this.getArticle(article.getId());
            if (articleDB == null) {
                return new Response<>(0, "您所编辑的文章不存在或已被删除");
            }
            articleExtendMapper.updateByPrimaryKeySelective(article);
        }
        return new Response<>(IdEncrypter.encodeId(article.getId()));
    }

    public Page<ArticleModel> getArticleList(Integer categoryId, Integer individualId, Integer currentPage, Integer pageSize) {
        Integer cnt = articleExtendMapper.countArticleList(categoryId, individualId);
        if (cnt <= 0) {
            return new Page<>();
        }
        Integer offset = (currentPage - 1) * pageSize;
        List<ArticleModel> articleListModels = Optional.fromNullable(
                articleExtendMapper.getArticleList(categoryId, individualId, offset, pageSize))
                .or(Lists.newArrayList());
        return new Page<>(getArticleModelConverter().convert(articleListModels), cnt);
    }


    @NotNull
    private Converter<ArticleModel, ArticleModel> getArticleModelConverter() {
        return new Converter<>(model -> {
            model.setArticleEid(IdEncrypter.encodeId(model.getArticleId()));
            model.setCategoryEid(IdEncrypter.encodeId(model.getCategoryId()));
            model.setIndividualEid(IdEncrypter.encodeId(model.getIndividualId()));
            model.setIndividualPhotoUrl(commonService.getImageUrlByKey(model.getIndividualPhotoKey()));
            model.setArticleId(null);
            model.setCategoryId(null);
            model.setIndividualId(null);
            return model;
        });
    }

    public ArticleModel getArticleDetail(Integer articleId, Integer individualId) {
        ArticleModel articleModel = articleExtendMapper.getArticleDetail(articleId, individualId);
        if (articleModel == null) {
            return null;
        }
        articleModel.setPermission(individualId.equals(articleModel.getIndividualId()));
        articleModel.setBookmarked(this.countArticleIndividualBookmark(individualId, articleId, StatusEnum.VALID.getCode()) > 0);
        articleModel.setLiked(this.countArticleIndividualLike(individualId, articleId, StatusEnum.VALID.getCode()) > 0);
        return getArticleModelConverter().convert(articleModel);
    }

    public void updateArticleStatistics(Integer articleId, Integer cnt, StatisticsEnum statisticsEnum) {
        threadPoolTaskExecutor.execute(() -> {
            Article article = this.getArticle(articleId);
            if (article == null) {
                return;
            }
            Article article2Update = new Article();
            article2Update.setId(articleId);
            if (StatisticsEnum.ARTICLE_LIKE_CNT.equals(statisticsEnum)) {
                article2Update.setLikeCnt(article.getLikeCnt() + cnt);
            }
            if (StatisticsEnum.ARTICLE_BOOKMARK_CNT.equals(statisticsEnum)) {
                article2Update.setBookmarkCnt(article.getBookmarkCnt() + cnt);
            }
            if (StatisticsEnum.ARTICLE_SHARE_CNT.equals(statisticsEnum)) {
                article2Update.setShareCnt(article.getShareCnt() + cnt);
            }
            if (StatisticsEnum.ARTICLE_COMMENT_CNT.equals(statisticsEnum)) {
                article2Update.setCommentCnt(article.getCommentCnt() + cnt);
            }
            if (StatisticsEnum.ARTICLE_READ_CNT.equals(statisticsEnum)) {
                article2Update.setReadCnt(article.getReadCnt() + cnt);
            }
            articleExtendMapper.updateByPrimaryKeySelective(article2Update);
        });
    }

    public Response<Object> likeArticle(Integer individualId, Integer articleId, Boolean like) {
        Integer status = (like ? StatusEnum.VALID : StatusEnum.INVALID).getCode();
        this.articleLikeExtendMapper.upsert(individualId, articleId, status);
        this.updateArticleStatistics(articleId, like ? 1 : -1, StatisticsEnum.ARTICLE_LIKE_CNT);
        return Response.SUCCESS;
    }

    public Integer countArticleIndividualLike(Integer individualId, Integer articleId, Integer status) {
        ArticleLikeExample example = new ArticleLikeExample();
        example.or().andArticleIdEqualTo(articleId).andIndividualIdEqualTo(individualId).andStatusEqualTo(status);
        return articleLikeExtendMapper.countByExample(example);
    }

    public Response<Object> bookmark(Integer individualId, Integer articleId, Boolean bookmark) {
        Integer status = bookmark ? StatusEnum.VALID.getCode() : StatusEnum.INVALID.getCode();
        ArticleBookmark articleBookmark = new ArticleBookmark();
        articleBookmark.setCreatorId(individualId);
        articleBookmark.setIndividualId(individualId);
        articleBookmark.setArticleId(articleId);
        articleBookmark.setStatus(status);
        articleBookmarkExtendMapper.upsert(articleBookmark);
        this.updateArticleStatistics(articleId, bookmark ? 1 : -1, StatisticsEnum.ARTICLE_BOOKMARK_CNT);
        return Response.SUCCESS;
    }

    public Integer countArticleBookmarker(Integer articleId) {
        ArticleBookmarkExample example = new ArticleBookmarkExample();
        example.or().andArticleIdEqualTo(articleId).andStatusEqualTo(StatusEnum.VALID.getCode());
        return articleBookmarkExtendMapper.countByExample(example);
    }

    public List<Integer> getArticleBookmarkerList(Integer articleId) {
        ArticleBookmarkExample example = new ArticleBookmarkExample();
        example.or().andArticleIdEqualTo(articleId).andStatusEqualTo(StatusEnum.VALID.getCode());
        List<ArticleBookmark> articleBookmarks = articleBookmarkExtendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(articleBookmarks)) {
            return Collections.EMPTY_LIST;
        }
        return articleBookmarks.stream().map(ArticleBookmark::getIndividualId).collect(Collectors.toList());
    }

    public Integer countArticleIndividualBookmark(Integer individualId, Integer articleId, Integer status) {
        ArticleBookmarkExample example = new ArticleBookmarkExample();
        example.or().andArticleIdEqualTo(articleId).andIndividualIdEqualTo(individualId).andStatusEqualTo(status);
        return articleBookmarkExtendMapper.countByExample(example);
    }

    public List<ArticleModel> getIndividualBookmarkList(Integer individualId, Integer currentPage, Integer pageSize) {
        Integer offset = (currentPage - 1) * pageSize;
        return this.getArticleModelConverter().convert(this.articleExtendMapper.getIndividualBookmarkList(individualId, offset, pageSize));
    }

    public Integer getIndividualBookmarkCnt(Integer individualId) {
        ArticleBookmarkExample example = new ArticleBookmarkExample();
        example.or().andIndividualIdEqualTo(individualId).andStatusEqualTo(StatusEnum.VALID.getCode());
        Integer total = articleBookmarkExtendMapper.countByExample(example);
        return total;
    }
}
