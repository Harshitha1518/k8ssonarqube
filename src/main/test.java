package com.example.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    StringUtils utils = new StringUtils();

    @Test
    void testReverse() {
        assertEquals("olleh", utils.reverse("hello"));
    }

    @Test
    void testPalindromeTrue() {
        assertTrue(utils.isPalindrome("Madam"));
    }

    @Test
    void testPalindromeFalse() {
        assertFalse(utils.isPalindrome("Hello"));
    }

    @Test
    void testNullInput() {
        assertEquals("", utils.reverse(null));
        assertFalse(utils.isPalindrome(null));
    }
}
