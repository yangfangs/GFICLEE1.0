package predict;

import io.FileInput;
import utils.ParseTaxa;

import java.util.ArrayList;

import java.util.List;

public class ParseProfile {
    private String profilePath;
    private final List<String[]> profilelist;
    private List<String> speciesNames = new ArrayList<>();
    private ArrayList<String> symbol = new ArrayList<>();
    private ArrayList<String> entrez = new ArrayList<>();
    private List<int[]> profile = new ArrayList<>();
    private int animalsCause = 0;
    private int plantsCause = 0;
    private int fungiCause = 0;
    private int protistsCause = 0;
    private int totalHGT = 0;
    public ParseProfile(String profilePath) {
        this.profilePath = profilePath;
        FileInput profileFile = new FileInput(this.profilePath);
        profilelist = profileFile.read();

    }

    public String getProfilePath() {
        return profilePath;
    }

    public ArrayList<String> getEntrez() {
        return entrez;
    }

    public List<String> getSpeciesNames() {
        return speciesNames;
    }

    public ArrayList<String> getSymbol() {
        return symbol;
    }

    public List<int[]> getProfile() {
        return profile;
    }

    public List<String[]> getProfilelist() {
        return profilelist;
    }


    public void readProfile() {
        FileInput profileFile = new FileInput(this.profilePath);

    }

    public void prepareData() {

//        get species name
        String[] head = profilelist.get(0);
//        System.out.println(Arrays.toString(head));
        for (int i = 2; i < head.length; i++) {
            speciesNames.add(head[i]);
        }
//        System.out.println(speciesNames);

        for (int j = 1; j < profilelist.size(); j++) {
//            get entrez
            entrez.add(profilelist.get(j)[0]);
//            get symbol
            symbol.add(profilelist.get(j)[1]);

//            trans profile string to Integer
            int len = profilelist.get(j).length;

            int[] tem = new int[len - 2];

            for (int jj = 2; jj < len; jj++) {

                tem[jj - 2] = Integer.parseInt(profilelist.get(j)[jj]);
            }
            profile.add(tem);
//            System.out.println(Arrays.toString(tem));
//            System.out.println(Arrays.toString(profilelist.get(j)));

        }

//        System.out.println(entrez.toString());
//        System.out.println(symbol.toString());
//        for(int www=0;www<profile.size();www++){
//            System.out.println("line"+ Arrays.toString(profile.get(www)));
//        }
    }

    private boolean isHGT(List<Integer> sub){
        int sum = 0;
        for (int i = 0; i < sub.size(); i++) {
            sum = sub.get(i) + sum;
        }
        if (sum == 1)
            return true;
        else
            return false;
    }

    private boolean removeHGT(int[] profile, ParseTaxa parseTaxa) {
        boolean indicator = false;
        List<Integer> subProfileAnimals = new ArrayList<>();
        List<Integer> subProfilePlants = new ArrayList<>();
        List<Integer> subProfileFungi = new ArrayList<>();
        List<Integer> subProfileProtists = new ArrayList<>();
        // animal

        for (String ani : parseTaxa.getAnimals()
        ) {
            int idx = this.speciesNames.indexOf(ani);
            subProfileAnimals.add(profile[idx]);
        }
//        System.out.println(subProfileAnimals);
        if (isHGT(subProfileAnimals)) {
            indicator = true;
            animalsCause +=1;
        }
        // plant
        for (String ani : parseTaxa.getPlants()
        ) {
            int idx = this.speciesNames.indexOf(ani);
            subProfilePlants.add(profile[idx]);
        }
        if (isHGT(subProfilePlants)) {
            indicator = true;
            plantsCause +=1;
        }
        // fungi
        for (String ani : parseTaxa.getFungi()
        ) {
            int idx = this.speciesNames.indexOf(ani);
            subProfileFungi.add(profile[idx]);

        }
        if (isHGT(subProfileFungi)) {
            indicator = true;
            fungiCause += 1;
        }
        // protists
        for (String ani : parseTaxa.getProtists()
        ) {
            int idx = this.speciesNames.indexOf(ani);
            subProfileProtists.add(profile[idx]);

        }
        if (isHGT(subProfileProtists)) {
            indicator = true;
            protistsCause +=1;
        }
        return indicator;
    }


    public void prepareDataRemoveHGT(String pathTaxa) {
        ParseTaxa parseTaxa = new ParseTaxa(pathTaxa);
        parseTaxa.classifyTaxa();
//        get species name
        String[] head = profilelist.get(0);
//        System.out.println(Arrays.toString(head));
        for (int i = 2; i < head.length; i++) {
            speciesNames.add(head[i]);
        }
//        System.out.println(speciesNames);

        for (int j = 1; j < profilelist.size(); j++) {
            //            trans profile string to Integer
            int len = profilelist.get(j).length;

            int[] tem = new int[len - 2];

            for (int jj = 2; jj < len; jj++) {

                tem[jj - 2] = Integer.parseInt(profilelist.get(j)[jj]);
            }
            //remove HGT
            if (!removeHGT(tem,parseTaxa)){
                profile.add(tem);

//            get entrez
                entrez.add(profilelist.get(j)[0]);
//            get symbol
                symbol.add(profilelist.get(j)[1]);

            } else {
                this.totalHGT +=1;
            }

//            System.out.println(Arrays.toString(tem));
//            System.out.println(Arrays.toString(profilelist.get(j)));

        }

        System.err.println("Detecting suspicious HGT by animals subgroup: " + this.animalsCause + " genes");
        System.err.println("Detecting suspicious HGT by plants subgroup: " + this.plantsCause + " genes");
        System.err.println("Detecting suspicious HGT by fungi subgroup: " + this.fungiCause + " genes");
        System.err.println("Detecting suspicious HGT by protists subgroup: " + this.protistsCause + " genes");
        System.err.println("Total infer suspicious HGT: " + this.totalHGT + " genes");
        System.err.println("Warning: removing " + this.totalHGT + " genes from the phylogenetic profile.");

//        System.out.println(entrez.toString());
//        System.out.println(symbol.toString());
//        for(int www=0;www<profile.size();www++){
//            System.out.println("line"+ Arrays.toString(profile.get(www)));
//        }
    }


}
