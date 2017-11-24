package com.aikon.wht.dao;

import com.aikon.wht.entity.ArticleBookmark;
import com.aikon.wht.entity.ArticleBookmarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleBookmarkMapper {
    int countByExample(ArticleBookmarkExample example);

    int deleteByExample(ArticleBookmarkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleBookmark record);

    int insertSelective(ArticleBookmark record);

    List<ArticleBookmark> selectByExample(ArticleBookmarkExample example);

    ArticleBookmark selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleBookmark record, @Param("example") ArticleBookmarkExample example);

    int updateByExample(@Param("record") ArticleBookmark record, @Param("example") ArticleBookmarkExample example);

    int updateByPrimaryKeySelective(ArticleBookmark record);

    int updateByPrimaryKey(ArticleBookmark record);
}