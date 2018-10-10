package test;

import gainModel.SCLscore;
import tree.ParseNewickTree;

import java.util.*;

public class TestSCLscore {
//           /-1A
// --------0|
//          |          /-3B
//           \2C------|
//                    |          /-5D
//                     \4E------|
//                               \-6G

    public static void main(String [] args) {
//        create the tree
        ParseNewickTree tree = ParseNewickTree.readNewickFormat("(A:0.350596,(B:0.728431,(D:0.609498,G:0.125729)1.300000:0.642905)1.000000:0.567737);");
        List<ParseNewickTree.Node> nodeList = tree.getNodeList();
        System.out.println(nodeList);
        for(ParseNewickTree.Node node:nodeList){
            System.out.print(node +":");
            System.out.println(node.getStatus());
        }


        Set<ParseNewickTree.Node> list1 = new HashSet<>();

        list1.add(tree.getNodebyLeafName("B"));
        list1.add(tree.getNodebyLeafName("D"));
//        list1.add(tree.getNodebyLeafName("G"));
        List<ParseNewickTree.Node> array = new ArrayList<>();
        boolean test = tree.getNodebyLeafName("A").getLeafNames(tree.getNodebyLeafName("A").getParent(), array);
        System.out.println(array);
        int[] profile = {0,1,1,1};
        List<String> allSpeName = new ArrayList(Arrays.asList("A", "B", "D", "G"));
        System.out.println("www" + allSpeName.indexOf("A"));


        SCLscore foo = new SCLscore(allSpeName,"(A:0.350596,(B:0.728431,(D:0.609498,G:0.125729)1.300000:0.642905)1.000000:0.567737);");
        foo.setProfile(profile);
        Set<ParseNewickTree.Node> set1 = foo.getTopLossNode(list1);
        Set<ParseNewickTree.Node> set2 = foo.checkTopLossNode(set1,list1);
        ParseNewickTree.Node gainNode = foo.getGainNode();
        ParseNewickTree tree2 = foo.getParseNewickTree();
        System.out.println("I am gainNode:" + gainNode.toString());


        List<ParseNewickTree.Node> nodeList2 = tree2.getNodeList();
        Set<ParseNewickTree.Node> setww = new HashSet<>();

        setww.add(tree2.getNodebyLeafName("B"));
        setww.add(tree2.getNodebyLeafName("D"));

        for(ParseNewickTree.Node node:nodeList2){
            System.out.print(node +":");
            System.out.println(node.getStatus());
        }

        List<List<ParseNewickTree.Node>> fff = foo.getAllSingleAndContinueLoss(setww);



        System.out.println("gainNode:" + gainNode);



        System.out.println(set1.toString());
        System.out.println(set2.toString());
        List<List<ParseNewickTree.Node>> list = foo.getSingleAndContinueLoss(set2);
        System.out.println("fff" + fff.toString());
//
//
//        System.out.println(set1.toString());
//        for(ParseNewickTree.Node node:set1){
//            System.out.println(node.getIdx());
//        }



        }

    }


