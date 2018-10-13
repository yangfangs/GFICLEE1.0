package utils;

import tree.ParseNewickTree;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Utils {

    //    is numeric ?
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    public static boolean isDouble(String number) {
        number = number.trim();
        String pointPrefix = "(\\-|\\+){0,1}\\d*\\.\\d+";

        String pointSuffix = "(\\-|\\+){0,1}\\d+\\.";
        if (number.matches(pointPrefix) || number.matches(pointSuffix))
            return true;
        else
            return false;

    }

    public static Map<ParseNewickTree.Node, Integer> countRepet(List<ParseNewickTree.Node> list) {
        Map<ParseNewickTree.Node, Integer> map = new HashMap<>();
        for (ParseNewickTree.Node node : list) {
            if (map.containsKey(node)) {
                map.put(node, map.get(node).intValue() + 1);
            } else {
                map.put(node, 1);
            }


        }

        return map;
    }

    public static Map<Integer, Integer> countRepetInteger(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer node : list) {
            if (map.containsKey(node)) {
                map.put(node, map.get(node).intValue() + 1);
            } else {
                map.put(node, 1);
            }
        }

        return map;
    }



        public static String checkInputFile(String inputPath){
            String checkPath;
            File file = new File(inputPath);
            File f= new File(".");
            String path = null;
            try {
                path = f.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(inputPath.startsWith("/")){
                checkPath = inputPath;
            }else {
                checkPath = path + "/" + inputPath;


            }
            return checkPath;


        }
        public static String checkOutputFile(String outputPath){
            String checkPath;
            String path = null;
            File f= new File(".");
            try {
                path = f.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(outputPath.startsWith("/")){
                checkPath = outputPath;
            }else {
                checkPath = path + "/" + outputPath;
            }
            return checkPath;
        }




}



