package com.study.core.struct;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PerfectBinaryTreeTest {

    @Test
    void createBinaryTree() {
        PerfectBinaryTree<Integer> tree =new PerfectBinaryTree<>();
        tree.createBinaryTree(3);
    }

    @Test
    void setValue() {
    }

    @Test
    void postOrderTraversals() {
        PerfectBinaryTree<Integer> tree =new PerfectBinaryTree<>();
        tree.createBinaryTree(4);
        tree.setValue();
        List<Integer> treeV=tree.levelOrderTraversal();
    }
}