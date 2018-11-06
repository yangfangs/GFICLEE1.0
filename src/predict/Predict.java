package predict;

import gainModel.BayesClassify;
import gainModel.SCLScore;
import io.FileInput;
import io.FileOutput;
import tree.ParseNewickTree;
import utils.Score;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

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
    private int genomeProfileSize;
    private ParseNewickTree readTree;

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
        genomeProfileSize = profile.getProfile().size();

//        prepare gene set
        this.geneSet = new ParseGeneSet(this.inputGenePath);
        geneSet.prepareData();

//        read tree
        FileInput tree = new FileInput(this.nwkTreePath);
        String treeString = tree.readString();
        readTree = ParseNewickTree.readNewickFormat(treeString);
        this.sclscore = new SCLScore(profile.getSpeciesNames(), readTree);



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


//      init bayes model

        BayesClassify bayesClassify = new BayesClassify(allInputGeneProfile);
        bayesClassify.assignGroup();
        bayesClassify.bayesTrain();
        this.bayesClassify = bayesClassify;

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

    /**
     * @param outputPath the abs path for output
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public  synchronized List<List<List<ParseNewickTree.Node>>> getEachSCL(List<int[]> inputProfile) {
        List<List<List<ParseNewickTree.Node>>> temList = new ArrayList<>();

        //        read tree
//        FileInput tree = new FileInput(this.nwkTreePath);
//        String treeString = tree.readString();
//        ParseNewickTree www = ParseNewickTree.readNewickFormat(treeString);
        ParseNewickTree www = readTree.clone();
        SCLScore temSCLScore = new SCLScore(profile.getSpeciesNames(),www);



        for (int[] line : inputProfile) {

            ParseNewickTree.Node gainNode = temSCLScore.getGainNode(line);
            List<ParseNewickTree.Node> gain = new ArrayList<>();
            gain.add(gainNode);
            Set<ParseNewickTree.Node> allAbsenceNode = temSCLScore.getAllAbsenceNode(gainNode,line);

                List<List<ParseNewickTree.Node>> result = temSCLScore.getAllSingleAndContinueLoss(allAbsenceNode);
                result.add(gain);
                temList.add(result);

        }
        return temList;
    }


    /**
     * Scanning all genes in genome profile, get single and continue loss node with multi thread
     */


    public void getAllSCLMultiThread(int threadNum) throws InterruptedException, ExecutionException {
        List<int[]> allProfile = profile.getProfile();
//      init task
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        List<Callable<List<List<List<ParseNewickTree.Node>>>>> tasks = new ArrayList<>();
        Callable<List<List<List<ParseNewickTree.Node>>>> task = null;

//      split all task
        int length = genomeProfileSize;
        int tl = length % threadNum == 0 ? length / threadNum : (length
                / threadNum + 1);


        for (int i = 0; i < threadNum; i++) {

            int end = (i + 1) * tl;
            final List<int[]> listprofile = allProfile.subList(i * tl, end > length ? length : end);
            final int start = i* tl;
            final int ends = end > length ? length : end;

//            System.out.println(listprofile);
            task = () -> {
                List<List<List<ParseNewickTree.Node>>> temList;

                temList = getEachSCL(listprofile);
                return temList;
            };
            tasks.add(task);


//                System.out.println(profileName.get(i));
//                System.out.println(Arrays.toString(profile.getProfile().get(i)));

        }
        List<Future<List<List<List<ParseNewickTree.Node>>>>> results = exec.invokeAll(tasks);
        System.out.println(results.size());
        for (Future<List<List<List<ParseNewickTree.Node>>>> future : results) {
            for (List<List<ParseNewickTree.Node>> list1 : future.get()) {
                allSingleLoss.add(list1.get(0));
                allContinueLoss.add(list1.get(1));
                allGainNode.add(list1.get(2).get(0));

            }
        }

        exec.shutdown();
        this.noInfoGene = getNoInfoGene();

    }


    public void getAllSCL() {

        List<int[]> allProfile = profile.getProfile();

        for (int i = 0; i < genomeProfileSize; i++) {


            ParseNewickTree.Node gainNode = sclscore.getGainNode(allProfile.get(i));


            allGainNode.add(gainNode);

            Set<ParseNewickTree.Node> allAbsenceNode = sclscore.getAllAbsenceNode(gainNode,allProfile.get(i));

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


    /**
     * @param a The single ro continues loss node list in input profile.
     * @param b The single ro continues loss node list in genome profile.
     * @return The single or continues loss score.
     */
    public List<List<ParseNewickTree.Node>> sameAndDiffLoss(List<ParseNewickTree.Node> a, List<ParseNewickTree.Node> b) {
        List<ParseNewickTree.Node> tem;
        if (a.size() > b.size()) {
            tem = a;
            a = b;
            b = tem;
        }

        ArrayList<ParseNewickTree.Node> exists = new ArrayList<ParseNewickTree.Node>(b);
        ArrayList<ParseNewickTree.Node> notExists = new ArrayList<ParseNewickTree.Node>(b);
        List<List<ParseNewickTree.Node>> sameAndDiff = new ArrayList<>();
//        diff
        exists.removeAll(a);
//        same
        notExists.removeAll(exists);

//      add same
        sameAndDiff.add(notExists);
//      add diff
        sameAndDiff.add(exists);

        return sameAndDiff;

    }

    /**
     * @param predictGeneIdx The index of predict gene.
     * @return The candidate predict genes score and predict by witch input gene.
     */
    public ArrayList<Integer> getSCLScoreWithBayes(int predictGeneIdx) {
        ArrayList<Integer> candidatePredict = new ArrayList<>();
        int score;

        ParseNewickTree.Node predictGeneGain = this.allGainNode.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneSingleLoss = this.allSingleLoss.get(predictGeneIdx);
        List<ParseNewickTree.Node> predictGeneContinueLoss = this.allContinueLoss.get(predictGeneIdx);


        int[] predictGeneProfile = profile.getProfile().get(predictGeneIdx);

//        BayesClassify bayesClassify = new BayesClassify(allInputGeneProfile);
//        bayesClassify.assignGroup();
//        bayesClassify.bayesTrain();
//        this.bayesClassify = bayesClassify;


        bayesClassify.bayesClassify(predictGeneProfile);
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
        candidatePredict.add(score);
        candidatePredict.add(label);

        return candidatePredict;

    }
    /**
     * Running predict
     */
    public void runPredictMulti(int threadNum) throws InterruptedException, ExecutionException {

        List<Score> result = new ArrayList<>();
        List<Integer> allIndex = new ArrayList<>();
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        List< Callable<List<Score>>> tasks = new ArrayList<>();
        Callable<List<Score>> task = null;

//      split all task
        int length = genomeProfileSize;
        int tl = length % threadNum == 0 ? length / threadNum : (length
                / threadNum + 1);
        for (int i = 0; i < length ; i++) {
            allIndex.add(i);
        }

        for (int i = 0; i < threadNum; i++) {

            int end = (i + 1) * tl;
            final List<Integer> listprofile = allIndex.subList(i * tl, end > length ? length : end);

//            System.out.println(listprofile);
            task = () -> {
                List<Score> resultTem = new ArrayList<>();
                for (int j:listprofile) {
                    List<Integer> candidatePredict = getSCLScoreWithBayes(j);
                    int score = candidatePredict.get(0);

                    String name = profileName.get(j);
                    String predictBy = geneName.get(candidatePredict.get(1));
                    if (noInfoGene[j] == 1)
                        score = -1000;
                    Score resultScore = new Score(name, score, predictBy);

                    resultTem.add(resultScore);
                }

                return resultTem;

            };
            tasks.add(task);


//                System.out.println(profileName.get(i));
//                System.out.println(Arrays.toString(profile.getProfile().get(i)));

        }
        List<Future<List<Score>>> results = exec.invokeAll(tasks);
        for(Future<List<Score>> future: results){
            result.addAll(future.get());
        }


        Collections.sort(result);
        exec.shutdown();
        FileOutput output = new FileOutput(this.outputPath);
        try {
            output.writeScore(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
//        System.out.println(result);





    /**
     * Running predict
     */
    public void runPredict() {

        List<Score> result = new ArrayList<>();

        for (int i = 0; i < genomeProfileSize; i++) {
//            int sum = 0;
//            List<Integer> candidateScore = getSCLScore(i);
//            int score = Collections.max(candidateScore);
            List<Integer> candidatePredict = getSCLScoreWithBayes(i);
            int score = candidatePredict.get(0);

            String name = profileName.get(i);
            String predictBy = geneName.get(candidatePredict.get(1));
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

    /**
     * @return no information genes
     */
    public int[] getNoInfoGene() {

        int[] noInfoGene = new int[genomeProfileSize];

        for (int i = 0; i < genomeProfileSize; i++) {
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


