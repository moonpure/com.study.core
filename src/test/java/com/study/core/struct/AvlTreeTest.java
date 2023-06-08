package com.study.core.struct;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AvlTreeTest {

    @Test
    void insert() {
        HashSet<Integer> keySet = new HashSet<>();
        for (int i = 0; i < 100; ++i) {
            keySet.add(getRandomKey());
        }
        Integer[] keyArray = new Integer[keySet.size()];
        keyArray = keySet.toArray(keyArray);
        List<Integer> keyList = Arrays.asList(keyArray);

        AvlTree<Integer,Integer> avlTree=new AvlTree<>();
        System.out.println("keyArray总长度：" + keyArray.length);
        System.out.println("~~~~~~~~~~~~~~开始插入节点：");
        for (Integer integer : keyArray) {
            System.out.println("-----------当前节点总数：" + avlTree.countNodes());
            System.out.println("-----------本次将插入节点：" + integer);
            avlTree.insert(new AvlTreeNode(integer, integer));
            System.out.println("-----------插入后AVL树字符串描述：");
            System.out.println(avlTree.getVisualizeString());
            System.out.println();
        }


    }
    private int getRandomKey() {
        return (int) (Math.random() * 100);
    }

}