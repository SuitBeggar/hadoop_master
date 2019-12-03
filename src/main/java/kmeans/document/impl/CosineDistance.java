package kmeans.document.impl;


import kmeans.document.rep.Distance;
import kmeans.document.rep.WordsVector;

/**
 * 计算两个向量之间的余弦距离
 *
 */
public class CosineDistance extends Distance {

	@Override
	public double calcDistance(WordsVector vector1, WordsVector wv2, double norm1, double norm2) {
		
		return 1.0 - (vector1.dotProduct(wv2) / (norm1*norm2));
	}

}
