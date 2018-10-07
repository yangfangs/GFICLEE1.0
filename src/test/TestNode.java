package test;

import tree.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static tree.Node.preOrderTraverse;

public class TestNode {

    public static void main(String[] args) {
        //create tree
        int[] array = new int[7];
        Arrays.setAll(array, x -> x);
//        System.out.println(Arrays.toString(array));
        Node binTree = new Node();
        List<Node> ww = binTree.createBinTree(array);
        Node root = ww.get(0);
        //traverse
//        System.out.println("preOrderTraverse：");

        preOrderTraverse(root);
//        System.out.println("*****");


//        Node comm = Node.getCommonRoot(root,binTree.getNode(3),binTree.getNode(6));
//            System.out.println(comm.getData());
//
        List<Node> list1 = new ArrayList<>();
        list1.add(binTree.getNode(3));
        list1.add(binTree.getNode(4));
        Node comm = binTree.getCommonAncestor(root,list1);
        System.out.println("Comm is:"+ comm.getData());

    }


}
