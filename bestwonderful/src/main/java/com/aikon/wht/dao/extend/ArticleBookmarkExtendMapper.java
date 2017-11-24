package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.ArticleBookmarkMapper;
import com.aikon.wht.entity.ArticleBookmark;
import org.apache.ibatis.annotations.Param;

public interface ArticleBookmarkExtendMapper extends ArticleBookmarkMapper{
    void upsert(@Param("articleBookmark") ArticleBookmark articleBookmark);
}