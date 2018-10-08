package test;

import io.FileInput;

public class testFileInput {
    public static void main(String[] args) {
        FileInput foo = new FileInput("/home/yangfang/PCSF/test_clime/test_tree.nwk");
        String str = foo.readString();
        System.out.println(str);
    }
}
