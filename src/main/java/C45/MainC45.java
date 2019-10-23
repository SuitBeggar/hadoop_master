package C45;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangyitao on 2019/10/22.
 */
public class MainC45 {
    private static final List<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
    private static final List<String> attributeList = new ArrayList<String>();

    public static void main(String args[]){

        DecisionTree dt = new DecisionTree();
        TreeNode node = dt.createDT(configData(),configAttribute());
        System.out.println();
    }


    /**
     * 初始化标签
     * @return
     */
    private static List<String> configAttribute() {
        try {
            ArrayList<String> candAttr = new ArrayList<String>();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:\\springcloud\\hadoop_master\\src\\main\\java\\C45\\lable.txt"))));

            String str = "";

            while ((str = reader.readLine()) != null) {
                String[] tempStr = str.split(" ");
                for (int i = 0; i <tempStr.length ; i++) {
                    candAttr.add(tempStr[i]);
                }
            }
            return candAttr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 初始化数据集
     * @return
     */
    private static List<ArrayList<String>> configData() {
        try {
            ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:\\springcloud\\hadoop_master\\src\\main\\java\\C45\\data.txt"))));

            String str = "";
            while ((str = reader.readLine()) != null) {

                String[] tempStr = str.split(" ");
                ArrayList<String> s = new ArrayList<String>();
                for (int i = 0; i <tempStr.length ; i++) {
                    s.add(tempStr[i]);
                }

                datas.add(s);
            }
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
