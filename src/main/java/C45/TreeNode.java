package C45;

import java.util.*;

/**
 * Created by fangyitao on 2019/10/22.
 */
public class TreeNode {
    private String attributeValue;
    private List<TreeNode> childTreeNode; // 子结点集合
    private List<String> pathName;
    private String targetFunValue;
    private String nodeName; // 节点名（分裂属性的名称）

    public TreeNode(String nodeName){

        this.nodeName = nodeName;
        this.childTreeNode = new ArrayList<TreeNode>();
        this.pathName = new ArrayList<String>();
    }

    public TreeNode(){
        this.childTreeNode = new ArrayList<TreeNode>();
        this.pathName = new ArrayList<String>();
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public List<TreeNode> getChildTreeNode() {
        return childTreeNode;
    }

    public void setChildTreeNode(List<TreeNode> childTreeNode) {
        this.childTreeNode = childTreeNode;
    }

    public String getTargetFunValue() {
        return targetFunValue;
    }

    public void setTargetFunValue(String targetFunValue) {
        this.targetFunValue = targetFunValue;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<String> getPathName() {
        return pathName;
    }

    public void setPathName(List<String> pathName) {
        this.pathName = pathName;
    }
}
