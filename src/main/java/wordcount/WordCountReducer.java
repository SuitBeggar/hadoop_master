package wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by fangyitao on 2019/9/9.
 */
public class WordCountReducer extends Reducer<Text,IntWritable,Text,IntWritable>{

    @Override
    protected void reduce(Text value, Iterable<IntWritable> key,
                          Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        //v2 k3  context<v3,k3>
        int sum=0;
        for(IntWritable tmpNum:key) {
            sum+=tmpNum.get();
        }
        context.write(value, new IntWritable(sum));
    }


}
