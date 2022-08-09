package leetcode;

import java.util.*;

public class LE_916_Word_Subsets {
    /**
     * You are given two string arrays words1 and words2.
     *
     * A string b is a subset of string a if every letter in b occurs in a including multiplicity.
     *
     * For example, "wrr" is a subset of "warrior" but is not a subset of "world".
     * A string a from words1 is universal if for every string b in words2, b is a subset of a.
     *
     * Return an array of all the universal strings in words1. You may return the answer in any order.
     *
     * Example 1:
     * Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
     * Output: ["facebook","google","leetcode"]
     *
     * Example 2:
     * Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
     * Output: ["apple","google","leetcode"]
     *
     * Constraints:
     * 1 <= words1.length, words2.length <= 104
     * 1 <= words1[i].length, words2[i].length <= 10
     * words1[i] and words2[i] consist only of lowercase English letters.
     * All the strings of words1 are unique.
     *
     * Medium
     *
     * https://leetcode.com/problems/word-subsets/
     */

    /**
     * Best explanation:
     * https://leetcode.com/problems/word-subsets/discuss/2353065/C%2B%2B-Solution-Explained-oror-Easy-Understanding-oror-Hashing-oror-strings-oror-Commented-Code
     *
     * Example:
     * words1 = ["amazon","apple","facebook","google","leetcode"],
     * words2 = ["lo","eo"]
     *
     * 1.Iterate through words2, create a max count map to count the max frequency of each char that appears in words of words2.
     *   So we will have one 'l', one 'e' and one 'o' in max count map => "leo" (order of the chars does not matter)
     * 2.Iterate through words2, count char frequency of each word, then compare with max count map in step1.
     *   So "leo" is a subset of 'google' and 'leetcode', answer is ["google", "leetcode"]
     *
     * Time : O(N * L2 + M * L1)
     * M = words1.length
     * L1 = average length of word in words1
     * N = words2.length
     * L2 = average length of word in words2
     */
    class Solution1 {
        public List<String> wordSubsets(String[] words1, String[] words2) {
            List<String> res = new ArrayList<>();

            int[] maxCount = new int[26];
            for (String word : words2) {
                int[] temp = new int[26];
                for (char c : word.toCharArray()) {
                    int idx = c - 'a';
                    temp[idx]++;
                    maxCount[idx] = Math.max(maxCount[idx], temp[idx]);
                }
            }

            for (String word : words1) {
                int[] count1 = new int[26];
                for (char c : word.toCharArray()) {
                    count1[c - 'a']++;
                }

                boolean isUniversal = true;
                for (int i = 0; i < 26; i++) {
                    if (maxCount[i] > count1[i]) {
                        isUniversal = false;
                        break;
                    }
                }

                if (isUniversal) {
                    res.add(word);
                }
            }

            return res;
        }
    }

    /**
     * Improvement from Solution1, extract the count procedure and make it a method to increase code re-use.
     */
    class Solution2 {
        public List<String> wordSubsets(String[] words1, String[] words2) {
            List<String> res = new ArrayList<>();

            int[] maxCount = new int[26];
            for (String word : words2) {
                int[] c = count(word);
                for (int i = 0; i < 26; i++) {
                    maxCount[i] = Math.max(c[i], maxCount[i]);
                }
            }

            for (String word : words1) {
                int[] count1 = count(word);

                boolean isUniversal = true;
                for (int i = 0; i < 26; i++) {
                    if (maxCount[i] > count1[i]) {
                        isUniversal = false;
                        break;
                    }
                }

                if (isUniversal) {
                    res.add(word);
                }
            }

            return res;
        }

        private int[] count(String a) {
            int[] temp = new int[26];
            for (char c : a.toCharArray()) {
                temp[c - 'a']++;
            }
            return temp;
        }
    }

    /**
     * Brutal Force solution as comparison
     * TLE
     *
     * Time = O(M * L1 * N * L2)
     * M = words1.length
     * L1 = average length of word in words1
     * N = words2.length
     * L2 = average length of word in words2
     */
    class Solution_BrutalForce {
        public List<String> wordSubsets(String[] words1, String[] words2) {
            List<String> res = new ArrayList<>();

            int[][] counts2 = new int[words2.length][26];
            int idx = 0;
            for (String word : words2) {
                for (char c : word.toCharArray()) {
                    counts2[idx][c - 'a']++;
                }
                idx++;
            }


            for (String word : words1) {
                int[] count1 = new int[26];
                for (char c : word.toCharArray()) {
                    count1[c - 'a']++;
                }

                boolean isUniversal = true;
                for (int[] count2 : counts2) {
                    for (int i = 0; i < 26; i++) {
                        if (count2[i] > count1[i]) {
                            isUniversal = false;
                            break;
                        }
                    }

                    if (!isUniversal)  break;
                }

                if (isUniversal) {
                    res.add(word);
                }
            }

            return res;
        }
    }
}
