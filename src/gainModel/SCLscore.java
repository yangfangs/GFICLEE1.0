package gainModel;

import tree.ParseNewickTree;

import java.util.*;

import static utils.Utils.countRepet;

public class SCLscore {
    private int[] profile;
    private List<String> allSpeName;
    private String treeString;
    private ParseNewickTree tree;

    public SCLscore(List<String> allSpeName, String treeString) {

        this.allSpeName = allSpeName;
        this.treeString = treeString;
        this.tree = ParseNewickTree.readNewickFormat(this.treeString);

    }

    public void setProfile(int[] profile) {
        this.profile = profile;
    }

    public ParseNewickTree getParseNewickTree(){
        return this.tree;
    }


    public boolean judgeGain(ParseNewickTree.Node gainA, ParseNewickTree.Node gainB){
        List<ParseNewickTree.Node> arrayA = new ArrayList<>();
        List<ParseNewickTree.Node> arrayB = new ArrayList<>();

        gainA.getLeafNames(gainA,arrayA);
        gainB.getLeafNames(gainA,arrayB);

        arrayA.retainAll(arrayB);
        if (arrayA.isEmpty()){
            return true;
        }else {
            return false;
        }

    }




    public void annoStatus(ParseNewickTree.Node gainNode, ParseNewickTree.Node present){
//        if(present.getParent() ==null){
//            return true;
//        }
        gainNode.setStatus(1);
        present.setStatus(1);
////        while (!gainNode.equals(present)){
////            present.setStatus(1);
////            present = present.getParent();
////        }
//        if(gainNode.equals(present)) {
//            return true;
//        }else return annoStatus(gainNode,present.getParent());

        for(;present != gainNode;present = present.getParent()){
            present.setStatus(1);
        }


    }


    public ParseNewickTree.Node getGainNode() {

        List<ParseNewickTree.Node> present = new ArrayList<>();
        ParseNewickTree.Node tems = null;
        for (int i = 0; i < profile.length; i++) {
            if (profile[i] == 1) {
                String SpeName = allSpeName.get(i);
                tems = tree.getNodebyLeafName(SpeName);
//                System.out.println("lalalal:" + tems.getName());
                present.add(tree.getNodebyLeafName(allSpeName.get(i)));
            }
        }
//        Set<ParseNewickTree.Node> gainNodeSet = tems.getCommonAncestor(present);
        ParseNewickTree.Node gainNode = tems.getCommonAncestor(present);
//        Iterator it = gainNodeSet.iterator();

//        ParseNewickTree.Node gainNode = (ParseNewickTree.Node) it.next();

        for(ParseNewickTree.Node st:tree.getNodeList()){
            st.setStatus(0);
        }


        for(ParseNewickTree.Node presentNode:present){
            annoStatus(gainNode,presentNode);
        }
        return gainNode;
    }

    public ParseNewickTree.Node getParentAnno(ParseNewickTree.Node absenceNode){
        ParseNewickTree.Node abs = null;
//        if(absenceNode.getParent() ==null){
//            return tree.getRoot();
//        }
//        if (absenceNode.getStatus() == 1){
//            return absenceNode;
//        }else return getParentAnno(absenceNode.getParent());
        for(;absenceNode.getStatus() != 1; absenceNode = absenceNode.getParent()){

                abs = absenceNode.getParent();
        }
        return abs;


    }



    public List<List<ParseNewickTree.Node>> getAllSingleAndContinueLoss(Set<ParseNewickTree.Node> allAbsenceNode){
        List<ParseNewickTree.Node> allNode = new ArrayList<>();
        List listSingle = new ArrayList();
        List listContinue = new ArrayList();
        List<List<ParseNewickTree.Node>> listAll = new ArrayList<>();

        Iterator it = allAbsenceNode.iterator();
        while (it.hasNext()){
            ParseNewickTree.Node node = (ParseNewickTree.Node) it.next();
            allNode.add(getParentAnno(node));
        }

        Map<ParseNewickTree.Node, Integer> map = countRepet(allNode);
        Iterator<Map.Entry<ParseNewickTree.Node,Integer>> ite = map.entrySet().iterator();
        while (ite.hasNext()){
            Map.Entry<ParseNewickTree.Node,Integer> entry = ite.next();

            if(entry.getValue() == 1){
                if(entry.getKey() != tree.getRoot())
                    listSingle.add(entry.getKey());
            }else {
                listContinue.add(entry.getKey());
            }


        }

        listAll.add(listSingle);
        listAll.add(listContinue);

        return listAll;
//        System.out.println("lalalal" + allNode.toString());





    };

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
            if(line == null){
                return listSet;
            }
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
        allLoss.remove(null);
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






