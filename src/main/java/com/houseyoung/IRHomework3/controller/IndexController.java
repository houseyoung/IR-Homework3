package com.houseyoung.IRHomework3.controller;

import com.houseyoung.IRHomework3.entity.DocEntity;
import com.houseyoung.IRHomework3.service.CacheService;
import com.houseyoung.IRHomework3.service.SearchService;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * IndexController
 *
 * @author: yangch
 * @time: 2015/12/2 11:35
 */
@Controller
@RequestMapping(value = "")
public class IndexController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = {"", "/", "index"}, method = RequestMethod.GET)
    public String toIndex(Model model) throws Exception{
        try {
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @RequestMapping(value = {"search"}, method = RequestMethod.POST)
    public String search(String queryWord, Model model) throws Exception{
        try {
            //计时器开始
            long startTime = System.currentTimeMillis();

            //搜索
            List<DocEntity> docList = searchService.search(queryWord);

            //计时器结束
            long endTime = System.currentTimeMillis();

            //输出关键词
            model.addAttribute("queryWord", queryWord);
            //输出结果数量
            model.addAttribute("num", docList.size());
            //输出搜索结果
            model.addAttribute("docList", docList);
            //输出耗时
            model.addAttribute("time", (endTime - startTime));
            return "search";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "search";
        }
    }

    @RequestMapping(value = {"cache"}, method = RequestMethod.GET)
    public String toCache(@RequestParam("docName") String docName, Model model) throws Exception{
        try {
            String cacheContent = cacheService.getCacheContent(docName);

            //输出文档名
            model.addAttribute("docName", docName);
            //输出文档快照
            model.addAttribute("cacheContent", cacheContent);
            return "cache";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "cache";
        }
    }
}