package leetcode;

import java.util.*;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_49_Group_Anagrams {
    /**
        Given an array of strings, group anagrams together.

        For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
        Return:

        [
          ["ate", "eat","tea"],
          ["nat","tan"],
          ["bat"]
        ]
        Note: All inputs will be in lower-case.

         变形题：
         按输入list的顺序，输出所有有anagram关系的string
         E.g. 输入[cat, taco, ape, tac, game, tcoa, tca]
         输出[cat, taco, tac, tcoa, tca] //cat和tac和tca为anagram，taco和tcoa为anagram
     */

    /**
     * example:
     * ["abc", "cba"]
     *
     * "abc":  count[0] = 1, count[1] = 1, count[2] = 1
     * key : "0a1b2c", count of char + char itself.
     *
     * same for "cba"
     *
     * if it's "aaabbddd":
     * key : "3a2b3d", since we go through count[] to generate key, it goes from 'a' to 'z'.
     */

    /**
     * Faster with only one StringBuilder(), 67%
     */
    public class Solution_best {
        public List<List<String>> groupAnagrams(String[] strs) {
            List<List<String>> res = new ArrayList<>();
            if (null == strs || strs.length == 0) {
                return res;
            }

            Map<String, List<String>> map = new HashMap<>();
            StringBuilder sb = new StringBuilder();


            for (String str : strs) {
                int[] count = new int[26];
                for (char c : str.toCharArray()) {
                    count[c - 'a']++;
                }

                /**
                 * !!!
                 * no "clear()" method for StringBuilder
                 */
                sb.setLength(0);

                for (int i = 0; i < 26; i++) {
                    sb.append(count[i]).append('a' + i);
                }

                String key = sb.toString();
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(str);
            }

            //or return new ArrayList<>(dist.values());

            for (List<String> list : map.values()) {
                res.add(list);
            }

            return res;
        }
    }

    /**
     * Time : O(m * n), Space: O(n)  counting sort
     *
     * 4.60%, slow with String append
     */
    class Solution1 {
        public List<List<String>> groupAnagrams(String[] strs) {
            List<List<String>> res = new ArrayList<>();
            if (null == strs || strs.length == 0) return res;

            Map<String, List<String>> map = new HashMap<>();

            for (String str : strs) {
                int[] count = new int[26];
                for (char c : str.toCharArray()) {
                    count[c - 'a']++;
                }

                String s = "";
                for (int i = 0; i < count.length; i++) {
                    if (count[i] > 0) {
                        s += String.valueOf(count[i]) + String.valueOf((char) ('a' + i));
                    }
                }

                if (!map.containsKey(s)) {
                    map.put(s, new ArrayList<String>());
                }
                List<String> al = map.get(s);
                al.add(str);
            }

            return new ArrayList<>(map.values());
        }
    }

    /**
     * Sorting solution
     *
     * O(m * nlogn)  38.2%
     */
    public class Solution2 {
        public List<List<String>> groupAnagrams(String[] strs) {
            // write your code here
            Map<String, List<String>> map = new HashMap<>();
            for (String s : strs) {
                char[] sc = s.toCharArray();
                Arrays.sort(sc);
                String key = String.valueOf(sc);
                map.putIfAbsent(key, new ArrayList<>());
                map.get(key).add(s);
            }
            return new ArrayList<>(map.values());
        }

    }

    class Solution_Practice {
        public List<List<String>> groupAnagrams(String[] strs) {
            List<List<String>> res = new ArrayList<>();
            if (strs == null || strs.length == 0) return res;

            Map<String, List<String>> map = new HashMap<>();
            StringBuilder sb = new StringBuilder();

            for (String str : strs) {
                /**
                 * !!!
                 * Must inside the for loop here
                 */
                int[] count = new int[26];

                for (char c : str.toCharArray()) {
                    count[c - 'a']++;
                }

                for (int i = 0;i < 26; i++) {
                    if (count[i] > 0) {
                        sb.append('a' + i).append(count[i]);
                    }
                }

                String key = sb.toString();
                sb.setLength(0);
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(str);
            }

            return new ArrayList<>(map.values());
        }
    }
}
