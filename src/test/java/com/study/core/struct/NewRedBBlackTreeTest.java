package com.study.core.struct;

import org.junit.jupiter.api.Test;

class NewRedBBlackTreeTest {

    @Test
    void insert() {
        Integer a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        RedBlackTreeSingle<Integer, Integer> tree = new RedBlackTreeSingle<>();
        for (int i = 0; i < a.length; i++) {
            tree.insert(a[i],a[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            System.out.printf("== 添加节点: %d\n", a[i]);
            System.out.printf("== 树的详细信息: \n");
            System.out.printf("\n");
        }
        tree.inOrderPrint();
    }
}