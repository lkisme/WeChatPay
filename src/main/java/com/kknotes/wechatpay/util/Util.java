package com.kknotes.wechatpay.util;

import java.io.*;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kuikui on 1/19/16.
 */
public class Util {
	private static final int BUFFER_SIZE = 1024;
	
    public static boolean objectEquivalent(Object o1, Object o2, String[] excludes){
        if(null == o1 || null == o2 ){
            return false;
        }
        Class<? extends Object> c1 = o1.getClass();
        Class<? extends Object> c2 = o2.getClass();

        if(!c1.equals(c2)){
            return false;
        }

        List<Field> fields = Arrays.asList(c1.getDeclaredFields());
        List<Field> fields2 = Arrays.asList(c1.getDeclaredFields());

        for (Field field : fields
             ) {
            if(!fields2.contains(field)){
                return false;
            }else{
                if(contains(excludes, field.getName())){
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Object fieldValue1 = field.get(o1);
                    Object fieldValue2 = field.get(o2);
                    boolean bothNull = (null == fieldValue1 && null == fieldValue2);
                    boolean equals = (null != fieldValue1 && fieldValue1.equals(fieldValue2));
                    if(!bothNull && !equals) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }
    public static boolean objectEquivalent(Object o1, Object o2){
        return objectEquivalent(o1,o2,null);
    }
    public static boolean contains(Object[] array, Object value){
        if(null == array || value == null || array.length < 1){
            return false;
        }
        for (Object v:array
                ) {
            if(value.equals(v)){
                return true;
            }
        }
        return false;
    }

    /**
     * use MD5 algorithm to hash String(UTF8)
     * @param content
     * @return
     */
    public static String caculateMD5HashOfString(String content) {

        byte[] bytes = new byte[0];
        try {
            bytes = content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return caculateMD5HashOfBytes(bytes);

    }
    /**
     * use MD5 algorithm
     * @param bytes
     * @return
     */
    public static String caculateMD5HashOfBytes(byte[] bytes) {


        MessageDigest caculator = null;
        try {
            caculator = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        caculator.update(bytes);

        return bytesToHexString(caculator.digest());

    }

    /**
     * byte数组转换成16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /** 
     * 将InputStream转换成String 
     * @param in InputStream 
     * @return String 
     * @throws Exception 
     *  
     */  
    public static String InputStreamToString(InputStream in) throws Exception{  
          
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return new String(outStream.toByteArray(),"ISO-8859-1");  
    }

    public static double squareDouble(double d){
        return d * d;
    }

}
