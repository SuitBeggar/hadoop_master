package tfidf;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TFIDF实现
 * Created by fangyitao on 2019/11/16.
 */
public class TFIDF {

    /**
     *
     *计算每个文章的tf值
     * @param wordAll （这个wordAll为一个文章的所有词，用空格分割）
     * @param tf  存放（单词，单词词频） (这tf为所有文章词的tf值，计算当前文章tf值时，
     *            需要考虑其他文章tf值，如果其他文章存在一个词和当前文章一样，
     *            那么两个tf值需要相加)
     * @return Map<String,Float> key是单词 value是tf值
     */
    public static Map<String,Float> tfCalculate(String wordAll,Map<String,Float> tf){
        //存放（单词，单词数量）
        HashMap<String, Integer> dict = new HashMap<String, Integer>();

        int wordCount=0;

        /**
         * 统计每个单词的数量，并存放到map中去
         * 便于以后计算每个单词的词频
         * 单词的tf=该单词出现的数量n/总的单词数wordCount
         */
        for(String word:wordAll.split(" ")){
            wordCount++;
            if(dict.containsKey(word)){
                dict.put(word,  dict.get(word)+1);
            }else{
                dict.put(word, 1);
            }
        }


        for(Map.Entry<String, Integer> entry:dict.entrySet()){
            //  tf(词频) = 某词在文章中出现的次数/文章总词数
            //这个地方是计算当前文章，当前词的tf值
            float wordTf=(float)entry.getValue()/wordCount;

            if(tf.containsKey(entry.getKey())){
                //如果tf值集合中已包含这个词，那么两个tf值需要相加
                tf.put(entry.getKey(), tf.get(entry.getKey())+wordTf);
            }else{
                //如果tf值集合中不包含这个词，那么记录该词的tf值
                tf.put(entry.getKey(), wordTf);

            }
        }
        return tf;
    }

    /**
     *
     * @param D 总文章数
     * @param doc_words 每个文档对应的分词(key为文章，value为该文章的词)
     * @param tf 计算好的tf,用这个作为基础计算tfidf
     * @return 每个文章中的单词的tfidf的值
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static Map<String,Float> tfidfCalculate(int D, Map<String,String> doc_words,Map<String,Float> tf) throws FileNotFoundException, IOException {

        HashMap<String,Float> tfidf=new HashMap<String, Float>();

        //遍历已计算出的tf值，为计算tfidf值做准备
        for(String key:tf.keySet()){
            int Dt=0;

            //遍历整个文章的每一个词，计算包含该词的文章数
            for(Map.Entry<String, String> entry:doc_words.entrySet()){

                String[] words=entry.getValue().split(" ");

                List<String> wordlist=new ArrayList<String>();
                for(int i=0;i<words.length;i++){
                    wordlist.add(words[i]);

                }

                //计算所有文章包含该词的文章数
                if(wordlist.contains(key)){
                    Dt++;
                }
            }
            // idf (反文档词频) = log（总文章数/包含该词的总文章数+1）
            float idfvalue=(float) Math.log(Float.valueOf(D)/(Dt+1));

            //tf-idf = tf  *idf
            tfidf.put(key, idfvalue * tf.get(key));

        }
        return tfidf;
    }


    /**
     * 目录
     * @param path
     */
    public  static void run(String path) throws Exception{
        File fileDir = new File(path);
        File[] files = fileDir.listFiles();

        //存储所有词的tf值 （key为词，value为tf值）
        Map<String,Float>  tfValue = new HashMap<String, Float>();

        //文章总词数
        int fileCount = 0;

        //每个文档对应的分词(key为文章，value为该文章的词)
        Map<String,String> doc_words = new HashMap<String, String>();
        for (File f : files) {

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String s = "";

            //一个文章的所有词
            String wordAll = "";

            while ((s = br.readLine()) != null) {

                //默认所有词已使用空格符分割（此处后续可添加中文分词）
                wordAll += s;

            }

            doc_words.put(f.getName(),wordAll);

            //计算tf
            Map<String, Float> stringFloatMap = tfCalculate(wordAll,tfValue);

        }

        //计算tfidf
        tfidfCalculate(fileCount,doc_words,tfValue);

    }
}
