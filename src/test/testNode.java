package test;

import tree.Node;

import java.util.Arrays;
import java.util.List;
import static tree.Node.preOrderTraverse;

public class testNode {

    public static void main(String[] args) {
        //create tree
        int[] array = new int[100];
        Arrays.setAll(array, x -> x);
        System.out.println(Arrays.toString(array));
        Node binTree = new Node();
        List<Node> ww = binTree.createBinTree(array);
        Node root = ww.get(0);
        //traverse
        System.out.println("preOrderTraverse：");
        preOrderTraverse(root);
            System.out.println(root.data);

    }


}
