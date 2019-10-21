package join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Created by fangyitao on 2019/9/10.
 */
public class JoinMapper  extends Mapper<LongWritable, Text, Text, Text> {

    public static final String DELIMITER = "\u0009"; // 字段分隔符



    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,
                    Reporter reporter) throws IOException, ClassCastException {
        // 获取输入文件的全路径和名称
        String filePath = ((FileSplit)reporter.getInputSplit()).getPath().toString();
        // 获取记录字符串
        String line = value.toString();
        // 抛弃空记录
        if (line == null || line.equals("")) return;

        // 处理来自表A的记录
        if (filePath.contains("m_ys_lab_jointest_a")) {
            String[] values = line.split(DELIMITER); // 按分隔符切割出字段
            if (values.length < 2) return;

            String id = values[0]; // id
            String name = values[1]; // name

            output.collect(new Text(id), new Text("a#"+name));
        }
        // 处理来自表B的记录
        else if (filePath.contains("m_ys_lab_jointest_b")) {
            String[] values = line.split(DELIMITER); // 按分隔符切割出字段
            if (values.length < 3) return;

            String id = values[0]; // id
            String statyear = values[1]; // statyear
            String num = values[2]; //num

            output.collect(new Text(id), new Text("b#"+statyear+DELIMITER+num));
        }
    }






}
