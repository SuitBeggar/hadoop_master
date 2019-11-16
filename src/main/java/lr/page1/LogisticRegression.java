package lr.page1;

import lr.LoadData;
import lr.Sigmoid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**线性回归的实现(LR)
 * Created by fangyitao on 2019/11/16.
 */
public class LogisticRegression {

    /**样本特征**/
    private double [][] feature;

    /**样本特征(训练集)**/
    //private double [][] train_feature;

    /**样本特征(测试集)**/
    //private double [][] pre_feature;

    /**样本标签**/
    private double [] Label;

    /**行(样本个数)**/
    private int row;

    /**列（权重标签的个数）**/
    private int col;

    /**学习率（步长）**/
    private double alpha;

    /**迭代次数**/
    private int iteration;

    /**模型的w（标签权重）**/
    private double [] W;


    //导入样本特征
    public static double[][] Loadfeature(String filename) throws IOException {
        File f = new File(filename);
        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象
        InputStreamReader reader = new InputStreamReader(fip,"UTF-8");
        // 构建InputStreamReader对象
        StringBuffer sb = new StringBuffer();
        while(reader.ready()) {
            sb.append((char) reader.read());
        }
        reader.close();
        fip.close();
        //将读入的数据流转换为字符串
        String sb1 = sb.toString();
        //按行将字符串分割,计算二维数组行数
        String [] a = sb1.split("\n");
        int n = a.length;
        System.out.println("二维数组行数为:" + n);
        //计算二维数组列数
        String [] a0 = a[0].split("\t");
        int m = a0.length;
        System.out.println("二维数组列数为:" + m);

        double [][] feature = new double[n][m];
        for (int i = 0; i < n; i ++) {
            String [] tmp = a[i].split("\t");
            for(int j = 0; j < m; j ++) {
                if (j == m-1) {
                    feature[i][j] = (double) 1;
                }
                else {
                    feature[i][j] = Double.parseDouble(tmp[j]);
                }
            }
        }
        return feature;
    }
    //导入样本标签
    public static double[] LoadLabel(String filename) throws IOException {
        File f = new File(filename);
        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象
        InputStreamReader reader = new InputStreamReader(fip,"UTF-8");
        // 构建InputStreamReader对象,编码与写入相同
        StringBuffer sb = new StringBuffer();
        while(reader.ready()) {
            sb.append((char) reader.read());
        }
        reader.close();
        fip.close();
        //将读入的数据流转换为字符串
        String sb1 = sb.toString();
        //按行将字符串分割,计算二维数组行数
        String [] a = sb1.split("\n");
        int n = a.length;
        System.out.println("二维数组行数为:" + n);
        //计算二维数组列数
        String [] a0 = a[0].split("\t");
        int m = a0.length;
        System.out.println("二维数组列数为:" + m);

        double [] Label = new double[n];
        for (int i = 0; i < n; i ++) {
            String [] tmp = a[i].split("\t");
            Label[i] = Double.parseDouble(tmp[m-1]);
        }
        return Label;
    }

    /**
     * 权值矩阵初始化
     * @param col
     * @return
     */
    public double [] ParaInitialize(int col) {
        double [] W = new double[col];
        for (int i = 0; i < col; i ++) {
            W[i] =  1.0;
        }
        return W;
    }


    /**
     * 计算每次迭代后的预测误差
     * @param row
     * @param col
     * @param feature
     * @param W
     * @return
     */
    public double [] PreVal(int row,int col, double [][] feature,double [] W) {
        double [] Preval = new double[row];
        for (int i = 0; i< row; i ++) {
            double tmp = 0;
            for(int j = 0; j < col; j ++) {
                tmp += feature[i][j] * W[j];
            }
            Preval[i] = Sigmoid.sigmoid(tmp);
        }
        return Preval;
    }

    /**
     *  Sigmoid函数
     * @param x
     * @return
     */
    public static double sigmoid(double x) {
        double i = 1.0;
        double y = i / (i + Math.exp(-x));
        return y;
    }

    /**
     * 计算误差率
     * @param row
     * @param Label
     * @param Preval
     * @return
     */
    public double error_rate(int row, double [] Label, double [] Preval) {
        double sum_err = 0.0;
        for(int i = 0; i < row; i ++) {
            sum_err += Math.pow(Label[i] - Preval[i], 2);
        }
        return sum_err;
    }

    /**
     * 初始化数据
     * @param filename  样本数据所在文件
     * @param alpha    学习率（步长）
     * @param iteration 迭代次数
     * @throws Exception
     */
    public void initData(String filename,double alpha,int iteration) throws Exception{
        // 导入样本特征和标签
        this.feature = Loadfeature(filename);
        this.Label = LoadLabel(filename);
        // 参数设置
        this.alpha = 0.01;
        this.iteration = 1000;

        this.col = feature[0].length;
        this.row = feature.length;

        //初始化权重矩阵
        this.W = ParaInitialize(col);

    }

    /**
     * LR模型训练
     * @param filename  样本数据所在文件
     * @param alpha    学习率（步长）
     * @param iteration 迭代次数
     * @throws Exception
     */
    public double[] train(String filename,double alpha,int iteration)  throws Exception{

        initData(filename,alpha,iteration);
        // 循环迭代优化权重矩阵
        for (int i = 0; i < iteration; i ++) {
            // 每次迭代后，样本预测值
            double [] Preval = PreVal(row,col,feature,W);
            double sum_err = error_rate(row,Label,Preval);
            if (i % 10 == 0) {
                System.out.println("第" + i + "次迭代的预测误差为:" + sum_err);
            }
            //预测值与标签的误差
            double [] err = new double[row];
            for(int j = 0; j < row; j ++) {
                err[j] = Label[j] - Preval[j];
            }
            // 计算权重矩阵的梯度方向
            double [] Delt_W = new double[col];
            for (int n = 0 ; n < col; n ++) {
                double tmp = 0;
                for(int m = 0; m < row; m ++) {
                    tmp += feature[m][n] * err[m];
                }
                Delt_W[n] = tmp / row;
            }

            for(int m = 0; m < col; m ++) {
                W[m] = W[m] + alpha * Delt_W[m];
            }
        }
        return W;
    }


    /**
     * 使用训练好的LR模型对样本进行预测
     * @return
     */
    public  String [] lrtest() {

        String [] pre_results = new String [row];
        for (int i = 0; i < row; i ++) {
            double tmp = 0;
            for (int j = 0; j < col; j ++) {
                tmp += feature[i][j] * W[j];
            }
            //分为两类
            if (tmp >= 0.5) {
                pre_results[i]= String.valueOf(tmp)+"\t"+"1";
            }
            else {
                pre_results[i]= String.valueOf(tmp)+"\t"+"0";
            }

        }
        return pre_results;
    }

    public static void main(String[] args) throws Exception{
    	LogisticRegression lr = new LogisticRegression();
        String filename = "F:\\myproject\\hadoop_master\\src\\main\\java\\lr\\data.txt";
        double rate = 0.01;
        int maxCycle = 1000;
        lr.train(filename,rate,maxCycle);
    }
}
