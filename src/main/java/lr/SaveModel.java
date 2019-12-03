package lr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Logistic Regression模型参数和测试结果存储
 * Created by fangyitao on 2019/10/18.
 */
public class SaveModel {
    public static void savemodel(String filename, double [] W) throws IOException{
        File f = new File(filename);
        // 构建FileOutputStream对象
        FileOutputStream fip = new FileOutputStream(f);
        // 构建OutputStreamWriter对象
        OutputStreamWriter writer = new OutputStreamWriter(fip,"UTF-8");
        //计算模型矩阵的元素个数
        int n = W.length;
        StringBuffer sb = new StringBuffer();
        sb.append("w:"); //w
        for (int i = 0; i < n-1; i ++) {
            sb.append(String.valueOf(W[i]));
            sb.append("\t");
        }
        sb.append("b:"); //b
        sb.append(String.valueOf(W[n-1]));
        String sb1 = sb.toString();
        writer.write(sb1);
        writer.close();
        fip.close();
    }

    //public static void saveresults(String filename, String [] pre_results) throws IOException {
    public static void saveresults(String filename, double [] pre_results) throws IOException {
        File f = new File(filename);
        // 构建FileOutputStream对象
        FileOutputStream fip = new FileOutputStream(f);
        // 构建OutputStreamWriter对象
        OutputStreamWriter writer = new OutputStreamWriter(fip,"UTF-8");
        //计算预测结果的个数
        int n = pre_results.length;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n-1; i ++) {
            sb.append(String.valueOf(pre_results[i]));
            sb.append("\n");
        }
        sb.append(String.valueOf(pre_results[n-1]));
        String sb1 = sb.toString();
        writer.write(sb1);
        writer.close();
        fip.close();
    }
}
