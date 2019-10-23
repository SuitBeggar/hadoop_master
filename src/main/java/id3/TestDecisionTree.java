package id3;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *  * 决策树算法测试类
 *
 * @description:
 * @Author:bella
 * @Date:2019/10/2022:38
 * @Version:
 **/
public class TestDecisionTree {
    /**
     * 读取候选属性
     * @return 候选属性集合
     * @throws IOException
     */
    public ArrayList<String> readCandAttr() throws Exception{
        try {
            ArrayList<String> candAttr = new ArrayList<String>();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:\\springcloud\\hadoop_master\\src\\main\\java\\id3\\lable.txt"))));

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
     * 读取训练元组
     * @return 训练元组集合
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> readData() {
        try {
            ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:\\springcloud\\hadoop_master\\src\\main\\java\\id3\\data.txt"))));

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

    /**
     * 递归打印树结构
     * @param root 当前待输出信息的结点
     */
    public void printTree(TreeNode root) {
        System.out.println("name:" + root.getName());
        ArrayList<String> rules = root.getRule();
        System.out.print("node rules: {");
        for (int i = 0; i < rules.size(); i++) {
            System.out.print(rules.get(i) + " ");
        }
        System.out.print("}");
        System.out.println("");
        ArrayList<TreeNode> children = root.getChild();
        int size = children.size();
        if (size == 0) {
            System.out.println("-->leaf node!<--");
        } else {
            System.out.println("size of children:" + children.size());
            for (int i = 0; i < children.size(); i++) {
                System.out.print("child " + (i + 1) + " of node " + root.getName() + ": ");
                printTree(children.get(i));
            }
        }
    }

    /**
     * 主函数，程序入口
     * @param args
     */
    public static void main(String[] args) {
        TestDecisionTree tdt = new TestDecisionTree();
        ArrayList<String> candAttr = null;
        ArrayList<ArrayList<String>> datas = null;
        try {
            candAttr = tdt.readCandAttr();
            datas = tdt.readData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DecisionTree tree = new DecisionTree();
        TreeNode root = tree.buildTree(datas, candAttr);
        //tdt.printTree(root);
    }
}
