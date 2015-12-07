package com.houseyoung.IRHomework3.controller;

import java.io.File;
import java.io.IOException;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 * LuceneSearch
 *
 * @author: yangch
 * @time: 2015/12/7 15:32
 */
public class LuceneSearch {
    public static void main(String[] args) throws IOException, ParseException {
        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置索引文件存放位置
        String indexPath = resourcesPath + "index/";

        Directory dir = new SimpleFSDirectory(new File(indexPath));

        //配置Analyzer，使用Paoding
        Analyzer analyzer=new PaodingAnalyzer();

        //创建IndexSearcher对象，相比IndexWriter对象，这个参数就要提供一个索引的目录就行了
        IndexReader indexReader = IndexReader.open(dir);
        IndexSearcher indexSearch = new IndexSearcher(indexReader);

        //创建QueryParser对象,第一个参数表示Lucene的版本,第二个表示搜索Field的字段,第三个表示搜索使用分词器
        QueryParser queryParser = new QueryParser(Version.LUCENE_46, "contents", analyzer);

        //生成Query对象
        Query query = queryParser.parse("我国");

        //搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
        TopDocs hits = indexSearch.search(query, 100);

        //hits.totalHits表示一共搜到多少个
        System.out.println("找到了"+hits.totalHits+"个");

        //循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc sdoc = hits.scoreDocs[i];
            Document doc = indexSearch.doc(sdoc.doc);
            System.out.println(doc.get("filename"));
//            System.out.println(doc.get("contents"));
        }
    }
}
