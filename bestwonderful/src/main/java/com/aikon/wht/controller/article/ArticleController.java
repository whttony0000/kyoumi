package com.aikon.wht.controller.article;

import com.aikon.wht.annotations.IndividualInfo;
import com.aikon.wht.controller.BaseController;
import com.aikon.wht.entity.Article;
import com.aikon.wht.entity.Individual;
import com.aikon.wht.enums.StatisticsEnum;
import com.aikon.wht.exception.InactiveException;
import com.aikon.wht.exception.InvalidInputException;
import com.aikon.wht.model.ArticleModel;
import com.aikon.wht.model.Page;
import com.aikon.wht.model.Response;
import com.aikon.wht.service.ArticleService;
import com.aikon.wht.service.CategoryService;
import com.aikon.wht.utils.IdEncrypter;
import com.aikon.wht.utils.JsonUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 文章controller.
 *
 * @author haitao.wang
 */
@Controller
public class ArticleController extends BaseController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    /**
     * 文章编辑页.
     *
     * @param modelMap
     * @param articleEid
     * @return String
     * @throws InactiveException
     */
    @RequestMapping("/editArticle")
    String editArticle(ModelMap modelMap, String articleEid) throws InactiveException {
        super.isTmpUser();
        Integer articleId = IdEncrypter.decodeId(articleEid);
        modelMap.addAttribute("articleId", articleId);

        modelMap.addAttribute("inner_page", "editArticle.ftl");
        Article article = this.getArticle(articleId);
        if (article == null) {
            return "template";
        }
        modelMap.addAttribute("content", article.getContent());
        article.setContent(null);
        modelMap.addAttribute("article", JsonUtil.writeValueAsJson(article));
        return "template";
    }

    /**
     * 新增文章页.
     *
     * @param modelMap
     * @return String
     * @throws InactiveException
     */
    @RequestMapping(value = "/addArticle")
    String addArticle(ModelMap modelMap) throws InactiveException {
        super.isTmpUser();
        modelMap.addAttribute("articleId", 0);
        modelMap.addAttribute("content", "");
        modelMap.addAttribute("article", JsonUtil.writeValueAsJson(new Article()));
        modelMap.addAttribute("inner_page", "editArticle.ftl");
        return "template";
    }

    /**
     * 文章列表页.
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/articleList")
    String articleList(ModelMap modelMap) {
        modelMap.addAttribute("inner_page", "articleList.ftl");
        return "template";
    }


    /**
     * 文章详情页.
     *
     * @param modelMap
     * @param articleEid
     * @return
     */
    @RequestMapping("/articleDetail")
    String articleDetail(ModelMap modelMap, String articleEid) {
        modelMap.addAttribute("articleEid", articleEid);
        Integer articleId = IdEncrypter.decodeId(articleEid);
        modelMap.addAttribute("inner_page", "articleDetail.ftl");
        // 阅读次数+1.
        articleService.updateArticleStatistics(articleId, 1, StatisticsEnum.ARTICLE_READ_CNT);
        return "template";
    }

    /**
     * 文章及作者详情数据.
     *
     * @param articleEid
     * @param individual
     * @return ArticleModel
     */
    @RequestMapping("/getArticleDetail")
    @ResponseBody
    ArticleModel getArticleDetail(String articleEid, @IndividualInfo Individual individual) {
        Integer articleId = IdEncrypter.decodeId(articleEid);
        return articleService.getArticleDetail(articleId, individual.getId());
    }

    /**
     * 文章数据.
     *
     * @param articleId
     * @return Article
     */
    @RequestMapping("/getArticle")
    @ResponseBody
    Article getArticle(Integer articleId) {
        return articleService.getArticle(articleId);
    }

    /**
     * 文章列表数据.
     *
     * @param categoryEid
     * @param individualEid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/getArticleList")
    @ResponseBody
    Response<Page<ArticleModel>> getArticleList(String categoryEid, String individualEid, @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer categoryId = null, individualId = null;
        if (StringUtils.isNotEmpty(categoryEid)) {
            categoryId = IdEncrypter.decodeId(categoryEid);
        }
        if (StringUtils.isNotEmpty(individualEid)) {
            individualId = IdEncrypter.decodeId(individualEid);
        }
        return new Response<>(articleService.getArticleList(categoryId, individualId, currentPage, pageSize));
    }

    /**
     * 保存文章数据.
     *
     * @param modelMap
     * @param article
     * @param individualId
     * @return Response
     * @throws InvalidInputException
     */
    @RequestMapping(value = "/saveArticle", method = RequestMethod.POST)
    @ResponseBody
    Response<String> saveArticle(ModelMap modelMap, @RequestBody Article article, @IndividualInfo Integer individualId) throws InvalidInputException {
        super.checkInput(article, Lists.newArrayList("title","memo","content"));
        article.setCreatorId(individualId);
        return articleService.saveArticle(article);
    }

    /**
     * 点赞文章操作.
     *
     * @param individualId
     * @param articleEid
     * @param like
     * @return Response
     * @throws InactiveException
     */
    @RequestMapping("/likeArticle")
    @ResponseBody
    Response<Object> likeArticle(@IndividualInfo Integer individualId, String articleEid, Boolean like) throws InactiveException {
        super.isTmpUser();
        Integer articleId = IdEncrypter.decodeId(articleEid);
        return articleService.likeArticle(individualId, articleId, like);
    }

    /**
     * 收藏文章操作.
     *
     * @param individualId
     * @param articleEid
     * @param bookmark
     * @return Response
     * @throws InactiveException
     */
    @RequestMapping("/bookmark")
    @ResponseBody
    Response<Object> bookmark(@IndividualInfo Integer individualId, String articleEid, Boolean bookmark) throws InactiveException {
        super.isTmpUser();
        Integer articleId = IdEncrypter.decodeId(articleEid);
        return articleService.bookmark(individualId, articleId, bookmark);
    }


}
