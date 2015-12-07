package com.houseyoung.IRHomework3.service.impl;

import com.houseyoung.IRHomework3.entity.DocEntity;
import com.houseyoung.IRHomework3.service.IndexService;
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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by houseyoung on 15/12/7 18:54.
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {
    @Override
    @PostConstruct  //运行时自动执行此方法
    public void createIndex() throws IOException {
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

        //设置索引文件存放路径
        String indexPath = classpath + "index/";

        //创建Directory对象
        Directory dir = new SimpleFSDirectory(new File(indexPath));

        //配置Analyzer，使用Paoding
        Analyzer analyzer = new PaodingAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_46, analyzer);

        //创建IndexWriter对象
        IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);

        //清除IndexWriter中原有的内容
        indexWriter.deleteAll();

        //将索引内容写入indexWriter
        File[] files = new File(docPath).listFiles();

        for (int i = 0; i < files.length; i++) {
            //读取Word文档中的内容
            InputStream in = new FileInputStream(files[i]);
            WordExtractor extractor = new WordExtractor(in);

            //创建Field对象，并放入Document对象中
            Document document = new Document();
            document.add(new Field("contents", extractor.getText(), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("filename", files[i].getName(),
                    Field.Store.YES, Field.Index.NOT_ANALYZED));
            document.add(new Field("indexDate", DateTools.dateToString(new Date(), DateTools.Resolution.DAY), Field.Store.YES, Field.Index.NOT_ANALYZED));

            //写入IndexWriter
            indexWriter.addDocument(document);
        }

        indexWriter.close();
    }
}
