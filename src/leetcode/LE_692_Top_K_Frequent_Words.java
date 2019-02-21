package leetcode;

import java.util.*;

/**
 * Created by yuank on 5/16/18.
 */
public class LE_692_Top_K_Frequent_Words {
    /**
         Given a non-empty list of words, return the k most frequent elements.

         Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency,
         then the word with the lower alphabetical order comes first.

         Example 1:
         Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
         Output: ["i", "love"]
         Explanation: "i" and "love" are the two most frequent words.
         Note that "i" comes before "love" due to a lower alphabetical order.

         Example 2:
         Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
         Output: ["the", "is", "sunny", "day"]
         Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
         with the number of occurrence being 4, 3, 2 and 1 respectively.

         Note:
         You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
         Input words contain only lowercase letters.

         Medium
     */

    /**
     Bucket solution, same as LE_347_Top_K_Frequent_Elements.
     Only difference - need to consider alphabetic order, therefore use PriorityQueue instead of list here

     Time  : O(nlogn)
     Space : O(n)
     **/
    class Solution1 {
        public List<String> topKFrequent(String[] words, int k) {
            List<String> res = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            PriorityQueue<String>[] bucket = new PriorityQueue[words.length + 1];

            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }

            for (String key : map.keySet()) {
                int freq = map.get(key);
                if (bucket[freq] == null) {
                    bucket[freq] = new PriorityQueue<>();
                }
                bucket[freq].offer(key);
            }

            for (int i = bucket.length - 1; i >= 0 && res.size() < k; i--) {
                if (bucket[i] != null) {
                    while (bucket[i].size() != 0 && res.size() < k) {
                        res.add(bucket[i].poll());
                    }
                }
            }

            return res;
        }
    }

    //Same algorithm as Solution1, return String[]
    class Solution2 {
        public String[] topKFrequentWords2(String[] words, int k) {
            int len = words.length;
            String[] res = new String[k];

            PriorityQueue<String>[] bucket = new PriorityQueue[len + 1];
            Map<String, Integer> map = new HashMap<>();

            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }

            for (String key : map.keySet()) {
                int num = map.get(key);
                if (bucket[num] == null) {
                    bucket[num] = new PriorityQueue<>();
                }
                bucket[num].offer(key);
            }

            for (int i = len, j = 0; i >= 0 && j < k; i--) {
                if (bucket[i] != null) {
                    while (bucket[i].size() != 0 && j < k) {
                        res[j] = bucket[i].poll();
                        j++;
                    }
                }
            }

            return res;
        }
    }

    /**
     * Same logic as Solution3 in LE_347_Top_K_Frequent_Elements
     *
     * Time  : O(n + nlogk)
     * Space : O(n)
     */
    class Solution3 {
        public List<String> topKFrequent(String[] words, int k) {
            Map<String, Integer> map = new HashMap<>();
            List<String> res = new ArrayList<>();

            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }

            /**
             * !!!
             * Comparator defined for sorting logic in pq
             */
            PriorityQueue<Map.Entry<String, Integer>> pq =
                    new PriorityQueue<>((a, b) -> a.getValue() != b.getValue() ? a.getValue() - b.getValue() : b.getKey().compareTo(a.getKey()));

            for (Map.Entry<String, Integer> e : map.entrySet()) {
                /**
                 * "pq.offer(e)"
                 */
                pq.offer(e);
                if (pq.size() > k) {
                    pq.poll();
                }
            }

            while (!pq.isEmpty()) {
                /**
                 * pq by default is min heap, requirement here is to put max freq first,
                 * therefore, add at index 0.
                 *
                 * "pq.poll().getKey()"
                 */
                res.add(0, pq.poll().getKey());
            }

            return res;
        }
    }
}
