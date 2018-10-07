package test;

import gainModel.SCLscore;
import tree.ParseNewickTree;

import java.util.*;

public class TestSCLscore {
//           /-1A
// --------0|
//          |          /-3B
//           \2C-------|
//                    |          /-5D
//                     \4E-------|
//                               \-6G

    public static void main(String [] args) {
//        create the tree
        ParseNewickTree tree = ParseNewickTree.readNewickFormat("(A:0.350596,(B:0.728431,(D:0.609498,G:0.125729)1.000000:0.642905)1.000000:0.567737);");
        List<ParseNewickTree.Node> nodeList = tree.getNodeList();
        System.out.println(nodeList);
        Set<ParseNewickTree.Node> list1 = new HashSet<>();

        list1.add(tree.getNodebyLeafName("A"));
        list1.add(tree.getNodebyLeafName("D"));
//        list1.add(tree.getNodebyLeafName("G"));
        List<ParseNewickTree.Node> array = new ArrayList<>();
        boolean test = tree.getNodebyLeafName("A").getLeafNames(tree.getNodebyLeafName("A").getParent(), array);
        System.out.println(array);


        SCLscore foo = new SCLscore();
        Set<ParseNewickTree.Node> set1 = foo.getTopLossNode(list1);
//        System.out.println(set1.toString());
//        for(ParseNewickTree.Node node:set1){
//            System.out.println(node.getIdx());
//        }



        }

    }


