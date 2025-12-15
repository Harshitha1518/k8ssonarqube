package com.example.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    StringUtils utils = new StringUtils();

    @Test
    void testPalindrome() {
        assertTrue(utils.isPalindrome("Madam"));
        assertTrue(utils.isPalindrome("Never odd or even"));
    }

    @Test
    void testReverse() {
        assertEquals("olleh", utils.reverse("hello"));
    }

    @Test
    void testWordCount() {
        assertEquals(4, utils.wordCount("Jenkins SonarQube Quality Gate"));
    }

    @Test
    void testNullInput() {
        assertFalse(utils.isPalindrome(null));
        assertEquals("", utils.reverse(null));
        assertEquals(0, utils.wordCount(null));
    }
}
