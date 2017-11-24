package com.aikon.wht.dao;

import com.aikon.wht.entity.RolePerm;
import com.aikon.wht.entity.RolePermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermMapper {
    int countByExample(RolePermExample example);

    int deleteByExample(RolePermExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePerm record);

    int insertSelective(RolePerm record);

    List<RolePerm> selectByExample(RolePermExample example);

    RolePerm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePerm record, @Param("example") RolePermExample example);

    int updateByExample(@Param("record") RolePerm record, @Param("example") RolePermExample example);

    int updateByPrimaryKeySelective(RolePerm record);

    int updateByPrimaryKey(RolePerm record);
}