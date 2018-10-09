package test;

import predict.Predict;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestGo {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        List<String> files = new ArrayList<>();
        File file = new File("/home/yangfang/PCSF/test_java_gificlee/input");
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//                System.out.println("file：" + tempList[i]);
                files.add(tempList[i].toString());
            }

        }
        Predict foo2 = new Predict("/home/yangfang/PCSF/test_java_gificlee/all_kegg_matrix_re111.txt",
                "/home/yangfang/PCSF/no_report/test_re111/input/0_0.txt",
                "/home/yangfang/PCSF/clime_roc/species111.abbrev.manual_binary.nwk",
                "/home/yangfang/PCSF/test_java_gificlee/result.txt");
        foo2.getAllSCL();

        for (int j = 0; j < tempList.length; j++) {
            String inputFile = tempList[j].toString();
            String fileName = tempList[j].getName();

                String outputFile = "/home/yangfang/PCSF/test_java_gificlee/result_kegg/" + fileName;
                foo2.setInputGenePath(inputFile);
                foo2.setOutputPath(outputFile);
                foo2.runPredict();


        }

        long endTime =System.currentTimeMillis();
        System.out.println("Time:" + (endTime-startTime)/1000);


    }
}
