package com.study.core.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * 完美二叉树
 */
@Getter
@Setter
public class PerfectBinaryTree<T> {
    private T dataNode;
    private PerfectBinaryTree<T> leftNode;
    private PerfectBinaryTree<T> rightNode;
    private PerfectBinaryTree<T> parentNode;
    /**
     * 指定层创建
     * @param layer
     */
    public void createBinaryTree(Integer layer) {

        if (layer <= 1) {
            dataNode = (T) layer;
            return;
        }
        if (layer > 1) {
            PerfectBinaryTree<T> left = new PerfectBinaryTree<>();
            PerfectBinaryTree<T> right = new PerfectBinaryTree<>();

            left.parentNode = this;
            right.parentNode = this;

            this.leftNode = left;
            this.rightNode = right;

            left.createBinaryTree(layer - 1);
            right.createBinaryTree(layer - 1);
        }
    }

    /**
     * 按层赋值
     */
    public void setValue() {
        if (this.parentNode == null) {
            Integer pdata = 1;
            this.dataNode = (T) pdata;
        }
        Integer parentv = (Integer) this.dataNode;

        PerfectBinaryTree cleft = this.leftNode;
        if (cleft != null) {
            Integer ldata = parentv * 2;
            cleft.dataNode = ((T) (ldata));
            cleft.setValue();
        }
        PerfectBinaryTree cright = this.rightNode;
        if (cright != null) {
            Integer rdata = parentv * 2 + 1;
            cright.dataNode = (T) (rdata);
            cright.setValue();
        }
    }

    //递归遍历
    public void levelOrder() {
        if (this == null) {
            return;
        }
        PerfectBinaryTree<T> current = this;
        System.out.println(this.dataNode);//前
        if (current.leftNode != null)//如果当前节点的左节点不为空入队
        {
            current.leftNode.levelOrder();
        }
        //System.out.println(this.dataNode);//中
        if (current.rightNode != null)//如果当前节点的右节点不为空，把右节点入队
        {
            current.rightNode.levelOrder();
        }
        //System.out.println(this.nodeData);//后
    }

    //层序遍历
    public List<T> levelOrderTraversal() {
        List<T> list = new ArrayList<>();
        if (this == null) {
            return list;
        }
        Queue<PerfectBinaryTree<T>> queue = new LinkedList<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                PerfectBinaryTree<T> node = queue.poll();
                list.add(node.dataNode);
                if (node.leftNode != null) {
                    queue.offer(node.leftNode);
                }
                if (node.rightNode != null) {
                    queue.offer(node.rightNode);
                }
            }
        }
        return list;
    }
    //前序遍历非递归实现
    public List<T> preOrderTraversal() {
        List<T> list = new ArrayList<>();
        if(this==null)
        {
            return list;
        }
        //非递归实现的本质是栈
        Deque<PerfectBinaryTree<T>> stack = new LinkedList<>();
        PerfectBinaryTree<T> current=this;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                list.add(current.dataNode);
                stack.push(current);
                current = current.leftNode;
            }
            current = stack.poll();
            current = current.rightNode;
        }
        return list;

    }
    //中序遍历非递归实现
    public List<T> inOrderTraversal() {
        List<T> list = new ArrayList<>();
        if(this==null)
        {
            return list;
        }
        //非递归实现的本质是栈
        Deque<PerfectBinaryTree<T>> stack = new LinkedList<>();
        PerfectBinaryTree<T> current=this;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.leftNode;
            }
            current = stack.poll();
            list.add(current.dataNode);
            current = current.rightNode;
        }
        return list;
    }
    //后序遍历非递归实现
    public List<T> postOrderTraversalTemp() {
        List<T> list = new ArrayList<>();
        if(this==null)
        {
            return list;
        }
        //非递归实现的本质是栈
        Deque<PerfectBinaryTree<T>> stack = new LinkedList<>();
        PerfectBinaryTree<T> prev = null;
        PerfectBinaryTree<T> current=this;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.leftNode;
            }
            //取出最左下节点
            current = stack.poll();
            if (current.rightNode == prev || current.rightNode == null) {
                list.add(current.dataNode);
                prev = current;
                //如果不置为null为被再压栈，比如一颗二叉树只有权值为1的根节点
                current = null;
            } else {
                stack.push(current);
                current = current.rightNode;
            }
        }
        return list;
    }
    //后序遍历非递归实现
    public List<T> postOrderTraversal() {
        List<T> list = new ArrayList<>();
        if(this==null)
        {
            return list;
        }
        //非递归实现的本质是栈
        Deque<PerfectBinaryTree<T>> stack = new LinkedList<>();
        PerfectBinaryTree<T> current=this;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                list.add(current.dataNode);
                stack.push(current);
                current = current.rightNode;
            }
            current = stack.poll();
            current = current.leftNode;
        }
        return list;
    }
}
