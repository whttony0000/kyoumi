package com.aikon.wht.mongo;

import static com.aikon.wht.utils.JsonUtil.writeValueAsJson;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.BsonDocument;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author haitao.wang
 */
public class MongoDao {

    private MongoCollection collection;

    public MongoDao(MongoCollection collection) {
        this.collection = collection;
    }

    /**
     * 插入.
     *
     * @param json
     */
    public void insertJson(String json) {
        this.collection.insertOne(Document.parse(json));
    }

    /**
     * 插入.
     *
     * @param map
     */
    public void insertMap(Map<String, Object> map) {
        Document doc = new Document(map);
        this.collection.insertOne(doc);
    }

    /**
     * 插入.
     *
     * @param list
     */
    public void insertList(List list) {
        List<Document> list2Return = (List<Document>) list.stream().map(item -> Document.parse(writeValueAsJson(item))).collect(Collectors.toList());
        this.collection.insertMany(list2Return);
    }

    /**
     * 查询.
     *
     * @return FindIterable<Document>
     */
    public FindIterable<Document> find() {
        return this.collection.find();
    }

    /**
     * 查询.
     *
     * @param filter
     * @return
     */
    public FindIterable<Document> find(Bson filter) {
        return this.collection.find(filter);
    }

    /**
     * 条件查询.
     *
     * @param key
     * @param pattern
     * @return
     */
    public FindIterable<Document> find(String key, String pattern) {
        return this.collection.find(new BsonDocument(key, new BsonRegularExpression(pattern)));
    }

    /**
     * 分页条件查询.
     *
     * @param key
     * @param pattern
     * @param currentPage
     * @param pageSize
     * @return
     */
    public FindIterable<Document> find(String key, String pattern, Integer currentPage, Integer pageSize) {
        Integer skip = (currentPage - 1) * pageSize;
        return this.collection.find(new BsonDocument(key, new BsonRegularExpression(pattern))).skip(skip).limit(pageSize);
    }

    /**
     * 条件查询.
     *
     * @param key
     * @param pattern
     * @return
     */
    public List<String> findJsonList(String key, String pattern) {
        return findIterable2List(this.find(key, pattern));
    }

    /**
     * 条件查询.
     *
     * @param filter
     * @return
     */
    public List<String> findJsonList(Bson filter) {
        return findIterable2List(this.find(filter));
    }

    /**
     * 分页条件查询.
     *
     * @param filter
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<String> findJsonList(BsonDocument filter, Integer currentPage, Integer pageSize) {
        Integer skip = (currentPage - 1) * pageSize;
        return findIterable2List(this.find(filter).skip(skip).limit(pageSize));
    }

    /**
     * count.
     *
     * @param filter
     * @return
     */
    public long count(BsonDocument filter) {
        return this.collection.count(filter);
    }


    /**
     * count.
     *
     * @param key
     * @param value
     * @return long
     */
    public long count(String key, Object value) {
        Document doc = new Document();
        doc.put(key, value);
        return this.collection.count(doc);

    }

    /**
     * 更新.
     *
     * @param oKey
     * @param oValue
     * @param nKey
     * @param nValue
     */
    public void updateMany(String oKey, Object oValue, String nKey, Object nValue) {
        UpdateOptions uo = new UpdateOptions();
        uo.upsert(true);// update or insert
        this.collection.updateMany(Filters.eq(oKey, oValue), new Document("$set", new Document(nKey, nValue)), uo);
    }

    /**
     * 删除.
     */
    public void clear() {
        this.collection.deleteMany(new BsonDocument());
    }

    /**
     * 删除.
     *
     * @param oKey
     * @param oValue
     */
    public void deleteMany(String oKey, Object oValue) {
        this.collection.deleteMany(Filters.eq(oKey, oValue));
    }


    /**
     * replace.
     *
     * @param oKey
     * @param oValue
     * @param nKey
     * @param nValue
     */
    public void replace(String oKey, Object oValue, String nKey, Object nValue) {
        this.collection.replaceOne(Filters.or(Filters.eq(oKey, oValue)), new Document(nKey, nValue));
    }

    /**
     * FindIterable => List
     *
     * @param findIterable
     * @return
     */
    public static List<String> findIterable2List(FindIterable<Document> findIterable) {
        Iterator iterator = findIterable.iterator();
        List<String> list2Return = Lists.newArrayList();
        while (iterator.hasNext()) {
            Document doc = (Document) iterator.next();
            String docJson = doc.toJson();
            list2Return.add(docJson);
        }
        return list2Return;
    }


}
