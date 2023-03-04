package com.study.core.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvlTreeNode<E,K extends Comparable<K>> {
    private K key; // 结点的键，用于标识结点在AVL树中的位置
    private E item; // 结点的值，用于存储数据
    private int height; // 结点的高度
    private AvlTreeNode<E,K> left; // 结点的左指针
    private AvlTreeNode<E,K> right; // 结点的右指针
    public AvlTreeNode(K key, E value) {
        this.key = key;
        this.item = value;
        height = 1;
    }
}
