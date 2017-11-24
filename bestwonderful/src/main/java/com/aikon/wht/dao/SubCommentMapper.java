package com.aikon.wht.dao;

import com.aikon.wht.entity.SubComment;
import com.aikon.wht.entity.SubCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubCommentMapper {
    int countByExample(SubCommentExample example);

    int deleteByExample(SubCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubComment record);

    int insertSelective(SubComment record);

    List<SubComment> selectByExample(SubCommentExample example);

    SubComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubComment record, @Param("example") SubCommentExample example);

    int updateByExample(@Param("record") SubComment record, @Param("example") SubCommentExample example);

    int updateByPrimaryKeySelective(SubComment record);

    int updateByPrimaryKey(SubComment record);
}