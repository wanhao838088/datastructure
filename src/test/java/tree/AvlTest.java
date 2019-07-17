package tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Created by LiuLiHao on 2019/7/15 0015 下午 09:30
 * @author : LiuLiHao
 * 描述：测试avl树
 */
public class AvlTest {

    @Test
    public void testHeight(){

        ArrayList<String> words = new ArrayList<String>();
        if(FileOperation.readFile("test.txt", words)) {
            System.out.println("总词数: " + words.size());

            AvlTree<String, Integer> tree = new AvlTree<String, Integer>();

            for (String word : words) {
                if (tree.contains(word)){
                    tree.set(word, tree.get(word) + 1);
                }
                else{
                    //重复
                    tree.insert(word, 1);
                    //System.out.println("重复");
                }
            }

            System.out.println("不重复单词数量: " + tree.size());
            System.out.println("是否是搜索树:  "+tree.isBST());
            System.out.println("是否是平衡树:  "+tree.isBalance());

        }

        System.out.println();
    }

    @Test
    public void test2(){
        int i = 1;

        //22500
        while (true){

            if (100 *i*i <= Math.pow(2,i)){
                System.out.println(i);
                break;
            }
            i++;
        }
    }

}
