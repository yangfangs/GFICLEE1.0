package test;

import predict.Predict;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestGo {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        List<String> files = new ArrayList<>();
        File file = new File("/home/yangfang/GFICLEE/test_kegg_gficlee_java/input/");
//        File file = new File("/home/yangfang/GFICLEE/test_ath_gficlee/input/");
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//                System.out.println("file：" + tempList[i]);
                files.add(tempList[i].toString());
            }

        }
        Predict foo2 = new Predict("/home/yangfang/GFICLEE/test_kegg_gficlee_java/hsa.matrix138.e3.q00.p20.txt",
                "/home/yangfang/PCSF/test_java_gificlee/input/0_0.txt",
                "/home/yangfang/GFICLEE/test_kegg_gficlee_java/species138.abbrev.manual_binary.nwk",
                "/home/yangfang/GFICLEE/test_kegg_gficlee_java/result.txt");
//        Predict foo2 = new Predict("/home/yangfang/GFICLEE/test_ath_gficlee/ath_138_matrix.txt",
//                "/home/yangfang/GFICLEE/test_ath_gficlee/input/0_0.txt",
//                "/home/yangfang/GFICLEE/test_ath_gficlee/species138.abbrev.manual_binary.nwk",
//                "/home/yangfang/GFICLEE/test_ath_gficlee/result.txt");
        foo2.getAllSCL();

        for (int j = 0; j < tempList.length; j++) {
            String inputFile = tempList[j].toString();
            String fileName = tempList[j].getName();
                String outputFile = "/home/yangfang/GFICLEE/test_kegg_gficlee_java/result_x/" + fileName;
                System.out.println(inputFile);
                foo2.setInputGenePath(inputFile);
                foo2.setOutputPath(outputFile);
                foo2.runPredict();


        }

        long endTime =System.currentTimeMillis();
        System.out.println("Time:" + (endTime-startTime)/1000);


    }
}
