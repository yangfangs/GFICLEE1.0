package test;

import predict.ParseProfile;
import utils.ParseTaxa;

public class TestParseProfile {
    public static void main(String[] args) {
        String profilePath = "/home/yangfang/GFICLEE/HTG_profile/hsa.matrix138.e3.q00.p20.txt";
        ParseProfile profile = new ParseProfile(profilePath);
        profile.prepareDataRemoveHGT("/home/yangfang/GFICLEE/HTG_profile/138_Taxa.txt");

//        ParseTaxa parseTaxa = new ParseTaxa("/home/yangfang/GFICLEE/HTG_profile/138_Taxa.txt");
//        parseTaxa.classifyTaxa();
//        System.out.println(parseTaxa.getAnimals());
    }

}
