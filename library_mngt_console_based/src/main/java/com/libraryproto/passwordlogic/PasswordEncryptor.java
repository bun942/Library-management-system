package com.libraryproto.passwordlogic;

public class PasswordEncryptor {
    public static String encrypt(String password, int shift) {
        StringBuilder result = new StringBuilder();

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char c_new = (char)(((c - 'A' + shift) % 26) + 'A');
                result.append(c_new);
            } else if (Character.isLowerCase(c)) {
                char c_new = (char)(((c - 'a' + shift) % 26) + 'a');
                result.append(c_new);
            } else {
                result.append(c); // Keep digits/symbols unchanged
            }
        }
        return result.toString();
    }
}

