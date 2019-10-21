package nb;

import java.util.List;

/**
 * @description:
 * @Author:bella
 * @Date:2019/10/2021:53
 * @Version:
 **/
public class Email {
    private List<String> wordList;
    private int flag;

    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public List<String> getWordList() {
        return wordList;
    }
    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }
}
