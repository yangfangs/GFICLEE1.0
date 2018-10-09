package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInput {

    private String filePath;
    private ArrayList<String[]> fileList = new ArrayList<>();

    public FileInput(String filePath) {
        this.filePath = filePath;

    }

    public List<String[]> read() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.filePath));
            String str;

            while ((str = in.readLine()) != null) {

                String[] arr = str.split("\t");

//                System.out.println(Arrays.toString(arr));
                fileList.add(arr);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;

    }


    public String readString() {
        StringBuilder buffer = null;
        try {
            buffer = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(this.filePath));

            String str;
            while ((str = br.readLine()) != null) {
                buffer.append(str.trim());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();

    }
}





