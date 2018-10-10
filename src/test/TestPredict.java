package test;

import predict.Predict;

public class TestPredict {
    public static void main(String[] args) {
//            Predict foo = new Predict("/home/yangfang/PCSF/test_java_gificlee/profile.txt",
//                    "/home/yangfang/PCSF/test_java_gificlee/test_input.txt",
//                    "/home/yangfang/PCSF/test_java_gificlee/test_tree.nwk",
//                    "/test/test/");
//            foo.getAllSCL();


        long startTime = System.currentTimeMillis();
        Predict foo2 = new Predict("/home/yangfang/PCSF/test_java_gificlee/all_kegg_matrix_re111.txt",
                "/home/yangfang/PCSF/no_report/test_re111/input/0_0.txt",
                "/home/yangfang/PCSF/clime_roc/species111.abbrev.manual_binary.nwk",
                "/home/yangfang/PCSF/test_java_gificlee/result.txt");
        foo2.getAllSCL();
        foo2.runsingle();
//        foo2.setInputGenePath("/home/yangfang/PCSF/no_report/test_re111/input/1_0.txt");
        foo2.runPredict();
//        foo2.getNoInfoGene("/home/yangfang/PCSF/test_java_gificlee/0_0.txt_0.info");


        long endTime =System.currentTimeMillis();
        System.out.println("Time:" + (endTime-startTime)/1000);


    }
}
