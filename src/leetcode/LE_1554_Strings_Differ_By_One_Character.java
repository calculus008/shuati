package leetcode;

import java.util.*;

public class LE_1554_Strings_Differ_By_One_Character {
    /**
     * Given a list of strings dict where all the strings are of the same length.
     *
     * Return true if there are 2 strings that only differ by 1 character in the same index, otherwise return false.
     *
     * Example 1:
     * Input: dict = ["abcd","acbd", "aacd"]
     * Output: true
     * Explanation: Strings "abcd" and "aacd" differ only by one character in the index 1.
     *
     * Example 2:
     * Input: dict = ["ab","cd","yz"]
     * Output: false
     *
     * Example 3:
     * Input: dict = ["abcd","cccc","abyd","abab"]
     * Output: true
     *
     * Constraints:
     * The number of characters in dict <= 105
     * dict[i].length == dict[j].length
     * dict[i] should be unique.(!!!)
     * dict[i] contains only lowercase English letters.
     *
     * Follow up: Could you solve this problem in O(n * m) where n is the length of dict and m is the length of each string.
     *
     * Medium
     *
     * https://leetcode.com/problems/strings-differ-by-one-character/
     */

    /**
     * Rabin-Karp
     * Rabin-Karp provides a solution to compute hash for string and substrings in O(1)
     *
     * 1.compute a hash for each string i in [0, m) as hash[i] = a[0] * 26 ^ (n - 1) + a[1] * 26 ^ (n - 2) + ... + a[m - 2] * 26 + a[m - 1]
     *   m is number of strings, n is length of a string (the same for all m strings)
     *
     * 2.Then, we go through each character position j in [0, n), and compute a hash without that character:
     *   h = hash[i] - a[j] * 26 ^ (n - j - 1). We track h in a hash set so we can detect if there is another string
     *   with the same hash.
     *
     * NOTE
     * in outter loop we are going right-to-left so it's easier to compute 26 ^ (n - j - 1).
     *
     * !!!
     * Given condition "dict[i] should be unique", so no 2 strings are exactly the same. That's why
     * we can return true if hash already exists in set.
     * Otherwise, if there are duplicated strings, this check will not be enough. Then we can do:
     *
     * instead of set, we use a HashMap to remember string indices
     * Map<Long, List<Integer> map : key - hash, value - list of index of the string.
     * If the cur hash exists in hashmap, get the list of string indices, compare them with current one, see if there's
     * one that differs with the current String at the current index.
     *
     * Time : O(n * m)
     */
    class Solution {
        public boolean differByOne(String[] dict) {
            if (dict == null || dict.length < 2) return false;

            int m = dict.length;
            int n = dict[0].length();

            long[] word2hash = new long[m];
            /**
             * mod to keep hashes within the integer range.
             */
            long mod = (long)Math.pow(10, 20) + 7;


            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    word2hash[i] = (word2hash[i] * 26 + dict[i].charAt(j) - 'a') % mod;
                }
            }

            Set<Long> set = new HashSet<>();
            long base = 1;

            for (int j = n - 1; j >= 0; j--) {
                set.clear();

                for (int i = 0; i < m; i++) {
                    long hash = (word2hash[i] - base * (dict[i].charAt(j) - 'a')) % mod;

                    if (set.contains(hash)) {
                        return true;
                    }

                    set.add(hash);
                }

                base = base * 26 % mod;
            }

            return false;
        }
    }

    /**
     * Naive solution
     *
     * Replace the char in the same position of each string with '#', put in set, check if it already exists.
     * But the process of replacing char and creating new String costs O(n). So the time complexity is O (m * n * n).
     * Therefore it is much slower than Rabin-karp solution above (189ms vs 19ms).
     *
     * Basically it takes O(n) to create a hash here, the rolling hash technic by Rabin-karp enables us to create
     * a hash in O(1).
     */
    class Solution_Naive {
        public boolean differByOne(String[] dict) {
            int m = dict.length;
            int n = dict[0].length();

            Set<String> set = new HashSet<>();

            for (int j = 0; j < n; j++) {
                set.clear();

                for (int i = 0; i < m; i++) {
                    char[] chars = dict[i].toCharArray();
                    chars[j] = '#';
                    String s = new String(chars);

                    if (set.contains(s)) return true;

                    set.add(s);
                }
            }

            return false;
        }
    }
}
