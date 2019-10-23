package cart;

/**
 * Created by fangyitao on 2019/10/23.
 */
public class Client {
    public static void main(String[] args){
        String filePath = "F:\\springcloud\\hadoop_master\\src\\main\\java\\cart\\data.txt";

        CARTTool tool = new CARTTool(filePath);

        tool.startBuildingTree();
    }
}
