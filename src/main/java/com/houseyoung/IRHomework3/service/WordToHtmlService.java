package com.houseyoung.IRHomework3.service;

import java.io.IOException;

/**
 * Created by houseyoung on 15/12/15 17:36.
 */
public interface WordToHtmlService {
    /**
     * 将Word文档中的内容转换为符合HTML格式的String
     * @param docName
     * @return
     * @throws IOException
     */
    public String wordToHtmlContent(String docName) throws Exception;
}
