package tree;

/**
 * Created by LiuLiHao on 2019/7/15 0015 下午 07:46
 * @author : LiuLiHao
 * 描述：红黑树
 */
public class RBTree<K extends Comparable<K>,V> {

    /**
     * 红色
     */
    private static final boolean RED = true;

    /**
     * 黑色
     */
    private static final boolean BLACK = false;

    /**
     * 二叉树根节点
     */
    private Node<K,V> root;

    /**
     * 元素个数
     */
    private int size = 0;

    /**
     * 节点
     * @param <K>
     * @param <V>
     */
    static class Node<K,V>{
        K key;
        V value;
        Node<K,V> left;
        Node<K,V> right;
        boolean color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            //默认为红色
            color = RED;
        }
    }

    /**
     * 右旋转
     * @param node
     * @return
     */
    private Node<K,V> rightRotate(Node<K,V> node){
        Node<K, V> x = node.left;
        node.left = x.right;
        x.right = node;

        //颜色修改
        x.color = node.color;
        node.color = RED;

        return x;
    }

    /**
     * 左旋转
     * @param node
     * @return
     */
    private Node<K,V> leftRotate(Node<K,V> node){
        Node<K, V> x = node.right;
        node.right = x.left;
        x.left = node;
        //颜色修改
        x.color = node.color;
        node.color = RED;

        return x;
    }

    /**
     * 是否为红色节点
     * @param node
     * @return
     */
    private boolean isRed(Node<K,V> node){
        return node==null?BLACK:node.color;
    }

    /**
     * 反转颜色
     * @param node
     */
    private void filpColor(Node<K,V> node){
        //自身为红色 子节点为黑色
        node.color = RED;
        if (node.right!=null){
            node.right.color = BLACK;
        }
        if (node.left!=null){
            node.left.color = BLACK;
        }
    }
    /**
     * 移除指定的key
     * @param key
     */
    public V remove(K key){
        //先判断要删除的有没有
        Node<K, V> rmNode = getNode(root, key);
        if (rmNode!=null){
            //删除
            root = remove(root,key);
            return rmNode.value;
        }
        return null;
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node==null){
            return null;
        }

        if (node.key.compareTo(key)<0){
            //往右
            node.right = remove(node.right,key);
            return node;
        }else if(node.key.compareTo(key)>0){
            //往左
            node.left = remove(node.left,key);
            return node;
        }else {
            //匹配了
            if (node.left==null){
                Node<K, V> right = node.right;
                node.right = null;
                size--;
                return right;
            }
            if (node.right==null){
                Node<K, V> left = node.left;
                node.left = null;
                size--;
                return left;
            }
            //左右都不为空 找到右子树中最小的元素 代替这个节点
            Node<K, V> minNode = getMinNode(node.right);
            minNode.right = removeMin(node.right);
            minNode.left = node.left;
            node.left = node.right = null;

            return minNode;
        }
    }


    /**
     * 是否包含
     * @param key
     * @return
     */
    public boolean contains(K key){
        return getNode(root,key)!=null;
    }
    /**
     * 更新值
     * @param key
     * @param value
     */
    public void set(K key,V value){
        Node<K, V> node = getNode(root, key);
        if (node!=null){
            node.value = value;
        }
    }

    /**
     * 获取最小元素
     * @param node
     * @return
     */
    private Node<K,V> getMinNode(Node<K,V> node) {
        if (node.left==null){
            return node;
        }
        return getMinNode(node.left);
    }

    /**
     * 获取最大元素
     * @param node
     * @return
     */
    private Node<K,V> getMaxNode(Node<K,V> node) {
        if (node.right==null){
            return node;
        }
        return getMaxNode(node.right);
    }

    public Node<K,V> getNode(Node<K, V> node,K key){
        if (node==null){
            return null;
        }
        if (node.key.compareTo(key)<0){
            //往右
            return getNode(node.right,key);
        }else{
            //往左
            return getNode(node.left,key);
        }
    }
    /**
     * 移除最小节点
     */
    public void removeMin(){
        root = removeMin(root);
    }

    private Node<K, V> removeMin(Node<K, V> node) {
        if (node.left==null){
            //最小值
            Node<K, V> right = node.right;
            size--;
            node.right = null;
            return right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 移除最大节点
     */
    public void removeMax(){
        root = removeMax(root);
        size--;
    }

    private Node<K, V> removeMax(Node<K, V> root) {
        if (root.right==null){
            Node<K, V> left = root.left;
            root.left = null;
            return left;
        }
        root.right = removeMax(root.right);

        return root;
    }

    /**
     * 获取最大的key
     * @return
     */
    public K getMaxKey(){
        return getMaxKey(root).key;
    }

    /**
     * 获取最小的Key
     * @return
     */
    public K getMinKey(){
        return getMinKey(root).key;
    }

    /**
     * 获取最小的节点
     * @param root
     * @return
     */
    private Node<K,V> getMinKey(Node<K, V> root) {
        if (root!=null && root.left!=null){
            return getMinKey(root.left);
        }
        return root;
    }

    /**
     * 获取最大的节点
     * @param root
     * @return
     */
    private Node<K,V> getMaxKey(Node<K, V> root) {
        if (root!=null && root.right!=null){
            return getMaxKey(root.right);
        }
        return root;
    }

    /**
     * 插入一个元素
     * @param key
     * @param value
     */
    public void insert(K key,V value){
        root = insert(root,key,value);
        //根节点为黑色
        root.color = BLACK;
    }

    private Node<K, V> insert(Node<K, V> root, K key, V value) {
        if (root==null){
            Node<K, V> node = new Node<K, V>(key, value);
            size++;

            return node;
        }
        //根据大小判断在左边还是右边
        if (root.key.compareTo(key)<0){
            //放到右边
            root.right = insert(root.right,key,value);
        }else if (root.key.compareTo(key)>0){
            //放到左边
            root.left = insert(root.left,key,value);
        }else {
            //相同了 更新值
            root.value = value;
        }

        //维护红黑树性质
        if (isRed(root.right) && !isRed(root.left)){
            //左旋
            root = leftRotate(root);
        }
        if (isRed(root.left) && isRed(root.left.left)){
            //右旋
            root = rightRotate(root);
        }
        //反转颜色
        if (isRed(root.left) && isRed(root.right)){
            filpColor(root);
        }
        return root;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return size==0;
    }

    /**
     * 节点个数
     * @return
     */
    public int size(){
        return size;
    }

}
