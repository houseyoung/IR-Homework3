package com.houseyoung.IRHomework3.controller;

import com.houseyoung.IRHomework3.entity.DocEntity;
import com.houseyoung.IRHomework3.service.SearchService;
import com.houseyoung.IRHomework3.service.WordToHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    private WordToHtmlService wordToHtmlService;

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

    @RequestMapping(value = {"content"}, method = RequestMethod.GET)
    public String toContent(@RequestParam("docName") String docName,
                            @RequestParam(required = false) String queryWord,
                            Model model) throws Exception{
        try {
            //获取文档内容
            String content = wordToHtmlService.wordToHtmlContent(docName);

            //获取文档编号
            int docNumber = Integer.parseInt(docName.substring(3, docName.length() - 4));

            //获取上一个、下一个文档名
            String preDocName = "";
            String nextDocName = "";
            if (docNumber == 1) {
                preDocName = "Doc30.doc";
                nextDocName = "Doc" + (docNumber + 1) + ".doc";
            }
            else if (docNumber == 30) {
                preDocName = "Doc" + (docNumber - 1) + ".doc";
                nextDocName = "Doc1.doc";
            }
            else {
                preDocName = "Doc" + (docNumber - 1) + ".doc";
                nextDocName = "Doc" + (docNumber + 1) + ".doc";
            }

            //输出关键词
            model.addAttribute("queryWord", queryWord);
            //输出本文档名
            model.addAttribute("docName", docName);
            //输出上一个文档名
            model.addAttribute("preDocName", preDocName);
            //输出下一个文档名
            model.addAttribute("nextDocName", nextDocName);
            //输出文档快照
            model.addAttribute("content", content);
            return "content";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "content";
        }
    }
}