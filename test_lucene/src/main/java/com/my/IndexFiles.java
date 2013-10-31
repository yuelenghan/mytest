package com.my;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexFiles {

	private static final Logger logger = Logger.getLogger(IndexFiles.class);

	// 文件目录
	public static File fileDir = new File("e:/test_lucene/docs");
	// 索引目录
	public static File indexDir = new File("e:/test_lucene/index");

	// 创建分词器(Version.LUCENE_43 为所选用的版本)
	// StandardAnalyzer标准分词器--对英文采用空白,标点符号进行分词。对中文采用单字分词
	// SmartChineseAnalyzer智能中文分词器
	public static Analyzer analyzer = new SmartChineseAnalyzer(
			Version.LUCENE_43);

	public static void main(String[] args) throws Exception {
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		if (!indexDir.exists()) {
			indexDir.mkdirs();
		}

		boolean create = false;

		// Directory: 索引存放地。
		// 文件系统:FSDirectory: FSDirectory.open(File file);
		// 内存RAMDirectory: new RAMDirectory();
		// 经测试，对6000多个文件创建索引，在MergeFactor和MaxBufferedDocs值为1000时，内存比硬盘只快了1秒左右；
		// 为10000时，基本没有区别
		FSDirectory directory = FSDirectory.open(indexDir);
		// RAMDirectory directory = new RAMDirectory();

		// IndexWriter配置
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,
				analyzer);

		if (create) {
			// Create a new index in the directory, removing any
			// previously indexed documents:
			config.setOpenMode(OpenMode.CREATE);
		} else {
			// Add new documents to an existing index:
			config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		// Optional: for better indexing performance, if you
		// are indexing many documents, increase the RAM
		// buffer. But if you do this, increase the max heap
		// size to the JVM (eg add -Xmx512m or -Xmx1g):
		//
		// config.setRAMBufferSizeMB(256.0);

		IndexWriter indexWriter = new IndexWriter(directory, config);

		// 得到文件目录下的所有文件
		List<File> fileList = new ArrayList<File>();
		fileList = FileUtil.getAllFiles(fileDir, fileList);

		String temp = "";

		for (int i = 0; i < fileList.size(); i++) {
			File file = fileList.get(i);

			if (file.isFile() && file.getName().endsWith(".txt")) {
				temp = FileUtil.FileReaderAll(file.getCanonicalPath());
			}

			if (file.isFile() && file.getName().endsWith(".doc")) {
				temp = FileUtil.Word_03_Reader(file.getCanonicalPath());
			}

			if (file.isFile() && file.getName().endsWith(".docx")) {
				temp = FileUtil.Word_07_Reader(file.getCanonicalPath());
			}

			if (file.isFile() && file.getName().endsWith(".xls")) {
				temp = FileUtil.Excel_03_Reader(file.getCanonicalPath());
			}

			if (file.isFile() && file.getName().endsWith(".xlsx")) {
				temp = FileUtil.Excel_07_Reader(file.getCanonicalPath());
			}

			logger.info(temp);

			// 索引的组成单元. 一组Field的集合
			Document document = new Document();

			// Add the path of the file as a field named "path". Use a
			// field that is indexed (i.e. searchable), but don't
			// tokenize
			// the field into separate words and don't index term
			// frequency
			// or positional information:
			document.add(new StringField("path", file.getPath(),
					Field.Store.YES));

			// Add the last modified date of the file a field named
			// "modified".
			// Use a LongField that is indexed (i.e. efficiently
			// filterable with
			// NumericRangeFilter). This indexes to milli-second
			// resolution, which
			// is often too fine. You could instead create a number
			// based on
			// year/month/day/hour/minutes/seconds, down the resolution
			// you require.
			// For example the long value 2011021714 would mean
			// February 17, 2011, 2-3 PM.
			document.add(new LongField("modified", file.lastModified(),
					Field.Store.YES));

			// Add the contents of the file to a field named "contents".
			// Specify a Reader,
			// so that the text of the file is tokenized and indexed,
			// but not stored.
			// Note that FileReader expects the file to be in UTF-8
			// encoding.
			// If that's not the case searching for special characters
			// will fail.
			document.add(new TextField("contents", temp, Field.Store.YES));

			// 把指定文档添加到索引写出器中
			if (create) {
				// New index, so we just add the document (no old
				// document can be there):
				logger.info("adding " + file);
				indexWriter.addDocument(document);
			} else {
				// Existing index (an old copy of this document may have
				// been indexed) so
				// we use updateDocument instead to replace the old one
				// matching the exact
				// path, if present:
				logger.info("updating " + file);
				indexWriter.updateDocument(new Term("path", file.getPath()),
						document);
			}
		}

		// NOTE: if you want to maximize search performance,
		// you can optionally call forceMerge here. This can be
		// a terribly costly operation, so generally it's only
		// worth it when your index is relatively static (ie
		// you're done adding documents to it):
		//
		// indexWriter.forceMerge(1);

		// 关闭索引写出器,此时才把索引写到目标存储地
		indexWriter.close();

	}
}
