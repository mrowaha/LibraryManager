package com.librarymanager.api.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    private String hashableWord ;
    private String resultString ;

    public SHA256(String input) throws NoSuchAlgorithmException {
        
        hashableWord = input ;
        byte[] temp =  this.getSHA() ;
        resultString = toHexString(temp) ;
    
    }

    public byte[] getSHA() throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(hashableWord.getBytes(StandardCharsets.UTF_8));
    
    }
     
    public String toHexString(byte[] hash) {

        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
 
        return hexString.toString();
    
    }

    public String getHashed(){
        return resultString;
    }
}
