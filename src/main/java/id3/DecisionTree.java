package id3;

/**生成决策树
 * @description:
 * @Author:bella
 * @Date:2019/10/2022:36
 * @Version:
 **/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DecisionTree {
    private Integer attrSelMode; // 最佳分裂属性选择模式，1表示以信息增益度量，2表示以信息增益率度量。暂未实现2

    public DecisionTree() {
        this.attrSelMode = 1;
    }

    public DecisionTree(int attrSelMode) {
        this.attrSelMode = attrSelMode;
    }

    public void setAttrSelMode(Integer attrSelMode) {
        this.attrSelMode = attrSelMode;
    }

    /**
     * 获取指定数据集中的类别及其计数
     *
     * @param datas
     *            指定的数据集
     * @return 类别及其计数的map
     */
    public static Map<String, Integer> classOfDatas(
            ArrayList<ArrayList<String>> datas) {
        Map<String, Integer> classes = new HashMap<String, Integer>();
        String c = "";
        ArrayList<String> tuple = null;
        for (int i = 0; i < datas.size(); i++) {
            tuple = datas.get(i);
            c = tuple.get(tuple.size() - 1);
            if (classes.containsKey(c)) {
                classes.put(c, classes.get(c) + 1);
            } else {
                classes.put(c, 1);
            }
        }
        return classes;
    }

    /**
     * 获取具有最大计数的类名，即求多数类
     *
     * @param classes
     *            类的键值集合
     * @return 多数类的类名
     */
    public static String maxClass(Map<String, Integer> classes) {
        String maxC = "";
        int max = -1;
        Iterator iter = classes.entrySet().iterator();
        for (int i = 0; iter.hasNext(); i++) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (val > max) {
                max = val;
                maxC = key;
            }
        }
        return maxC;
    }

    /**
     * 构造决策树
     *
     * @param datas
     *            训练元组集合
     * @param attrList
     *            候选属性集合
     * @return 决策树根结点
     */
    public static TreeNode buildTree(ArrayList<ArrayList<String>> datas,
                                     ArrayList<String> attrList) {

        System.out.print("候选属性列表： ");
        for (int i = 0; i < attrList.size(); i++) {
            System.out.print(" " + attrList.get(i) + " ");
        }

        System.out.println();
        TreeNode node = new TreeNode();
        node.setDatas(datas);
        node.setCandAttr(attrList);
        Map<String, Integer> classes = classOfDatas(datas);
        System.out.println(classes);// #

        String maxC = maxClass(classes);

        System.out.println(maxC);// #

        System.out.println("存放分类类型的个数是" + classes.size());
        System.out.println("剩余特征数为" + attrList.size());
        if (classes.size() == 1 || attrList.size() == 1) {
            node.setName(maxC);


            return node;
        }

        Gain gain = new Gain(datas, attrList);
        int bestAttrIndex = gain.bestGainAttrIndex();
        System.out.println("最佳分类特征索引为" + bestAttrIndex);// #
        ArrayList<String> rules = gain.getValues(datas, bestAttrIndex);
        System.out.println("分类规则为" + rules);// #
        node.setRule(rules);
        node.setName(attrList.get(bestAttrIndex));

        attrList.remove(bestAttrIndex);

        ArrayList<ArrayList<ArrayList<String>>> allDatas=new ArrayList<ArrayList<ArrayList<String>>>() ;
        for (int i = 0; i < rules.size(); i++) {
            String rule = rules.get(i);

            ArrayList<ArrayList<String>> di = gain.datasOfValue(bestAttrIndex,
                    rule);
            allDatas.add(di);
        }
        for(int i=0;i<allDatas.size();i++){
            for (int j = 0; j < allDatas.get(i).size(); j++) {
                allDatas.get(i).get(j).remove(bestAttrIndex);
            }


            System.out.println("剩余分类特征为" + attrList);// #
            System.out.println();
            if (allDatas.get(i).size() == 0 || attrList.size() == 0) {
                TreeNode leafNode = new TreeNode();
                leafNode.setName(maxC);
                leafNode.setDatas(allDatas.get(i));
                leafNode.setCandAttr(attrList);
                node.getChild().add(leafNode);
            } else {

                TreeNode newNode = buildTree(allDatas.get(i), attrList);

                node.getChild().add(newNode);

            }

        }

        return node;
    }

}
