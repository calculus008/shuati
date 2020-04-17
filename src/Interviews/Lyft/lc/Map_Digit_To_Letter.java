package Interviews.Lyft.lc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Map_Digit_To_Letter {
    /**
     * ⽤用trie 就是给⼀一个file ⾥⾯上半部分是单词下半部分是⼀一些数字的组合:
     *
     * AA
     * WOT
     * YOU
     * ME
     * 968
     * 63
     * 12345
     *
     * ​要求output是:
     *
     * 968: you, wot
     * 63: me
     * 12345: no result
     *
     * 就是把数字对应的字⺟母能组成的单词找出来，数字对应的是电话号码key pad上的character
     */

    static int[] mapping = {2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9};

    static class TrieNode {
        TrieNode[] children;
        String number;

        public TrieNode() {
            children = new TrieNode[26];
            number = "";
        }
    }

    static TrieNode root = new TrieNode();

    public static Map<String, List<String>> matchNumberWithWords(String fileName) {
        Set<String> numSet = new HashSet<>();
        Set<String> wordSet = new HashSet<>();
        Map<String, List<String>> map = new HashMap<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String l = line.trim();

                if (line.isEmpty()) continue;

                if (isInteger(l)) {
                    numSet.add(l);
                } else {
                    wordSet.add(l);
                }

                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String num : numSet) {
            map.put(num, new ArrayList<>());
        }

        for (String word : wordSet) {
            TrieNode cur = root;

//            System.out.println("processing " + word);

            for (char c : word.toCharArray()) {
                int idx = c - 'A';
                if (cur.children[idx] == null) {
                    cur.children[idx] = new TrieNode();

                    /**
                     * !!!
                     */
                    cur.children[idx].number = cur.number + mapping[idx];
                }
                cur = cur.children[idx];
            }

//            System.out.println(cur.number);

            if (numSet.contains(cur.number)) {
                map.get(cur.number).add(word);
            }
        }

        return map;
    }

    private static boolean isInteger(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private static void printResult(Map<String, List<String>> map) {
        System.out.println("");
        System.out.println("---result----");
        for (String key : map.keySet()) {
            System.out.println(key + " : " + Arrays.toString(map.get(key).toArray()));
        }
    }

    public static void main(String[] args) {
        printResult(matchNumberWithWords(
                "/Users/yuank/Intellij/git/shuati/src/Interviews/Lyft/lc/Map_Digit_To_Letter_Input.txt"));
    }

}
