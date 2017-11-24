package com.aikon.wht.dao;

import com.aikon.wht.entity.MidIndividualIndividual;
import com.aikon.wht.entity.MidIndividualIndividualExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MidIndividualIndividualMapper {
    int countByExample(MidIndividualIndividualExample example);

    int deleteByExample(MidIndividualIndividualExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MidIndividualIndividual record);

    int insertSelective(MidIndividualIndividual record);

    List<MidIndividualIndividual> selectByExample(MidIndividualIndividualExample example);

    MidIndividualIndividual selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MidIndividualIndividual record, @Param("example") MidIndividualIndividualExample example);

    int updateByExample(@Param("record") MidIndividualIndividual record, @Param("example") MidIndividualIndividualExample example);

    int updateByPrimaryKeySelective(MidIndividualIndividual record);

    int updateByPrimaryKey(MidIndividualIndividual record);
}