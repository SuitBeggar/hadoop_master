package kmeans.document;


import kmeans.document.api.Encoder;
import kmeans.document.impl.CosineDistance;
import kmeans.document.impl.KMeansImpl;
import kmeans.document.impl.TfidfEncoder;
import kmeans.document.rep.Cluster;
import kmeans.document.rep.ClusterList;
import kmeans.document.rep.Distance;
import kmeans.document.rep.DocumentList;

/**
 * Main class to run k-means algorithm
 * @author hazoom
 *
 */
public class Main {

	public static void main(String[] args) {	
		DocumentList documents = new DocumentList(args[0]);
		
		System.out.println("Finish preprocessing...");

		//词汇量  30000(预估，然后把所有词取hash取值，进行向量化)
		Encoder encoder = new TfidfEncoder(30000);
		encoder.encode(documents);

		System.out.println("Finish encoding...");
		
		Distance distancce = new CosineDistance();
		
		KMeansImpl kmeans = new KMeansImpl(distancce, 8, 10);
		ClusterList clusters = kmeans.cluster(documents);
		
		System.out.println("Finish K-means algorithm...");
		
		int i = 1;
		for (Cluster cluster : clusters.getClusters()) {
			System.out.println("Cluster no. " + i + " has " + cluster.getDocuments().size() + " documents.");
			i++;
		}
	}

}
