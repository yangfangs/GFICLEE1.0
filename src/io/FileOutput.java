package io;

import utils.Score;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileOutput {
    private String fileOutputPath;

    public FileOutput(String fileOutputPath) {
        this.fileOutputPath = fileOutputPath;
    }

    public void writeScore(List<Score> listScore) throws IOException {
        File file = new File(this.fileOutputPath);
        if (!file.isFile()) {
            file.createNewFile();

        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileOutputPath));
        String header = "name\tscore\n";
        writer.write(header);


        for(Score line:listScore){
            if (line.getValue() != -1000)
            writer.write(line.getName() + "\t" + line.getValue() + "\n");

        }
        writer.close();

    }


}
