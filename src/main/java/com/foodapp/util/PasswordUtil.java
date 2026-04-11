package com.foodapp.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PasswordUtil — SHA-256 password hashing utility.
 * Used for secure password storage and verification.
 */
public class PasswordUtil {

    /**
     * Hash a plain-text password using SHA-256.
     * 
     * @param password the plain-text password
     * @return the hexadecimal SHA-256 hash string
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, hashBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros to ensure 64 hex characters
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * Verify a plain-text password against a stored hash.
     * 
     * @param plainPassword  the plain-text password entered by the user
     * @param hashedPassword the stored SHA-256 hash
     * @return true if the password matches
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hashed = hashPassword(plainPassword);
        return hashed.equals(hashedPassword);
    }
}
