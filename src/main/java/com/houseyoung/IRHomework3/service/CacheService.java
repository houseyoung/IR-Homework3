package com.houseyoung.IRHomework3.service;

import java.io.IOException;

/**
 * Created by houseyoung on 15/12/7 22:21.
 */
public interface CacheService {
    /**
     * 获得文档快照内容
     * @param docName
     * @return
     * @throws IOException
     */
    public String getCacheContent(String docName) throws Exception;
}
