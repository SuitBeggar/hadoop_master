package cf;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**按用户分组，计算所有物品出现的组合列表，得到用户对物品的评分矩阵
 *
 *
 *
 * 计算结果：
 *
 1 102:3.0,103:2.5,101:5.0
 2 101:2.0,102:2.5,103:5.0,104:2.0
 3 107:5.0,101:2.0,104:4.0,105:4.5
 4 101:5.0,103:3.0,104:4.5,106:4.0
 5 101:4.0,102:3.0,103:2.0,104:4.0,105:3.5,106:4.0


 * Created by fangyitao on 2019/10/18.
 */
public class Step1 {

    public static class Step1_ToItemPreMapper extends MapReduceBase implements Mapper<Object, Text, IntWritable, Text> {
        private final static IntWritable k = new IntWritable();
        private final static Text v = new Text();
        //@Override
        public void map(Object key, Text value, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
            String[] tokens = Recommend.DELIMITER.split(value.toString());
            int userID = Integer.parseInt(tokens[0]);
            String itemID = tokens[1];
            String pref = tokens[2];
            k.set(userID);
            v.set(itemID + ":" + pref);
            output.collect(k, v);
        }
    }
    public static class Step1_ToUserVectorReducer extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
        private final static Text v = new Text();
        //@Override
        public void reduce(IntWritable intWritable, Iterator<Text> iterator, OutputCollector<IntWritable, Text> outputCollector, Reporter reporter) throws IOException {
            StringBuilder sb = new StringBuilder();
            while (iterator.hasNext()) {
                sb.append("," + iterator.next());
            }
            v.set(sb.toString().replaceFirst(",", ""));
            outputCollector.collect(intWritable, v);
        }
    }
    public static void run(Map<String, String> path) throws IOException {
        JobConf conf = Recommend.config();
        String input = path.get("Step1Input");
        String output = path.get("Step1Output");
        HdfsDAO hdfs = new HdfsDAO(Recommend.HDFS, conf);
        hdfs.rmr(input);
        hdfs.mkdirs(input);
        hdfs.copyFile(path.get("data"), input);
        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(Text.class);
        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(Text.class);
        conf.setMapperClass(Step1_ToItemPreMapper.class);
        conf.setCombinerClass(Step1_ToUserVectorReducer.class);
        conf.setReducerClass(Step1_ToUserVectorReducer.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        FileInputFormat.setInputPaths(conf, new Path(input));
        FileOutputFormat.setOutputPath(conf, new Path(output));
        RunningJob job = JobClient.runJob(conf);
        while (!job.isComplete()) {
            job.waitForCompletion();
        }
    }


}
