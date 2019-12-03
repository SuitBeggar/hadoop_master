package kmeans.document.rep;

/**
 * �������
 * @author hazoom
 *
 */
public abstract class Distance {

	/**
	 * �������
	 * @param document
	 * @param cluster
	 * @return
	 */
	public double calcDistance(Document document, Cluster cluster) {
		
		return this.calcDistance(document.getTfidf(), cluster.getCentroid(),
				document.getNorm(), cluster.getControidNorm());
	}
	
	public abstract double calcDistance(WordsVector vector1, WordsVector wv2, double norm1, double norm2);
}
