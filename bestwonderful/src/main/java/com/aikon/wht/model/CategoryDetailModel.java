package com.aikon.wht.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author haitao.wang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailModel {

    Integer categoryId;

    String categoryEid;

    String name;

    String description;

    UploadFileModel imageModel;

    Boolean onWatch;
}
