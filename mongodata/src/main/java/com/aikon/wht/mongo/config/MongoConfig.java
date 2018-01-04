package com.aikon.wht.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mongo配置.
 *
 * @author haitao.wang
 */
public class MongoConfig {

    private static Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    private String host;

    private String port;

    private String userName;

    private String password;

    private String databaseName;

    private String collectionName;

    private int connectionsPerHost;

    private int threadsAllowedToBlockForConnectionMultiplier;

    private int maxWaitTime;

    private MongoClient mongoClient;

    private static MongoConfig instance;

    private MongoClientOptions.Builder clientOptionsBuilder;

    public synchronized static MongoConfig instance(String host, String port, String userName, String password,
                                                    String databaseName, String collectionName, int connectionsPerHost,
                                                    int threadsAllowedToBlockForConnectionMultiplier, int maxWaitTime) {
        if (instance == null) {
            instance = new MongoConfig(host, port, userName, password, databaseName, collectionName, connectionsPerHost,
                    threadsAllowedToBlockForConnectionMultiplier, maxWaitTime);
        }
        return instance;
    }

    public MongoConfig(String host, String port, String userName, String password, String databaseName,
                       String collectionName, int connectionsPerHost,
                       int threadsAllowedToBlockForConnectionMultiplier, int maxWaitTime) {
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.connectionsPerHost = connectionsPerHost;
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
        this.maxWaitTime = maxWaitTime;
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(connectionsPerHost);
        builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        builder.maxWaitTime(maxWaitTime);
        this.clientOptionsBuilder = builder;
    }


    public MongoClient getMongoClient() {
        if (this.mongoClient == null) {
            String uri = String.format("mongodb://%s:%s@%s:%s/%s", userName, password, host, port, databaseName);
            MongoClientURI mongoClientURI = new MongoClientURI(uri,this.clientOptionsBuilder);
            this.mongoClient = new MongoClient(mongoClientURI);
            return this.mongoClient;
        }
        return this.mongoClient;
    }

    public static MongoClient getMongoClient(String uri) {
        return new MongoClient(new MongoClientURI(uri));
    }

    public MongoDatabase getDb() {
        return this.getDb(databaseName);
    }

    public MongoDatabase getDb(String databaseName) {
        MongoClient mongoClient = this.getMongoClient();
        return mongoClient.getDatabase(databaseName);
    }

    public MongoCollection getCollection() {
        return this.getDb().getCollection(collectionName);
    }

    public MongoCollection getCollection(String collectionName) {
        return this.getDb().getCollection(collectionName);
    }

}

