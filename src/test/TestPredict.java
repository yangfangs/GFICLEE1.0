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
        Predict foo2 = new Predict("/home/yangfang/GFICLEE/test_kegg_gficlee/hsa.matrix138.e3.q00.p20.txt",
                "/home/yangfang/GFICLEE/test_kegg_gficlee/input/0_0.txt",
                "/home/yangfang/GFICLEE/test_kegg_gficlee/species138.abbrev.manual_binary.nwk",
                "/test/test/");
        foo2.getAllSCL();
        foo2.runPredict();



        long endTime =System.currentTimeMillis();
        System.out.println("Time:" + (endTime-startTime)/1000);


    }
}
