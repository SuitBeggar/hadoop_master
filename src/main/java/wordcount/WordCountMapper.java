package wordcount;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by fangyitao on 2019/9/9.
 */
public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable>{

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        //v1 k1 ConText<v2,k2>
        String line = value.toString();
        if(StringUtils.isNotBlank(line)) {
            String[] words = line.split(",");//通过，分割单词
            if(words!=null&&words.length!=0) {
                for(String word:words) {
                    if(StringUtils.isNotBlank(word)) {
                        Text wordkey = new Text(word);
                        IntWritable tmpValue = new IntWritable(1);
                        context.write(wordkey, tmpValue);
                    }
                }
            }
        }
    }
}
