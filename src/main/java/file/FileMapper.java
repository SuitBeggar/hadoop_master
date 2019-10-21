package file;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by fangyitao on 2019/9/10.
 */
public class FileMapper extends Mapper<LongWritable, Text, Text, Text> {
    /**
     * 读取小文件进行缓存  （分布式缓存）
     */
    static List<String> li = new ArrayList<String>();
    @Override
    protected void setup(Context context)throws IOException, InterruptedException {

        //文件分发的三种方式

        //①获取缓存文件路径的数组
        Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());

        //②获取文件路径的数组
        //Path[] paths =  DistributedCache.getFileClassPaths(context.getConfiguration());

        //③
        //Path[] paths =  DistributedCache.getCacheArchives(context.getConfiguration());

        //循环读取每一个缓存文件
        for (Path p : paths) {
            //获取文件名字
            String fileName = p.getName();
            if(fileName.equals("dir")){
                BufferedReader sb = null;
                sb = new BufferedReader(new FileReader(new File(p.toString())));
                //读取BufferedReader里面的数据
                String tmp = null;
                while ( (tmp = sb.readLine()) != null) {
                    String ss []= tmp.split(" ");
                    for (String s : ss) {
                        li.add(s);
                    }
                }
                //关闭sb对象
                sb.close();
            }
        }
    }

    @Override
    protected void map(LongWritable key, Text value,Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer lines = new StringTokenizer(line);
        while (lines.hasMoreTokens()) {
            //判断每一个单词是否是敏感词汇
            String word = lines.nextToken();
            if(!li.contains(word)){
                context.write(new Text(word), new Text("1"));
            }
        }
    }

    @Override
    protected void cleanup(Context context)throws IOException, InterruptedException {
    }

}
