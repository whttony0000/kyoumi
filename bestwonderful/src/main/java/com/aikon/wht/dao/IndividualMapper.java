package com.aikon.wht.dao;

import com.aikon.wht.entity.Individual;
import com.aikon.wht.entity.IndividualExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndividualMapper {
    int countByExample(IndividualExample example);

    int deleteByExample(IndividualExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Individual record);

    int insertSelective(Individual record);

    List<Individual> selectByExample(IndividualExample example);

    Individual selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Individual record, @Param("example") IndividualExample example);

    int updateByExample(@Param("record") Individual record, @Param("example") IndividualExample example);

    int updateByPrimaryKeySelective(Individual record);

    int updateByPrimaryKey(Individual record);
}