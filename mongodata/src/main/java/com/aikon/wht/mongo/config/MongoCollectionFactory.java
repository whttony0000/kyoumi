package com.aikon.wht.mongo.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * mongocolletion工厂.
 *
 * @author haitao.wang
 */
public class MongoCollectionFactory {

    MongoDatabase mongoDatabase;

    public MongoCollectionFactory(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public MongoCollection getCollection(String collectionName) {
        return this.mongoDatabase.getCollection(collectionName);
    }

}

