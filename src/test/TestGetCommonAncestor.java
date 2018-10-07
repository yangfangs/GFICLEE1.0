package test;

import tree.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGetCommonAncestor {

    public static void main(String[] args) {
                /*
create tree
                              /-14
                    /-------6|
                   |          \-13
                   |
          /-------2|
         |         |
         |         |          /-12
         |          \-------5|
         |                    \-11
         |
--------0|
         |
         |                    /-10
         |          /-------4|
         |         |          \-9
         |         |
          \-------1|
                   |
                   |          /-8
                    \-------3|
                              \-7



*/

        int[] array = new int[15];
        Arrays.setAll(array, x -> x);
//        System.out.println(Arrays.toString(array));
        Node binTree = new Node();
        List<Node> ww = binTree.createBinTree(array);
        Node root = ww.get(0);
        System.out.println("root id:" + root.getData());



//        Node comm = Node.getCommonRoot(root,binTree.getNode(3),binTree.getNode(6));
//            System.out.println(comm.getData());
//
        List<Node> list1 = new ArrayList<>();
        list1.add(binTree.getNode(7));
        list1.add(binTree.getNode(9));
        list1.add(binTree.getNode(10));
        list1.add(binTree.getNode(12));
        list1.add(binTree.getNode(14));
        Node comm = binTree.getCommonAncestor(root,list1);
        System.out.println("Comm is:"+ comm.getData());

    }


}
