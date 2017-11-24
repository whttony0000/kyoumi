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
 * @author haitao.wang
 */
@Controller
public class ArticleController extends BaseController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

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

    @RequestMapping(value = "/addArticle")
    String addArticle(ModelMap modelMap) throws InactiveException {
        super.isTmpUser();
        modelMap.addAttribute("articleId", 0);
        modelMap.addAttribute("content", "");
        modelMap.addAttribute("article", JsonUtil.writeValueAsJson(new Article()));
        modelMap.addAttribute("inner_page", "editArticle.ftl");
        return "template";
    }

    @RequestMapping(value = "/articleList")
    String articleList(ModelMap modelMap) {
        modelMap.addAttribute("inner_page", "articleList.ftl");
        return "template";
    }

    @RequestMapping("/articleDetail")
    String articleDetail(ModelMap modelMap, String articleEid) {
        modelMap.addAttribute("articleEid", articleEid);
        Integer articleId = IdEncrypter.decodeId(articleEid);
        modelMap.addAttribute("inner_page", "articleDetail.ftl");
        articleService.updateArticleStatistics(articleId, 1, StatisticsEnum.ARTICLE_READ_CNT);
        return "template";
    }

    @RequestMapping("/getArticleDetail")
    @ResponseBody
    ArticleModel getArticleDetail(String articleEid, @IndividualInfo Individual individual) {
        Integer articleId = IdEncrypter.decodeId(articleEid);
        return articleService.getArticleDetail(articleId, individual.getId());
    }

    @RequestMapping("/getArticle")
    @ResponseBody
    Article getArticle(Integer articleId) {
        return articleService.getArticle(articleId);
    }

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

    @RequestMapping(value = "/saveArticle", method = RequestMethod.POST)
    @ResponseBody
    Response<String> saveArticle(ModelMap modelMap, @RequestBody Article article, @IndividualInfo Integer individualId) throws InvalidInputException {
        super.checkInput(article, Lists.newArrayList("title","memo","content"));
        article.setCreatorId(individualId);
        return articleService.saveArticle(article);
    }

    @RequestMapping("/likeArticle")
    @ResponseBody
    Response<Object> likeArticle(@IndividualInfo Integer individualId, String articleEid, Boolean like) throws InactiveException {
        super.isTmpUser();
        Integer articleId = IdEncrypter.decodeId(articleEid);
        return articleService.likeArticle(individualId, articleId, like);
    }

    @RequestMapping("/bookmark")
    @ResponseBody
    Response<Object> bookmark(@IndividualInfo Integer individualId, String articleEid, Boolean bookmark) throws InactiveException {
        super.isTmpUser();
        Integer articleId = IdEncrypter.decodeId(articleEid);
        return articleService.bookmark(individualId, articleId, bookmark);
    }


}
