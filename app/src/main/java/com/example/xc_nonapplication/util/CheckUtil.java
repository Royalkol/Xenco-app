package com.example.xc_nonapplication.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/7/24 09:01
 */
public class CheckUtil {
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }
    public static boolean isDouble(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9.]+$").matcher(input);
        return mer.find();
    }
}
