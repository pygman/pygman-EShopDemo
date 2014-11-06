package com.pygman.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pygmalion on 2014/11/3.
 */
public class MD5 {
    private final static String[] strDigits={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    public MD5(){
    }
    private static String byteToArrayString(byte bByte){
        int iRet=bByte;
        if(iRet<0){
            iRet+=256;
        }
        int iD1=iRet/16;
        int iD2=iRet%16;
        return strDigits[iD1]+strDigits[iD2];
    }
    private static String byteToNum(byte bByte){
        int iRet=bByte;
        System.out.println("iRet1="+iRet);
        if(iRet<0){
            iRet+=256;
        }
        return String.valueOf(iRet);
    }
    private static String byteToString(byte[] bByte){
        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;i<bByte.length;i++){
            stringBuffer.append(byteToArrayString(bByte[i]));
        }
        return stringBuffer.toString();
    }
    public static String GetMD5Code(String strObj){
        String resultString=null;
        try {
            resultString=new String(strObj);
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            resultString=byteToString(messageDigest.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
        MD5 getMD5=new MD5();
        System.out.println(getMD5.GetMD5Code("Hello"));
    }
}
