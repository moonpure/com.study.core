package com.study.core.struct;

public class AvlTree<E, K extends Comparable<K>> {
    private AvlTreeNode<E, K> root;
    private final StringBuilder visualizeBuilder; // 二叉树图形化解析的builder

    public AvlTree() {
        visualizeBuilder = new StringBuilder();
    }

    /**
     * 平衡调整
     */
    private AvlTreeNode<E, K> reBalance(AvlTreeNode<E, K> avlNode) {
        // 获取avlNode的平衡因子
        int altitudeDiff = getAltitudeDiff(avlNode);

        if (Math.abs(altitudeDiff) <= 1) {
            return avlNode;
        }
        if (altitudeDiff < 0) { // LL旋转或者RL旋转
            // avlNode的右子树的平衡因子只可能为-1|0|1
            if (getAltitudeDiff(avlNode.getRight()) == 1) {
                // avlNode的右子树右旋
                avlNode.setRight(rightRevolve(avlNode.getRight()));
            }
            // avlNode左旋
            return leftRevolve(avlNode);
        } else { // RR旋转或者LR旋转
            // avlNode的左子树的平衡因子只可能为-1|0|1
            if (getAltitudeDiff(avlNode.getLeft()) == -1) {
                // avlNode的左子树左旋
                avlNode.setLeft(leftRevolve(avlNode.getLeft()));
            }
            // avlNode右旋
            return rightRevolve(avlNode);
        }
    }

    /**
     * 左旋
     */
    private AvlTreeNode<E, K> leftRevolve(AvlTreeNode<E, K> avlNode) {
        // 先保存avlNode的右子树
        AvlTreeNode<E, K> right = avlNode.getRight();

        // avlNode的右指针指向right的左子树
        avlNode.setRight(right.getLeft());

        // right的左指针指向avlNode
        right.setLeft(avlNode);

        // 先更新avlNode的高度
        resetHeight(avlNode);

        // 再更新right的高度
        resetHeight(right);

        // 返回right作为调整后的root
        return right;
    }

    /**
     * 右旋
     */
    private AvlTreeNode<E, K> rightRevolve(AvlTreeNode<E, K> avlNode) {
        // 保存avlNode的左子树
        AvlTreeNode<E, K> left = avlNode.getLeft();

        // avlNode的左指针指向left的右子树
        avlNode.setLeft(left.getRight());

        // left的右指针指向avlNode
        left.setRight(avlNode);

        // 先更新avlNode的高度
        resetHeight(avlNode);

        // 再更新left的高度
        resetHeight(left);

        // 返回left作为调整后的root
        return left;
    }

    /**
     * 获取结点高度差（平衡因子）
     */
    private int getAltitudeDiff(AvlTreeNode<E, K> avlNode) {
        return (avlNode.getLeft() != null ? avlNode.getLeft().getHeight() : 0) -
                (avlNode.getRight() != null ? avlNode.getRight().getHeight() : 0);
    }

    /**
     * 插入结点
     */
    public void insert(AvlTreeNode<E, K> newNode) {
        root = insert(newNode, root);
    }

    private AvlTreeNode<E, K> insert(AvlTreeNode<E, K> newNode, AvlTreeNode<E, K> root) {
        if (root == null) {
            // 找到了正确的位置，直接返回新结点
            return newNode;
        }
        if (root.getKey().compareTo(newNode.getKey()) > 0) {
            // 新结点的key比当前结点的小，因此向当前结点的左子树递归
            root.setLeft(insert(newNode, root.getLeft()));
        } else if (root.getKey().compareTo(newNode.getKey()) < 0) {
            // 新结点的key比当前结点的大，因此向当前结点的右子树递归
            root.setRight(insert(newNode, root.getRight()));
        } else {
            // 两个结点的key相同，因此直接更新value并返回
            root.setItem(newNode.getItem());
            return root;
        }
        resetHeight(root); // 在调整之前，更新当前结点的高度
        return reBalance(root); // 调整并返回
    }

    /**
     * 删除结点
     */
    public boolean delete(K key) {
        if (root == null) {
            return false;
        }

        // delete方法返回null的情况：待删除的是唯一一个结点
        AvlTreeNode<E, K> delete = delete(key, root);

        // 待删除的结点存在
        if (delete != null || root.getLeft() == null && root.getRight() == null && key.compareTo(root.getKey()) == 0) {
            root = delete;
            return true;
        }

        // 待删除的结点不存在
        return false;
    }

    private AvlTreeNode<E, K> delete(K key, AvlTreeNode<E, K> avlNode) {
        if (avlNode == null) { // 待删除的结点不存在
            return null;
        }
        if (avlNode.getKey().compareTo(key) > 0) {
            // 对avlNode的左子树递归调用方法
            avlNode.setLeft(delete(key, avlNode.getLeft()));
        } else if (avlNode.getKey().compareTo(key) < 0) {
            // 对avlNode的右子树递归调用方法
            avlNode.setRight(delete(key, avlNode.getRight()));
        } else {
            if (avlNode.getLeft() == null && avlNode.getRight() == null) { // 待删除的结点没有左右子树
                // 返回空表示删除当前结点（avlNode）
                return null;
            } else if (avlNode.getRight() == null) { // 待删除的结点只有左子树
                // 保存avlNode的左子树
                AvlTreeNode<E, K> left = avlNode.getLeft();

                // 清空avlNode的左指针
                avlNode.setItem(null);

                // 用avlNode的左子树接替avlNode
                return left;
            } else if (avlNode.getLeft() == null) { // 待删除的结点只有右子树
                // 保存avlNode的右子树
                AvlTreeNode<E, K> right = avlNode.getRight();

                // 清空avlNode的右指针
                avlNode.setRight(null);

                // 用avlNode的右子树接替avlNode
                return right;
            } else { // 待删除的结点有左右子树
                // 获取左子树的最右结点
                AvlTreeNode<E, K> mostRightNodeOfLeftTree = getMostRightNode(avlNode.getLeft());

                // 删除待删除结点的左子树的最右结点，由于此时最右结点没有右子树，因此只涉及两种返回情况：
                // 1、只有左子树
                // 2、没有左右子树
                // 由于可能会发生旋转，因此newRoot不一定等于avlNode
                AvlTreeNode<E, K> newRoot = delete(mostRightNodeOfLeftTree.getKey(), avlNode);

                // 将avlNode的数据更新为左子树的最右结点的数据，此时avlNode的高度已在上一步更新
                avlNode.setKey(mostRightNodeOfLeftTree.getKey());
                avlNode.setItem(mostRightNodeOfLeftTree.getItem());

                // 返回调整之后的以原待删除结点为根结点的AVL树的根结点
                return newRoot;
            }
        }
        resetHeight(avlNode); // 在调整之前，更新当前结点的高度
        return reBalance(avlNode); // 调整并返回
    }

    /**
     * 重新设置给定结点的高度
     */
    private void resetHeight(AvlTreeNode<E, K> avlNode) {
        avlNode.setHeight(Math.max(
                avlNode.getLeft() == null ? 0 : avlNode.getLeft().getHeight(),
                avlNode.getRight() == null ? 0 : avlNode.getRight().getHeight()
        ) + 1);
    }

    /**
     * 获取给定结点的最右子结点
     */
    private AvlTreeNode<E, K> getMostRightNode(AvlTreeNode<E, K> avlNode) {
        return avlNode.getRight() == null ? avlNode : getMostRightNode(avlNode.getRight());
    }

    /**
     * 检查AVL树是否平衡
     */
    public boolean checkBalance() {
        if (root != null) {
            if (Math.abs(getAltitudeDiff(root)) > 1) {
                return false;
            }
            return checkBalance(root.getLeft()) && checkBalance(root.getRight());
        }
        return true;
    }

    private boolean checkBalance(AvlTreeNode<E, K> root) {
        if (root != null) {
            if (Math.abs(getAltitudeDiff(root)) > 1) {
                return false;
            }
            return checkBalance(root.getLeft()) && checkBalance(root.getRight());
        }
        return true;
    }

    /**
     * 设置用于二叉树图形化解析的builder
     * 图形化解析网址：http://mshang.ca/syntree
     */
    public String getVisualizeString() {
        visualizeBuilder.setLength(0);
        setVisualizeBuilder(root, visualizeBuilder);
        return visualizeBuilder.toString();
    }

    private void setVisualizeBuilder(AvlTreeNode<E, K> avlNode, StringBuilder builder) {
        if (avlNode == null) {
            builder.append("[null]");
            return;
        }
        builder.append("[").append(avlNode.getKey()).append("-").append(avlNode.getHeight());
        setVisualizeBuilder(avlNode.getLeft(), builder);
        setVisualizeBuilder(avlNode.getRight(), builder);
        builder.append("]");
    }

    /**
     * 获取二叉树的结点总数
     */
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(AvlTreeNode<E, K> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        return 1 + countNodes(avlNode.getLeft()) + countNodes(avlNode.getRight());
    }

    /**
     * 查找结点值
     */
    public E get(K key) {
        return get(key, root);
    }

    private E get(K key, AvlTreeNode<E, K> root) {
        if (root == null) {
            return null;
        }
        if (root.getKey().compareTo(key) < 0) {
            return get(key, root.getRight());
        }
        if (root.getKey().compareTo(key) > 0) {
            return get(key, root.getLeft());
        }
        return root.getItem();
    }
}