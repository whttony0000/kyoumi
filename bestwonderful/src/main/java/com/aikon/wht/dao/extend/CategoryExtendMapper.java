package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.CategoryMapper;
import com.aikon.wht.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryExtendMapper extends CategoryMapper{

    int insertSelectiveExt(Category category);

    List<Category> getOnWatchCategoryList(@Param("watcherId") Integer watcherId,@Param("offset") Integer offset,@Param("limit") Integer pageSize);
}