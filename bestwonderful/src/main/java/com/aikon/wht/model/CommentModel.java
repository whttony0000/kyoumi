package com.aikon.wht.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author haitao.wang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {

    Integer id;

    Integer individualId;

    String individualEid;

    String individualName;

    Integer commentId;

    String photoKey;

    String photoUrl;

    Integer floor;

    String createTime;

    String mailMd5Hash;

    Boolean me;

    Integer likeCnt;

    String content;

    Integer targetIndividualId;

    String targetIndividualEid;

    String targetIndividualName;

    List<CommentModel> subComments;


}
