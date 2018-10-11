package test;

import gainModel.BayesClassify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * C1:
 * -----------------
 * [0, 1, 1, 1, 0],
 * [1, 1, 1, 0, 1],
 * [1, 1, 0, 0, 1],
 * -----------------
 *
 * C2:
 * -----------------
 * [1, 1, 1, 0, 1],
 * [1, 1, 1, 1, 0],
 * -----------------
 *
 * X:
 * -----------------
 * [1,1,0,0,0]
 * -----------------
 *
 * Bayes probability:
 * -----------------
 * p1 = (0.6 * 0.8 * 0.4 * 0.6 * 0.4) * (4/7) = 0.026331428571428572
 *
 * p2 = (0.75 * 0.75 * 0.75 * 0.5 * 0.5) * (3/7) = 0.015066964285714284
 *
 * log(p1) = -3.636992050529924
 * log(p2) = -4.1952507275305466
 */
public class TestBayesClassify {



    public static void main(String[] args) {
        List<int[]> geneSet = new ArrayList<>();
        geneSet.add(new int[]{0, 1, 1, 1, 0});
        geneSet.add(new int[]{1, 1, 1, 0, 1});
        geneSet.add(new int[]{1, 1, 0, 0, 1});
        geneSet.add(new int[]{1, 1, 1, 0, 1});
        geneSet.add(new int[]{1, 1, 1, 1, 0});


        int[] gene = {1, 1, 0, 0, 0};

        BayesClassify foo = new BayesClassify(geneSet);
        foo.setGene(gene);
//        foo.assignGroup();

        List<Integer> group = Arrays.asList(0, 0, 0, 1, 1);

        foo.setGroup(group);
        foo.bayesTrain();
        List<Double> res = foo.bayesClassify();
        System.out.println(res.toString());

        int x = foo.label();
        List<Integer> group1 = foo.getGroup();
        System.out.println(x);
        System.out.println(group1.toString());


    }


}
