package com.aikon.wht.controller.data;

import com.aikon.wht.model.Page;
import com.aikon.wht.model.Response;
import com.aikon.wht.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * @author haitao.wang
 */
@Controller
@RequestMapping("/data")
public class DataController {

    @Autowired
    DataService dataService;

    @RequestMapping("/realTimeBoxOffice")
    @ResponseBody
    public Response<List<String>> getRealTimeBoxOffice() {
        return new Response<>(dataService.getRealTimeBoxOffice());
    }

    @RequestMapping("/getLatestNews")
    @ResponseBody
    public Response<Page<String>> getLatestNews(Integer currentPage, Integer pageSize) {
        return new Response<>(dataService.getLatestNews(currentPage, pageSize));
    }

    @RequestMapping("/getStockTopList")
    @ResponseBody
    public Response<Page<String>> getStockTopList(Integer currentPage, Integer pageSize) {
        return new Response<>(dataService.getStockTopList(currentPage, pageSize));
    }

    @RequestMapping("/getGDPYear")
    @ResponseBody
    public Response<List<String>> getGDPYear() {
        List<String> gdpYear = dataService.getGDPYear();
        Collections.reverse(gdpYear);
        return new Response<>(gdpYear);
    }

    @RequestMapping("/getDoubanMovieTop250")
    @ResponseBody
    public Response<Page<String>> getDoubanMovieTop250(Integer currentPage, Integer pageSize) {
        return new Response<>(dataService.getDoubanMovieTop250(currentPage, pageSize));
    }

    @RequestMapping("/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("inner_page", "data.ftl");
        return "template";
    }
}
