package leetcode;

import java.util.*;

public class LE_1002_Find_Common_Characters {
    /**
     * Given an array A of strings made only from lowercase letters,
     * return a list of all characters that show up in all strings
     * within the list (including duplicates).  For example, if a
     * character occurs 3 times in all strings but not 4 times, you
     * need to include that character three times in the final answer.
     *
     * You may return the answer in any order.
     *
     * Example 1:
     * Input: ["bella","label","roller"]
     * Output: ["e","l","l"]
     *
     * Example 2:
     * Input: ["cool","lock","cook"]
     * Output: ["c","o"]
     *
     * Note:
     * 1 <= A.length <= 100
     * 1 <= A[i].length <= 100
     * A[i][j] is a lowercase letter
     *
     * Easy
     */

    /**
     * Build inverted index using hashmap
     */
    class Solution1 {
        class Pair {
            int idx;
            int count;

            public Pair(int idx, int count) {
                this.idx = idx;
                this.count = count;
            }
        }

        public List<String> commonChars(String[] A) {
            List<String> res = new ArrayList<>();
            if (A == null || A.length == 0) return res;

            int n = A.length;
            Map<Character, List<Pair>> map = new HashMap<>();

            for (int i = 0; i < n; i++) {
                Map<Character, Integer> count = new HashMap<>();
                for (char c : A[i].toCharArray()) {
                    count.put(c, count.getOrDefault(c, 0) + 1);
                }

                for (char key : count.keySet()) {
                    if (!map.containsKey(key)) {
                        map.put(key, new ArrayList<>());
                    }
                    map.get(key).add(new Pair(i, count.get(key)));
                }
            }

            for (char key : map.keySet()) {
                List<Pair> l = map.get(key);

                if (l.size() == n) {
                    /**
                     * !!!
                     * If one character appears multiple times in each word,
                     * then we only add the number of the min among all counts.
                     * This satisfies the requirement we only add char that shows
                     * in all words.
                     */
                    int min = Integer.MAX_VALUE;
                    for (Pair p : l) {
                        min = Math.min(min, p.count);
                    }

                    for (int i = 0; i < min; i++) {
                        res.add(key + "");
                    }
                }
            }

            return res;
        }
    }

    /**
     * same inverted index, using array
     */
    class Solution2 {
        public List<String> commonChars(String[] strs) {
            int[] charset = new int[26]; // only lowercase letters
            Arrays.fill(charset, Integer.MAX_VALUE);

            for (String str : strs) {
                int[] count = buildCharFrequencyTable(str);
                for (int i = 0 ; i < charset.length; i++) {
                    charset[i] = Math.min(charset[i], count[i]);
                }
            }

            return toList(charset);
        }

        private List<String> toList(int[] charset) {
            List<String> result = new ArrayList<>();

            for (int i = 0; i < charset.length; i++) {
                for (int sz = charset[i]; sz > 0; sz--) {
                    result.add("" + (char) (i + 'a'));
                }
            }

            return result;
        }

        private int[] buildCharFrequencyTable(String str) {
            int[] count = new int[26];
            for (char c : str.toCharArray()) {
                count[c - 'a']++;
            }
            return count;
        }
    }
}
