package lr;

/**
 * Sigmoid函数
 * Created by fangyitao on 2019/10/18.
 */
public class Sigmoid {
    public static double sigmoid(double x) {
        double i = 1.0;
        double y = i / (i + Math.exp(-x));
        return y;
    }
}
