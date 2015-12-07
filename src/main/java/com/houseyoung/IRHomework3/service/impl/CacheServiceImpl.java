package com.houseyoung.IRHomework3.service.impl;

import com.houseyoung.IRHomework3.service.CacheService;
import com.houseyoung.IRHomework3.service.IndexService;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by houseyoung on 15/12/7 22:23.
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {
    @Override
    public String getCacheContent(String docName) throws Exception {
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

        //获取Word文档
        File file = new File(docPath + docName);

        //读取Word文档中的内容
        InputStream in = new FileInputStream(file);
        WordExtractor extractor = new WordExtractor(in);
        String cacheContent = "<p>" + extractor.getText();

        //将换行符转换为HTML的换行符
        cacheContent = cacheContent.replaceAll("\r\n", "</p> <p>");

        return cacheContent;
    }
}
