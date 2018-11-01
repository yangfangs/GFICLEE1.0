package predict;

import gainModel.BayesClassify;
import gainModel.SCLScore;
import io.FileInput;
import io.FileOutput;
import tree.ParseNewickTree;
import utils.Score;

import java.io.IOException;
import java.util.*;

public class Predict {

    private int[] noInfoGene;
    private String profilePath;
    private String inputGenePath;
    private String nwkTreePath;
    private String outputPath;
    private ParseProfile profile;
    private ParseGeneSet geneSet;
    private SCLScore sclscore;
    private List<ParseNewickTree.Node> allGainNode = new ArrayList<>();
    private List<List<ParseNewickTree.Node>> allSingleLoss = new ArrayList<>();
    private List<List<ParseNewickTree.Node>> allContinueLoss = new ArrayList<>();
    private List<String> geneName;
    private List<String> profileName;
    private List<int[]> allInputGeneProfile;
    private BayesClassify bayesClassify;


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
        geneSet.prepareData();

//        read tree
        FileInput tree = new FileInput(this.nwkTreePath);
        String treeString = tree.readString();
        this.sclscore = new SCLScore(profile.getSpeciesNames(), treeString);

//      predict by symbol or entrez
        if (geneSet.isEntrez()) {
            geneName = geneSet.getInputEntrez();
            profileName = profile.getEntrez();

        } else {
            geneName = geneSet.getInputSymbol();
            profileName = profile.getSymbol();
        }
        ArrayList<int[]> allInputGeneProfileTemp = new ArrayList<>();
        for (String name : geneName) {
            int idx = profileName.indexOf(name);
            allInputGeneProfileTemp.add(profile.getProfile().get(idx));
        }
        this.allInputGeneProfile = allInputGeneProfileTemp;


//      noinfo


//      init bayes model

//        BayesClassify bayesClassify = new BayesClassify(allInputGeneProfile);
//        bayesClassify.assignGroup();
//        bayesClassify.bayesTrain();
//        this.bayesClassify = bayesClassify;

    }

    public List<ParseNewickTree.Node> getAllSingleLoss(int idx) {
        return allSingleLoss.get(idx);
    }

    public List<ParseNewickTree.Node> getAllContinueLoss(int idx) {
        return allContinueLoss.get(idx);
    }

    public ParseProfile getProfile() {
        return profile;
    }

    public void setInputGenePath(String inputGenePath) {
        this.inputGenePath = inputGenePath;
        //        prepare profile
        this.geneSet = new ParseGeneSet(this.inputGenePath);
        geneSet.prepareData();
        //      predict by symbol or entrez
        if (geneSet.isEntrez()) {
            geneName = geneSet.getInputEntrez();
            profileName = profile.getEntrez();

        } else {
            geneName = geneSet.getInputSymbol();
            profileName = profile.getSymbol();
        }
        ArrayList<int[]> allInputGeneProfileTemp = new ArrayList<>();
        for (String name : geneName) {
            int idx = profileName.indexOf(name);
            allInputGeneProfileTemp.add(profile.getProfile().get(idx));
        }
        this.allInputGeneProfile = allInputGeneProfileTemp;


//        prepare gene set
        this.geneSet = new ParseGeneSet(this.inputGenePath);
        try {
            geneSet.prepareData();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(this.inputGenePath);
        }
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void getAllSCL() {

        List<int[]> allProfile = profile.getProfile();

        for (int i = 0; i < allProfile.size(); i++) {
            sclscore.setProfile(allProfile.get(i));


            ParseNewickTree.Node gainNode = sclscore.getGainNode();


            allGainNode.add(gainNode);

            Set<ParseNewickTree.Node> allAbsenceNode = sclscore.getAllAbsenceNode(gainNode);

            List<List<ParseNewickTree.Node>> result = sclscore.getAllSingleAndContinueLoss(allAbsenceNode);

            allSingleLoss.add(result.get(0));
            allContinueLoss.add(result.get(1));


//                System.out.println(profileName.get(i));
//                System.out.println(Arrays.toString(profile.getProfile().get(i)));

        }
//        System.out.println(allGainNode);
//
//        System.out.println(allSingleLoss.toString());
//
//        System.out.println(allContinueLoss.toString());
        this.noInfoGene = getNoInfoGene();
    }


    public List<List<ParseNewickTree.Node>> sameAndDiffLoss(List<ParseNewickTree.Node> a, List<ParseNewickTree.Node> b) {
        List<ParseNewickTree.Node> tem;
        if (a.size() > b.size()) {
            tem = a;
            a = b;
            b = tem;
        }
//        ArrayList<ParseNewickTree.Node> existsA = new ArrayList<ParseNewickTree.Node>(a);
        ArrayList<ParseNewickTree.Node> exists = new ArrayList<ParseNewickTree.Node>(b);
        ArrayList<ParseNewickTree.Node> notexists = new ArrayList<ParseNewickTree.Node>(b);
        List<List<ParseNewickTree.Node>> sameAndDiff = new ArrayList<>();
//        diff
        exists.removeAll(a);

//        same
        notexists.removeAll(exists);

//        existsA.removeAll(notexists);


//      add same
        sameAndDiff.add(notexists);
//        exists.addAll(existsA);
//      add diff
        sameAndDiff.add(exists);

        return sameAndDiff;

    }

    public int getSCLScoreWithBayes(int predictGeneIdx) {
        int score;

        ParseNewickTree.Node predictGeneGain = this.allGainNode.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneSingleLoss = this.allSingleLoss.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneContinueLoss = this.allContinueLoss.get(predictGeneIdx);


        int[] predictGeneProfile = profile.getProfile().get(predictGeneIdx);

        BayesClassify bayesClassify = new BayesClassify(allInputGeneProfile);
        bayesClassify.assignGroup();
        bayesClassify.bayesTrain();
        this.bayesClassify = bayesClassify;

        bayesClassify.setGene(predictGeneProfile);
        bayesClassify.bayesClassify();
        int label = bayesClassify.label();

        int geneNameIdx = profileName.indexOf(geneName.get(label));


        ParseNewickTree.Node inputGeneGain = this.allGainNode.get(geneNameIdx);
        List<ParseNewickTree.Node> inputGeneSingleLoss = this.allSingleLoss.get(geneNameIdx);
        List<ParseNewickTree.Node> inputGeneContinueLoss = this.allContinueLoss.get(geneNameIdx);

        if (predictGeneGain.equals(inputGeneGain)) {
            List<List<ParseNewickTree.Node>> singleSameAndDiffNode = sameAndDiffLoss(predictGeneSingleLoss, inputGeneSingleLoss);

            List<List<ParseNewickTree.Node>> continueSameAndDiffNode = sameAndDiffLoss(predictGeneContinueLoss, inputGeneContinueLoss);

            int singleScore = singleSameAndDiffNode.get(0).size() - singleSameAndDiffNode.get(1).size();

            int continueScore = continueSameAndDiffNode.get(0).size() - continueSameAndDiffNode.get(1).size();

            score = singleScore + continueScore;

        } else score = -1000;

        if (noInfoGene[predictGeneIdx] == 1)
            score = -1000;

        return score;

    }


    public void runPredict() {
        List<Score> result = new ArrayList<>();


        for (int i = 0; i < this.allGainNode.size(); i++) {
//            int sum = 0;
//            List<Integer> candidateScore = getSCLScore(i);
//            int score = Collections.max(candidateScore);
            int score = getSCLScoreWithBayes(i);


            String name = profileName.get(i);
//            String predictBy = geneName.get(candidateScore.indexOf(score));
            String predictBy = "SSS";
            if (noInfoGene[i] == 1)
                score = -1000;
            Score resultScore = new Score(name, score, predictBy);
            result.add(resultScore);
            Collections.sort(result);


        }
//        System.out.println(result);
        FileOutput output = new FileOutput(this.outputPath);
        try {
            output.writeScore(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int[] getNoInfoGene() {

        int[] noInfoGene = new int[allGainNode.size()];

        for (int i = 0; i < this.allGainNode.size(); i++) {
            int sum = 0;

            int[] profile = this.profile.getProfile().get(i);
            for (int j = 0; j < profile.length; j++) {
                sum += profile[j];

            }
            if (sum >= profile.length - 2 || sum <= 2) {
                noInfoGene[i] = 1;
            }


        }

        return noInfoGene;

    }




}


