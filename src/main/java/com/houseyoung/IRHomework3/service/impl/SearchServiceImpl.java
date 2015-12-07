package com.houseyoung.IRHomework3.service.impl;

import com.houseyoung.IRHomework3.entity.DocEntity;
import com.houseyoung.IRHomework3.service.SearchService;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SearchService
 *
 * @author: yangch
 * @time: 2015/12/7 16:04
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    @Override
//    @PostConstruct
    public List<DocEntity> search(String queryWord) throws IOException, ParseException {
        //获取classpath的路径
        String classpath = SearchService.class.getClassLoader().getResource("/").toString();
        classpath = classpath.substring(6, classpath.length());

        //设置索引文件存放位置
        String indexPath = classpath + "index/";

        Directory dir = new SimpleFSDirectory(new File(indexPath));

        //创建IndexSearcher对象，相比IndexWriter对象，这个参数就要提供一个索引的目录就行了
        IndexReader indexReader = IndexReader.open(dir);
        IndexSearcher indexSearch = new IndexSearcher(indexReader);

        //配置Analyzer，使用Paoding
        Analyzer analyzer = new PaodingAnalyzer();

        //创建QueryParser对象,第一个参数表示Lucene的版本,第二个表示搜索Field的字段,第三个表示搜索使用分词器
        QueryParser queryParser = new QueryParser(Version.LUCENE_46, "contents", analyzer);

        //生成Query对象
        Query query = queryParser.parse(queryWord);

        //搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
        TopDocs hits = indexSearch.search(query, 100);

        List<DocEntity> docList = new ArrayList<DocEntity>();

        //循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值，存入DocEntity实体
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc sdoc = hits.scoreDocs[i];
            Document doc = indexSearch.doc(sdoc.doc);

            DocEntity docEntity = new DocEntity();
            docEntity.setDocName(doc.get("filename"));
            docEntity.setDocContent(doc.get("contents"));

            docList.add(docEntity);
        }

        return docList;
    }
}
