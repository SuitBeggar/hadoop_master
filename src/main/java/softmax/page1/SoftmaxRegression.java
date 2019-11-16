package softmax.page1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by fangyitao on 2019/11/16.
 */
public class SoftmaxRegression {
    /**样本特征**/
    private double [][] feature;

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
    private double [][] W;

    /**多少个分类**/
    private int labelNum;



    /**
     * 权值矩阵初始化
     * @param col
     * @param labelNum
     * @return
     */
    public double [][] ParaInitialize(int col,int labelNum) {
        double [][] W = new double[col][labelNum];
        for (int i = 0; i < col; i ++) {
            for (int j = 0; j < labelNum; j ++) {
                W[i][j] =  1.0;
            }
        }
        return W;
    }

    /**
     * 计算假设函数的分子部分
     * @param W
     * @param feature
     * @return
     */
    public double [][] err(double[][] W, double [][] feature){
        double [][] errMatrix = new double[feature.length][W[0].length];
        for (int i = 0; i < feature.length; i ++) {
            for (int j = 0; j < W[0].length; j ++) {
                double tmp = 0;
                for (int n = 0; n < W.length; n ++) {
                    tmp = tmp + feature[i][n] * W[n][j];
                }
                errMatrix[i][j] = Math.exp(tmp);
            }
        }
        return errMatrix;
    }

    /**
     * 计算假设函数的分母部分
     * @param errMatrix
     * @return
     */
    public double [] errSum(double [][] errMatrix) {
        double [] errsum = new double[errMatrix.length];
        for (int i = 0; i < errMatrix.length; i ++) {
            double tmp = 0;
            for (int j = 0; j < errMatrix[0].length; j ++) {
                tmp = tmp - errMatrix[i][j];
            }
            errsum[i] = tmp;
        }
        return errsum;
    }

    /**
     * 计算假设函数的负数矩阵
     * @param errMatrix
     * @param errsum
     * @return
     */
    public double [][] errFunction(double [][] errMatrix, double [] errsum){
        double [][] errResult = new double [errMatrix.length][errMatrix[0].length];
        for (int i = 0; i < errMatrix.length; i ++) {
            for (int j = 0; j < errMatrix[0].length; j ++) {
                errResult[i][j] = errMatrix[i][j] / errsum[i];
            }
        }
        return errResult;
    }

    /**
     * 计算预测损失函数值
     * @param Label
     * @param errMatrix
     * @param errsum
     * @param row
     * @return
     */
    public double cost(double [] Label,double [][] errMatrix, double [] errsum,int row) {
        double sum_cost = 0;
        for(int i = 0; i < row; i ++) {
            int m = (int) Label[i];
            if ((errMatrix[i][m] / (- errsum[i])) > 0) {
                sum_cost -= Math.log(errMatrix[i][m] / (- errsum[i]));
            }
            else {
                sum_cost -= 0;
            }
        }
        return sum_cost / row;
    }


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
    public static double[] LoadLabel(String filename) throws IOException{
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
    public static int LabelNum(double [] Label) {
        int n = Label.length;
        double [] LabelTmp = new double [n];
        System.arraycopy(Label, 0, LabelTmp, 0, n);
        int labelNum = 1;
        Arrays.sort(LabelTmp);
        for(int i = 1; i < n; i ++) {
            if (LabelTmp[i] != LabelTmp[i-1]) {
                labelNum ++;
            }
        }
        return labelNum;
    }

    public  void init(String filename,double alpha,int iteration) throws Exception{
        // 导入样本特征和标签
        this.feature = Loadfeature(filename);
        this.Label = LoadLabel(filename);
        this.labelNum = LabelNum(this.Label);
        // 参数设置
        this.alpha = alpha;
        this.iteration = iteration;
        this.col = feature[0].length;
        this.row = feature.length;
        //初始化权重矩阵
        this.W= ParaInitialize(col,labelNum);
    }

    public double [][] train(String filename,double alpha,int iteration) throws Exception{

        init(filename,alpha,iteration);
        // 循环迭代优化权重矩阵
        for(int i = 0; i < iteration; i ++) {
            //假设函数的分子部分
            double [][] errMatrix = err(W,feature);
            //假设函数的分母部分的负数
            double [] errsum = errSum(errMatrix);
            if (i % 10 == 0) {
                double cost = cost(Label,errMatrix,errsum,row);
                System.out.println("第" + i + "次迭代的损失函数值为:" + cost);
            }
            //假设函数的负数矩阵
            double [][] errResult = errFunction(errMatrix,errsum);
            for (int j = 0; j < row; j ++) {
                int m = (int) Label[j];
                errResult[j][m] += 1;
            }
            // 计算权重矩阵中每个权重参数的梯度方向
            double [][] delt_weights = new double[col][labelNum];
            for (int iter1 = 0; iter1 < col; iter1 ++) {
                for (int iter2 = 0; iter2 < labelNum; iter2 ++) {
                    double tmp = 0;
                    for (int iter3 = 0; iter3 < row; iter3 ++) {
                        tmp = tmp + feature[iter3][iter1] * errResult[iter3][iter2];
                    }
                    delt_weights[iter1][iter2] = tmp / row;
                }
            }

            for (int iter1 = 0; iter1 < col; iter1 ++) {
                for (int iter2 = 0; iter2 < labelNum; iter2 ++) {
                    W[iter1][iter2] = W[iter1][iter2] + alpha * delt_weights[iter1][iter2];
                }    		}
        }
        return W;
    }


    /**
     * 从矩阵的一行中找到最大元素对应的指针
     * @param array
     * @return
     */
    public static int MaxSearch(double [] array) {
        int  pointer = 0;
        double tmp = 0;
        for (int j = 0; j < array.length; j ++) {
            if (array[j] > tmp) {
                tmp = array[j];
                pointer = j;
            }
        }
        return pointer;
    }


    /**
     * 计算预测结果
     * @return
     */
    public double [] SRtest() {
        double [][] pre_results = new double [col][labelNum];
        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < labelNum; j ++) {
                double tmp = 0;
                for (int n = 0; n < col; n ++) {
                    tmp += feature[i][n] * W[n][j];
                }
                pre_results[i][j] = tmp;
            }
        }
        double [] results = new double [row];
        for (int m = 0; m < row; m ++) {
            results[m] = MaxSearch(pre_results[m]);
        }
        return results;
    }

    public static void main(String[] args) throws Exception{
        SoftmaxRegression SR = new SoftmaxRegression();
        String filename = "F:\\myproject\\hadoop_master\\src\\main\\java\\softmax\\SoftInput.txt";
        double rate = 0.04;
        int maxCycle = 10000;
        SR.train(filename,rate,maxCycle);
    }
}
