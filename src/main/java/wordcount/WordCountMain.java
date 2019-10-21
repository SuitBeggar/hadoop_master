package wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;


/**
 * Created by fangyitao on 2019/9/9.
 */
public class WordCountMain {


    private final static String FILE_IN_PATH = "hdfs://cluster1/topk/in";
    private final static String FILE_OUT_PATH = "hdfs://cluster1/topk/out";

    public static void main(String[] args) throws Exception{
        System.setProperty("hadoop.home.dir", "D:\\desktop\\hadoop-2.6.0");



        Configuration conf = new Configuration();
        conf.setStrings("dfs.nameservices", "cluster1");
        conf.setStrings("dfs.ha.namenodes.cluster1", "hadoop1,hadoop2");
        conf.setStrings("dfs.namenode.rpc-address.cluster1.hadoop1", "172.19.7.31:9000");
        conf.setStrings("dfs.namenode.rpc-address.cluster1.hadoop2", "172.19.7.32:9000");
        conf.setStrings("dfs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        // 删除已存在的输出目录
        FileSystem fileSystem = FileSystem.get(new URI(FILE_OUT_PATH), conf);
        if (fileSystem.exists(new Path(FILE_OUT_PATH))) {
            fileSystem.delete(new Path(FILE_OUT_PATH), true);
        }

        //JobConf job = new JobConf(conf);
        Job job = Job.getInstance(conf, "MapReduce TopK Demo");
        job.setMapperClass(WordCountMapper.class);
        job.setJarByClass(WordCountMain.class);
        job.setReducerClass(WordCountReducer.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        FileInputFormat.addInputPath(job, new Path(FILE_IN_PATH));
        FileOutputFormat.setOutputPath(job, new Path(FILE_OUT_PATH));
        job.waitForCompletion(true);
    }
}
