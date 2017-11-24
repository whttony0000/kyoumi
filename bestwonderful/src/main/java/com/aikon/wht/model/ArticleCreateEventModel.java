package com.aikon.wht.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author haitao.wang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleCreateEventModel extends EventModel {

    Integer articleId;

    Integer individualId;

    String articleTitle;

}
