package com.houseyoung.IRHomework3.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.hwpf.extractor.WordExtractor;
/**
 * LuceneIndex
 *
 * @author: yangch
 * @time: 2015/12/7 15:19
 */
public class LuceneIndex {
    /**
     * 对Doc文件创建索引
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置文档文件夹的路径
        String docPath = resourcesPath + "Doc/";
        //设置索引文件存放位置
        String indexPath = resourcesPath + "index/";

        //创建Directory对象
        Directory dir = new SimpleFSDirectory(new File(indexPath));

        //配置Analyzer，使用Paoding
        Analyzer analyzer=new PaodingAnalyzer();
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(Version.LUCENE_46, analyzer);

        //创建IndexWriter对象
        IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);
        File[] files = new File(docPath).listFiles();

        for (int i = 0; i < files.length; i++) {
            Document doc = new Document();
            InputStream in = new FileInputStream(files[i]);
            WordExtractor w = new WordExtractor(in);
            //创建Field对象，并放入doc对象中
            doc.add(new Field("contents", w.getText(),Field.Store.YES,Field.Index.ANALYZED));
            doc.add(new Field("filename", files[i].getName(),
                    Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("indexDate",DateTools.dateToString(new Date(), DateTools.Resolution.DAY),Field.Store.YES,Field.Index.NOT_ANALYZED));
            //写入IndexWriter
            indexWriter.addDocument(doc);
        }

        //查看IndexWriter里面有多少个索引
        System.out.println("numDocs"+indexWriter.numDocs());
        indexWriter.close();
    }
}
