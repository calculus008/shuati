package leetcode;

import java.util.*;

public class LE_839_Similar_String_Groups {
    /**
     * Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y.
     * Also two strings X and Y are similar if they are equal.
     *
     * For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar,
     * but "star" is not similar to "tars", "rats", or "arts".
     *
     * Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that "tars"
     * and "arts" are in the same group even though they are not similar.  Formally, each group is such that a word is in
     * the group if and only if it is similar to at least one other word in the group.
     *
     * We are given a list strs of strings where every string in strs is an anagram of every other string in strs.
     * How many groups are there?
     *
     * Example 1:
     * Input: strs = ["tars","rats","arts","star"]
     * Output: 2
     *
     * Example 2:
     * Input: strs = ["omv","ovm"]
     * Output: 1
     *
     * Constraints:
     * 1 <= strs.length <= 300
     * 1 <= strs[i].length <= 300
     * strs[i] consists of lowercase letters only.
     * All words in strs have the same length and are anagrams of each other.
     *
     * Hard
     *
     * https://leetcode.com/problems/similar-string-groups/
     */

    /**
     * DFS
     *
     * Blockers
     * 1.How to check if two strings are "similar"?
     *   Iterate both strings, check how many chars are different. They are similar if diff is 0 (equal) or 2.
     * 2.Find groups
     *   This approach is pretty much similar to finding number of islands.
     *   We start with a string and perform dfs on that string. While performing dfs we are using the helper function to
     *   identify all the set of strings in strs[] which are in the same group as source string. We continue our exploration
     *   with the new string in strs[] and performs DFS traversal.
     *   In this way we form number of islands available(number of similar group strings)
     *
     *   Similar to number of islands, we mark visited String as NULL, it has the effect of removing the string from
     *   strs so that we don't check it anymore.
     *
     * Time : O(n ^ 2 * k) n is the length of the array, k is the avg length of every string in the array
     *        I am calling each string in dfs method only once
     */

    class Solution1 {
        public int numSimilarGroups(String[] strs) {
            int res = 0;
            for (int i = 0; i < strs.length; i++) {
                if (strs[i] != null) {
                    helper(i, strs);
                    res++;
                }
            }

            return res;
        }

        public void helper(int idx, String[] strs) {
            String s = strs[idx];
            /**
             * !!!
             * Set to null here, so we remove it from strs and don't compare it.
             */
            strs[idx] = null;

            /**
             * !!!
             * Must start from idx 0, since we need to check all strings that are left to find "similar" one.
             * Example:
             * Suppose we have [A, B, C, D], A -> C, C -> B, but A !-> B. If you don't start from 0, you'll count B as a new string.
             */
            for (int i = 0; i < strs.length; i++) {
                if (strs[i] != null && isSimilar(s, strs[i])) {
                    helper(i, strs);
                }
            }
        }

        public boolean isSimilar(String s, String t) {
            int n = s.length();
            int i = 0;
            int diff = 0;
            while (i < n && diff <= 2) {
                if (s.charAt(i) != t.charAt(i)) {
                    diff++;
                }
                i++;
            }

            /**
             * !!!
             * 0 : equal
             * 2 : can be the same with one swap
             */
            return diff == 2 || diff == 0;
        }
    }


    /**
     * Union Find
     *
     *
     */
    class Solution2 {
        public int numSimilarGroups(String[] A) {
            int[] parent = new int[A.length];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;
            }

            for (int i = 0; i < A.length; i++) {
                for (int j = i + 1; j < A.length; j++) {
                    String str1 = A[i];
                    String str2 = A[j];

                    if (isSimilar(str1, str2)) {
                        union(i, j, parent);
                    }
                }
            }

            Set<Integer> groups = new HashSet();
            for (int i = 0; i < parent.length; i++) {
                groups.add(find(i, parent));
            }

            return groups.size();
        }

        public int find(int i, int[] parent) {
            if (parent[i] == i)
                return i;

            parent[i] = find(parent[i], parent);
            return parent[i];
        }

        public void union(int i, int j, int[] parent) {
            int parenti = find(i, parent);
            int parentj = find(j, parent);
            parent[parenti] = parentj;
        }
    }

    public boolean isSimilar(String s, String t) {
        int n = s.length();
        int i = 0;
        int diff = 0;
        while (i < n && diff <= 2) {
            if (s.charAt(i) != t.charAt(i)) {
                diff++;
            }
            i++;
        }

        return diff == 2 || diff == 0;
    }
}
