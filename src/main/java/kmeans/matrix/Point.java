package kmeans.matrix;

import java.util.Arrays;

/**分装Point类
 * Created by fangyitao on 2019/11/13.
 */
public class Point {
    private float[] localArray;
    private int id;
    private int clusterId;  // 标识属于哪个类中心。
    private float dist;     // 标识和所属类中心的距离。

    public Point(int id, float[] localArray) {
        this.id = id;
        this.localArray = localArray;
    }

    public Point(float[] localArray) {
        this.id = -1; //表示不属于任意一个类
        this.localArray = localArray;
    }

    public float[] getLocalArray() {
        return localArray;
    }

    public void setLocalArray(float[] localArray) {
        this.localArray = localArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }


    @Override
    public String toString() {
        return "Point{" +
                "localArray=" + Arrays.toString(localArray) +
                ", id=" + id +
                ", clusterId=" + clusterId +
                ", dist=" + dist +
                '}';
    }

}
