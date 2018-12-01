package leetcode;

import java.util.*;

/**
 * Created by yuank on 11/30/18.
 */
public class LE_451_Sort_Characters_By_Frequency {
    /**
         Given a string, sort it in decreasing order based on the frequency of characters.

         Example 1:

         Input:
         "tree"

         Output:
         "eert"

         Explanation:
         'e' appears twice while 'r' and 't' both appear once.
         So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.

         Example 2:

         Input:
         "cccaaa"

         Output:
         "cccaaa"

         Explanation:
         Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
         Note that "cacaca" is incorrect, as the same characters must be together.

         Example 3:

         Input:
         "Aabb"

         Output:
         "bbAa"

         Explanation:
         "bbaA" is also a valid answer, but "Aabb" is incorrect.
         Note that 'A' and 'a' are treated as two different characters.

         Medium
     */

    /**
     * HashMap + Bucket Sort
     *
     * Space and Time : O(n)
     *
     */
    public class Solution1 {
        public String frequencySort(String s) {
            if (null == s || "".equals(s))
                return s;

            int[] map = new int[256];
            int max = 0;

            for (char c : s.toCharArray()) {
                map[c]++;
                max = Math.max(map[c], max);
            }

            String[] bucket = new String[max + 1];
            for (int i = 0; i < 256; i++) {
                if (map[i] > 0) {
                    String val = bucket[map[i]];
                    if (val == null) {
                        bucket[map[i]] = "" + (char) i;
                    } else {
                        bucket[map[i]] = val + (char) i;
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int j = max; j > 0; j--) {
                if (!"".equals(bucket[j]) && null != bucket[j]) {
                    char[] p = bucket[j].toCharArray();
                    for (char q : p) {
                        for (int k = 0; k < j; k++) {
                            sb.append(q);
                        }
                    }
                }
            }

            return sb.toString();
        }
    }

    /**
     * A concise version of HashMap + Bucket sort
     */
    public class Solution2 {
        public String frequencySort(String s) {
            Map<Character, Integer> map = new HashMap<>();
            for (char c : s.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            List<Character>[] bucket = new List[s.length() + 1];
            for (char key : map.keySet()) {
                int frequency = map.get(key);
                if (bucket[frequency] == null) {
                    bucket[frequency] = new ArrayList<>();
                }
                bucket[frequency].add(key);
            }

            StringBuilder sb = new StringBuilder();
            for (int pos = bucket.length - 1; pos >= 0; pos--) {
                if (bucket[pos] != null) {
                    for (char c : bucket[pos]) {
                        for (int i = 0; i < map.get(c); i++) {
                            sb.append(c);
                        }
                    }
                }
            }

            return sb.toString();
        }
    }

    /**
     * HashMap + Heap
     * Time  : O(nlogm), m is number of unique chars in s.
     * Space : O(m)
     */
    public class Solution3 {
        public String frequencySort(String s) {
            Map<Character, Integer> map = new HashMap<>();
            for (char c : s.toCharArray())
                map.put(c, map.getOrDefault(c, 0) + 1);

            /**
             * !!!
             * Use Map.Entry, instead of creating a new class "Pair", in Max Heap
             */
            PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
            /**
             * !!!
             * pq.addAll
             */
            pq.addAll(map.entrySet());

            StringBuilder sb = new StringBuilder();
            while (!pq.isEmpty()) {
                Map.Entry e = pq.poll();
                for (int i = 0; i < (int)e.getValue(); i++) {
                    sb.append(e.getKey());
                }
            }
            return sb.toString();
        }
    }
}
