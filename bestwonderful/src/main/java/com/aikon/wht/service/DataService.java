package com.aikon.wht.service;

import com.aikon.wht.mongo.MongoDao;
import com.aikon.wht.model.Page;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.bson.BsonRegularExpression;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据服务service.
 *
 * @author haitao.wang
 */
@Service
@Slf4j
public class DataService {

    public static final String REAL_TIME_BOX_OFFICE = "realtime_boxoffice";

    public static final String LATEST_NEWS = "latest_news";

    public static final String STOCK_TOP_LIST = "stock_top_list";

    public static final String GDP_YEAR = "gdp_year";

    public static final String DOUBAN_MOVIE_TOP_250 = "douban_movie_top_250";

    @Autowired
    @Qualifier("singleMongoDatabase")
    MongoDatabase mongoDatabase;

    /**
     * 实时票房列表.
     *
     * @return List
     */
    public List<String> getRealTimeBoxOffice() {
        BsonDocument filter = new BsonDocument();
//        String today = new DateTime().toString("yyyy-MM-dd");
//        filter.put("time", new BsonRegularExpression(today));
        return this.getData(REAL_TIME_BOX_OFFICE, filter);
    }


    /**
     * 最新资讯列表.
     *
     * @param currentPage
     * @param pageSize
     * @return Page
     */
    public Page<String> getLatestNews(Integer currentPage, Integer pageSize) {
        BsonDocument filter = new BsonDocument();
        return this.getPage(LATEST_NEWS, filter, currentPage, pageSize);
    }

    /**
     * 牛股列表.
     *
     * @param currentPage
     * @param pageSize
     * @return Page
     */
    public Page<String> getStockTopList(Integer currentPage, Integer pageSize) {
        return this.getPage(STOCK_TOP_LIST, new BsonDocument(), currentPage, pageSize);
    }

    /**
     * GDP数据.
     *
     * @return List
     */
    public List<String> getGDPYear() {
        return this.getData(GDP_YEAR, new BsonDocument());
    }

    /**
     * douban top250数据.
     *
     * @param currentPage
     * @param pageSize
     * @return Page
     */
    public Page<String> getDoubanMovieTop250(Integer currentPage, Integer pageSize) {
        MongoDao mongoDao = new MongoDao(mongoDatabase.getCollection(DOUBAN_MOVIE_TOP_250));
        Long total = mongoDao.count(new BsonDocument());
        Bson filter = Filters.and(Filters.gt("ranking", currentPage * pageSize), Filters.lte("ranking", (currentPage + 1) * pageSize));
        List<String> doubanMovieTop250List = mongoDao.findJsonList(filter);
        return new Page<>(doubanMovieTop250List,total.intValue());
    }

    /**
     * page形式返回.
     *
     * @param collectionName
     * @param filter
     * @param currentPage
     * @param limit
     * @return Page
     */
    public Page<String> getPage(String collectionName, BsonDocument filter, Integer currentPage, Integer limit) {
        MongoDao mongoDao = new MongoDao(mongoDatabase.getCollection(collectionName));
        Long total = mongoDao.count(filter);
        return new Page<>(mongoDao.findJsonList(filter, currentPage, limit), total.intValue());
    }

    /**
     * list形式返回.
     *
     * @param collectionName
     * @param filter
     * @return List
     */
    public List<String> getData(String collectionName, BsonDocument filter) {
        return new MongoDao(mongoDatabase.getCollection(collectionName)).findJsonList(filter);
    }


}
