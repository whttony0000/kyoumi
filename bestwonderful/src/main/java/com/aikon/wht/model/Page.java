package com.aikon.wht.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by haitao.wang on 2017/8/13 0013.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    List<T> rows;

    Integer total;
}
