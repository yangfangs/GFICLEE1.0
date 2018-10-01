package gainModel;

import tree.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SLscore {

    public static Set getTopLossNode(Node gain, Set<Node> name) {
        List<Node> listA = new ArrayList<>();
        Set<Node> listSet = new HashSet<>();
//        System.out.println("****************");

        for (Node line : name) {
            Node temp = Node.getParentLate(gain, line);
//            System.out.println(temp.data);
            if (!name.contains(temp)) {
                listA.add(temp);
                listSet.add(temp);
            }
        }

        if (listA.size() == listSet.size()) {
            return listSet;

        }
        return getTopLossNode(gain, listSet);

    }

    public static List<List<Node>> getSingleAndContinueLoss(List<Node> allLossNode) {
        List listSingle = new ArrayList();
        List listContinue = new ArrayList();
        List<List<Node>> listAll = new ArrayList<List<Node>>();
        for (Node line : allLossNode) {
            List<Node> tem = Node.getChildren(line);
            if (tem != null) {
                Node leftChild = tem.get(0);
                Node rightChild = tem.get(1);
                if (Node.getChildren(leftChild) == null || Node.getChildren(rightChild) == null) {
                    listSingle.add(line);
                } else {
                    listContinue.add(line);
                }
            } else {
                listSingle.add(line);
            }
        }
        listAll.add(listSingle);
        listAll.add(listContinue);
        return listAll;
    }


//public static int getleafsum(Node top, List<Integer> allLeafNum, int[] profile){
//        int sum = 0;
//    List<Node> list = new ArrayList<Node>();
//    List<Integer> leafNum = new ArrayList<>();
//    list = Node.getLeaf(top);
//    for (Node line: list){
//        int idx = line.data;
//        sum += profile[allLeafNum.indexOf(idx)];
//    }
//return sum ;
//}




    public  static List<Node> getLeafNode(List<Node>allLeafNum, int[] profile){
        List<Node> list = new ArrayList<>();
        for(int i =0;i<profile.length;i++){
            if (profile[i]==0){
                list.add(allLeafNum.get(i));
            }

        }

        return list;
    }


    public static List<List<Node>> getAllloss(Node gain, List<Node>allLeafNum){
        Set<Node> tem = new HashSet<>();
        List<Node> tem2 = new ArrayList<>();

        for (Node all: allLeafNum){
            tem.add(all);
        }



        Set lastSet = getTopLossNode(gain, tem);

        for (Object all: lastSet){
            tem2.add((Node) all);
        }


        List<List<Node>> result = getSingleAndContinueLoss(tem2);
        return result;
    }
}



