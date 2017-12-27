package com.stiei.trainticket.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    public static String MD5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] b = s.getBytes("UTF-8");
        m.update(b, 0, b.length);
        return new BigInteger(1, m.digest()).toString(16);
    }

}
