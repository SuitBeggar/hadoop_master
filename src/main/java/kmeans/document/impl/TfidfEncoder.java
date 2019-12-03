package kmeans.document.impl;


import kmeans.document.api.Encoder;
import kmeans.document.rep.Document;
import kmeans.document.rep.DocumentList;
import kmeans.document.rep.WordsVector;

public class TfidfEncoder implements Encoder {

	private int vocabularySize;//�ʻ�����
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
	 * ����������
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
	 * ��������ĵ��б�ĵ��ʵ�����
	 * @param documents
	 */
	private void calcHistogram(DocumentList documents) {
		for (Document document : documents.getDocuments()) {
			this.calcHistogram(document);
		}
	}
	
	/**
	 * �������Ͽ����ĵ�����idf
	 * @param documents
	 */
	private void calcIDF(DocumentList documents) {
		
		this.idf = new WordsVector(this.vocabularySize);
				
		for (Document document : documents.getDocuments()) {
			for (int i = 0; i < this.vocabularySize; i++) {
				if (document.getHistogram().get(i) > 0) {
					
					//�����������ĵ��п����ôʵĴ���
					this.idf.increment(i);
				}
			}
		}
		
		//�����ĵ���
		this.idf.multiply(documents.getDocuments().size());
		
		//��¼����
		this.idf.log();
	}
	
	/**
	 * �Ը����ĵ���tf idf�������б���
	 * @param document
	 */
	private void encode(Document document) {
		
		WordsVector tfidf = document.getHistogram().clone();
		
		// ������Ҫ������
		document.setHistogram(null);
		
		// �������������ֵ����׼����
		tfidf.divide(tfidf.max());
		
		// tf��idf�Ļ���tfs-idf��
		tfidf.multiply(this.idf);
		
		// ���ø����µ�tfidf��ŷ�Ϸ���
		document.setTfidf(tfidf);
		document.setNorm(tfidf.norm());
	}

}
