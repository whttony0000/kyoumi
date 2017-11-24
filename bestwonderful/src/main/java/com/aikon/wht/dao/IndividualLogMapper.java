package com.aikon.wht.dao;

import com.aikon.wht.entity.IndividualLog;
import com.aikon.wht.entity.IndividualLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndividualLogMapper {
    int countByExample(IndividualLogExample example);

    int deleteByExample(IndividualLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IndividualLog record);

    int insertSelective(IndividualLog record);

    List<IndividualLog> selectByExample(IndividualLogExample example);

    IndividualLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IndividualLog record, @Param("example") IndividualLogExample example);

    int updateByExample(@Param("record") IndividualLog record, @Param("example") IndividualLogExample example);

    int updateByPrimaryKeySelective(IndividualLog record);

    int updateByPrimaryKey(IndividualLog record);
}