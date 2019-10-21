package join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import wordcount.WordCountMain;
import wordcount.WordCountMapper;
import wordcount.WordCountReducer;

import java.io.IOException;
import java.net.URI;

/**
 * Created by fangyitao on 2019/9/10.
 */
public class JoinMain{


    public static void main(String[] args) throws Exception{
        System.setProperty("hadoop.home.dir", "D:\\desktop\\hadoop-2.6.0");



        Configuration conf = new Configuration();
        conf.setStrings("dfs.nameservices", "cluster1");
        conf.setStrings("dfs.ha.namenodes.cluster1", "hadoop1,hadoop2");
        conf.setStrings("dfs.namenode.rpc-address.cluster1.hadoop1", "172.19.7.31:9000");
        conf.setStrings("dfs.namenode.rpc-address.cluster1.hadoop2", "172.19.7.32:9000");
        conf.setStrings("dfs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

        //JobConf job = new JobConf(conf);
        Job job = Job.getInstance(conf, "JOIN");
        job.setMapperClass(JoinMapper.class);
        job.setJarByClass(JoinMain.class);
        job.setReducerClass(JoinReduce.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        FileInputFormat.addInputPath(job, new Path("a.txt"));
        FileInputFormat.addInputPath(job, new Path("b.txt"));
        FileOutputFormat.setOutputPath(job, new Path("c.txt"));
        job.waitForCompletion(true);
    }
}
