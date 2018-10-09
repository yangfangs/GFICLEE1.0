package predict;

import gainModel.SCLscore;
import io.FileInput;
import tree.ParseNewickTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Predict {

    private String profilePath;
    private String inputGenePath;
    private String nwkTreePath;
    private String outputPath;
    private ParseProfile profile;
    private ParseGeneSet geneSet;
    private SCLscore sclscore;
    private List<ParseNewickTree.Node> allGainNode = new ArrayList<>();
    private List<List<ParseNewickTree.Node>> allSingleLoss = new ArrayList<>();
    private List<List<ParseNewickTree.Node>> allContinueLoss = new ArrayList<>();


    public Predict(String profilePath,
                   String inputGenePath,
                   String nwkTreePath,
                   String outputPath) {

        this.profilePath = profilePath;
        this.inputGenePath = inputGenePath;
        this.nwkTreePath = nwkTreePath;
        this.outputPath = outputPath;
//        prepare profile
        this.profile = new ParseProfile(this.profilePath);
        profile.prepareData();


//        prepare gene set
        this.geneSet = new ParseGeneSet(this.inputGenePath);
        geneSet.prapareData();

//        read tree
        FileInput tree = new FileInput(this.nwkTreePath);
        String treeString = tree.readString();
        this.sclscore = new SCLscore(profile.getSpeciesNames(), treeString);

    }

    public void getAllSCL() {

        List<int[]> allProfile = profile.getProfile();

        for (int i = 0; i < allProfile.size(); i++) {
            sclscore.setProfile(allProfile.get(i));

            ParseNewickTree.Node gainNode = sclscore.getGainNode();
            allGainNode.add(gainNode);

            Set<ParseNewickTree.Node> allAbsenceNode = sclscore.getAllAbsenceNode(gainNode);

            Set<ParseNewickTree.Node> allSCLoss = sclscore.getTopLossNode(allAbsenceNode);

            Set<ParseNewickTree.Node> allSCLoss2 = sclscore.checkTopLossNode(allSCLoss, allAbsenceNode);

            List<List<ParseNewickTree.Node>> result = sclscore.getSingleAndContinueLoss(allSCLoss2);
            allSingleLoss.add(result.get(0));
            allContinueLoss.add(result.get(1));


        }
//        System.out.println(allGainNode);
//
//        System.out.println(allSingleLoss.toString());
//
//        System.out.println(allContinueLoss.toString());

    }

}



//    public static void main(String[] args) {
////        prepare profile
//        ParseProfile profile = new ParseProfile("/home/yangfang/PCSF/test_java_gificlee/profile.txt");
//        profile.prepareData();
////        prepare gene set
//        ParseGeneSet geneSet = new ParseGeneSet("/home/yangfang/PCSF/test_java_gificlee/test_input.txt");
//        geneSet.prapareData();
////        read tree
//        FileInput tree = new FileInput("/home/yangfang/PCSF/test_java_gificlee/test_tree.nwk");
//        String treeString = tree.readString();
//
////        get single loss and continue loss
//
//        SCLscore sclscore = new SCLscore(profile.getSpeciesNames(), treeString);
//        List<int[]> allProfile = profile.getProfile();
//
//        sclscore.setProfile(allProfile.get(0));
//
//        ParseNewickTree.Node gainNode = sclscore.getGainNode();
//
//        Set<ParseNewickTree.Node> allAbsenceNode = sclscore.getAllAbsenceNode(gainNode);
//
//        Set<ParseNewickTree.Node> allSCLoss = sclscore.getTopLossNode(allAbsenceNode);
//
//        Set<ParseNewickTree.Node> allSCLoss2 = sclscore.checkTopLossNode(allSCLoss, allAbsenceNode);
//
//
//        List<List<ParseNewickTree.Node>> result = sclscore.getSingleAndContinueLoss(allSCLoss2);
//
//        System.out.println(gainNode);
//
//        System.out.println(allSCLoss2.toString());
//
//        System.out.println(result.toString());
//
//
//    }
//
//
//}
