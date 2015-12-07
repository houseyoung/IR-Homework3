package com.houseyoung.IRHomework3.service;

import com.houseyoung.IRHomework3.entity.DocEntity;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import java.io.IOException;
import java.util.List;

/**
 * SearchService
 *
 * @author: yangch
 * @time: 2015/12/7 16:04
 */
public interface SearchService {
    /**
     * 按关键词进行搜索
     * @param queryWord
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public List<DocEntity> search(String queryWord) throws IOException, ParseException, InvalidTokenOffsetsException;
}
