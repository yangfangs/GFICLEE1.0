package gainModel;

import tree.ParseNewickTree;

import java.util.*;

import static utils.Utils.countRepet;

public class SCLScore {
    private int[] profile;
    private List<String> allSpeName;
    private String treeString;
    private ParseNewickTree tree;

    public SCLScore(List<String> allSpeName, String treeString) {

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


//    public boolean judgeGain(ParseNewickTree.Node gainA, ParseNewickTree.Node gainB){
//        List<ParseNewickTree.Node> arrayA = new ArrayList<>();
//        List<ParseNewickTree.Node> arrayB = new ArrayList<>();
//
//        gainA.getLeafNames(gainA,arrayA);
//        gainB.getLeafNames(gainA,arrayB);
//
//        arrayA.retainAll(arrayB);
//        if (arrayA.isEmpty()){
//            return true;
//        }else {
//            return false;
//        }
//
//    }
//
//
//    public boolean judgeInfoGene(ParseNewickTree.Node gainNode, int[] geneProfile){
//        boolean judge = false;
//        List<ParseNewickTree.Node> array = new ArrayList<>();
//        gainNode.getLeafNames(gainNode,array);
//
//        int sum = 0;
//        for(ParseNewickTree.Node node: array){
//            String name = node.getName();
//            int idx = allSpeName.indexOf(name);
//            if(geneProfile[idx] == 1)
//                sum +=1;
//        }
//        if (sum  <= array.size()/3 ) {
//            judge = true;
//        }
//
//        return judge;
//    }


    /**
     * @param gainNode The common gain Node
     * @param presence The presence of leaf node status
     */
    public void annoStatus(ParseNewickTree.Node gainNode, ParseNewickTree.Node presence){
//        if(presence.getParent() ==null){
//            return true;
//        }
        gainNode.setStatus(1);
        presence.setStatus(1);
////        while (!gainNode.equals(presence)){
////            presence.setStatus(1);
////            presence = presence.getParent();
////        }
//        if(gainNode.equals(presence)) {
//            return true;
//        }else return annoStatus(gainNode,presence.getParent());

        for(;presence != gainNode;presence = presence.getParent()){
            presence.setStatus(1);
        }


    }


    /**
     * @return the gain node
     */
    public ParseNewickTree.Node getGainNode() {

        List<ParseNewickTree.Node> presence = new ArrayList<>();
        ParseNewickTree.Node tems = null;
        for (int i = 0; i < profile.length; i++) {
            if (profile[i] == 1) {
                String SpeName = allSpeName.get(i);
                tems = tree.getNodeByLeafName(SpeName);
//                System.out.println("lalalal:" + tems.getName());
                presence.add(tree.getNodeByLeafName(allSpeName.get(i)));
            }
        }
//        Set<ParseNewickTree.Node> gainNodeSet = tems.getCommonAncestor(presence);
        ParseNewickTree.Node gainNode = tems.getCommonAncestor(presence);
//        Iterator it = gainNodeSet.iterator();

//        ParseNewickTree.Node gainNode = (ParseNewickTree.Node) it.next();

        for(ParseNewickTree.Node st:tree.getNodeList()){
            st.setStatus(0);
        }


        for(ParseNewickTree.Node presenceNode:presence){
            annoStatus(gainNode,presenceNode);
        }
        return gainNode;
    }

    /**
     * @param absenceNode the the absence node
     * @return parent node
     */
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


    /**
     * @param allAbsenceNode A set contains absence node
     * @return the single and continues loss node
     */
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




    }

    /**
     * @param gainNode gain node
     * @return a set contains all absence node
     */
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




}






