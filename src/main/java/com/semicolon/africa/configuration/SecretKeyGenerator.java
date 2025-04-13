package com.semicolon.africa.configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("HmacSha256");
        keygen.init(256);
        SecretKey key = keygen.generateKey();
        String encodeKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated Secret Key: " + encodeKey);
    }
}
