package nb2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by fangyitao on 2019/10/22.
 */
public class IoUtil {
    public static String readFromFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            StringBuilder sb = new StringBuilder();
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                sb.append(string);
            }
            return  sb.toString();
        } catch (Exception e) {
        			// TODO: handle exception
        }
        return "";
    }
}
