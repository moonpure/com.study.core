package com.study.core.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedBlackTreeNode<T,K extends Comparable<K>> {
    private Boolean color;        // 颜色
    private  K key;                // 关键字(键值)
    private T item;//数据项
    private  RedBlackTreeNode<T, K> left;    // 左孩子
    private RedBlackTreeNode<T, K> right;    // 右孩子
    private  RedBlackTreeNode<T, K> parent;    // 父结点

    public RedBlackTreeNode(T item,K key, boolean color, RedBlackTreeNode<T, K> parent, RedBlackTreeNode<T, K> left, RedBlackTreeNode<T, K> right) {
        this.key = key;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.item=item;
    }
}


