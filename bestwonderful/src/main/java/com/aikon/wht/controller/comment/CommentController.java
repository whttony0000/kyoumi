package com.aikon.wht.controller.comment;

import com.aikon.wht.annotations.IndividualInfo;
import com.aikon.wht.controller.BaseController;
import com.aikon.wht.entity.Individual;
import com.aikon.wht.exception.InactiveException;
import com.aikon.wht.model.CommentModel;
import com.aikon.wht.model.Page;
import com.aikon.wht.model.Response;
import com.aikon.wht.service.CommentService;
import com.aikon.wht.utils.IdEncrypter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 评论及评论的评论controller.
 *
 * @author haitao.wang
 */
@Controller
public class CommentController extends BaseController{

    @Autowired
    CommentService commentService;

    /**
     * 评论数据.
     *
     * @param articleEid
     * @param currentPage
     * @param pageSize
     * @param inidividualId
     * @return Response
     */
    @RequestMapping("/getComments")
    @ResponseBody
    Response<Page<CommentModel>> getComments(String articleEid, Integer currentPage, Integer pageSize,Integer inidividualId) {
        Integer articleId = IdEncrypter.decodeId(articleEid);
        return new Response<>(new Page(commentService.getComments(articleId, currentPage, pageSize, inidividualId),commentService.getCommentCnt(articleId)));
    }

    /**
     * 提交评论的评论.
     *
     * @param commentId
     * @param targetIndividualEid
     * @param content
     * @param creator
     * @return Response
     * @throws InactiveException
     */
    @RequestMapping("/submitSubComment")
    @ResponseBody
    Response<CommentModel> submitSubComment(Integer commentId,String targetIndividualEid, String content, @IndividualInfo Individual creator) throws InactiveException {
        super.isTmpUser();
        Integer targetIndividualId = IdEncrypter.decodeId(targetIndividualEid);
        if (StringUtils.isBlank(content)) {
            return new Response<>(-1, "评论内容不能为空");
        }
        return commentService.submitSubComment(commentId,targetIndividualId,creator,content);
    }

    /**
     * 提交评论.
     *
     * @param articleEid
     * @param content
     * @param individual
     * @return Response
     * @throws InactiveException
     */
    @RequestMapping("/submitComment")
    @ResponseBody
    Response<CommentModel> submitComment(String articleEid, String content, @IndividualInfo Individual individual) throws InactiveException {
        super.isTmpUser();
        Integer articleId = IdEncrypter.decodeId(articleEid);
        if (StringUtils.isBlank(content)) {
            return new Response<>(-1, "评论内容不能为空");
        }
        return commentService.submitComment(articleId, content, individual);
    }
}
