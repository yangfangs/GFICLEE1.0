package predict;

import gainModel.BayesClassify;
import gainModel.SCLscore;
import io.FileInput;
import io.FileOutput;
import tree.ParseNewickTree;
import utils.Score;

import java.io.IOException;
import java.util.*;

public class Predict {

    private final List<String> noInfoGene;
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
    private List<String> geneName;
    private List<String> profileName;
    private List<int[]> allInputGeneProfile = new ArrayList<>();
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
        geneSet.prapareData();

//        read tree
        FileInput tree = new FileInput(this.nwkTreePath);
        String treeString = tree.readString();
        this.sclscore = new SCLscore(profile.getSpeciesNames(), treeString);

//      predict by symbol or entrez
        if(geneSet.isEntrez()){
            geneName = geneSet.getInputEntrez();
            profileName = profile.getEntrez();

        }else {
            geneName = geneSet.getInputSymbol();
            profileName = profile.getSymbol();
        }
        ArrayList<int[]> allInputGeneProfileTemp = new ArrayList<>();
        for(String name: geneName){
            int idx = profile.getSymbol().indexOf(name);
            allInputGeneProfileTemp.add(profile.getProfile().get(idx));
        }
        this.allInputGeneProfile = allInputGeneProfileTemp;


//      noinfo
        this.noInfoGene = getNoInfoGene("/home/yangfang/PCSF/test_java_gificlee/0_0.txt_0.info");


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
        geneSet.prapareData();
        //      predict by symbol or entrez
        if(geneSet.isEntrez()){
            geneName = geneSet.getInputEntrez();
            profileName = profile.getEntrez();

        }else {
            geneName = geneSet.getInputSymbol();
            profileName = profile.getSymbol();
        }
        ArrayList<int[]> allInputGeneProfileTemp = new ArrayList<>();
        for(String name: geneName){
            int idx = profile.getSymbol().indexOf(name);
            allInputGeneProfileTemp.add(profile.getProfile().get(idx));
        }
        this.allInputGeneProfile = allInputGeneProfileTemp;




//        prepare gene set
        this.geneSet = new ParseGeneSet(this.inputGenePath);
        try{
        geneSet.prapareData();}
        catch (ArrayIndexOutOfBoundsException e){
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

//            Set<ParseNewickTree.Node> allSCLoss = sclscore.getTopLossNode(allAbsenceNode);
//
//            Set<ParseNewickTree.Node> allSCLoss2 = sclscore.checkTopLossNode(allSCLoss, allAbsenceNode);
//
//            List<List<ParseNewickTree.Node>> result = sclscore.getSingleAndContinueLoss(allSCLoss2);




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

    }


    public List<List<ParseNewickTree.Node>> sameAnddiffLoss(List<ParseNewickTree.Node> a, List<ParseNewickTree.Node> b){
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

    public int getSCLScoreWithBayes(int predictGeneIdx){
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
        List<Integer> group = bayesClassify.getGroup();

        int geneNameIdx = profileName.indexOf(geneName.get(group.indexOf(label)));


        ParseNewickTree.Node inputGeneGain = this.allGainNode.get(geneNameIdx);
        List<ParseNewickTree.Node> inputGeneSingleLoss = this.allSingleLoss.get(geneNameIdx);
        List<ParseNewickTree.Node> inputGeneContinueLoss = this.allContinueLoss.get(geneNameIdx);

        if(predictGeneGain.equals(inputGeneGain)){
            List<List<ParseNewickTree.Node>> singleSameAndDiffNode = sameAnddiffLoss(predictGeneSingleLoss, inputGeneSingleLoss);

            List<List<ParseNewickTree.Node>> continueSameAndDiffNode = sameAnddiffLoss(predictGeneContinueLoss, inputGeneContinueLoss);

            int singleScore = singleSameAndDiffNode.get(0).size() - singleSameAndDiffNode.get(1).size();

            int continueScore = continueSameAndDiffNode.get(0).size() - continueSameAndDiffNode.get(1).size();

            score = singleScore  + continueScore;

        }else score = -1000;

        if(noInfoGene.contains(geneName.get(group.indexOf(label))))
            score = -1000;

        return score;

    }

    public List<Integer> getSCLScore(int predictGeneIdx){

        List<Integer> candidateScore = new ArrayList<>();

//        get predict by symbol or entrez



        ParseNewickTree.Node predictGeneGain = this.allGainNode.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneSingleLoss = this.allSingleLoss.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneContinueLoss = this.allContinueLoss.get(predictGeneIdx);



        for (int i = 0; i < geneName.size(); i++) {
            int geneNameIdx = profileName.indexOf(geneName.get(i));
            int score;
            ParseNewickTree.Node inputGeneGain = this.allGainNode.get(geneNameIdx);
            List<ParseNewickTree.Node> inputGeneSingleLoss = this.allSingleLoss.get(geneNameIdx);
            List<ParseNewickTree.Node> inputGeneContinueLoss = this.allContinueLoss.get(geneNameIdx);

            if(predictGeneGain.equals(inputGeneGain)){
                List<List<ParseNewickTree.Node>> singleSameAndDiffNode = sameAnddiffLoss(predictGeneSingleLoss, inputGeneSingleLoss);
                List<List<ParseNewickTree.Node>> continueSameAndDiffNode = sameAnddiffLoss(predictGeneContinueLoss, inputGeneContinueLoss);

                int singleScore = singleSameAndDiffNode.get(0).size() - singleSameAndDiffNode.get(1).size();

                int continueScore = continueSameAndDiffNode.get(0).size() - continueSameAndDiffNode.get(1).size();

                score = singleScore  + continueScore;
//                if(sclscore.judgeGain(predictGeneGain,inputGeneGain))
//                    score = -1000;

            }else score = -1000;

            if(noInfoGene.contains(geneName.get(i)))
                score = -1000;


            candidateScore.add(score);
        }

    return candidateScore;


    }



    public void runPredict(){
        List<Score> result = new ArrayList<>();



        for (int i = 0; i < this.allGainNode.size(); i++) {
//            int sum = 0;
//            List<Integer> candidateScore = getSCLScore(i);
//            int score = Collections.max(candidateScore);
            int score = getSCLScoreWithBayes(i);




            String name = profileName.get(i);
//            String predictBy = geneName.get(candidateScore.indexOf(score));
            String predictBy = "SSS";
            if(noInfoGene.contains(profileName.get(i)))
                score = -1000;
            Score resultScore = new Score(name,score,predictBy);
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

    public List<String> getNoInfoGene(String noInfoPath){
        List<String> noInfoGene = new ArrayList<>();
        FileInput noinfo = new FileInput(noInfoPath);
        List<String[]> list1 = noinfo.read();
        for(String[] line:list1){
            noInfoGene.add(line[0]);
//            System.out.println(line[0]);
            for (int i = 0; i <this.allGainNode.size() ; i++) {
                int sum = 0;
                int [] profile = this.profile.getProfile().get(i);
                for (int j = 0; j < profile.length; j++) {
                    sum += profile[j];
                }

                if(sum>=profile.length -2 || sum <=2)
                    noInfoGene.add(profileName.get(i));

            }
        }




        return noInfoGene;

    }



     public void runsingle(){
        int x = profile.getSymbol().indexOf("TAF11");
         int y = profile.getSymbol().indexOf("ERCC4");
        List<Integer> candidate = getSCLScore(x);
        System.out.println(Arrays.toString(profile.getProfile().get(x)));
         System.out.println("*************************");
         for (int i = 0; i < geneSet.getInputSymbol().size() ; i++) {
             System.out.println(allGainNode.get(profile.getSymbol().indexOf(geneSet.getInputSymbol().get(i))));

         }
         System.out.println("*************************");
         System.out.println(allGainNode.get(x));
//         System.out.println(allGainNode.get(y));
         System.out.println(geneSet.getInputSymbol());
         System.out.println(candidate);
//         System.out.println(allSingleLoss.get(x));
         System.out.println("X single:" + allSingleLoss.get(x).size());
         System.out.println("y single:" + allSingleLoss.get(y).size());
         System.out.println("X continue:" + allContinueLoss.get(x).size());
         System.out.println("y continue:" + allContinueLoss.get(y).size());
         bayesClassify.setGene(profile.getProfile().get(x));
         bayesClassify.bayesClassify();
         List<Integer> group = bayesClassify.getGroup();
         System.out.println(group.toString());
         System.out.println(bayesClassify.label());

     }



    }


