package com.my;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchFiles {

	private static final Logger logger = Logger.getLogger(SearchFiles.class);

	public static void main(String[] args) throws Exception {
		IndexReader reader = DirectoryReader.open(FSDirectory
				.open(IndexFiles.indexDir));
		IndexSearcher searcher = new IndexSearcher(reader);

		String field = "contents";

		QueryParser parser = new QueryParser(Version.LUCENE_43, field,
				IndexFiles.analyzer);

		Query query = parser.parse("香蕉");
		logger.info("Searching for:" + query.toString(field));

		TopDocs results = searcher.search(query, 10);

		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;

		if (numTotalHits > 0) {
			logger.info("共找到:" + numTotalHits + "个结果!");
		} else {
			logger.info("没有搜索到任何结果!");
		}

		for (int i = 0; i < hits.length; i++) {
			Document doc = searcher.doc(hits[i].doc);

			String path = doc.get("path");
			logger.info("path : " + path);

			String contents = doc.get("contents");
			logger.info("contents : " + contents);

			Number modified = doc.getField("modified").numericValue();
			logger.info("modified : " + modified);
		}

		reader.close();
	}

}
