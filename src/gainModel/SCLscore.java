package gainModel;

import tree.Node;
import tree.ParseNewickTree;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SCLscore {
    private int[] profile;
    private List<String> allSpeName;
    private String treePath;
    private ParseNewickTree tree;

    public SCLscore(int[] profile, List<String> allSpeName, String treePath) {
        this.profile = profile;
        this.allSpeName = allSpeName;
        this.treePath = treePath;
        this.tree = ParseNewickTree.readNewickFormat("(A:0.350596,(B:0.728431,(D:0.609498,G:0.125729)1.000000:0.642905)1.000000:0.567737);");

    }


    public Set<ParseNewickTree.Node> getAllAbsenceNode(ParseNewickTree.Node gainNode) {
        List<ParseNewickTree.Node> array = new ArrayList<>();
        Set<ParseNewickTree.Node> absenceSet = new HashSet<>();

        gainNode.getLeafNames(gainNode,array);

        for(ParseNewickTree.Node node: array){
            if(this.profile[this.allSpeName.indexOf(node.getName())] == 0){
                absenceSet.add(node);
            }
        }
        return absenceSet;
    }


    public Set<ParseNewickTree.Node> getTopLossNode(Set<ParseNewickTree.Node> name) {
        List<ParseNewickTree.Node> listA = new ArrayList<>();
        Set<ParseNewickTree.Node> listSet = new HashSet<>();
//        System.out.println("****************");

        for (ParseNewickTree.Node line : name) {
            ParseNewickTree.Node temp = line.getParent();
//            System.out.println(temp.data);
            if (!name.contains(temp)) {
//                if(listA.contains(temp)){
//                    listSet.add(temp);
//                }else {
//                    listA.add(temp);
//                }
                listA.add(temp);

                listSet.add(temp);
            }
        }

        if (listA.size() == listSet.size()) {
            return listSet;

        }
        return getTopLossNode(listSet);
    }


    public Set<ParseNewickTree.Node> checkTopLossNode(Set<ParseNewickTree.Node> allLoss,Set<ParseNewickTree.Node> name){
        Set<ParseNewickTree.Node> result = new HashSet<>();
        List<ParseNewickTree.Node> leftLeafNode = new ArrayList<>();
        List<ParseNewickTree.Node> rightLeafNode = new ArrayList<>();
        for(ParseNewickTree.Node node:allLoss){

            ParseNewickTree.Node leftChild = node.getChildren().get(0);
            ParseNewickTree.Node rightChild = node.getChildren().get(1);


            if (leftChild.getChildren()!=null) {

                leftChild.getLeafNames(leftChild,leftLeafNode);
                if (name.containsAll(leftLeafNode)) {
                    result.add(leftChild);
                }
            }else {
                result.add(node);
            }

            if (rightChild.getChildren()!=null) {

                rightChild.getLeafNames(rightChild,rightLeafNode);

                if (name.containsAll(rightLeafNode)) {
                    result.add(rightChild);

                }
            }else {
                result.add(node);
            }

        }
        return result;
    }



    public int sumOfleafprofile(List<ParseNewickTree.Node> allLeafNode){
        int sum = 0;

        for(ParseNewickTree.Node node: allLeafNode){

            int idx = this.allSpeName.indexOf(node.getName());
            sum += this.profile[idx];

        }
        return sum;
    }


    public List<List<ParseNewickTree.Node>> getSingleAndContinueLoss(Set<ParseNewickTree.Node> allLossNode) {

        boolean isSingleLoss = false;
        List listSingle = new ArrayList();
        List listContinue = new ArrayList();
        List<List<ParseNewickTree.Node>> listAll = new ArrayList<>();
        List<ParseNewickTree.Node> leftChildLeafNode = new ArrayList();
        List<ParseNewickTree.Node> rightChildLeafNode = new ArrayList();


        for (ParseNewickTree.Node line : allLossNode) {
            List<ParseNewickTree.Node> tem = line.getChildren();
            if (tem != null) {
                ParseNewickTree.Node leftChild = tem.get(0);
                ParseNewickTree.Node rightChild = tem.get(1);

                leftChild.getLeafNames(leftChild,leftChildLeafNode);
                rightChild.getLeafNames(rightChild,rightChildLeafNode);

                if(this.sumOfleafprofile(leftChildLeafNode) == 0)
                    isSingleLoss = true;

                if(this.sumOfleafprofile(rightChildLeafNode) == 0)
                    isSingleLoss = true;

                if (isSingleLoss) {
                    listSingle.add(line);
                }else {
                    listContinue.add(line);
                }
            }

        }
        listAll.add(listSingle);
        listAll.add(listContinue);
        return listAll;


    }

}






