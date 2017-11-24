package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.IndividualLogMapper;
import com.aikon.wht.entity.IndividualLog;
import org.apache.ibatis.annotations.Param;

public interface IndividualLogExtendMapper extends IndividualLogMapper{
    IndividualLog getLastLog(@Param("individualId") Integer id,@Param("type") Integer code);
}