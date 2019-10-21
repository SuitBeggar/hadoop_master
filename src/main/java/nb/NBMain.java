package nb;

/**
 * @description:
 * @Author:bella
 * @Date:2019/10/2022:11
 * @Version:
 **/
public class NBMain {
    public static void main(String[] args) {
        NaiveBayesian bayesian = new NaiveBayesian();
        double d = 0;
        for (int i = 0; i < 50; i++) {
            d +=bayesian.testingNB();
        }
        System.out.println("total error rate is: " + d / 50);
    }
}
