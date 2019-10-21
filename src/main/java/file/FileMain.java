package file;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.net.URI;

/**
 * Created by fangyitao on 2019/9/10.
 */
public class FileMain {
    
    public static void main(String[] args) {
        try {
            //对输入参数作解析
            String[] argss = new GenericOptionsParser(new Configuration(), args).getRemainingArgs();

            //1、获取conf对象
            Configuration conf = new Configuration();

            conf.set("fs.defaultFS", "hdfs://hadoop01:9000");

            //2、创建job
            Job job = Job.getInstance(conf, "GrepDemo");
            //3、设置运行job的class
            job.setJarByClass(FileMain.class);
            //4、设置map相关属性
            job.setMapperClass(FileMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));

            //设置文件三种方式
            //①设置分布式缓存文件
            job.addCacheFile(new URI("hdfs://hadoop01:9000/1603data/dir"));

            //②
            job.addFileToClassPath(new Path("hdfs://hadoop01:9000/1603data/dir"));

            //③
            job.addCacheArchive(new URI("hdfs://hadoop01:9000/1603data/dir"));


            //5、设置reduce相关属性
            job.setReducerClass(FileReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            //判断输出目录是否存在，若存在则删除
            FileSystem fs = FileSystem.get(conf);
            if (fs.exists(new Path(args[1]))) {
                fs.delete(new Path(args[1]), true);
            }
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            //6、提交运行job
            int isok = job.waitForCompletion(true) ? 0 : 1;


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
