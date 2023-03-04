package com.study.core.struct;

import lombok.Getter;
import lombok.Setter;

import javax.swing.tree.DefaultTreeCellRenderer;
import java.util.TreeMap;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class DoubleLinkNode<E> {
    private DoubleLinkNode<E> next;
    private DoubleLinkNode<E> prev;
    private E item;
    //LinkedBlockingDeque
    //ReentrantReadWriteLock
    //
    //Phaser
  //TreeMap
    final ReentrantLock lock = new ReentrantLock();
}
