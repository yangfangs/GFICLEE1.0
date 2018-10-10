package test;

import gainModel.SCLscore;
import io.FileInput;
import predict.ParseGeneSet;
import predict.ParseProfile;
import tree.ParseNewickTree;

import java.util.List;
import java.util.Set;

public class TestSCLscore2 {

    public static void main(String[] args) {
//        prepare profile
        ParseProfile profile = new ParseProfile("/home/yangfang/PCSF/test_java_gificlee/all_kegg_matrix_re111.txt");
        profile.prepareData();
//        prepare gene set
        ParseGeneSet geneSet = new ParseGeneSet("/home/yangfang/PCSF/no_report/test_re111/input/0_0.txt");
        geneSet.prapareData();
//        read tree
        FileInput tree = new FileInput("/home/yangfang/PCSF/clime_roc/species111.abbrev.manual_binary.nwk");
        String treeString = tree.readString();

//        get single loss and continue loss

        SCLscore sclscore = new SCLscore(profile.getSpeciesNames(), treeString);
        List<int[]> allProfile = profile.getProfile();
        int x = profile.getSymbol().indexOf("XPC");
        sclscore.setProfile(allProfile.get(x));

        ParseNewickTree.Node gainNode = sclscore.getGainNode();

        Set<ParseNewickTree.Node> allAbsenceNode = sclscore.getAllAbsenceNode(gainNode);

        List<List<ParseNewickTree.Node>> fff = sclscore.getAllSingleAndContinueLoss(allAbsenceNode);
        System.out.println(fff.get(0).size());
        System.out.println(fff.get(1).size());


        Set<ParseNewickTree.Node> allSCLoss = sclscore.getTopLossNode(allAbsenceNode);

        Set<ParseNewickTree.Node> allSCLoss2 = sclscore.checkTopLossNode(allSCLoss, allAbsenceNode);


        List<List<ParseNewickTree.Node>> result = sclscore.getSingleAndContinueLoss(allSCLoss2);

//        System.out.println(gainNode);
//        System.out.println(allAbsenceNode.size());
//        System.out.println(allSCLoss.size());
//        System.out.println(allSCLoss2.size());

//        System.out.println(result.toString());


    }


}




