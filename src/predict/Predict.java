package predict;

import io.FileInput;

public class Predict {
    public static void main(String[] args) {
//        prepare profile
        ParseProfile profile = new ParseProfile("/home/yangfang/PCSF/test_java_gificlee/profile.txt");
//        prepare gene set
        ParseGeneSet geneSet = new ParseGeneSet("/home/yangfang/PCSF/test_java_gificlee/test_input.txt");

//        read tree
        FileInput treeString = new FileInput("/home/yangfang/PCSF/test_java_gificlee/test_tree.nwk");



    }






}
