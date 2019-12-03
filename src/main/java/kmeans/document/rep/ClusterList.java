package kmeans.document.rep;

import java.util.ArrayList;
import java.util.List;

/**
 * 簇列集合（所有文章分为几个簇）
 * @author hazoom
 *
 */
public class ClusterList {

	private List<Cluster> clusters;
	

	public ClusterList(int k) {
		this.clusters = new ArrayList<Cluster>(k);
	}
	
	/**
	 * 新添加文章集合到改簇
	 * @param cluster
	 */
	public void add(Cluster cluster) {
		this.clusters.add(cluster);
	}
	
	/**
	 * 更新改簇的质心
	 */
	public void updateControids() {
		for (Cluster cluster : this.clusters) {
			cluster.updateCentroid();
		}
	}

	/**
	 * 在所有簇中查找离给定文档最近的簇
	 * @param distance
	 * @param document
	 * @return
	 */
	public Cluster findNearestCluster(Distance distance, Document document) {
		
		double min_distance = Double.MAX_VALUE;
		Cluster nearestCluster = null;
		
		for (Cluster cluster : this.clusters) {
			double dDistance = distance.calcDistance(document, cluster);
			if (dDistance < min_distance) {
				min_distance = dDistance;
				nearestCluster = cluster;
			}
		}
		
		return nearestCluster;
	}
	
	/**
	 * 清除改簇所有文章集合
	 */
	public void clearClusters() {
		for (Cluster cluster : this.clusters) {
			cluster.getDocuments().clear();
		}
	}

	public List<Cluster> getClusters() {
		return clusters;
	}
}
