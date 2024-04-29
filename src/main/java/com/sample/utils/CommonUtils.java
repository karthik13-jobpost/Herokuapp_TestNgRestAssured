package com.sample.utils;

public class CommonUtils {

    public static boolean isBoolean(String text) {

            if(text.equalsIgnoreCase("TRUE")) {
                return true;
            }else{
                return false;
            }


    }

    public static boolean isInteger(String text){
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false; // Not a number or invalid format
        }
    }
}
