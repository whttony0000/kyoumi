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
public class UploadFileModel {

    Long id;

    String name;

    String key;

    String url;

    String msg;
}
