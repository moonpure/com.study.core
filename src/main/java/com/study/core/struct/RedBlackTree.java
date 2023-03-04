package com.study.core.struct;

public class RedBlackTree<T, K extends Comparable<K>> {

    private RedBlackTreeNode<T, K> root;    // 根结点

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RedBlackTree() {
        root = null;
    }

    private RedBlackTreeNode<T, K> parentOf(RedBlackTreeNode<T, K> node) {
        return node != null ? node.getParent() : null;
    }

    private boolean colorOf(RedBlackTreeNode<T, K> node) {
        return node != null ? node.getColor() : BLACK;
    }

    private boolean isRed(RedBlackTreeNode<T, K> node) {
        return ((node != null) && (node.getColor() == RED)) ? true : false;
    }

    private boolean isBlack(RedBlackTreeNode<T, K> node) {
        return !isRed(node);
    }

    private void setBlack(RedBlackTreeNode<T, K> node) {
        if (node != null)
            node.setColor(BLACK);
    }

    private void setRed(RedBlackTreeNode<T, K> node) {
        if (node != null)
            node.setColor(RED);
    }

    private void setParent(RedBlackTreeNode<T, K> node, RedBlackTreeNode<T, K> parent) {
        if (node != null)
            node.setParent(parent);
    }

    private void setColor(RedBlackTreeNode<T, K> node, boolean color) {
        if (node != null)
            node.setColor(color);
    }

    /*
     * 前序遍历"红黑树"
     */
    private void preOrder(RedBlackTreeNode<T, K> tree) {
        if (tree != null) {
            // System.out.print(tree.key+" ");
            preOrder(tree.getLeft());
            preOrder(tree.getRight());
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    /*
     * 中序遍历"红黑树"
     */
    private void inOrder(RedBlackTreeNode<T, K> tree) {
        if (tree != null) {
            inOrder(tree.getLeft());
            //System.out.print(tree.key+" ");
            inOrder(tree.getRight());
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    /*
     * 后序遍历"红黑树"
     */
    private void postOrder(RedBlackTreeNode<T, K> tree) {
        if (tree != null) {
            postOrder(tree.getLeft());
            postOrder(tree.getRight());
        }
    }

    public void postOrder() {
        postOrder(root);
    }


    /*
     * (递归实现)查找"红黑树x"中键值为key的节点
     */
    private RedBlackTreeNode<T, K> search(RedBlackTreeNode<T, K> x, K key) {
        if (x == null)
            return x;

        int cmp = key.compareTo(x.getKey());
        if (cmp < 0)
            return search(x.getLeft(), key);
        else if (cmp > 0)
            return search(x.getRight(), key);
        else
            return x;
    }

    public RedBlackTreeNode<T, K> search(K key) {
        return search(root, key);
    }

    /*
     * (非递归实现)查找"红黑树x"中键值为key的节点
     */
    private RedBlackTreeNode<T, K> iterativeSearch(RedBlackTreeNode<T, K> x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.getKey());

            if (cmp < 0)
                x = x.getLeft();
            else if (cmp > 0)
                x = x.getRight();
            else
                return x;
        }

        return x;
    }

    public RedBlackTreeNode<T, K> iterativeSearch(K key) {
        return iterativeSearch(root, key);
    }

    /*
     * 查找最小结点：返回tree为根结点的红黑树的最小结点。
     */
    private RedBlackTreeNode<T, K> minimum(RedBlackTreeNode<T, K> tree) {
        if (tree == null)
            return null;

        while (tree.getLeft() != null)
            tree = tree.getLeft();
        return tree;
    }

    public K minimum() {
        RedBlackTreeNode<T, K> p = minimum(root);
        if (p != null)
            return p.getKey();

        return null;
    }

    /*
     * 查找最大结点：返回tree为根结点的红黑树的最大结点。
     */
    private RedBlackTreeNode<T, K> maximum(RedBlackTreeNode<T, K> tree) {
        if (tree == null)
            return null;

        while (tree.getRight() != null)
            tree = tree.getRight();
        return tree;
    }

    public K maximum() {
        RedBlackTreeNode<T, K> p = maximum(root);
        if (p != null)
            return p.getKey();

        return null;
    }

    /*
     * 找结点(x)的后继结点。即，查找"红黑树中数据值大于该结点"的"最小结点"。
     */
    public RedBlackTreeNode<T, K> successor(RedBlackTreeNode<T, K> x) {
        // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
        if (x.getRight() != null)
            return minimum(x.getRight());

        // 如果x没有右孩子。则x有以下两种可能：
        // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
        // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
        RedBlackTreeNode<T, K> y = x.getParent();
        while ((y != null) && (x == y.getRight())) {
            x = y;
            y = y.getParent();
        }

        return y;
    }

    /*
     * 找结点(x)的前驱结点。即，查找"红黑树中数据值小于该结点"的"最大结点"。
     */
    public RedBlackTreeNode<T, K> predecessor(RedBlackTreeNode<T, K> x) {
        // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
        if (x.getLeft() != null)
            return maximum(x.getLeft());

        // 如果x没有左孩子。则x有以下两种可能：
        // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
        // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
        RedBlackTreeNode<T, K> y = x.getParent();
        while ((y != null) && (x == y.getLeft())) {
            x = y;
            y = y.getParent();
        }

        return y;
    }

    /*
     * 对红黑树的节点(x)进行左旋转
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)-.           / \                #
     *  lx   y                          x  ry
     *     /   \                       /  \
     *    ly   ry                     lx  ly
     *
     *
     */
    private void leftRotate(RedBlackTreeNode<T, K> x) {
        // 设置x的右孩子为y
        RedBlackTreeNode<T, K> y = x.getRight();

        // 将 “y的左孩子” 设为 “x的右孩子”；
        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        x.setLeft(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }

        // 将 “x的父亲” 设为 “y的父亲”
        y.setParent(x.getParent());

        if (x.getParent() == null) {
            this.root = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
        } else {
            if (x.getParent().getLeft() == x)
                x.getParent().setLeft(y);    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            else
                x.getParent().setRight(y);    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
        }

        // 将 “x” 设为 “y的左孩子”
        y.setLeft(x);
        // 将 “x的父节点” 设为 “y”
        x.setRight(y);
    }

    /*
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)-.            /  \                     #
     *        x   ry                           lx   y
     *       / \                                   / \                   #
     *      lx  rx                                rx  ry
     *
     */
    private void rightRotate(RedBlackTreeNode<T, K> y) {
        // 设置x是当前节点的左孩子。
        RedBlackTreeNode<T, K> x = y.getLeft();

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.setLeft(x.getRight());
        if (x.getRight() != null)
            x.getRight().setParent(y);

        // 将 “y的父亲” 设为 “x的父亲”
        x.setParent(y.getParent());

        if (y.getParent() == null) {
            this.root = x;            // 如果 “y的父亲” 是空节点，则将x设为根节点
        } else {
            if (y == y.getParent().getParent())
                y.getParent().setRight(x);    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
            else
                y.getParent().setLeft(x);    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
        }

        // 将 “y” 设为 “x的右孩子”
        x.setRight(y);

        // 将 “y的父节点” 设为 “x”
        y.setParent(x);
    }

    /*
     * 红黑树插入修正函数
     *
     * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     * 参数说明：
     *     node 插入的结点        // 对应《算法导论》中的z
     */
    private void insertFixUp(RedBlackTreeNode<T, K> node) {
        RedBlackTreeNode<T, K> parent, gparent;

        // 若“父节点存在，并且父节点的颜色是红色”
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);

            //若“父节点”是“祖父节点的左孩子”
            if (parent == gparent.getLeft()) {
                // Case 1条件：叔叔节点是红色
                RedBlackTreeNode<T, K> uncle = gparent.getRight();
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.getRight() == node) {
                    RedBlackTreeNode<T, K> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {    //若“z的父节点”是“z的祖父节点的右孩子”
                // Case 1条件：叔叔节点是红色
                RedBlackTreeNode<T, K> uncle = gparent.getLeft();
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.getLeft() == node) {
                    RedBlackTreeNode<T, K> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        // 将根节点设为黑色
        setBlack(this.root);
    }

    /*
     * 将结点插入到红黑树中
     *
     * 参数说明：
     *     node 插入的结点        // 对应《算法导论》中的node
     */
    private void insert(RedBlackTreeNode<T, K> node) {
        int cmp;
        RedBlackTreeNode<T, K> y = null;
        RedBlackTreeNode<T, K> x = this.root;

        // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
        while (x != null) {
            y = x;
            cmp = node.getKey().compareTo(x.getKey());
            if (cmp < 0)
                x = x.getLeft();
            else
                x = x.getRight();
        }

        node.setParent(y);
        if (y != null) {
            cmp = node.getKey().compareTo(y.getKey());
            if (cmp < 0)
                y.setLeft(node);
            else
                y.setRight(node);
        } else {
            this.root = node;
        }

        // 2. 设置节点的颜色为红色
        node.setColor(RED);

        // 3. 将它重新修正为一颗二叉查找树
        insertFixUp(node);
    }

    /*
     * 新建结点(key)，并将其插入到红黑树中
     *
     * 参数说明：
     *     key 插入结点的键值
     */
    public void insert(T data, K key) {
        RedBlackTreeNode<T, K> node = new RedBlackTreeNode<T, K>(data, key, BLACK, null, null, null);

        // 如果新建结点失败，则返回。
        if (node != null)
            insert(node);
    }


    /*
     * 红黑树删除修正函数
     *
     * 在从红黑树中删除插入节点之后(红黑树失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     * 参数说明：
     *     node 待修正的节点
     */
    private void removeFixUp(RedBlackTreeNode<T, K> node, RedBlackTreeNode<T, K> parent) {
        RedBlackTreeNode<T, K> other;

        while ((node == null || isBlack(node)) && (node != this.root)) {
            if (parent.getLeft() == node) {
                other = parent.getRight();
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.getRight();
                }

                if ((other.getLeft() == null || isBlack(other.getLeft())) &&
                        (other.getRight() == null || isBlack(other.getRight()))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.getRight() == null || isBlack(other.getRight())) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.getLeft());
                        setRed(other);
                        rightRotate(other);
                        other = parent.getRight();
                    }
                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.getRight());
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {

                other = parent.getLeft();
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.getLeft();
                }

                if ((other.getLeft() == null || isBlack(other.getLeft())) &&
                        (other.getRight() == null || isBlack(other.getRight()))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.getLeft() == null || isBlack(other.getLeft())) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.getRight());
                        setRed(other);
                        leftRotate(other);
                        other = parent.getLeft();
                    }

                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.getLeft());
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }

        if (node != null)
            setBlack(node);
    }

    /*
     * 删除结点(node)，并返回被删除的结点
     *
     * 参数说明：
     *     node 删除的结点
     */
    private void remove(RedBlackTreeNode<T, K> node) {
        RedBlackTreeNode<T, K> child, parent;
        boolean color;

        // 被删除节点的"左右孩子都不为空"的情况。
        if ((node.getLeft() != null) && (node.getRight() != null)) {
            // 被删节点的后继节点。(称为"取代节点")
            // 用它来取代"被删节点"的位置，然后再将"被删节点"去掉。
            RedBlackTreeNode<T, K> replace = node;

            // 获取后继节点
            replace = replace.getRight();
            while (replace.getLeft() != null)
                replace = replace.getLeft();

            // "node节点"不是根节点(只有根节点不存在父节点)
            if (parentOf(node) != null) {
                if (parentOf(node).getLeft() == node)
                    parentOf(node).setLeft(replace);
                else
                    parentOf(node).setRight(replace);
            } else {
                // "node节点"是根节点，更新根节点。
                this.root = replace;
            }

            // child是"取代节点"的右孩子，也是需要"调整的节点"。
            // "取代节点"肯定不存在左孩子！因为它是一个后继节点。
            child = replace.getRight();
            parent = parentOf(replace);
            // 保存"取代节点"的颜色
            color = colorOf(replace);

            // "被删除节点"是"它的后继节点的父节点"
            if (parent == node) {
                parent = replace;
            } else {
                // child不为空
                if (child != null)
                    setParent(child, parent);
                parent.setLeft(child);

                replace.setRight(node.getRight());
                setParent(node.getRight(), replace);
            }

            replace.setParent(node.getParent());
            replace.setColor(node.getColor());
            replace.setLeft(node.getLeft());
            node.getLeft().setParent(replace);

            if (color == BLACK)
                removeFixUp(child, parent);

            node = null;
            return;
        }

        if (node.getLeft() != null) {
            child = node.getLeft();
        } else {
            child = node.getRight();
        }

        parent = node.getParent();
        // 保存"取代节点"的颜色
        color = node.getColor();

        if (child != null)
            child.setParent(parent);

        // "node节点"不是根节点
        if (parent != null) {
            if (parent.getLeft() == node)
                parent.setLeft(child);
            else
                parent.setRight(child);
        } else {
            this.root = child;
        }

        if (color == BLACK)
            removeFixUp(child, parent);
        node = null;
    }

    /*
     * 删除结点(z)，并返回被删除的结点
     *
     * 参数说明：
     *     tree 红黑树的根结点
     *     z 删除的结点
     */
    public void remove(K key) {
        RedBlackTreeNode<T, K> node;

        if ((node = search(root, key)) != null)
            remove(node);
    }

    /*
     * 销毁红黑树
     */
    private void destroy(RedBlackTreeNode<T, K> tree) {
        if (tree == null)
            return;

        if (tree.getLeft() != null)
            destroy(tree.getLeft());
        if (tree.getRight() != null)
            destroy(tree.getRight());

        tree = null;
    }

    public void clear() {
        destroy(root);
        root = null;
    }

    /*
     * 打印"红黑树"
     *
     * key        -- 节点的键值
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(RedBlackTreeNode<T, K> tree, K key, int direction) {

        if (tree != null) {
            if (direction == 0){    // tree是根节点
                System.out.printf("%2d(B) is root\n", tree.getKey());
            } else{// tree是分支节点
                System.out.printf("%2d(%s) is %2d's %6s child\n", tree.getKey(), isRed(tree) ? "R" : "B", key, direction == 1 ? "right" : "left");
            }
            print(tree.getLeft(), tree.getKey(), -1);
            print(tree.getRight(), tree.getKey(), 1);
        }
    }

    public void print() {
        if (root != null)
            print(root, root.getKey(), 0);
    }
}

