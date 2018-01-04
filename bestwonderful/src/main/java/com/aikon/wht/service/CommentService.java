package com.aikon.wht.service;

import com.aikon.wht.dao.extend.CommentExtendMapper;
import com.aikon.wht.dao.extend.SubCommentExtendMapper;
import com.aikon.wht.entity.Comment;
import com.aikon.wht.entity.CommentExample;
import com.aikon.wht.entity.Individual;
import com.aikon.wht.entity.SubComment;
import com.aikon.wht.enums.StatisticsEnum;
import com.aikon.wht.enums.StatusEnum;
import com.aikon.wht.model.CommentModel;
import com.aikon.wht.model.Response;
import com.aikon.wht.utils.Converter;
import com.aikon.wht.utils.IdEncrypter;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 评论service.
 *
 * @author haitao.wang
 */
@Service
public class CommentService {

    @Autowired
    CommentExtendMapper commentExtendMapper;

    @Autowired
    SubCommentExtendMapper subCommentExtendMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    IndividualService individualService;

    @Autowired
    ArticleService articleService;

    /**
     * 获取文章的评论.
     *
     * @param articleId
     * @param currentPage
     * @param pageSize
     * @param inidividualId
     * @return List
     */
    public List<CommentModel> getComments(Integer articleId, Integer currentPage, Integer pageSize, Integer inidividualId) {
        Integer offset = (currentPage - 1) * pageSize;
        List<CommentModel> commentModels = this.commentExtendMapper.getComments(articleId,offset,pageSize);
        if (CollectionUtils.isEmpty(commentModels)) {
            return Collections.EMPTY_LIST;
        }
        commentModels.stream().forEach(commentModel -> {
            List<CommentModel> subCommentModels = this.subCommentExtendMapper.getSubComments(commentModel.getId());
            if (CollectionUtils.isEmpty(subCommentModels)) {
                return;
            }
            commentModel.setSubComments(this.getSubCommentConverter().convert(subCommentModels));
        });
        return this.getCommentConverter().convert(commentModels);
    }

    /**
     * {@link CommentModel}
     *
     * =>
     *
     * {@link CommentModel}
     *
     * @return Converter
     */
    public Converter<CommentModel, CommentModel> getCommentConverter() {
        return new Converter<>(commentModel -> {
            commentModel.setIndividualEid(IdEncrypter.encodeId(commentModel.getIndividualId()));
            commentModel.setPhotoUrl(commonService.getImageUrlByKey(commentModel.getPhotoKey()));
            return commentModel;
        });
    }

    /**
     * {@link CommentModel}
     *
     * =>
     *
     * {@link CommentModel}
     *
     * @return Converter
     */
    public Converter<CommentModel, CommentModel> getSubCommentConverter() {
        return new Converter<>(subComment -> {
            subComment.setTargetIndividualEid(IdEncrypter.encodeId(subComment.getTargetIndividualId()));
            subComment.setIndividualEid(IdEncrypter.encodeId(subComment.getIndividualId()));
            return subComment;
        });
    }

    /**
     * 获取评论数.
     *
     * @param articleId
     * @return Integer
     */
    public Integer getCommentCnt(Integer articleId) {
        CommentExample example = new CommentExample();
        example.or().andArticleIdEqualTo(articleId).andStatusEqualTo(StatusEnum.VALID.getCode());
        return commentExtendMapper.countByExample(example);
    }

    /**
     * 提交评论的评论.
     *
     * @param commentId
     * @param targetIndividualId
     * @param creator
     * @param content
     * @return Response
     */
    public Response<CommentModel> submitSubComment(Integer commentId, Integer targetIndividualId, Individual creator, String content) {
        Individual targetIndividual = individualService.getIndividualById(targetIndividualId);
        if (targetIndividual == null) {
            return new Response<>(-1, "评论失败!");
        }
        SubComment subComment = new SubComment();
        subComment.setCreatorId(creator.getId());
        subComment.setStatus(StatusEnum.VALID.getCode());
        subComment.setContent(content);
        subComment.setCommentId(commentId);
        subComment.setTargetIndividualId(targetIndividualId);
        int cnt = subCommentExtendMapper.insertSelectiveExt(subComment);
        if (cnt <= 0) {
            return new Response<>(0, "评论失败!");
        }
        CommentModel subCommentModel = new CommentModel();
        subCommentModel.setCommentId(subComment.getCommentId());
        subCommentModel.setContent(subComment.getContent());
        subCommentModel.setIndividualName(creator.getName());
        subCommentModel.setIndividualEid(IdEncrypter.encodeId(creator.getId()));
        subCommentModel.setTargetIndividualName(targetIndividual.getName());
        subCommentModel.setTargetIndividualEid(IdEncrypter.encodeId(targetIndividual.getId()));
        subCommentModel.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm"));
        return new Response<>(subCommentModel);
    }

    /**
     * 提交评论.
     *
     * @param articleId
     * @param content
     * @param individual
     * @return Response
     */
    public Response<CommentModel> submitComment(Integer articleId, String content, Individual individual) {
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setCreatorId(individual.getId());
        comment.setStatus(StatusEnum.VALID.getCode());
        comment.setFloor(this.getCurrentFloor(articleId) + 1);
        comment.setContent(content);
        int cnt = commentExtendMapper.insertSelectiveExt(comment);
        if (cnt <= 0) {
            return new Response<>(0, "评论失败!");
        }
        CommentModel commentModel = new CommentModel();
        commentModel.setPhotoKey(individual.getPhotoKey());
        commentModel.setIndividualId(individual.getId());
        commentModel.setIndividualName(individual.getName());
        commentModel.setFloor(comment.getFloor());
        commentModel.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm"));
        commentModel.setContent(comment.getContent());
        commentModel.setId(comment.getId());
        commentModel.setMailMd5Hash(individual.getMailMd5Hash());
        articleService.updateArticleStatistics(articleId,1, StatisticsEnum.ARTICLE_COMMENT_CNT);
        return new Response<>(this.getCommentConverter().convert(commentModel));
    }

    /**
     * 获取当前评论总楼层.
     *
     * @param articleId
     * @return Integer
     */
    public Integer getCurrentFloor(Integer articleId) {
        return Optional.ofNullable(commentExtendMapper.getCurrentFloor(articleId)).orElse(1);
    }
}
