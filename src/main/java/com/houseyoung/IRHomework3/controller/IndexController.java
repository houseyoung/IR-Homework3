package com.houseyoung.IRHomework3.controller;

import com.houseyoung.IRHomework3.entity.DocEntity;
import com.houseyoung.IRHomework3.service.SearchService;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String search(@RequestParam("queryWord") String queryWord, Model model) throws Exception{
        try {
            List<DocEntity> docList = searchService.search(queryWord);

            model.addAttribute("docList", docList);
            return "search";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "search";
        }
    }
}