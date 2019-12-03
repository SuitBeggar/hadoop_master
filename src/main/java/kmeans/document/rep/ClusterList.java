package kmeans.document.rep;

import java.util.ArrayList;
import java.util.List;

/**
 * ���м��ϣ��������·�Ϊ�����أ�
 * @author hazoom
 *
 */
public class ClusterList {

	private List<Cluster> clusters;
	

	public ClusterList(int k) {
		this.clusters = new ArrayList<Cluster>(k);
	}
	
	/**
	 * ��������¼��ϵ��Ĵ�
	 * @param cluster
	 */
	public void add(Cluster cluster) {
		this.clusters.add(cluster);
	}
	
	/**
	 * ���¸Ĵص�����
	 */
	public void updateControids() {
		for (Cluster cluster : this.clusters) {
			cluster.updateCentroid();
		}
	}

	/**
	 * �����д��в���������ĵ�����Ĵ�
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
	 * ����Ĵ��������¼���
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
