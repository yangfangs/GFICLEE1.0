package utils;

import io.FileInput;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParseTaxa {
    private final List<String[]> taxaList;
    private String taxaPath;
    //phylum
    private List<String> Animals = new ArrayList<>();
    private List<String> Plants = new ArrayList<>();
    private List<String> Fungi = new ArrayList<>();
    private List<String> Protists = new ArrayList<>();


    public ParseTaxa(String taxaPath) {
        this.taxaPath = taxaPath;
        FileInput taxaFile = new FileInput(this.taxaPath);
        taxaList = taxaFile.read();
    }

    public String getTaxaPath() {
        return taxaPath;
    }

    public List<String> getAnimals() {
        return Animals;
    }

    public List<String> getPlants() {
        return Plants;
    }

    public List<String> getFungi() {
        return Fungi;
    }

    public List<String> getProtists() {
        return Protists;
    }

    public void classifyTaxa() {
        for (int i = 0; i < taxaList.size(); i++) {
            String speciesName = taxaList.get(i)[0];
//            System.out.println(speciesName);
            String[] tem = taxaList.get(i)[1].split(";");
//            System.out.println(Arrays.toString(tem));
//            System.out.println(tem[1]);
            if (tem[1].equals("Animals")) {
                Animals.add(speciesName);
            } else if (tem[1].equals("Plants")) {
                Plants.add(speciesName);
            } else if (tem[1].equals("Fungi")) {
                Fungi.add(speciesName);
            } else if (tem[1].equals("Protists")) {
                Protists.add(speciesName);
            }

        }

    }
}