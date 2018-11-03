//package test;
//
//import gainModel.SCLScore;
//import tree.ParseNewickTree;
//
//import java.util.*;
//
//public class TestSCLscore {
////           /-1A
//// --------0|
////          |          /-3B
////           \2C------|
////                    |          /-5D
////                     \4E------|
////                               \-6G
//
//    public static void main(String [] args) {
////        create the tree
//        ParseNewickTree tree = ParseNewickTree.readNewickFormat("(A:0.350596,(B:0.728431,(D:0.609498,G:0.125729)1.300000:0.642905)1.000000:0.567737);");
//        List<ParseNewickTree.Node> nodeList = tree.getNodeList();
//        System.out.println(nodeList);
//        for(ParseNewickTree.Node node:nodeList){
//            System.out.print(node +":");
//            System.out.println(node.getStatus());
//        }
//
//
//        Set<ParseNewickTree.Node> list1 = new HashSet<>();
//
//        list1.add(tree.getNodeByLeafName("B"));
//        list1.add(tree.getNodeByLeafName("D"));
////        list1.add(tree.getNodeByLeafName("G"));
//        List<ParseNewickTree.Node> array = new ArrayList<>();
//        boolean test = tree.getNodeByLeafName("A").getLeafNames(tree.getNodeByLeafName("A").getParent(), array);
//        System.out.println(array);
//        int[] profile = {1,0,1,0};
//        List<String> allSpeName = new ArrayList(Arrays.asList("A", "B", "D", "G"));
//        System.out.println("www" + allSpeName.indexOf("A"));
//
//
//        SCLScore foo = new SCLScore(allSpeName,"(A:0.350596,(B:0.728431,(D:0.609498,G:0.125729)1.300000:0.642905)1.000000:0.567737);");
//        foo.setProfile(profile);
//        ParseNewickTree.Node gainNode = foo.getGainNode();
//        ParseNewickTree tree2 = foo.getParseNewickTree();
//        System.out.println("I am gainNode:" + gainNode.toString());
//
//
//        List<ParseNewickTree.Node> nodeList2 = tree2.getNodeList();
//        Set<ParseNewickTree.Node> setww = new HashSet<>();
//
//        setww.add(tree2.getNodeByLeafName("B"));
//        setww.add(tree2.getNodeByLeafName("G"));
//
//        for(ParseNewickTree.Node node:nodeList2){
//            System.out.print(node +":");
//            System.out.println(node.getStatus());
//        }
//
//        List<List<ParseNewickTree.Node>> fff = foo.getAllSingleAndContinueLoss(setww);
//
//
//
//        System.out.println("gainNode:" + gainNode);
//
//
//        System.out.println("fff" + fff.toString());
//
//        ParseNewickTree.Node parentAnno = foo.getParentAnno(tree2.getNodeByLeafName("B"));
//        System.out.println(parentAnno);
////
////
////        System.out.println(set1.toString());
////        for(ParseNewickTree.Node node:set1){
////            System.out.println(node.getIdx());
////        }
//
//
//
//        }
//
//    }
//
//
