package test;

import tree.ParseNewickTree;

import java.util.*;

public class TestParseNewickTree {


//           /-1A
// --------0|
//          |          /-3B
//           \2C-------|
//                    |          /-5D
//                     \4E-------|           /-F
//                               \-6G-------|
//                                           \-H
    public static void main(String [] args){
        ParseNewickTree tree = ParseNewickTree.readNewickFormat("(A:0.350596,(B:0.728431,(D:0.609498,G:0.125729,(F:0.22,H:0.33))1.000000:0.642905)1.000000:0.567737);");
//        System.out.println(tree.toString());
        List<ParseNewickTree.Node> nodeList = tree.getNodeList();
        System.out.println(nodeList.get(6).getParent() == null);
        Set<Integer> testww = new HashSet<>();
        testww.add(null);
        System.out.println(testww.size());
        System.out.println(testww == null);


        System.out.println(nodeList);
        for(ParseNewickTree.Node node:nodeList){
            System.out.print(node +":");
            System.out.println(node.hashCode());
        }

        for(ParseNewickTree.Node x: nodeList){
            System.out.println(x.getName());
        }

//        test get all leaf node

        List<ParseNewickTree.Node> array = new ArrayList<>();
        boolean test = tree.getNodebyLeafName("A").getLeafNames(tree.getNodebyLeafName("A").getParent(), array);
        System.out.println(array);
        System.out.println("www:");

//        Set<ParseNewickTree.Node> set1 = new HashSet<>();
        List<ParseNewickTree.Node> set1 = new ArrayList<>();
        set1.add(tree.getNodebyLeafName("A"));
//        set1.add(tree.getNodebyLeafName("A"));
        set1.add(tree.getNodebyLeafName("F"));
        set1.add(tree.getNodebyLeafName("H"));
        System.out.println("www:");
        ParseNewickTree.Node set2 = tree.getNodebyLeafName("A").getCommonAncestor(set1);

        System.out.println("www:"+set2.toString());

        List<ParseNewickTree.Node> testzzz = new ArrayList<>();

        int high = tree.getNodebyLeafName("A").high(tree.getNodebyLeafName("B"));
        System.out.println(high);

//        System.out.println(nodeList.get(0).children);


//        String s = "(A,(B,(D,G)));";
//        int leftParen = s.indexOf('(');
//        System.out.println(leftParen);
//        int rightParen = s.lastIndexOf(')');
//        System.out.println(rightParen);
//        String name = s.substring(leftParen + 1, rightParen);
//        System.out.println(name);
//
//        String[] childrenString = split(s.substring(leftParen + 1, rightParen));
//        System.out.println(Arrays.toString(childrenString));
//        for(String idx:childrenString){
//            System.out.println(idx);
//        }
    }
}
