package com.study.core.struct;

/**
 * TODO
 *
 * @author DELL
 * @version 1.0
 * @since 2022-04-24  10:06:39
 * 性质一:每个节点要么是黑色，要么是红色
 * 性质二:根节点是黑色
 * 性质三:每个叶子节点（NIL即NULL）
 * 性质四:每个红色节点的两个子节点一定是黑色
 * 性质五:任意一节点到两个叶子节点的路径都包含相同的黑节点，俗称黑高
 * <p>
 * 变色：结点的颜色由红变黑或由黑变红
 * 左旋：以某个结点作为作为支点（旋转节点），其左子结点变为旋转节点的父节点，左子节点的右子节点变为旋转节点的右子节点，右子节点保持不变
 * 右旋：以某个结点作为作为支点（旋转节点），其右子结点变为旋转节点的父节点，右子节点的左子节点变为旋转节点的左子节点，左子节点保持不变
 * <p>
 * 创建RBtree,定义颜色
 * 创建RBNode
 * 辅助定义方法:parentOf(node),isRed(node),setRed(node),setBlack(node),inOrderPrint()
 * 左旋方法定义:leftRotate(node)
 * 右旋方法定义:rightRotate(node)
 * 公开插入接口定义方法定义:insert(K key,V value)
 * 内部插入接口方法定义:insert(RBNode node)
 * 修正插入导致红黑树失衡的方法定义：insertFixUp(RBNode node)
 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private final boolean RED = true;
    private final boolean BLACK = false;

    private Node<K, V> root;


    private static final class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private boolean color;
        private Node<K, V> parent;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public Node<K,V> getLeft() {
            return this.left;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Node<K, V> getParent() {
            return parent;
        }

        public Node<K, V> getRight() {
            return right;
        }
    }

    /**
     * 左旋方法
     * 左旋示意图 左旋x结点
     * p               p
     * |               |
     * x   ---->       y
     * / \            / \
     * lx  y          x   ry
     * / \        / \
     * ly   ry     lx  ly
     */
    private void leftRotate(Node<K, V> x) {

        Node<K, V> y = x.right;

        x.right = y.left;

        if (x.parent != null) {
            y.parent = x.parent;

            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        } else {
            this.root = y;
            y.parent = null;
        }

        x.parent = y;

        y.left = x;
    }

    /**
     * 右旋方法
     * 右旋示意图
     * <p>
     * P                    P
     * |                    |
     * y                    x
     * / \    ------->      / \
     * x  ry                lx  y
     * / \                      / \
     * lx  ly                   ly  ry
     */
    private void RightRotate(Node<K, V> y) {
        // 1.将x的右子节点指向y的左子节点，并更新y的左子节点的父亲节点为x
        Node<K, V> x = y.left;

        y.left = x.right;
        // 2.当y的父节点不为空时，更新y的父节点为x的父节点
        if (y.parent != null) {
            x.parent = y.parent;
            //判断y是其父节点的左孩子还有右孩子
            if (y == y.parent.left) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }

        } else {
            this.root = x;
            x.parent = null;
        }
        //3.更新y的父节点为x,更新x的右子节点为y
        y.parent = x;
        x.right = y;

    }

    //是否是红色
    private boolean isRed(Node<K,V> node) {
        return node.color == RED;
    }

    //是否是红色
    private boolean isBlack(Node<K,V> node) {
        return node.color == BLACK;
    }

    //返回节点的父亲
    private Node<K, V> parentOf(Node<K,V> node) {
        return node.parent;
    }

    //设置红色
    private void setRED(Node<K,V> node) {
        node.color = RED;
    }

    //设置黑色
    public void setBLACK(Node<K,V> node) {
        node.color = BLACK;
    }

    //添加
    public void insert(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        node.setColor(RED);
        insert(node);
    }

    private void insert(Node<K, V> node) {
        Node<K, V> newRoot = root;
        Node<K, V> parent = null;

        while (newRoot != null) {
            parent = newRoot;
            if (node.key.compareTo(newRoot.key) > 0) {
                newRoot = newRoot.right;
            } else if (node.key.compareTo(newRoot.key) < 0) {
                newRoot = newRoot.left;
            } else {
                newRoot.value = node.value;
                return;
            }
        }
        node.parent = parent;

        if (parent == null) {
            this.root = node;
        } else if (parent.key.compareTo(node.key) > 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        insertFixUp(node);//修复红黑树
    }

    /**
     * 插入后修复红黑树平衡的方法
     * |---情景1:红黑树为空树
     * |---情景2:插入的节点key已经存在
     * |---情景3:插入的节点的父节点为黑色
     * |---情景4:插入节点的父亲节点为红色
     * |---情景4.1:叔叔节点存在，并且为红色(父-叔双红)，将爸爸和叔叔染成黑色，爷爷染成红色，进行下一轮处理
     * |---情景4.2:叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树
     * |---情景4.2.1:插入节点为其父节点的左子节点（LL情况），将爸爸染成黑色，将爷爷染成红色然后以爷爷为旋转节点 右旋
     * |---情景4.2.2:插入节点为其父节点的右子节点（LR情况），以爸爸节点进行一次左旋，得到LL双红的情景，然后指定爸爸节点为当前节点进行下一轮处理
     * |---情景4.3:叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树
     * |---情景4.3.1:插入节点为其父节点的右子节点（RR情况）将爸爸染成黑色，将爷爷染成红色然后以爷爷为旋转节点 左旋
     * |---情景4.3.2:插入节点为其父节点的左子节点（RL情况），以爸爸节点进行一次右旋，得到RR双红的情景，然后指定爸爸节点为当前节点进行下一轮处理
     */
    private void insertFixUp(Node<K, V> node) {
        Node<K,V> parent = node.parent;

        this.root.setColor(BLACK);
        //情景4:插入节点的父亲节点为红色
        if (parent != null && isRed(parent)) {
            //情景4.1:叔叔节点存在，并且为红色(父-叔双红)，将爸爸和叔叔染成黑色，爷爷染成红色，进行下一轮处理
            Node<K,V> gParent = parent.parent;
            Node<K,V> uncle = null;
            int cmp = gParent.key.compareTo(parent.key);
            if (cmp < 0) {
                uncle = gParent.left;
            } else if (cmp > 0) {
                uncle = gParent.right;
            }

            if (uncle != null && isRed(uncle)) {
                setBLACK(uncle);
                setBLACK(parent);
                setRED(gParent);
                insertFixUp(node);
                return;
            }

            if ((uncle == null || isBlack(uncle)) && cmp > 0) {
                if (parent.key.compareTo(node.key) > 0) {
                    setBLACK(parent);
                    setRED(gParent);
                    RightRotate(gParent);
                    return;
                } else {
                    leftRotate(parent);
                    insertFixUp(parent);
                    return;
                }
            } else {
                if (parent.key.compareTo(node.key) > 0) {
                    RightRotate(parent);
                    insertFixUp(parent);
                    return;
                } else {
                    setBLACK(parent);
                    setRED(gParent);
                    leftRotate(gParent);
                    return;
                }
            }

        }
    }

    //中序打印
    public void inOrderPrint() {

        if (this.root == null) return;

        inOrderPrint(this.root);
    }

    public Node<K,V> getRoot() {
        return this.root;
    }

    private void inOrderPrint(Node<K, V> node) {
        if (node == null) return;

        inOrderPrint(node.left);
        //System.out.printf("%2d(%s) is %2d's %6s child\n", tree.getKey(), isRed(tree) ? "R" : "B", key, direction == 1 ? "right" : "left");
        System.out.println("color:" + (isRed(node) ? "R" : "B") + ",key:" + node.key + ",value:" + node.value);
        inOrderPrint(node.right);
    }

}