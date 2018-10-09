package predict;

import gainModel.SCLscore;
import io.FileInput;
import tree.ParseNewickTree;

import java.util.ArrayList;
import java.util.Collections;
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


    private List<List<ParseNewickTree.Node>> sameAnddiffLoss(List<ParseNewickTree.Node> a, List<ParseNewickTree.Node> b){
        List<ParseNewickTree.Node> tem;
        if(a.size() > b.size()){
            tem = a;
            a = b;

            b = tem;
        }

        ArrayList<ParseNewickTree.Node> exists = new ArrayList<ParseNewickTree.Node>(b);
        ArrayList<ParseNewickTree.Node> notexists = new ArrayList<ParseNewickTree.Node>(b);
        List<List<ParseNewickTree.Node>> sameAnddiff = new ArrayList<>();
//        diff
        exists.removeAll(a);

//        same
        notexists.removeAll(exists);

//      add same
        sameAnddiff.add(notexists);

//      add diff
        sameAnddiff.add(exists);


        return sameAnddiff;

    }



    public List<Integer> getSCLscore(int predictGeneIdx){

        List<Integer> candidateScore = new ArrayList<>();

//        get predict by symbol or entrez
        List<String> geneName;
        List<String> profileName;
        if(geneSet.isEntrez()){
            geneName = geneSet.getInputEntrez();
            profileName = profile.getEntrez();

        }else {
            geneName = geneSet.getInputSymbol();
            profileName = profile.getSymbol();
        }

        ParseNewickTree.Node predictGeneGain = this.allGainNode.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneSingleLoss = this.allSingleLoss.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneContiueLoss = this.allContinueLoss.get(predictGeneIdx);



        for (int i = 0; i < geneName.size(); i++) {
            int geneNameIdx = profileName.indexOf(geneName.get(i));
            int score;
            ParseNewickTree.Node inputGeneGain = this.allGainNode.get(geneNameIdx);
            List<ParseNewickTree.Node> inputGeneSingleLoss = this.allSingleLoss.get(geneNameIdx);
            List<ParseNewickTree.Node> inputGeneContinueLoss = this.allContinueLoss.get(geneNameIdx);

            if(predictGeneGain.equals(inputGeneGain)){
                List<List<ParseNewickTree.Node>> singleSameAndDiffNode = sameAnddiffLoss(predictGeneSingleLoss, inputGeneSingleLoss);
                List<List<ParseNewickTree.Node>> continueSameAndDiffNode = sameAnddiffLoss(predictGeneContiueLoss, inputGeneContinueLoss);

                int singleScore = singleSameAndDiffNode.get(0).size() - singleSameAndDiffNode.get(1).size();
                int continueScore = continueSameAndDiffNode.get(0).size() - continueSameAndDiffNode.get(1).size();

                score = singleScore + continueScore;

            }else score = -1000;
            candidateScore.add(score);
        }

    return candidateScore;


    }



    public void runPredict(){
        for (int i = 0; i < this.allGainNode.size(); i++) {
            List<Integer> candidateScore = getSCLscore(i);
            int score = Collections.max(candidateScore);
            if(score>0) {
                System.out.print(profile.getSymbol().get(i) + ": ");
                System.out.println(score);
            }

        }


        }


    }


