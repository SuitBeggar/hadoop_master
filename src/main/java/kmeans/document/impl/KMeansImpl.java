package kmeans.document.impl;

import kmeans.document.rep.*;

import java.util.Random;


public class KMeansImpl {

	private int k; //列簇数量
	private int iterations; //迭代次数
	private Distance distance;
	
	public KMeansImpl(Distance distance, int k, int iterations) {
		this.iterations = iterations;
		this.k = k;
		this.distance = distance;
	}
	
	public ClusterList cluster(DocumentList documents) {
		ClusterList clusters = new ClusterList(this.k);
		
		// 用随机k个样本初始化k个簇
		Random random = new Random();
		for (int i = 0; i < k; i++) {
			int rand = random.nextInt(documents.getDocuments().size());
			Cluster cluster = new Cluster(documents.getDocuments().get(rand));
			clusters.add(cluster);
		}
		
		// 迭代次数的Run k-means算法
		for (int i = 0; i < this.iterations; i++) {
			for (Document document : documents.getDocuments()) {
				if (!document.isAssignToCluster()) {
					Cluster nearestCluster = clusters.findNearestCluster(this.distance, document);
					nearestCluster.add(document);
				}
			}
			
			clusters.updateControids();
			
			// 如果不是最后一次迭代
			if (i < this.iterations - 1) {
				
				// 清除数据
				clusters.clearClusters();
				
				// 重新指定文档集合
				for (Document document : documents.getDocuments()) {
					document.setAssignToCluster(false);
				}
			}
		}
		
		return clusters;
	}
}
