package join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by fangyitao on 2019/9/10.
 */
public class JoinReduce extends Reducer<Text, Text, Text, Text> {

    public static final String DELIMITER = "\u0009"; // 字段分隔符

    // reduce过程
    public void reduce(Text key, Iterator<Text> values,
                       OutputCollector<Text, Text> output, Reporter reporter)
            throws IOException {

        Vector<String> vecA = new Vector<String>(); // 存放来自表A的值
        Vector<String> vecB = new Vector<String>(); // 存放来自表B的值

        while (values.hasNext()) {
            String value = values.next().toString();
            if (value.startsWith("a#")) {
                vecA.add(value.substring(2));
            } else if (value.startsWith("b#")) {
                vecB.add(value.substring(2));
            }
        }

        int sizeA = vecA.size();
        int sizeB = vecB.size();

        // 遍历两个向量
        int i, j;
        for (i = 0; i < sizeA; i ++) {
            for (j = 0; j < sizeB; j ++) {
                output.collect(key, new Text(vecA.get(i) + DELIMITER +vecB.get(j)));
            }
        }
    }
}
