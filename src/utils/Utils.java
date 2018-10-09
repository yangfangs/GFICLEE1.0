package utils;

import java.util.regex.Pattern;

public class Utils {

//    is numeric ?
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
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
        number=number.trim();
        String pointPrefix="(\\-|\\+){0,1}\\d*\\.\\d+";

        String pointSuffix="(\\-|\\+){0,1}\\d+\\.";
        if(number.matches(pointPrefix)||number.matches(pointSuffix))
            return true;
        else
            return false;

    }



}
