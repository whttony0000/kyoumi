package com.aikon.wht.processor;

import com.aikon.wht.mongo.MongoDao;
import com.aikon.wht.mongo.MongoService;
import com.aikon.wht.pipeline.DoubanMovieTop250Pipeline;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;

/**
 * 抓取豆瓣电影top250.
 *
 * @author haitao.wang
 */
public class DoubanMovieTop250PageProcessor implements PageProcessor {

    static Logger logger = Logger.getLogger(DoubanMovieTop250PageProcessor.class.getName());


    /**
     * Page代表了从Downloader下载到的一个页面——可能是HTML，也可能是JSON或者其他文本格式的内容.
     * 对于下载到的Html页面使用XPath、正则表达式和CSS选择器找到我们想要的元素.
     *
     * @param page
     */
    @Override
    public void process(Page page) {
        // 排名.
        page.putField("ranking",page.getHtml().xpath("//div[@class='item']//div[@class='pic']//em/text()").all());
        // 电影名.
        page.putField("name", page.getHtml().xpath("//div[@class='item']//div[@class='hd']//a//span/text()").all());
        // 电影导演演员等.
        page.putField("member", page.getHtml().xpath("//div[@class='item']//div[@class='bd']//p[1]/text()").all());
        // 评分.
        page.putField("rating", page.getHtml().xpath("//div[@class='item']//span[@class='rating_num']/text()").all());
        // 评分人数.
        page.putField("raterCnt", page.getHtml().xpath("//div[@class='item']//div[@class='star']//span[4]/text()").all());
        // 电影图片.
        page.putField("img",page.getHtml().xpath("//div[@class='item']//div[@class='pic']//img/@src").all());
    }

    /**
     * 设置代理，重试次数等.
     *
     * @return Site
     */
    @Override
    public Site getSite() {
        return Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
                .setRetryTimes(3).setSleepTime(1000);
    }

    /**
     * 入口.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // 配置日志.
        PropertyConfigurator.configure("log4j.properties");

        // 清空库.
        MongoDao dao = MongoService.instance("douban_movie_top_250");
        dao.clear();
        // Spider是WebMagic内部流程的核心。Downloader、PageProcessor、Scheduler、Pipeline都是Spider的一个属性.
        // 这些属性是可以自由设置的，通过设置这个属性可以实现不同的功能.
        Spider spider = Spider.create(new DoubanMovieTop250PageProcessor());
        // 手动分页.
        for (int i = 0; i < 10; i++) {
            spider.addUrl("https://movie.douban.com/top250?start=" + i * 25 + "&filter=");
        }
        // 配置pipeline.
        spider.addPipeline(new DoubanMovieTop250Pipeline())
        .run();
    }
}
