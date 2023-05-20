package com.study.core.struct;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 完美二叉树
 */
@Getter
@Setter
public class PerfectBinaryTree<T> {
    private T nodeData;
    private PerfectBinaryTree<T> leftNode;
    private PerfectBinaryTree<T> rightNode;
    private PerfectBinaryTree<T> parentNode;
    public void createBinaryTree(Integer layer) {

        if (layer <= 1) {
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
    public void setValue()
    {
        if(this.parentNode==null)
        {
            Integer pdata=1;
            this.nodeData=(T)pdata;
        }
        Integer parentv= (Integer)this.nodeData;

        PerfectBinaryTree cleft=this.leftNode;
        if(cleft!=null)
        {
            Integer ldata=parentv*2;
            cleft.nodeData=((T)(ldata));
            cleft.setValue();
        }
        PerfectBinaryTree cright=this.rightNode;
        if(cright!=null)
        {
            Integer rdata=parentv*2+1;
            cright.nodeData=(T)(rdata);
            cright.setValue();
        }
    }
}
