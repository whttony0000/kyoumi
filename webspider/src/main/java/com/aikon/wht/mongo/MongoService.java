package com.aikon.wht.mongo;

import com.aikon.wht.mongo.config.MongoConfig;

/**
 * @author haitao.wang
 */
public class MongoService {


    private static MongoDao mongoDao;

    /**
     * To get mongo client instance,figure out the fields below first.
     */
    private static final String host = "host";

    private static final String port = "port";

    private static final String userName = "userName";

    private static final String password = "password";

    private static final String databaseName = "databaseName";


    public static MongoDao instance(String collectionName) {
        if (mongoDao == null) {
            MongoConfig mongoConfig = new MongoConfig(host, port, userName, password,
                    databaseName, "douban_movie_top_250", 50, 50, 500);
            mongoDao = new MongoDao(mongoConfig.getCollection(collectionName));
        }
        return mongoDao;
    }
}
