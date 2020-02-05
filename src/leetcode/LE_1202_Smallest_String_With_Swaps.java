package leetcode;

import java.util.*;

public class LE_1202_Smallest_String_With_Swaps {
    /**
     * You are given a string s, and an array of pairs of indices in the string pairs
     * where pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.
     *
     * You can swap the characters at any pair of indices in the given pairs any number of times.
     *
     * Return the lexicographically smallest string that s can be changed to after using the swaps.
     *
     *
     * Example 1:
     * Input: s = "dcab", pairs = [[0,3],[1,2]]
     * Output: "bacd"
     * Explaination:
     * Swap s[0] and s[3], s = "bcad"
     * Swap s[1] and s[2], s = "bacd"
     *
     * Example 2:
     * Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
     * Output: "abcd"
     * Explaination:
     * Swap s[0] and s[3], s = "bcad"
     * Swap s[0] and s[2], s = "acbd"
     * Swap s[1] and s[2], s = "abcd"
     *
     * Example 3:
     * Input: s = "cba", pairs = [[0,1],[1,2]]
     * Output: "abc"
     * Explaination:
     * Swap s[0] and s[1], s = "bca"
     * Swap s[1] and s[2], s = "bac"
     * Swap s[0] and s[1], s = "abc"
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * 0 <= pairs.length <= 10^5
     * 0 <= pairs[i][0], pairs[i][1] < s.length
     * s only contains lower case English letters.
     *
     * Medium
     *
     *
     */

    /**
     *  https://zxi.mytechroad.com/blog/graph/leetcode-1202-smallest-string-with-swaps/
     *
     *  DFS + sorting
     *
     * Use DFS / Union-Find to find all the connected components of swapable indices.
     * For each connected components (index group), extract the subsequence of corresponding
     * chars as a string, sort it and put it back to the original string in the same location.
     *
     * e.g. s = “dcab”, pairs = [[0,3],[1,2]]
     * There are two connected components: {0,3}, {1,2}
     * subsequences:
     * 1. 0,3 “db”, sorted: “bd”
     * 2. 1,2 “ca”, sorted: “ac”
     * 0 => b
     * 1 => a
     * 2 => c
     * 3 => d
     * final = “bacd”
     *
     * Time complexity: DFS: O(nlogn + k*(V+E)), Union-Find: O(nlogn + V+E)
     * Space complexity: O(n)
     */
    class Solution1 {
        Map<Integer, List<Integer>> map;

        public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
            if (s == null || s.length() == 0 || pairs == null || pairs.size() == 0) return s;

            map = new HashMap<>();
            char[] chars = new char[s.length()];

            for (List<Integer> pair : pairs) {
                int index1 = pair.get(0);
                int index2 = pair.get(1);

                /**
                 * Build undirected graph
                 */
                map.computeIfAbsent(index1, l -> new ArrayList<>()).add(index2);
                map.computeIfAbsent(index2, l -> new ArrayList<>()).add(index1);
            }

            Set<Integer> seen = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                List<Integer> indexList = new ArrayList<>();
                List<Character> charList = new ArrayList<>();

                dfs(s, seen, indexList, charList, i);

                Collections.sort(indexList);
                Collections.sort(charList);

                for (int j = 0; j < indexList.size(); j++) {
                    chars[indexList.get(j)] = charList.get(j);
                }
            }

            return new String(chars);

        }

        /**
         * Graph DFS
         */
        private void dfs(String s, Set<Integer> seen, List<Integer> indexList, List<Character> charList, int idx) {
            if (seen.contains(idx)) return;

            seen.add(idx);
            indexList.add(idx);
            charList.add(s.charAt(idx));

            /**
             * !!!
             * the case that the value at idx can't switch with value of any other index
             */
            if (map.get(idx) == null) return;

            for (int n : map.get(idx)) {
                dfs(s, seen, indexList, charList, n);
            }
        }
    }

    /**
     * Union Find
     *
     * Time  : O(nlogn + V+E)
     * Space : O(n)
     */
    class Solution2 {
        private int[] parents;

        private int find(int u) {
            while (parents[u] != u) {
                parents[u] = parents[parents[u]];
                u = parents[u];
            }

            return u;
        }

        private boolean union(int u, int v) {
            int pu = find(u);
            int pv = find(v);

            if (pu == pv) return false;

            parents[pu] = pv;

            return true;
        }

        public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
            if (s == null || s.length() == 0 || pairs == null || pairs.size() == 0) return s;

            int n = s.length();

            /**
             * init union find set
             */
            parents = new int[n];
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
            }

            Map<Integer, List<Integer>> map = new HashMap<>();
            char[] chars = new char[s.length()];

            /**
             * union all pairs
             */
            for (List<Integer> pair : pairs) {
                union(pair.get(0), pair.get(1));
            }

            /**
             * base on root id in UFS, put connected components in hashmap
             */
            for (int i = 0; i < n; i++) {
                int p = find(i);
                map.computeIfAbsent(p, l -> new ArrayList<>()).add(i);
            }

            /**
             * for each connected component, sort chars and put them to the correct position
             */
            for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                List<Integer> idxList = new ArrayList<>();
                List<Character> charList = new ArrayList<>();

                for (int val : entry.getValue()) {
                    idxList.add(val);
                    charList.add(s.charAt(val));
                }

                Collections.sort(idxList);
                Collections.sort(charList);

                for (int i = 0; i < idxList.size(); i++) {
                    chars[idxList.get(i)] = charList.get(i);
                }
            }

            return new String(chars);
        }

    }
}
