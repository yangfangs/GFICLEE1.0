package gainModel;

import java.util.*;

public class BayesClassify {
    private int[] gene;
    private List<int[]> geneSet;
    private List<Integer> group;
    private List<float[]> allEachX1;
    private float[] pc;
    private List<Double> res;

    public BayesClassify(List<int[]> geneSet) {
        this.geneSet = geneSet;

    }

    public List<Integer> getGroup() {
        return group;
    }

    public void setGroup(List<Integer> group) {
        this.group = group;
    }

    public int[] getGene() {
        return gene;
    }


    public void setGene(int[] gene) {
        this.gene = gene;
    }

    private List<Integer> removeDuplicate(List<Integer> list) {
        List<Integer> list1 = new ArrayList<>(list);

        LinkedHashSet<Integer> set = new LinkedHashSet<>(list1.size());
        set.addAll(list1);
        list1.clear();
        list1.addAll(set);
        return list1;
    }

    private boolean contain(List<int[]> a, int[] b) {
        boolean tem = false;
        if (a.isEmpty()) {
            tem = false;
        } else {
            for (int[] line : a) {
                if (Arrays.equals(line, b))
                    tem = true;
            }


        }

        return tem;

    }


    public int getValueByKey(Map<int[], Integer> map, int[] key){
        int res = 0;
        for(int[] k: map.keySet()){
            if(Arrays.equals(k,key)){
                res = map.get(k);
            }
        }
        return res;
    }



    public void assignGroup() {
        List<Integer> groupTem = new ArrayList<>();
        List<int[]> tem = new ArrayList<>();
        Map<int[], Integer> map = new HashMap<>();
        for (int i = 0; i < geneSet.size(); i++) {
            groupTem.add(i);
        }
//        int num = 0;
//        for (int i = 0; i < geneSet.size(); i++) {
//            if (!contain(tem,geneSet.get(i))) {
//                groupTem.add(num);
//
//                tem.add(geneSet.get(i));
//
//                map.put(geneSet.get(i),num);
//                num++;
//            } else {
////                tem.add(geneSet.get(i));
//                int id = getValueByKey(map,geneSet.get(i));
//
//
//                groupTem.add(id);
//            }
//        }
////        System.out.println(groupTem.toString());

        this.group = groupTem;

    }


    public void bayesTrain() {
        List<Integer> countUnique = removeDuplicate(group);

        int countUniqueSize = countUnique.size();
        int countAll = group.size();
        int sizeOfgene = geneSet.get(0).length;

        float[] pc = new float[countUniqueSize];
        List<float[]> allEachX1 = new ArrayList<>();
//        int[] eachX1 = new int[gene.length];

        for (int i = 0; i < countUniqueSize; i++) {
            float[] eachX1 = new float[sizeOfgene];
            float[] eachX1Temp = new float[sizeOfgene];
            List<int[]> eachProfile = new ArrayList<>();
            int sum = 0;
            for (Integer idx : group) {

                if (idx == i)
                    sum += 1;
            }

            pc[i] = (float) (sum + 1) / (countAll + countUniqueSize);
//            System.out.println("pc[i]:" + pc[i]);
            for (int j = 0; j < group.size(); j++) {
                if (group.get(j) == i)
                    eachProfile.add(geneSet.get(j));

            }
//            System.out.println(eachProfile.size());

            for (int[] line : eachProfile) {
                for (int k = 0; k < line.length; k++) {
                    eachX1Temp[k] += line[k];
                }
            }

            for (int jj = 0; jj < eachX1.length; jj++) {

                eachX1[jj] = (eachX1Temp[jj] + 1) / (eachProfile.size() + 2);

            }
            allEachX1.add(eachX1);
//            System.out.println(Arrays.toString(eachX1));

        }
        this.allEachX1 = allEachX1;
        this.pc = pc;
//        System.out.println("wwww" + Arrays.toString(pc));
    }


    public List<Double> bayesClassify() {
        List<Double> res = new ArrayList<>();

        List<Integer> countUnique = removeDuplicate(group);
        int countUniqueSize = countUnique.size();

        for (int i = 0; i < countUniqueSize; i++) {
            double sum = 0;
            for (int j = 0; j < gene.length; j++) {
                if (gene[j] == 1) {
                    sum += Math.log(allEachX1.get(i)[j]);
                } else {
                    sum += Math.log(1 - allEachX1.get(i)[j]);
                }
            }
            sum = sum + Math.log(pc[i]);
            res.add(sum);
        }
        this.res = res;
        return res;
    }

    public int label() {
        List<Integer> countUnique = removeDuplicate(group);

        int maxIdx = this.res.indexOf(Collections.max(this.res));

        return countUnique.get(maxIdx);

    }


}
