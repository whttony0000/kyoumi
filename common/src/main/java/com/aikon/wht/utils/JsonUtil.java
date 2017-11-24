package com.aikon.wht.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Created by haitao.wang on 2016/11/29.
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static JsonFactory jsonFactory = new JsonFactory();

    public static <T> String writeValueAsJson(T t) {

        Writer writer = new StringWriter(300);
        try {

            JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);
            objectMapper.writeValue(jsonGenerator, t);
        } catch (Exception e) {
            log.error("Error Writing Value As Json {}", e);
        }
        return writer.toString();
    }

    public static <T> T readValueFromJson(String json, Class<T> type) {
        Reader reader = new StringReader(json);
        T t = null;
        try {
            JsonParser jsonParser = jsonFactory.createParser(json);
            t = objectMapper.readValue(jsonParser, type);
        } catch (Exception e) {
            log.error("Error Reading Value From Json {}", e);
        }
        return t;
    }


}
