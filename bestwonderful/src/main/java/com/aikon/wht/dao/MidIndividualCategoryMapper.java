package com.aikon.wht.dao;

import com.aikon.wht.entity.MidIndividualCategory;
import com.aikon.wht.entity.MidIndividualCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MidIndividualCategoryMapper {
    int countByExample(MidIndividualCategoryExample example);

    int deleteByExample(MidIndividualCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MidIndividualCategory record);

    int insertSelective(MidIndividualCategory record);

    List<MidIndividualCategory> selectByExample(MidIndividualCategoryExample example);

    MidIndividualCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MidIndividualCategory record, @Param("example") MidIndividualCategoryExample example);

    int updateByExample(@Param("record") MidIndividualCategory record, @Param("example") MidIndividualCategoryExample example);

    int updateByPrimaryKeySelective(MidIndividualCategory record);

    int updateByPrimaryKey(MidIndividualCategory record);
}