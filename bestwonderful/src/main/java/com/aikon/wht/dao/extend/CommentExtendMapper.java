package com.aikon.wht.dao.extend;

import com.aikon.wht.dao.CommentMapper;
import com.aikon.wht.entity.Comment;
import com.aikon.wht.model.CommentModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentExtendMapper extends CommentMapper{
    List<CommentModel> getComments(@Param("articleId") Integer articleId, @Param("offset") Integer offset, @Param("limit") Integer pageSize);

    Integer getCurrentFloor(@Param("articleId") Integer articleId);

    int insertSelectiveExt(Comment record);
}