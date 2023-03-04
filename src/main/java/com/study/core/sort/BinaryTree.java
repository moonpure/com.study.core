package com.study.core.sort;

public class BinaryTree {
    class Node { // 声明一个节点类
        private Comparable data; // 节点的数据类型为Comparable
        private Node left; // 保存左子树
        private Node right; // 保存右子树
        public Node(Comparable data) { // 构造函数
            this.data = data;
        }
        public void addNode(Node newNode) {
            // 确定是放在左子树还是右子树
            if (newNode.data.compareTo(this.data) < 0) { // 新节点值小于当前节点
                if (this.left == null) {
                    this.left = newNode; // 左子树为空的话，新节点设为左子树
                } else {
                    this.left.addNode(newNode); // 否则继续向下判断
                }
            } else { // 新节点的值大于或等于当前节点
                if (this.right == null) {
                    this.right = newNode;
                } else {
                    this.right.addNode(newNode);
                }
            }
        }
        public void printNode() { // 采用中序遍历
            if (this.left != null) { // 如果不为空先输出左子树
                this.left.printNode();
            }
            System.out.print(this.data + "\t"); // 输出当前根节点
            if (this.right != null) { // 输出右子树
                this.right.printNode();
            }
        }
    }
    private Node root; // 表示根元素
    public void add(Comparable data) { // 向二叉树中插入元素
        Node newNode = new Node(data);
        if (root == null) { // 没有根节点
            root = newNode;
        } else {
            root.addNode(newNode); // 判断放在左子树还是右子树
        }
    }
    public void print() {
        root.printNode(); // 根据根节点输出
    }
}