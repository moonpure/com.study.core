package com.study.core.struct;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @Test
    void insert() {
        Integer a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        ;

        for (int i = 0; i < a.length; i++) {
            tree.insert(a[i],a[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            System.out.printf("== 添加节点: %d\n", a[i]);
            System.out.printf("== 树的详细信息: \n");
            tree.print();
            System.out.printf("\n");
        }

    }
}