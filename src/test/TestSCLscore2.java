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
        ParseProfile profile = new ParseProfile("/home/yangfang/GFICLEE/test_kegg_gficlee/hsa.matrix138.e3.q00.p20.txt");
        profile.prepareData();
//        prepare gene set
        ParseGeneSet geneSet = new ParseGeneSet("/home/yangfang/PCSF/no_report/test_re111/input/0_0.txt");
        geneSet.prapareData();
//        read tree
        FileInput tree = new FileInput("/home/yangfang/GFICLEE/test_kegg_gficlee/species138.abbrev.manual_binary.nwk");
        String treeString = tree.readString();

//        get single loss and continue loss

        SCLscore sclscore = new SCLscore(profile.getSpeciesNames(), treeString);
        List<int[]> allProfile = profile.getProfile();
        int x = profile.getSymbol().indexOf("GTF2H2D");
        sclscore.setProfile(allProfile.get(x));

        ParseNewickTree.Node gainNode = sclscore.getGainNode();

        Set<ParseNewickTree.Node> allAbsenceNode = sclscore.getAllAbsenceNode(gainNode);

        Set<ParseNewickTree.Node> allSCLoss = sclscore.getTopLossNode(allAbsenceNode);

        Set<ParseNewickTree.Node> allSCLoss2 = sclscore.checkTopLossNode(allSCLoss, allAbsenceNode);


        List<List<ParseNewickTree.Node>> result = sclscore.getSingleAndContinueLoss(allSCLoss2);

        System.out.println(gainNode);

        System.out.println(allSCLoss2.size());

//        System.out.println(result.toString());


    }


}




