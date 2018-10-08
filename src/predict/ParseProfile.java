package predict;

import io.FileInput;

import java.util.ArrayList;

import java.util.List;

public class ParseProfile {
    private String profilePath;
    private final List<String[]> profilelist;
    private ArrayList<String> speciesNames = new ArrayList<>();
    private ArrayList<String> symbol = new ArrayList<>();
    private ArrayList<String> entrez = new ArrayList<>();
    private ArrayList<int[]> profile = new ArrayList<>();



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

    public ArrayList<String> getSpeciesNames() {
        return speciesNames;
    }

    public ArrayList<String> getSymbol() {
        return symbol;
    }

    public ArrayList<int[]> getProfile() {
        return profile;
    }

    public List<String[]> getProfilelist() {
        return profilelist;
    }


    public void readProfile() {
        FileInput profileFile = new FileInput(this.profilePath);

    }

    public void prepareData(){

//        get species name
        String[] head = profilelist.get(0);
//        System.out.println(Arrays.toString(head));
        for(int i=2;i<head.length;i++){
            speciesNames.add(head[i]);
        }
        System.out.println(speciesNames);

        for(int j=1; j<profilelist.size();j++){
//            get entrez
            entrez.add(profilelist.get(j)[0]);
//            get symbol
            symbol.add(profilelist.get(j)[1]);

//            trans profile string to Integer
            int len = profilelist.get(j).length;

            int [] tem = new int[len-2];

            for(int jj=2; jj<len;jj++){

                tem[jj-2] = Integer.parseInt(profilelist.get(j)[jj]);
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




}
