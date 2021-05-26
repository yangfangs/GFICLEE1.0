package test;

import predict.Predict;
import tree.ParseNewickTree;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestPredict {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//            Predict foo = new Predict("/home/yangfang/PCSF/test_java_gificlee/profile.txt",
//                    "/home/yangfang/PCSF/test_java_gificlee/test_input.txt",
//                    "/home/yangfang/PCSF/test_java_gificlee/test_tree.nwk",
//                    "/test/test/");
//            foo.getAllSCL();

        long startTime = System.currentTimeMillis();
        Predict foo2 = new Predict("/home/yangfang/GFICLEE/test_kegg_gficlee_java/hsa.matrix138.e3.q00.p20.txt",
                "/home/yangfang/GFICLEE/test_running_time/hsa_genome/gficlee/0_0.txt",
                "/home/yangfang/GFICLEE/test_kegg_gficlee_java/species138.abbrev.manual_binary.nwk",
                "/home/yangfang/GFICLEE/test_java_gficlee/result.txt",
                null,
                null,
                "eq");
        foo2.getAllSCL();
        int num = 6;
//        try {
//            foo2.getAllSCLMultiThread(num);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        foo2.setInputGenePath("/home/yangfang/PCSF/no_report/test_re111/input/1_0.txt");
//        int x = foo2.getProfile().getSymbol().indexOf("NAGPA");
//        int y = foo2.getProfile().getSymbol().indexOf("ERCC4");
//
//
//        List<ParseNewickTree.Node> a1 = foo2.getAllSingleLoss(x);
//        List<ParseNewickTree.Node> b1 = foo2.getAllSingleLoss(y);
//
//        List<ParseNewickTree.Node> a2 = foo2.getAllContinueLoss(x);
//        List<ParseNewickTree.Node> b2 = foo2.getAllContinueLoss(y);
//
//        List<List<ParseNewickTree.Node>> singleSameAndDiffNode = foo2.sameAnddiffLoss(a1,b1);
//
//        List<ParseNewickTree.Node> same1 = singleSameAndDiffNode.get(0);
//        List<ParseNewickTree.Node> diff1 = singleSameAndDiffNode.get(1);
//
//        System.out.println(same1.size());
//        System.out.println(diff1.size());
//
//        List<List<ParseNewickTree.Node>> continueSameAndDiffNode = foo2.sameAnddiffLoss(a2,b2);
//        List<ParseNewickTree.Node> same2 = continueSameAndDiffNode.get(0);
//        List<ParseNewickTree.Node> diff2 = continueSameAndDiffNode.get(1);
//        System.out.println(same2.size());
//        System.out.println(diff2.size());
//
//        List<Integer> res = foo2.getSCLscore(x);
//        System.out.println(res.toString());

        foo2.runPredict();
//        foo2.runPredictMulti(1);
//        foo2.getNoInfoGene("/home/yangfang/PCSF/test_java_gificlee/0_0.txt_0.info");


        long endTime =System.currentTimeMillis();
        System.out.println("Time:" + (endTime-startTime)/1000);


    }
}
