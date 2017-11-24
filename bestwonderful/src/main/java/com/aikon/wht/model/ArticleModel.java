package com.aikon.wht.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author haitao.wang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleModel {

    String title;

    String memo;

    String content;

    Integer articleId;

    String articleEid;

    Integer articleLikeCnt;

    Integer articleCommentCnt;

    Integer articleBookmarkCnt;

    Integer articleReadCnt;

    Integer categoryId;

    String categoryEid;

    String categoryName;

    Integer individualId;

    String individualEid;

    String individualName;

    Integer individualScore;

    String individualPhotoKey;

    String individualPhotoUrl;

    String individualMailCode;

    String createTime;

    Boolean permission;



    Boolean bookmarked;

    Boolean liked;


}
