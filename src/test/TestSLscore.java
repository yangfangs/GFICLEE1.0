package test;

import tree.Node;

import java.util.ArrayList;
import java.util.List;

import static gainModel.SLscore.getAllloss;
import static gainModel.SLscore.getLeafNode;

public class TestSLscore {
    public static void main(String[] args) {
//            create tree

        List<Node> allLeaf = new ArrayList();
        int[] array = new int[2*138-1];
        for (int x = 0; x < array.length; x++) {
            array[x] = x;
//                System.out.println(x);
        }

        Node binTree = new Node();
        List<Node> ww = binTree.createBinTree(array);
        Node root = ww.get(0);
//        System.out.println("先序遍历：");
//        preOrderTraverse(root);
//            System.out.println(root.data);

//        List<Node> allLeaf = Node.getLeaf(root);
        for (int i = 138;i>0;i--){
            allLeaf.add(ww.get(i));
        }



//            create profile
        int[][] ary = new int[25000][138];
        for (int line1 = 0; line1 < ary.length; line1++) {
            for (int line2 = 0; line2 < ary[line1].length; line2++) {
                ary[line1][line2] = (int) (Math.random() * 2);
            }

        }
        //do predict
        long startTime = System.currentTimeMillis();
        for (int i=0;i<ary.length;i++){
            int [] profile = ary[i];
            List<Node> getLeaf = getLeafNode(allLeaf,profile);
            List<List<Node>> result = getAllloss(root, getLeaf);
            System.out.println(result);

        }
        long endTime =System.currentTimeMillis();
        System.out.println("Time:" + (endTime-startTime)/1000);






//		        for (int x = 0; x<ary.length; x++){
//		        	for(int y =0; y<ary[x].length; y++){
//		        		System.out.print(ary[x][y]);
//		        	}
//		        	System.out.println();
//
//		        }


    }




}
