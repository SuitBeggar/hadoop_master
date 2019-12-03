package kmeans.document.impl;

import kmeans.document.rep.*;

import java.util.Random;


public class KMeansImpl {

	private int k; //�д�����
	private int iterations; //��������
	private Distance distance;
	
	public KMeansImpl(Distance distance, int k, int iterations) {
		this.iterations = iterations;
		this.k = k;
		this.distance = distance;
	}
	
	public ClusterList cluster(DocumentList documents) {
		ClusterList clusters = new ClusterList(this.k);
		
		// �����k��������ʼ��k����
		Random random = new Random();
		for (int i = 0; i < k; i++) {
			int rand = random.nextInt(documents.getDocuments().size());
			Cluster cluster = new Cluster(documents.getDocuments().get(rand));
			clusters.add(cluster);
		}
		
		// ����������Run k-means�㷨
		for (int i = 0; i < this.iterations; i++) {
			for (Document document : documents.getDocuments()) {
				if (!document.isAssignToCluster()) {
					Cluster nearestCluster = clusters.findNearestCluster(this.distance, document);
					nearestCluster.add(document);
				}
			}
			
			clusters.updateControids();
			
			// ����������һ�ε���
			if (i < this.iterations - 1) {
				
				// �������
				clusters.clearClusters();
				
				// ����ָ���ĵ�����
				for (Document document : documents.getDocuments()) {
					document.setAssignToCluster(false);
				}
			}
		}
		
		return clusters;
	}
}
