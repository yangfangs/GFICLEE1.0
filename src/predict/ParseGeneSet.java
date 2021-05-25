package predict;

import io.FileInput;

import java.util.ArrayList;
import java.util.List;

import static utils.Utils.isNumeric;


public class ParseGeneSet {
    private final List<String[]> geneSetlist;
    private boolean isSymbol = false;
    private boolean isEntrez = false;
    private ArrayList<String> inputSymbol = new ArrayList<>();
    private ArrayList<String> inputEntrez = new ArrayList<>();
    private String inputGeneSetPath;
    private ArrayList<String> pathway = new ArrayList<>();


    public ParseGeneSet(String inputGeneSetPath) {
        this.inputGeneSetPath = inputGeneSetPath;
        FileInput geneSetFile = new FileInput(this.inputGeneSetPath);
        geneSetlist = geneSetFile.read();


    }

    public String getInputGeneSetPath() {
        return inputGeneSetPath;
    }

    public ArrayList<String> getInputEntrez() {
        return inputEntrez;
    }

    public ArrayList<String> getInputSymbol() {
        return inputSymbol;
    }

    public ArrayList<String> getPathway() {
        return pathway;
    }

    public List<String[]> getGeneSetlist() {
        return geneSetlist;
    }

    public boolean isEntrez() {
        return isEntrez;
    }

    public boolean isSymbol() {
        return isSymbol;
    }

    public void prepareData() {
        for (int i = 1; i < geneSetlist.size(); i++){
            String name = geneSetlist.get(i)[0];
            pathway.add(geneSetlist.get(i)[1]) ;
            if(isNumeric(name)){
                inputEntrez.add(name);
                isEntrez = true;

            }else {
                inputSymbol.add(name);
                isSymbol = true;

            }
        }

    }


    }

