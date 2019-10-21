package file;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by fangyitao on 2019/9/10.
 */
public class FileReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void setup(Context context)throws IOException, InterruptedException {
    }

    @Override
    protected void reduce(Text key, Iterable<Text> value,Context context)
            throws IOException, InterruptedException {
        int counter = 0;
        for (Text t : value) {
            counter += Integer.parseInt(t.toString());
        }
        context.write(key, new Text(counter+""));
    }

    @Override
    protected void cleanup(Context context)throws IOException, InterruptedException {
    }

}
