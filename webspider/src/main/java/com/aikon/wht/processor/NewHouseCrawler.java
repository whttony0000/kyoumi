package com.aikon.wht.processor;

import com.aikon.wht.mongo.MongoDao;
import com.aikon.wht.mongo.MongoService;
import com.aikon.wht.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jsoup爬虫.
 *
 * @author haitao.wang
 */
public class NewHouseCrawler {


    public static void main(String[] args) throws IOException {
        String url = "http://sh.fang.lianjia.com";
        // 获取页面dom.
        Document doc = Jsoup.connect("http://sh.fang.lianjia.com/loupan/search?utm_source=shlianjia&utm_medium=referral&utm_campaign=search").get();
        // 解析列表页，获取详情页url.
        Elements eles = doc.select("#nameShow");
        if (CollectionUtils.isEmpty(eles)) {
            return;
        }
        Set<String> dataCodes = Sets.newLinkedHashSet();
        for (Element ele : eles) {
            String dataCode = ele.attr("href");
            if (StringUtils.isBlank(dataCode)) {
                continue;
            }
            dataCodes.add(dataCode);
        }

        List<Map<String,Object>> list2Return = Lists.newArrayList();
        for (String dataCode : dataCodes) {
            String propertyUrl = url + dataCode;
            Map<String, Object> baseInfoMap = Maps.newHashMap();
            // 获取详情页dom.
            Document propertyDoc = Jsoup.connect(propertyUrl).get();
            // 解析详情页
            baseInfoMap.put("name", propertyDoc.select(".nameShow").get(0).text());
            Elements alias = propertyDoc.getElementsByClass("alias-row");
            if (CollectionUtils.isNotEmpty(alias)) {
                baseInfoMap.put("alias", propertyDoc.getElementsByClass("alias-row").get(0).text());
            }
            Element priceRow = propertyDoc.getElementsByClass("price-row").get(0);
            baseInfoMap.put("price",priceRow.child(0).text());
            list2Return.add(baseInfoMap);
        }
        String json2Return = JsonUtil.writeValueAsJson(list2Return);
        System.out.println(json2Return);

        // 信息入库.
        MongoDao mongoDao = MongoService.instance("lianjiaNewHouse");
        mongoDao.insertList(list2Return);

    }

}
