package leetcode;

import java.util.*;

public class LE_1096_Brace_Expansion_II {
    /**
     * Under the grammar given below, strings can represent a set of lowercase words. Let R(expr) denote the set of words
     * the expression represents.
     *
     * The grammar can best be understood through simple examples:
     *
     * Single letters represent a singleton set containing that word.
     * R("a") = {"a"}
     * R("w") = {"w"}
     * When we take a comma-delimited list of two or more expressions, we take the union of possibilities.
     * R("{a,b,c}") = {"a","b","c"}
     * R("{{a,b},{b,c}}") = {"a","b","c"} (notice the final set only contains each word at most once)
     * When we concatenate two expressions, we take the set of possible concatenations between two words where the first
     * word comes from the first expression and the second word comes from the second expression.
     * R("{a,b}{c,d}") = {"ac","ad","bc","bd"}
     * R("a{b,c}{d,e}f{g,h}") = {"abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"}
     * Formally, the three rules for our grammar:
     *
     * For every lowercase letter x, we have R(x) = {x}.
     * For expressions e1, e2, ... , ek with k >= 2, we have R({e1, e2, ...}) = R(e1) ∪ R(e2) ∪ ...
     * For expressions e1 and e2, we have R(e1 + e2) = {a + b for (a, b) in R(e1) × R(e2)}, where + denotes concatenation,
     * and × denotes the cartesian product.
     * Given an expression representing a set of words under the given grammar, return the sorted list of words that the
     * expression represents.
     *
     * Example 1:
     * Input: expression = "{a,b}{c,{d,e}}"
     * Output: ["ac","ad","ae","bc","bd","be"]
     *
     * Example 2:
     * Input: expression = "{{a,z},a{b,c},{ab,z}}"
     * Output: ["a","ab","ac","z"]
     * Explanation: Each distinct word is written only once in the final answer.
     *
     * Constraints:
     * 1 <= expression.length <= 60
     * expression[i] consists of '{', '}', ','or lowercase English letters.
     * The given expression represents a set of words based on the grammar given in the description.
     *
     * Hard
     *
     * https://leetcode.com/problems/brace-expansion-ii/
     */

    /**
     * Compare with LE_1087_Brace_Expansion, now, we have nested brackets and Cartesian product rules.
     * We can use the exact same stack solution to solve it. The general idea of the stack solution is :
     *
     * 1.For each pair of brackets, expand and generate a collection of new Strings, which are put into stack for procssing.
     * 2.Scan from left to right, always find the inner most brackets among the nested brackets to expand.
     * 3.When string popped from stack has no bracket, we get one final from of expanded string, add it to a Set (dedup).
     * 4.Create a list from set, then sort.
     *
     * Examples:
     * #1
     * input : "{a,b}{c,{d,e}}"
     *
     * #First, expand "{a, b}"
     * a{c,{d,e}},
     *    |  #Then expand "{d, e}"
     *    |_ a{c, d}
     *    |    | #expand "{c, d}"
     *    |    |___________ac, ad
     *    |
     *    |_ a{c, e}
     *         | #expand "{c, e}"
     *         |___________ac, ae
     *
     *
     * b{c,{d,e}}
     *    |_ b{c, d}
     *    |    |___________bc, bd
     *    |
     *    |_ b{c, e}
     *         |___________bc, be
     *
     * #2
     * input : ""{a,b}{c,d}"
     *
     * {a,b}{c,d}
     *     |___a{c,d}
     *     |     |_____ac, ad
     *     |
     *     |
     *     |___b{c,d}
     *           |_____bc, bd
     *
     * Time Complexity:
     * I think its at least exponential runtime (2 ^ n).
     * Inputs like {a,b}{c,d}{e,f}{g,h}... return 2 ^ (n/5) strings of length n/5
     */
    class Solution {
        Set<String> set = new HashSet<String>();
        /**
         * Use set to de-dup
         */
        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack<String>();
        List<String> res;

        public List<String> braceExpansionII(String expression) {
            stack.push(expression);
            helper();
            /**
             * Convert set to list
             */
            res = new ArrayList<>(set);
            Collections.sort(res);
            return res;
        }

        private void helper(){
            while(!stack.isEmpty()) {
                String str = stack.pop();
                /**
                 * No more bracket in current string, meaning no need to do further expansion, we find one result.
                 */
                if (str.indexOf('{') == -1) {
                    set.add(str);
                    continue;
                }

                /**
                 * Find the bottom level of brackets pair substring, the one that does not have nested brackets.
                 * For example, for "{{a,b},{b,c}}", it will find "{a, b}".
                 */
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

                /**
                 * Get token
                 */
                String[] tokens = str.substring(l + 1, r).split(",");

                for (String s : tokens) {
                    /**
                     * expand and push into stack for further processing
                     */
                    sb.setLength(0);
                    stack.push(sb.append(before).append(s).append(after).toString());
                }
            }
        }
    }
}
