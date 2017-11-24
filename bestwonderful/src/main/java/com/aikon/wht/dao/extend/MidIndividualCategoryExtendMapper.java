package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.MidIndividualCategoryMapper;
import com.aikon.wht.entity.MidIndividualCategory;
import org.apache.ibatis.annotations.Param;

public interface MidIndividualCategoryExtendMapper extends MidIndividualCategoryMapper{

    void upsert(@Param("midIndividualCategory") MidIndividualCategory midIndividualCategory);
}