package leetcode;

import java.util.*;

public class LE_433_Minimum_Genetic_Mutation {
    /**
     * A gene string can be represented by an 8-character long string, with choices from 'A', 'C', 'G', and 'T'.
     *
     * Suppose we need to investigate a mutation from a gene string start to a gene string end where one mutation is
     * defined as one single character changed in the gene string.
     *
     * For example, "AACCGGTT" --> "AACCGGTA" is one mutation.
     * There is also a gene bank bank that records all the valid gene mutations. A gene must be in bank to make it a
     * valid gene string.
     *
     * Given the two gene strings start and end and the gene bank bank, return the minimum number of mutations needed
     * to mutate from start to end. If there is no such a mutation, return -1.
     *
     * Note that the starting point is assumed to be valid, so it might not be included in the bank.
     *
     * Example 1:
     * Input: start = "AACCGGTT", end = "AACCGGTA", bank = ["AACCGGTA"]
     * Output: 1
     *
     * Example 2:
     * Input: start = "AACCGGTT", end = "AAACGGTA", bank = ["AACCGGTA","AACCGCTA","AAACGGTA"]
     * Output: 2
     *
     * Example 3:
     * Input: start = "AAAAACCC", end = "AACCCCCC", bank = ["AAAACCCC","AAACCCCC","AACCCCCC"]
     * Output: 3
     *
     * Constraints:
     * start.length == 8
     * end.length == 8
     * 0 <= bank.length <= 10
     * bank[i].length == 8
     * start, end, and bank[i] consist of only the characters ['A', 'C', 'G', 'T'].
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-genetic-mutation/
     */

    /**
     * BFS
     *
     * A simplified version of LE_127_Word_Ladder
     *
     * Time : O(n * (4 ^ m) * m), n is the number of strings from start to end and m is the length.
     *
     * This is because you will end up polling n strings from your queue ultimately and for each of these strings,
     * you would have gone from the start of the string to the end checking 4 characters (A, C, G, T) against each
     * position. Additionally, you will be converting that char array to string each time - which adds the extra *m
     * in the end
     */
    class Solution {
        public int minMutation(String start, String end, String[] bank) {
            Set<String> set = new HashSet<>(Arrays.asList(bank));

            if (!set.contains(end)) return -1;

            Queue<String> q = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            q.add(start);
            int steps = 0;
            char[] charSet = {'A', 'C', 'G', 'T'};

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();

                    if (cur.equals(end)) return steps;

                    char[] chars = cur.toCharArray();
                    for (int j = 0; j < chars.length; j++) {
                        char original = chars[j];

                        for (char c : charSet) {
                            if (c != original) {
                                chars[j] = c;
                                String s = new String(chars);
                                if (set.contains(s) && !visited.contains(s)) {
                                    q.offer(s);
                                    visited.add(s);
                                }
                            }
                        }

                        chars[j] = original;
                    }
                }

                steps++;
            }

            return -1;
        }
    }
}
