package com.epam.payments.utils;

import com.epam.payments.exception.EncryptionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private static final Logger LOGGER = LogManager.getLogger(Encryption.class);

    public static String encrypt(String dataToEncrypt) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(dataToEncrypt.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Cryptographic algorithm is requested but is not available in the environment", e);
            throw new EncryptionException(e);
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        StringBuilder encryptedPassword = new StringBuilder(bigInteger.toString());

        while (encryptedPassword.length() < 32) {
            encryptedPassword.insert(0, "0");
        }

        return encryptedPassword.toString();
    }
}
