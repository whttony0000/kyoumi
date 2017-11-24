package com.aikon.wht.pipeline;

import com.aikon.wht.mongo.MongoDao;
import com.aikon.wht.model.DoubanMovieTop250Model;
import com.aikon.wht.mongo.MongoService;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 豆瓣电影top250数据处理 实现Pipeline.
 *
 * @author haitao.wang
 */
public class DoubanMovieTop250Pipeline implements Pipeline {

    /**
     * ResultItems 核心是一个hashmap，存放抓取到的元素.
     *
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {

        Map<String, Object> resultMap = resultItems.getAll();
        final Multimap<Integer, String> nameMultiMap = handleName((Iterable<String>) resultMap.get("name"));
        List<String> memberList = iterable2List((Iterable<String>) resultMap.get("member"));
        List<String> ratingList = iterable2List((Iterable<String>) resultMap.get("rating"));
        List<String> raterCntList = iterable2List((Iterable<String>) resultMap.get("raterCnt"));
        List<String> rankingList = iterable2List((Iterable<String>) resultMap.get("ranking"));
        List<String> imgList = iterable2List((Iterable<String>) resultMap.get("img"));


        final List<DoubanMovieTop250Model> movieTop250Models = Lists.newArrayList();

        nameMultiMap.keySet().stream().forEach((key) -> {
            DoubanMovieTop250Model movieTop250Model = new DoubanMovieTop250Model();
            movieTop250Model.setName(nameMultiMap.get(key));
            movieTop250Model.setMember(memberList.get(key));
            movieTop250Model.setRating(ratingList.get(key));
            movieTop250Model.setRaterCnt(raterCntList.get(key));
            movieTop250Model.setRanking(rankingList.get(key));
            movieTop250Model.setImg(imgList.get(key));
            movieTop250Models.add(movieTop250Model);
        });

        // 入库.
        MongoDao dao = MongoService.instance("douban_movie_top_250");
        dao.insertList(movieTop250Models);
    }

    /**
     * Iterable => List.
     *
     * @param memberIterable
     * @return List
     */
    private List iterable2List(Iterable memberIterable) {
        return Lists.newArrayList(memberIterable);
    }


    /**
     * 处理电影名.
     *
     * @param iterable
     * @return Multimap<Integer, String>
     */
    private Multimap<Integer, String> handleName(Iterable<String> iterable) {
        // ["肖申克的救赎",
        //  "/ The Shawshank Redemption",
        //  "/ 月黑高飞(港)  /  刺激1995(台)",
        // "霸王别姬",
        //  "/ 再见，我的妾  /  Farewell My Concubine"]
        // =>
        // [0=["肖申克的救赎","The Shawshank Redemption","月黑高飞(港)","刺激1995(台)"],1=["霸王别姬","再见，我的妾","Farewell My Concubine"]]
        Iterator iterator = iterable.iterator();
        Multimap<Integer, String> multiMap = ArrayListMultimap.create();
        int i = -1;
        while (iterator.hasNext()) {
            String item = (String) iterator.next();
            item = item.replaceAll("\u00A0", "").trim();
            if (item.contains("/")) {
                Iterable iterable1 = Splitter.on("/").split(item);
                Iterator iterator1 = iterable1.iterator();
                while (iterator1.hasNext()) {
                    String item1 = (String) iterator1.next();
                    if (StringUtils.isBlank(item1)) {
                        continue;
                    }
                    multiMap.put(i, StringUtils.trim(item1));
                }
                continue;
            } else {
                i++;
            }
            multiMap.put(i, StringUtils.trim(item));
        }
        return multiMap;
    }


}
