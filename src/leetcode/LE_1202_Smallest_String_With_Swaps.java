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
    class Solution {
        Map<Integer, List<Integer>> map;

        public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
            if (s == null || s.length() == 0) return s;

            map = new HashMap<>();
            char[] chars = new char[s.length()];

            for (List<Integer> pair : pairs) {
                int l1 = pair.get(0);
                int l2 = pair.get(1);

                map.computeIfAbsent(l1, l -> new ArrayList<>()).add(l2);
                map.computeIfAbsent(l2, l -> new ArrayList<>()).add(l1);
            }

            Set<Integer> seen = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                List<Integer> cur = new ArrayList<>();
                StringBuilder sb = new StringBuilder();

                dfs(s, seen, cur, i, sb);

                Collections.sort(cur);
                char[] temp = sb.toString().toCharArray();

                Arrays.sort(temp);

                for (int j = 0; j < temp.length; j++) {
                    chars[cur.get(j)] = temp[j];
                }
            }

            return new String(chars);

        }

        private void dfs(String s, Set<Integer> seen, List<Integer> cur, int idx, StringBuilder sb) {
            if (seen.contains(idx)) return;

            seen.add(idx);
            cur.add(idx);
            sb.append(s.charAt(idx));

            if (map.get(idx) == null) return;

            for (int n : map.get(idx)) {
                dfs(s, seen, cur, n, sb);
            }
        }
    }
}
