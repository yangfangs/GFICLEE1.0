package utils;

public class Score implements Comparable<Score> {
    private String name;
    private int value;
    private String predictBy;



    public Score(String name,int value,String predictBy){
        this.name = name;
        this.value = value;
        this.predictBy = predictBy;
    }




    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getPredictBy() {
        return predictBy;
    }

    @Override
    public int compareTo(Score score) {
        return score.getValue() - this.value;
    }
    @Override
    public String toString(){
        return "name=" + name + ", value=" + value +", predictBy=" + predictBy+"\n";
    }

}
