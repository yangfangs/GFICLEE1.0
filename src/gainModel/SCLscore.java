package gainModel;

import tree.Node;
import tree.ParseNewickTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SCLscore {

    public Set<ParseNewickTree.Node> getTopLossNode(Set<ParseNewickTree.Node> name) {
        List<ParseNewickTree.Node> listA = new ArrayList<>();
        Set<ParseNewickTree.Node> listSet = new HashSet<>();
//        System.out.println("****************");

        for (ParseNewickTree.Node line : name) {
            ParseNewickTree.Node temp = line.getParent();
//            System.out.println(temp.data);
            if (!name.contains(temp)) {
                listA.add(temp);
                listSet.add(temp);
            }
        }

        if (listA.size() == listSet.size()) {
            return listSet;

        }
        return getTopLossNode(listSet);
    }

//        public void getSingleAndContinueLoss() {
//            List listSingle = new ArrayList();
//            List listContinue = new ArrayList();
//            List<List<Node>> listAll = new ArrayList<List<Node>>();
//
//
//
//        }







    }






