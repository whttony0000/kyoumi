package com.aikon.wht.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author haitao.wang
 */
public class SqlSessionFactoryUtil {

    private static Logger logger = Logger.getLogger(SqlSessionFactoryUtil.class);

    private SqlSessionFactoryUtil(){

    }

    public static SqlSessionFactory sqlSessionFactory;

    public static SqlSession sqlSession;

    public static SqlSessionFactory  getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                InputStream inputStream = Resources.getResourceAsStream("spring/mybatisConfig.xml");
                SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                sqlSessionFactory = builder.build(inputStream);
            } catch (IOException e) {
                logger.info("Error Loading Resource:{}",e);
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession() {
        if (sqlSession == null) {
            sqlSession = getSqlSessionFactory().openSession();
        }
        return sqlSession;
    }


}
