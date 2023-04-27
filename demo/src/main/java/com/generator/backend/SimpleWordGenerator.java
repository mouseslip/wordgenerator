package com.generator.backend;

import java.util.*;


/**
 * This word generator only supports words of length 3,4 and 7
 */
public class SimpleWordGenerator {

    private Dictionary dictionary = Dictionary.fileDictionary();

    private static Map<Character,Character> revKeyboard = new HashMap<>();

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
        Map<Integer, List<String>> wordsBySize = dictionary.getWordsBySize();
        List<String> prefixWords = new ArrayList<>();
        List<String> suffixWords = new ArrayList<>();
        // full here means prefix + suffix
        List<String> fullWords = new ArrayList<>();
        List<String> generatedWords = new ArrayList<>();
        for (String prefixWordCandidate : wordsBySize.get(3)) {
            StringBuilder prefixCandidate = new StringBuilder();
            for (char c : prefixWordCandidate.toLowerCase().toCharArray()) {
                prefixCandidate.append(revKeyboard.get(c));
            }
            if (phoneNumber.getPrefix().equals(prefixCandidate.toString())) {
                prefixWords.add(prefixWordCandidate);
            }
        }

        for (String suffixWordCandidate : wordsBySize.get(4)) {
            StringBuilder suffixCandidate = new StringBuilder();
            for (char c : suffixWordCandidate.toLowerCase().toCharArray()) {
                suffixCandidate.append(revKeyboard.get(c));
            }
            if (phoneNumber.getSuffix().equals(suffixCandidate.toString())) {
                suffixWords.add(suffixWordCandidate);
            }
        }

        for (String fullWordCandidate : wordsBySize.get(7)) {
            StringBuilder fullCandidate = new StringBuilder();
            for (char c : fullWordCandidate.toLowerCase().toCharArray()) {
                fullCandidate.append(revKeyboard.get(c));
            }
            String prefixAndSuffix = phoneNumber.getPrefix() + phoneNumber.getSuffix();
            if (prefixAndSuffix.equals(fullCandidate.toString())) {
                fullWords.add(fullWordCandidate);
            }
        }

        // to perform cartesian product make sure both prefix words and suffix words are not empty
        if (suffixWords.size() != 0 && prefixWords.size() != 0) {
            for (String w1 : prefixWords) {
                for (String w2: suffixWords) {
                    generatedWords.add(phoneNumber.getArea() + "-" + w1 + "-" + w2);
                }
            }
        }

        if (fullWords.size() != 0) {
            for (String w : fullWords) {
                generatedWords.add(phoneNumber.getArea() + "-" + w);
            }
        }

        return generatedWords;
    }

}
