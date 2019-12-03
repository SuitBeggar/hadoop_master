package kmeans.document.impl;


import kmeans.document.api.Encoder;
import kmeans.document.rep.Document;
import kmeans.document.rep.DocumentList;
import kmeans.document.rep.WordsVector;

public class TfidfEncoder implements Encoder {

	private int vocabularySize;//词汇数量
	private WordsVector idf;
	
	public TfidfEncoder(int vocabularySize) {
		this.vocabularySize = vocabularySize;
	}

	public void encode(DocumentList documents) {
		
		this.calcHistogram(documents);
		this.calcIDF(documents);
		
		for (Document document : documents.getDocuments()) {
			this.encode(document);
		}
	}
	
	/**
	 * 文章向量化
	 * @param document
	 */
	private void calcHistogram(Document document) {
		
		WordsVector hist = new WordsVector(this.vocabularySize);
		
		String[] tokens = document.getTokens();
		for (String token : tokens) {
			int hash = Math.abs(token.hashCode()) % this.vocabularySize;
			hist.increment(hash);
		}
		
		document.setHistogram(hist);
	}
	
	/**
	 * 计算给定文档列表的单词的向量
	 * @param documents
	 */
	private void calcHistogram(DocumentList documents) {
		for (Document document : documents.getDocuments()) {
			this.calcHistogram(document);
		}
	}
	
	/**
	 * 计算语料库中文档的逆idf
	 * @param documents
	 */
	private void calcIDF(DocumentList documents) {
		
		this.idf = new WordsVector(this.vocabularySize);
				
		for (Document document : documents.getDocuments()) {
			for (int i = 0; i < this.vocabularySize; i++) {
				if (document.getHistogram().get(i) > 0) {
					
					//增加在整个文档中看到该词的次数
					this.idf.increment(i);
				}
			}
		}
		
		//乘以文档数
		this.idf.multiply(documents.getDocuments().size());
		
		//记录阵列
		this.idf.log();
	}
	
	/**
	 * 对给定文档的tf idf分数进行编码
	 * @param document
	 */
	private void encode(Document document) {
		
		WordsVector tfidf = document.getHistogram().clone();
		
		// 不在需要词向量
		document.setHistogram(null);
		
		// 除以向量的最大值（标准化）
		tfidf.divide(tfidf.max());
		
		// tf和idf的积（tfs-idf）
		tfidf.multiply(this.idf);
		
		// 设置改文章的tfidf和欧氏范数
		document.setTfidf(tfidf);
		document.setNorm(tfidf.norm());
	}

}
