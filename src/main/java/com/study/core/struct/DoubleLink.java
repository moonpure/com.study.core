package com.study.core.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class DoubleLink<T> {

    private  int size;
    private Node<T> first;
    private Node<T> last;
    private void examplecode()
    {
        //private static final long serialVersionUID = -387911632671998426L;
        //LinkedBlockingDeque
        //ReentrantReadWriteLock
        //Phaser
        //TreeMap
        int[] a=new int[10];
        Arrays.sort(a);
    }

    final ReentrantLock lock = new ReentrantLock();
    private static final class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }
    /**
     * 添加节点到头部
     * */
    private boolean offerFirst(  Node<T> node ){
        if (size == 0) {
            first = node;
            last = first;
        }else {
            node.next = first;
            first.prev = node;
            first = node;
        }
        size++;
        return true;

    }
    /**
     * 添加节点到头部.加锁
     * */
    public boolean addFirst(T item) {
        if (item == null) throw new NullPointerException();
        Node<T> node = new Node<>(item);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return offerFirst(node);
        } finally {
            lock.unlock();
        }
    }
    /**
     * 添加节点到尾部,加锁
     * */
    public boolean addLast(T item){
        if (item == null) throw new NullPointerException();
        Node<T> node = new Node<>(item);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return offerLast(node);
        } finally {
            lock.unlock();
        }
    }
    /**
     * 添加节点到尾部
     * */
   private boolean offerLast( Node<T> item)
   {
       if (size == 0){
           return offerFirst(item);
       }else {
           last.next = item;
           item.prev = last;
           last = item;
       }
       size++;
       return true;
   }
    /**
     * 元素添加到指定位置
     * */
    public boolean add(int index,T item){
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("数据下标越界 Index:" + index + "\tsize:" + size);
        } else if(index == 0){
            return addFirst( item);
        }else if (index == size){
            return addLast( item);
        }else {
            Node<T> node = new Node<>(item);
            Node<T> head = first;
            for (int i = 0; i < index-1; i++) {
                head = head.next;
            }
            node.next = head.next;
            head.next = node;
            node.prev = head;
            node.next.prev = node;
        }
        size++;
        return true;
    }

    /**
     * 删除头结点
     * */
    public T removeFirst(){
        if (size == 0){
            throw new RuntimeException("链表为空！");
        }
        T data = first.item;
        Node<T> node  = first.next;
        node.prev=null;
        first = node;
        return data;
    }

    /**
     * 删除尾节点
     * */
    public T removeLast(){
        if (size == 0){
            throw new RuntimeException("链表为空！");
        }
        T data = last.item;
        Node<T> node = last.prev;
        node.next=null;
        last = node;
        return data;
    }

    /**
     * 删除指定下标结点
     * */
    public T remove(int index){
        if (size == 0){
            throw new RuntimeException("链表为空！");
        }
        //注意添加的时候，下标取不到size，但是添加的位置可以是size，但是删除的时候不行
        if (index < 0 || index > size-1){
            throw new IndexOutOfBoundsException("数据下标越界 Index:" + index + "\tsize:" + size);
        } else if(index == 0){
            return removeFirst();
        }else {
            Node<T>  node = first;
            for (int i = 0; i < index - 1; i++) {
                node = node.next;
            }
            Node<T>  temp = node.next;
            if (temp != last){
                T data = temp.item;
                node.next = temp.next;
                temp.next.prev = node;
                temp.next=null;
                return data;
            }else {
                return removeLast();
            }
        }
    }

    /**
     * 获取对应下标数据
     * */
    public T getData(int index){
        if (index<0 || index>size-1){
            throw new IndexOutOfBoundsException("数据下标越界 Index:" + index + "\tsize:" + size);
        }else if (size == 0){
            throw new RuntimeException("链表为空");
        }else if (size == 1){
            return first.item;
        }else {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node.item;
        }
    }

    /**
     * 清空链表
     * */
    public void clear(){
        first.next = null;
        last = first;
    }

    /**
     * 遍历输出当前所有数据
     * */
    public void print(){
        if (size == 0) {
            System.out.println("该链表为空!");
        }
        Node<T> node = first;
        while (node != null) {
            System.out.print(node.item + "\t");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 详细遍历输出:
     *      前驱节点值
     *      当前节点值
     *      后继节点值
     * */
    public void detailPrint(){
        if (size == 0) {
            System.out.println("该链表为空!");
        }
        Node<T> node = first;
        while (node != null) {
            System.out.print("前驱节点值：");
            System.out.printf("%-5s",node.prev == null ? "null\t" : node.prev.item+"\t");
            System.out.print("当前节点值：");
            System.out.printf("%-6s",node.item+ "\t");
            System.out.print("后继节点值：");
            System.out.printf("%-5s",node.next == null ? "null\t" : node.next.item+"\t");
            System.out.println();
            node = node.next;
        }
        System.out.println();
    }
}
