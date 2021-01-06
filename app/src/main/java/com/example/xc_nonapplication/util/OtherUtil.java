package com.example.xc_nonapplication.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/20 14:27
 */
public class OtherUtil {
    /**
     *
     * @param phoneNum
     * @return
     */
    public static boolean CheckMobilePhoneNum(String phoneNum) {
        String regex = "^(1[3-9]\\d{9}$)";
        if (phoneNum.length() == 11) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneNum);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

}
