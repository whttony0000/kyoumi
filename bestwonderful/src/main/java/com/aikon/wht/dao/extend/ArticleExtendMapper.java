package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.ArticleMapper;
import com.aikon.wht.entity.Article;
import com.aikon.wht.model.ArticleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleExtendMapper extends ArticleMapper{
    int insertSelectiveExt(Article record);

    List<ArticleModel> getArticleList(@Param("categoryId") Integer categoryId, @Param("individualId") Integer individualId,@Param("offset") Integer offset,@Param("limit") Integer limit);

    ArticleModel getArticleDetail(@Param("articleId") Integer articleId,@Param("individualId") Integer individualId);

    List<ArticleModel> getHotArticleList(@Param("categoryIds") List<Integer> categoryIds);

    Integer countArticleList(@Param("categoryId") Integer categoryId,@Param("individualId") Integer individualId);

    List<ArticleModel> getIndividualBookmarkList(@Param("individualId") Integer individualId, @Param("offset") Integer offset, @Param("limit") Integer pageSize);
}