package lr;

import java.io.IOException;

/**
 * Created by fangyitao on 2019/10/18.
 */
public class LRMain {
    public static void main(String[] args) throws IOException {
        // filename
        String filename = "F:\\springcloud\\hadoop_master\\src\\main\\java\\lr\\data.txt";
        // 导入样本特征和标签
        double [][] feature = LoadData.Loadfeature(filename);
        double [] Label = LoadData.LoadLabel(filename);
        // 参数设置
        int samNum = feature.length;
        int paraNum = feature[0].length;
        double rate = 0.01;
        int maxCycle = 1000;
        // LR模型训练
        LRtrainGradientDescent LR = new LRtrainGradientDescent(feature,Label,paraNum,rate,samNum,maxCycle);
        double [] W = LR.Updata(feature, Label, maxCycle, rate);
        //保存模型
        String model_path = "F:\\springcloud\\hadoop_master\\src\\main\\java\\lr\\wrights.txt";
        SaveModel.savemodel(model_path, W);
        //模型测试
        double [] pre_results = LRTest.lrtest(paraNum, samNum, feature, W);
        //String [] pre_results = LRTest.lrtest(paraNum, samNum, feature, W);
        //保存测试结果
        String results_path = "F:\\springcloud\\hadoop_master\\src\\main\\java\\lr\\pre_results.txt";
        SaveModel.saveresults(results_path, pre_results);

        //准确度
        double accuracy = LRTest.Accuracy(Label, pre_results);

        System.out.println("准确率为："+accuracy);


        double[] doubles = LRTest.RecallAndPrecision(Label, pre_results, 1.0);
        System.out.println("召回率为："+doubles[0]);
        System.out.println("正确率为："+doubles[1]);
    }

}
