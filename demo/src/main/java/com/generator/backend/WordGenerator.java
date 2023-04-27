package com.generator.backend;

import java.util.*;

public class WordGenerator {

    // Assumption: phone number will only be broken to up to two words
    // 7 could be either a words of 7 lengths
    // or 2 words, first word of length 1 second word of length 6
    // or          first word of length 2 second word of length 5
    // or          first word of length 3 second word of length 4
    // etc ...
    // Examples:
    // 1-6   A- MOMENT
    // 2-5   GO-FEDEX
    // 3-4   DOG POOP
    // 4-3 POOP DOG
    // ...
    private static int[] ALLOWED_LEFT_SIZES = {1, 2, 3, 4, 5, 6, 7};
    private Dictionary dictionary = Dictionary.fileDictionary();
    private static Map<Character, Character> revKeyboard = new HashMap<>();

    static {
        revKeyboard.put('a', '2');
        revKeyboard.put('b', '2');
        revKeyboard.put('c', '2');
        revKeyboard.put('d', '3');
        revKeyboard.put('e', '3');
        revKeyboard.put('f', '3');
        revKeyboard.put('g', '4');
        revKeyboard.put('h', '4');
        revKeyboard.put('i', '4');
        revKeyboard.put('j', '5');
        revKeyboard.put('k', '5');
        revKeyboard.put('l', '5');
        revKeyboard.put('m', '6');
        revKeyboard.put('n', '6');
        revKeyboard.put('o', '6');
        revKeyboard.put('p', '7');
        revKeyboard.put('q', '7');
        revKeyboard.put('r', '7');
        revKeyboard.put('s', '7');
        revKeyboard.put('t', '8');
        revKeyboard.put('u', '8');
        revKeyboard.put('v', '8');
        revKeyboard.put('w', '9');
        revKeyboard.put('x', '9');
        revKeyboard.put('y', '9');
        revKeyboard.put('z', '9');
    }

    public List<String> generateWords(PhoneNumber phoneNumber) {
        List<String> fullWords = new ArrayList<>();
        for (int leftSize : ALLOWED_LEFT_SIZES) {
            fullWords.addAll(generateWords(phoneNumber, leftSize));
        }
        return fullWords;
    }

    /**
     *      * @param phoneNumber phone number used to generate the words
     *      * @param leftSize   this will be the size of the prefix part of the Phone Number
     *
     * A 7 characters word could be broken up into two parts of following lengths:
     *      Left side 7 - Right Side 0  Example: THRIFTY - EMPTY
     *      Left side 1 - Right side 6  Example: A       - MOMENT
     *      Left side 2 - Right side 5  Example: GO      - FEDEX
     *      Left side 3 - Right side 4  Example: DOG     - POOP
     *      Left side 4 - Right side 3  Example: LOOP    - FOG
     *      Left side 5 - Right side 2  Example: CRIME   - NO
     *      Left side 5 - Right side 1  Example: MOMENT  - A
     */
    /**
     *

     * @return
     * returns the list of generated words
     */
    public List<String> generateWords(PhoneNumber phoneNumber, int leftSize) {
        Map<Integer, List<String>> wordsBySize = dictionary.getWordsBySize();
        List<String> prefixWords = new ArrayList<>();
        List<String> suffixWords = new ArrayList<>();
        // full here means prefix + suffix
        List<String> fullWords = new ArrayList<>();
        List<String> generatedWords = new ArrayList<>();
        for (String prefixWordCandidate : wordsBySize.get(leftSize)) {
            StringBuilder prefixCandidate = new StringBuilder();
            for (char c : prefixWordCandidate.toLowerCase().toCharArray()) {
                prefixCandidate.append(revKeyboard.get(c));
            }
            if (phoneNumber.getPrefix(leftSize).equals(prefixCandidate.toString())) {
                prefixWords.add(prefixWordCandidate);
            }
        }

        for (String suffixWordCandidate : wordsBySize.get(PhoneNumber.PREFIX_AND_SUFFIX_LENGTH - leftSize)) {
            StringBuilder suffixCandidate = new StringBuilder();
            for (char c : suffixWordCandidate.toLowerCase().toCharArray()) {
                suffixCandidate.append(revKeyboard.get(c));
            }
            if (phoneNumber.getSuffix(leftSize).equals(suffixCandidate.toString())) {
                suffixWords.add(suffixWordCandidate);
            }
        }
        // This is the case where there is only a prefix of size 7 and no suffix
        if (leftSize == PhoneNumber.PREFIX_AND_SUFFIX_LENGTH) {
            for (String w : prefixWords) {
                generatedWords.add(phoneNumber.getArea() + "-" + w);
            }
        }
        // to perform cartesian product make sure both prefix words and suffix words are not empty
        else if (suffixWords.size() != 0 && prefixWords.size() != 0) {
            for (String w1 : prefixWords) {
                for (String w2 : suffixWords) {
                    generatedWords.add(phoneNumber.getArea() + "-" + w1 + "-" + w2);
                }
            }
        }
        return generatedWords;
    }

}
