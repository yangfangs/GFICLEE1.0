package test;

import predict.ParseGeneSet;

import java.util.ArrayList;
import java.util.Arrays;

public class TestParseGeneSet {
    public static void main(String[] args){
        ParseGeneSet foo = new ParseGeneSet("/home/yangfang/PCSF/test_pfa_gficlee/input/0_0.txt");
        foo.prapareData();
        ArrayList<String> list = foo.getInputSymbol();
        System.out.println(list);

    }

}
