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
public class IndividualDetail {

    String name;

    String description;

    Integer sex;

    Integer score;

    String photoKey;

    String photoUrl;

    Integer watchCategoryCnt;

    Integer watchIndividualCnt;

    Integer fanCnt;

    String mailMd5Hash;

    String birthday;

    Boolean onWatch;

    String individualEid;

}
