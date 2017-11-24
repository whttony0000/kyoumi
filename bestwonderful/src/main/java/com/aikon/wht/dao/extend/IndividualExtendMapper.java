package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.IndividualMapper;
import com.aikon.wht.entity.Individual;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndividualExtendMapper extends IndividualMapper{

    int insertSelectiveExt(Individual individual);

    List<Individual> getOnWatchIndividualList(@Param("watcherId") Integer watcherId,@Param("offset") Integer offset,@Param("limit") Integer pageSize);

    List<Individual> getFanList(@Param("watchedId") Integer watchedId,@Param("offset") Integer offset,@Param("limit") Integer pageSize);

    List<Individual> getFanDetails(@Param("individualId") Integer individualId);
}