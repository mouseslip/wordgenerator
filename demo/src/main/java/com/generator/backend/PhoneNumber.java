package com.generator.backend;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class PhoneNumber {
    //                            0 1 2 3 4 5 6 7 8 9  10 11
    // Example if phone number is 5 1 2 - 6 7 3 - 8 2  7   3
    // area will be 512
    // prefix is 673
    // suffix is 8273
    // prefixAndSuffix 6738273
    private String area;
    private String prefix;
    private String suffix;
    private String prefixAndSuffix;
    static Set<Character> digits = new HashSet<>();

    public static final int PREFIX_AND_SUFFIX_LENGTH = 7;

    static {
        // This is just a compact way of doing
        // digits.add('0');
        // digits.add('1');
        // ... until digits.add('10')
        for (int i = 0; i < 10; i++) {
            digits.add((char) ((int)'0' + i));
        }
    }

    public PhoneNumber(String phoneNumberStr) {
        validate(phoneNumberStr);
        String[] parts = phoneNumberStr.split("-");
        area = parts[0];
        prefix = parts[1];
        suffix = parts[2];
        this.prefixAndSuffix = prefix + suffix;
    }

    // only accept format like 512-673-8273 (XXX-XXX-XXXX where X is a digit)
    // TODO use a regex instead of the complicated checks below
    public static void validate(String phoneNumberStr) {
        if (phoneNumberStr.length() != 12) throw new InvalidParameterException("Phone Number length should be 12");
        if (phoneNumberStr.charAt(3) != '-') throw new InvalidParameterException("4th character should be '-'");
        if (phoneNumberStr.charAt(7) != '-') throw new InvalidParameterException("7th character should be '-'");
        String[] parts = phoneNumberStr.split("-");
        if (parts.length!=3) throw new InvalidParameterException("Phone number should only have two '-'");
        if (!allDigits(parts[0]) || !allDigits(parts[0]) || !allDigits(parts[0])) {
            throw new InvalidParameterException("Phone Number should only have digits in, supported format is XXX-XXX-XXXX where X is a digit");
        }
    }

    private static boolean allDigits(String s) {
        for (Character c : s.toCharArray()) {
            if (!digits.contains(c)) return false;
        }
        return true;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPrefix(int leftSideLength) {
        validateLength(leftSideLength);
        return prefixAndSuffix.substring(0, leftSideLength);
    }
    public String getSuffix() {
        return suffix;
    }

    public String getSuffix(int leftSideLength) {
        validateLength(leftSideLength);
        return prefixAndSuffix.substring(leftSideLength, PREFIX_AND_SUFFIX_LENGTH);
    }
    public String getArea() {
        return area;
    }
    public String getPrefixAndSuffix() {
        return prefixAndSuffix;
    }

    private void validateLength(int length) {
        if (length < 0 || length > PREFIX_AND_SUFFIX_LENGTH) {
            throw new InvalidParameterException("leftSideLength should be between 0 and " + PREFIX_AND_SUFFIX_LENGTH);
        }
    }


}
