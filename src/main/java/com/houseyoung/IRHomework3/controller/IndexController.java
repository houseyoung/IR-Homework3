package com.houseyoung.IRHomework3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * IndexController
 *
 * @author: yangch
 * @time: 2015/12/2 11:35
 */
@Controller
@RequestMapping(value = "")
public class IndexController {
    @RequestMapping(value = {"", "/", "index"}, method = RequestMethod.GET)
    public String toIndex(Model model) throws Exception{
        try {
            model.addAttribute("msg", "JSP Test!");
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }
}