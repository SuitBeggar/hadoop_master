package lr;

/**
 * 使用训练好的LR模型对样本进行预测
 * Created by fangyitao on 2019/10/18.
 */
public class LRTest {
    //public static String [] lrtest(int paraNum, int samNum, double [][] feature, double [] W) {
    public static double [] lrtest(int paraNum, int samNum, double [][] feature, double [] W) {
        double [] pre_results = new double [samNum];
        //String [] pre_results = new String [samNum];
        for (int i = 0; i < samNum; i ++) {
            double tmp = 0;
            for (int j = 0; j < paraNum; j ++) {
                tmp += feature[i][j] * W[j];
            }
            if (tmp >= 0.5) {
                pre_results[i]= 1;
                //pre_results[i]= String.valueOf(tmp)+"\t"+"1";
            }
            else {
                //pre_results[i]= String.valueOf(tmp)+"\t"+"0";
                pre_results[i] = 0;
            }

        }
        return pre_results;
    }

    /**
     * 准确度
     * @param Label  真实类别
     * @param pre_results 预测类别
     * @return
     */
    public  static double Accuracy(double [] Label,double [] pre_results){

        double accuracy = 0;
        double TP = 0; //预测正确数量
        double FP = 0; //预测错误数量
        for (int i = 0; i < Label.length; i++) {

                if(Label[i] == pre_results[i]){
                    TP++;
                }else{
                    FP++;
                }
        }

        if(TP+FP !=0 ){
            accuracy = TP/(TP+FP );
        }
        return accuracy;
    }

    /**
     * 召回率和正确率
     * @param Labels   真实类别
     * @param pre_results 预测类别
     * @param Label  预测为正例
     * @return
     */
    public  static double []  RecallAndPrecision(double [] Labels,double [] pre_results,double Label){
        double []  recallAndPrecision = new double[2];
        double recall = 0;//召回率
        double precision = 0;//正确率
        double TP = 0; //真正例（TP）
        double FP = 0; //伪正例（FP）
        double FN = 0; //伪反例（FN）
        double TN = 0; //真反例（TN）
        for (int i = 0; i < Labels.length; i++) {
            if(Labels[i] == Label){
                if(Labels[i] == pre_results[i]){
                    TP++;
                }else{
                    FN++;
                }
            }else{
                if(Labels[i] == pre_results[i]){
                    TN++;
                }else{
                    FP++;
                }
            }

        }

        if(TP+FP != 0){
            precision = TP/(TP+FP);
        }

        if(TP+FN != 0){
            recall = TP/(TP+FN);
        }

        recallAndPrecision[0] = recall;
        recallAndPrecision[1] = precision;
        return recallAndPrecision;
    }

}
