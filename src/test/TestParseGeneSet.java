package test;

import predict.ParseGeneSet;

import java.util.ArrayList;
import java.util.Arrays;

public class TestParseGeneSet {
    public static void main(String[] args){
        ParseGeneSet foo = new ParseGeneSet("/home/yangfang/PCSF/test_jar/1_0.txt");
        foo.prepareData();
        ArrayList<String> list = foo.getInputEntrez();
        System.out.println(list);

    }

}
