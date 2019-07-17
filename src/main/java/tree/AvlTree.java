package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuLiHao on 2019/7/15 0015 下午 07:46
 * @author : LiuLiHao
 * 描述：avl树
 */
public class AvlTree<K extends Comparable<K>,V> {

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
        int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            //默认高度为1
            height = 1;
        }
    }

    /**
     * 对节点进行右旋转
     * @param y
     */
    private Node<K,V> rightRotate(Node<K,V> y){
        Node<K, V> x = y.left;
        Node<K, V> t3 = x.right;
        //旋转
        x.right = y;
        y.left = t3;
        //从新计算高度
        y.height = Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height = Math.max(getHeight(x.left),getHeight(x.right))+1;

        return x;
    }

    /**
     * 对节点进行左旋转
     * @param y
     * @return
     */
    private Node<K,V> leftRotate(Node<K,V> y){
        Node<K, V> x = y.right;
        Node<K, V> t2 = x.left;
        //旋转
        x.left = y;
        y.right = t2;
        //计算高度
        y.height = Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height = Math.max(getHeight(x.left),getHeight(x.right))+1;

        return x;
    }
    /**
     * 是否是一颗搜索树
     * @return
     */
    public boolean isBST(){
        //使用中序遍历的特点判断是否有序
        List<K> list = new ArrayList<K>();
        inOrder(root,list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1).compareTo(list.get(i))>0){
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是一颗平衡树
     * @return
     */
    public boolean isBalance(){
        return isBalance(root);
    }

    private boolean isBalance(Node<K, V> node) {
        if (node==null){
            return true;
        }
        //判断平衡因子
        int factor = getBalanceFactor(node);
        if (Math.abs(factor)>1){
            return false;
        }
        return isBalance(node.left) && isBalance(node.right);
    }

    /**
     * 中序遍历
     * @param node
     * @param list
     */
    private void inOrder(Node<K, V> node, List<K> list) {
        if (node==null){
            return;
        }
        inOrder(node.left,list);
        list.add(node.key);
        inOrder(node.right,list);
    }

    /**
     * 获取高度
     * @param node
     * @return
     */
    private int getHeight(Node<K,V> node){
        if (node==null){
            return 0;
        }
        return node.height;
    }

    /**
     * 获取平衡因子
     *  = 左树高度-右树高度
     * @param node
     * @return
     */
    private int getBalanceFactor(Node<K,V> node){
        if (node==null){
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
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
     * get
     * @param key
     * @return
     */
    public V get(K key) {
        Node<K, V> node = getNode(root, key);
        return node==null?null:node.value;
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
        //计算node的高度
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;

        int factor = getBalanceFactor(root);
        //分别判断不平衡的情况
        //LL
        if (factor>1 && getBalanceFactor(root.left)>=0){
            //右旋
            return rightRotate(root);
        }
        //RR
        if (factor<-1 &&getBalanceFactor(root.right)<=0){
            //左旋
            return leftRotate(root);
        }
        //LR
        if (factor>1 && getBalanceFactor(root.left)<0){
            //先左旋再右旋
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        //RL
        if (factor<-1 && getBalanceFactor(root.right)>0){
            //先右旋再左旋
            root.right = rightRotate(root.right);
            return leftRotate(root);
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
