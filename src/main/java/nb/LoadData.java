package nb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**初始化加载数据
 * @description:
 * @Author:bella
 * @Date:2019/10/2021:56
 * @Version:
 **/
public class LoadData {

    /**
     *   第一个文件夹下放的是25篇正常的纯文本邮件，第二个文件夹放的是25篇纯文本垃圾邮件
     * 初始化数据集
     *
     * @return
     */
    public static  List<Email> initDataSet() {
        List<Email> dataSet = new ArrayList<Email>();
        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        try {
            for (int i = 1; i < 26; i++) {
                bufferedReader1 = new BufferedReader(new InputStreamReader(
                        new FileInputStream(
                                "/home/shenchao/Desktop/MLSourceCode/machinelearninginaction/Ch04/email/ham/"
                                        + i + ".txt")));
                StringBuilder sb1 = new StringBuilder();
                String string = null;
                while ((string = bufferedReader1.readLine()) != null) {
                    sb1.append(string);
                }
                Email hamEmail = new Email();
                hamEmail.setWordList(textParse(sb1.toString()));
                hamEmail.setFlag(0);

                bufferedReader2 = new BufferedReader(new InputStreamReader(
                        new FileInputStream(
                                "/home/shenchao/Desktop/MLSourceCode/machinelearninginaction/Ch04/email/spam/"
                                        + i + ".txt")));
                StringBuilder sb2 = new StringBuilder();
                while ((string = bufferedReader2.readLine()) != null) {
                    sb2.append(string);
                }
                Email spamEmail = new Email();
                spamEmail.setWordList(textParse(sb2.toString()));
                spamEmail.setFlag(1);

                dataSet.add(hamEmail);
                dataSet.add(spamEmail);
            }
            return dataSet;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                bufferedReader1.close();
                bufferedReader2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 分词，英文的分词相比中文的分词要简单很多，这里使用的分隔符为除单词、数字外的任意字符串
     * 如果使用中文，则可以使用中科院的一套分词系统，分词效果还算不错
     *
     * @param originalString
     * @return
     * @return
     */
    private static List<String> textParse(String originalString) {
        String[] s = originalString.split("\\W");
        List<String> wordList = new ArrayList<String>();
        for (String string : s) {
            if (string.contains(" ")) {
                continue;
            }
            if (string.length() > 2) {
                wordList.add(string.toLowerCase());
            }
        }
        return wordList;

    }


}
