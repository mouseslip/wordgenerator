package com.generator.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Dictionary {
    private List<String> words;

    public Dictionary(List<String> words) {
        this.words = new ArrayList<>();
        for (String word: words) {
            this.words.add(word);
        }
    }

    /**
     * Organizes the words in the dictionary into multiple buckets of words
     * There are 8 buckets
     * Bucket 1: words of size 1
     * Bucket 2: words of size 2
     * ...
     * Bucket i: words of size i
     *
     * @return
     * Map of words, where the key is size of the value is the list of words
     */
    public Map<Integer, List<String>> getWordsBySize() {
        Map<Integer, List<String>> wordsBySize = new HashMap<>();
        for (int i = 0; i <= 7; i++) {
            wordsBySize.put(i, new ArrayList<String>());
            wordsBySize.put(i, new ArrayList<String>());
            wordsBySize.put(i, new ArrayList<String>());
        }
        for (String word : words) {
            int wl = word.length();
            if (wl >=1 && wl <= 7) {
                wordsBySize.get(wl).add(word);
            }
        }
        return wordsBySize;
    }

    static Dictionary fileDictionary() {
        List<String> examples = new ArrayList<>();
        try {
            String content = new Scanner(new File("src/database.txt")).useDelimiter("\\Z").next();
            for (String line : content.split("\n")) {
                for (String word : line.split(" ")) {
                    examples.add(word.trim());
                }
            }
        }
        catch (Exception e) {
            System.out.println("Exception happened while loading words file");
        }
        Dictionary d = new Dictionary(examples);
        return d;
    }


}

