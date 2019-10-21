package lr;

/**
 * 使用训练好的LR模型对样本进行预测
 * Created by fangyitao on 2019/10/18.
 */
public class LRTest {
    public static String [] lrtest(int paraNum, int samNum, double [][] feature, double [] W) {
    //public static double [] lrtest(int paraNum, int samNum, double [][] feature, double [] W) {
        //double [] pre_results = new double [samNum];
        String [] pre_results = new String [samNum];
        for (int i = 0; i < samNum; i ++) {
            double tmp = 0;
            for (int j = 0; j < paraNum; j ++) {
                tmp += feature[i][j] * W[j];
            }
            if (tmp >= 0.5) {
                //pre_results[i]= 1;
                pre_results[i]= String.valueOf(tmp)+"\t"+"1";
            }
            else {
                pre_results[i]= String.valueOf(tmp)+"\t"+"0";
                //pre_results[i] = 0;
            }

        }
        return pre_results;
    }

}
