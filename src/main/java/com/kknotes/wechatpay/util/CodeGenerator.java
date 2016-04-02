package com.kknotes.wechatpay.util;

import java.util.Random;

/**
 * Created by kuikui on 2/24/16.
 * 
 * random code generator
 */
public class CodeGenerator {

    private static String allChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static String upperCaseCharsAndNumber = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int length){
        Random random = new Random();
        StringBuilder buf = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(allChars.charAt(num));
        }
        return buf.toString();
    }
    public static String generateUpperCaseRandomString(int length){
        Random random = new Random();
        StringBuilder buf = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(36);
            buf.append(upperCaseCharsAndNumber.charAt(num));
        }
        return buf.toString();
    }

    public static String generateRandomString(){
        return generateRandomString(8);
    }

}
