package leetcode;

import java.util.*;

public class LE_990_Satisfiability_Of_Equality_Equations {
    /**
     * You are given an array of strings equations that represent relationships between variables where each string
     * equations[i] is of length 4 and takes one of two different forms: "xi==yi" or "xi!=yi".Here, xi and yi are
     * lowercase letters (not necessarily different) that represent one-letter variable names.
     *
     * Return true if it is possible to assign integers to variable names so as to satisfy all the given equations,
     * or false otherwise.
     *
     * Example 1:
     * Input: equations = ["a==b","b!=a"]
     * Output: false
     * Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.
     * There is no way to assign the variables to satisfy both equations.
     *
     * Example 2:
     * Input: equations = ["b==a","a==b"]
     * Output: true
     * Explanation: We could assign a = 1 and b = 1 to satisfy both equations.
     *
     * Example 3:
     * Input: equations = ["a==b","b==c","a==c"]
     * Output: true
     *
     * Example 4:
     * Input: equations = ["a==b","b!=c","c==a"]
     * Output: false
     *
     * Example 5:
     * Input: equations = ["c==c","b==d","x!=z"]
     * Output: true
     *
     * Constraints:
     * 1 <= equations.length <= 500
     * equations[i].length == 4
     * equations[i][0] is a lowercase letter.
     * equations[i][1] is either '=' or '!'.
     * equations[i][2] is '='.
     * equations[i][3] is a lowercase letter.
     *
     * Medium
     *
     * https://leetcode.com/problems/satisfiability-of-equality-equations/
     */

    /**
     * Build Graph + DFS
     *
     * Time  : O(N), where N is the length of equations.
     * Space : O(1), assuming the size of the alphabet is O(1). Since we only deal with 26 alphabets, size is constant.
     */
    class Solution {
        public boolean equationsPossible(String[] equations) {
            /**
             * First, build a graph, or connected component. All chars that have '==' relationship are in one
             * connected component, represented as a HashMap, key is char, value is a set of chars that it is connected
             * to.
             */
            Map<Character, Set<Character>> map = new HashMap<>();
            for (String e : equations) {
                if (e.charAt(1) == '=') {
                    char c1 = e.charAt(0);
                    char c2 = e.charAt(3);
                    map.computeIfAbsent(c1, l -> new HashSet<>()).add(c2);
                    map.computeIfAbsent(c2, l -> new HashSet<>()).add(c1);
                }
            }

            /**
             * Second, for each pair of '!=' relationship, start with one char, check if we can find the other char
             * in the graph, if yes, then we can't meet the requirement in the question, return false.
             */
            for (String e : equations) {
                if (e.charAt(1) == '!') {
                    char c1 = e.charAt(0);
                    char c2 = e.charAt(3);

                    if (helper(map, c1, c2, new HashSet<>())) {
                        return false;
                    }
                }
            }

            return true;
        }

        /**
         * DFS helper, try to find target char.
         */
        private boolean helper(Map<Character, Set<Character>> map, char cur, char target, Set<Character> visited) {
            if (cur == target) return true;

            /**
             * !!!
             * Must consider the possibility that current char is not connected to any other chars
             */
            if (!map.containsKey(cur)) return false;

            for (char c : map.get(cur)) {
                if (visited.contains(c)) continue;

                visited.add(c);
                if (helper(map, c, target, visited)) return true;
                visited.remove(c);
            }

            return false;
        }
    }
}
