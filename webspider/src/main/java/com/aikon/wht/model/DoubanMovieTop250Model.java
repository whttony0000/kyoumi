package com.aikon.wht.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

/**
 * @author haitao.wang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DoubanMovieTop250Model {

    String ranking;

    Collection<String> name;

    String member;

    String rating;

    String raterCnt;

    String img;

    public Integer getRanking() {
        return Integer.valueOf(ranking);
    }
}
