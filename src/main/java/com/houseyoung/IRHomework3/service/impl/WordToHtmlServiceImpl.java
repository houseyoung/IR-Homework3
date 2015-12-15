package com.houseyoung.IRHomework3.service.impl;

import com.houseyoung.IRHomework3.service.IndexService;
import com.houseyoung.IRHomework3.service.WordToHtmlService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by houseyoung on 15/12/15 17:37.
 */
@Service("wordToHtmlService")
public class WordToHtmlServiceImpl implements WordToHtmlService {
    @Override
    public String wordToHtmlContent(String docName) throws Exception {
        //获取classpath的路径
        String classpath = IndexService.class.getClassLoader().getResource("/").toString();
        //若为Windows系统，则删去前6位
        if (classpath.substring(8) == ":"){
            classpath = classpath.substring(6, classpath.length());
        }
        //否则删除前5位
        else {
            classpath = classpath.substring(5, classpath.length());
        }

        //设置文档文件夹路径
        String docPath = classpath.substring(0, classpath.length() - 16) + "resources/Doc/";

        //获取Word文档的内容
        InputStream is = new FileInputStream(docPath + docName);
        WordExtractor extractor = new WordExtractor(is);
        String wordContent = extractor.getText();

        //获取文档中字符的总数
        int length = wordContent.length();

        //存储输出至HTML页中的内容的String
        String htmlContent = "";

        //按字符读取文档内容，将换行符转换为HTML格式
        for (int i = 0; i < length; i++) {
            char c = wordContent.charAt(i);

            //判断是否为第一个字符
            if (i == 0) {
                htmlContent = "<p/>";
            }

            //判断是否为回车
            if (c == 13) {
                htmlContent += "<p/>";
            }

            //判断是否为软换行符
            if (c == '\u000B') {
                htmlContent += "<p/>";
            }

            //判断是否为空格
            if (c == 160 || c == 32) {
                htmlContent += "";
            }

            else {
                htmlContent += c;
            }
        }

        return htmlContent;
    }
}
