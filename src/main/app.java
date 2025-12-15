package com.example.utils;

public class StringUtils {

    public boolean isPalindrome(String input) {
        if (input == null) {
            return false;
        }
        String clean = input.replaceAll("\\s+", "").toLowerCase();
        return new StringBuilder(clean).reverse().toString().equals(clean);
    }

    public String reverse(String input) {
        if (input == null) {
            return "";
        }
        return new StringBuilder(input).reverse().toString();
    }

    public int wordCount(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }
        return input.trim().split("\\s+").length;
    }
}
