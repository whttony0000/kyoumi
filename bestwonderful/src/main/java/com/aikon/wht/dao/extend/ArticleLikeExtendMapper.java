package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.ArticleLikeMapper;
import org.apache.ibatis.annotations.Param;

public interface ArticleLikeExtendMapper extends ArticleLikeMapper {
    void upsert(@Param("individualId") Integer individualId, @Param("articleId") Integer articleId,@Param("status") Integer status);
}