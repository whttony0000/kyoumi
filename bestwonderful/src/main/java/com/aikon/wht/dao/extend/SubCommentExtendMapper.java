package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.SubCommentMapper;
import com.aikon.wht.entity.SubComment;
import com.aikon.wht.model.CommentModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubCommentExtendMapper extends SubCommentMapper{

    int insertSelectiveExt(SubComment record);

    List<CommentModel> getSubComments(@Param("commentId") Integer commentId);
}