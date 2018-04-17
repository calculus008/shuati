package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/15/18.
 */
public class LE_269_Alien_Dictionary {
    /**
     * There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you.
     * You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language.
     * Derive the order of letters in this language.

         Example 1:
         Given the following words in dictionary,

         [
         "wrt",
         "wrf",
         "er",
         "ett",
         "rftt"
         ]
         The correct order is: "wertf".

         Example 2:
         Given the following words in dictionary,

         [
         "z",
         "x"
         ]
         The correct order is: "zx".

         Example 3:
         Given the following words in dictionary,

         [
         "z",
         "x",
         "z"
         ]
         The order is invalid, so return "".

         Note:
         You may assume all letters are in lowercase.
         You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
         If the order is invalid, return an empty string.
         There may be multiple valid order of letters, return any one of them is fine.
     */

    /**
     Topological Sort
     1.Create graph in HashMap
     2.Find the start node, which is the one that has 0 in degree.
     3.From start node, do toloplogical sort using BFS

     Time : O(V + E) -> O(n), Space : O(n) -> O(26) -> O(1)

     Other topological sort problems : LE_207, LE_210
     */
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) return "";

        StringBuilder res = new StringBuilder();

        Map<Character, Set<Character>> map = new HashMap<>();

        int count = 0;
        int[] degree = new int[26];
        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (degree[c - 'a'] == 0) {
                    count++; //Get the total number of characters, to be used to verify if the order is invalid.
                    degree[c - 'a'] = 1; //init chars that appear as 1, since we can't find the start node with indegree '0'.
                }
            }
        }

        //Create graph in HashMap
        for (int i = 0; i < words.length - 1; i++) {
            char[] cur = words[i].toCharArray();
            char[] next = words[i + 1].toCharArray();
            int len = Math.min(cur.length, next.length);
            for (int j = 0; j < len; j++) {
                if(cur[j] != next[j]) {
                    if (!map.containsKey(cur[j])) {
                        map.put(cur[j], new HashSet<>());
                    }

                    /**
                     !!! need to use if here to avoid duplicates.
                     Yes, set won't have duplicate element, but the calculation for degree will go wrong
                     */
                    if (map.get(cur[j]).add(next[j])) {
                        degree[next[j] - 'a']++;
                    }
                    break;//!!!
                }
            }
        }

        Queue<Character> queue = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            if (degree[i] == 1) {
                queue.offer((char)('a' + i));
                // break;
            }
        }

        while (!queue.isEmpty()) {
            char c = queue.poll();
            res.append(c);
            if (map.containsKey(c)) {//!!!
                for (char ch : map.get(c)) {
                    //!!! decrease the degree, and insert the new nodes which have no parents now
                    if (--degree[ch - 'a'] == 1) {
                        queue.offer(ch);
                    }
                }
            }
        }

        if (count != res.length()) return "";

        return res.toString();
    }
}
