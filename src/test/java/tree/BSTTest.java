package tree;

import org.junit.jupiter.api.Test;

/**
 * Created by LiuLiHao on 2019/7/15 0015 下午 08:03
 * @author : LiuLiHao
 * 描述：测试二分搜索树
 */
public class BSTTest {

    @Test
    public void testInsert(){
        BST<Integer,String> bst = new BST<Integer,String>();

        bst.insert(5,"老啊");
        bst.insert(15,"是个");
        bst.insert(3,"啥都干");
        bst.insert(1,"昂达");

        //最大key
        System.out.println(bst.getMaxKey());
        //最小key
        System.out.println(bst.getMinKey());
    }

    @Test
    public void testRemove(){
        BST<Integer,String> bst = new BST<Integer,String>();

        bst.insert(5,"老啊");
        bst.insert(15,"是个");
        bst.insert(3,"啥都干");
        bst.insert(1,"昂达");

        bst.removeMin();
        System.out.println(bst);
    }
}
