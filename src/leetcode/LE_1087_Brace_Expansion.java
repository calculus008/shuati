package leetcode;

import java.util.*;

public class LE_1087_Brace_Expansion {
    /**
     * You are given a string s representing a list of words. Each letter in the word has one or more options.
     *
     * If there is one option, the letter is represented as is.
     * If there is more than one option, then curly braces delimit the options. For example, "{a,b,c}" represents options ["a", "b", "c"].
     * For example, if s = "a{b,c}", the first character is always 'a', but the second character can be 'b' or 'c'. The original list is ["ab", "ac"].
     *
     * Return all words that can be formed in this manner, sorted in lexicographical order.
     *
     * Example 1:
     *
     * Input: s = "{a,b}c{d,e}f"
     * Output: ["acdf","acef","bcdf","bcef"]
     * Example 2:
     *
     * Input: s = "abcd"
     * Output: ["abcd"]
     *
     *
     * Constraints:
     * 1 <= s.length <= 50
     * s consists of curly brackets '{}', commas ',', and lowercase English letters.
     * s is guaranteed to be a valid input.
     * There are no nested curly brackets.(!!!)
     * All characters inside a pair of consecutive opening and ending curly brackets are different.
     *
     * Medium
     *
     * https://leetcode.com/problems/brace-expansion/
     */

    /**
     * Stack
     *
     * Exact the same solution for LE_1096_Brace_Expansion_II
     */
    class Solution1 {
        Set<String> set = new HashSet<String>();

        StringBuilder sb = new StringBuilder();
        List<String> res;
        Stack<String> stack = new Stack<String>();

        public String[] expand(String s) {
            stack.push(s);
            helper();
            res = new ArrayList<>(set);
            Collections.sort(res);
            return res.toArray(new String[0]);
        }

        private void helper(){
            while(!stack.isEmpty()) {
                String str = stack.pop();
                if (str.indexOf('{') == -1) {
                    set.add(str);
                    continue;
                }

                int i = 0, l = 0, r = 0;
                while (str.charAt(i) != '}') {
                    if (str.charAt(i) == '{') {
                        l = i;
                    }
                    i++;
                }
                r = i;

                String before = str.substring(0, l);
                String after = str.substring(r + 1, str.length());
                String[] args = str.substring(l + 1, r).split(",");

                for (String s : args) {
                    sb.setLength(0);
                    stack.push(sb.append(before).append(s).append(after).toString());
                }
            }
        }
    }

    /**
     * DFS backtracking
     *
     * Important condition: "There are no nested curly brackets"
     *
     * At each recursion level, check if the current s starts with '{', if yes, it means we have branches now, find the
     * end of branches (index of '}'), get substring split it by ',', then iterate each token (each branch).
     *
     * Example:
     * Input: s = "{a,b}c{d,e}f"
     *
     * s={a,b}c{d,e}f, word=
     * s=c{d,e}f, word=a
     * s={d,e}f, word=ac
     * s=f, word=acd
     * s=, word=acdf
     * Add acdf
     *
     * s=f, word=ace
     * s=, word=acef
     * Add acef
     *
     * s=c{d,e}f, word=b
     * s={d,e}f, word=bc
     * s=f, word=bcd
     * s=, word=bcdf
     * Add bcdf
     *
     * s=f, word=bce
     * s=, word=bcef
     * Add bcef
     *                    ""
     *                 /      \
     *               a         b
     *               |         |
     *              ac        bc
     *             / \       / \
     *          acd  ace   bcd bce
     *           |    |     |   |
     *         acdf acef  bcdf bcef
     */

    class Solution2 {
        List<String> list = new ArrayList<>();
        public String[] expand(String s) {
            helper(s, "");
            Collections.sort(list);
            return list.toArray(new String[0]); //convert list of string to string array
        }

        private void helper(String s, String word) {
            if (s.equals("")) {
                list.add(word);
                return;
            }

            if (s.charAt(0) == '{') {
                int idx = s.indexOf("}");
                String[] options = s.substring(1, idx).split(",");
                /**
                 * No nexted curly brackets, so token will be valid token
                 */
                for (String str : options) {
                    /**
                     * "s.substring(idx + 1)", get the rest of the string after current brackets pair.
                     */
                    helper(s.substring(idx + 1), word + str);
                }
            } else {
                helper(s.substring(1), word + s.charAt(0));
            }
        }
    }

}
