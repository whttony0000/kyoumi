package com.aikon.wht.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.FactoryBean;

/**
 * mongodatabase bean factory.
 *
 * @author haitao.wang
 */
public class MongoDatabaseFactoryBean implements FactoryBean<MongoDatabase>{

    private String host;

    private String port;

    private String userName;

    private String password;

    private String databaseName;

    private String collectionName;

    private int connectionsPerHost;

    private int threadsAllowedToBlockForConnectionMultiplier;

    private int maxWaitTime;

    private MongoDatabase mongoDatabase;


    public MongoDatabaseFactoryBean(String host, String port, String userName, String password, String databaseName,
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
        String uri = String.format("mongodb://%s:%s@%s:%s/%s", userName, password, host, port, databaseName);
        MongoClientURI mongoClientURI = new MongoClientURI(uri,this.getMongoClientOptionsBuilder());
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        this.mongoDatabase = mongoClient.getDatabase(this.databaseName);
    }

    public MongoClientOptions.Builder getMongoClientOptionsBuilder() {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(connectionsPerHost);
        builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        builder.maxWaitTime(maxWaitTime);
        return builder;
    }

    @Override
    public MongoDatabase getObject() throws Exception {
        return this.mongoDatabase;
    }

    @Override
    public Class<?> getObjectType() {
        return MongoDatabase.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

