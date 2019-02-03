package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuank on 8/11/18.
 */
public class LE_17_Letter_Combinations_Of_A_Phone_Number {
    /**
         Given a string containing digits from 2-9 inclusive, return all possible letter
         combinations that the number could represent.

         A mapping of digit to letters (just like on the telephone buttons) is given below.
         Note that 1 does not map to any letters.

         Example:

         Input: "23"
         Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
         Note:

         Although the above answer is in lexicographical order,
         your answer could be in any order you want.

         Medium

         !!!Follow up :
         如果有一个词典(Dictionary)，要求组成的单词都是词典里的，如何优化?  Trie or Hash
     */

    /**
     https://zxi.mytechroad.com/blog/searching/leetcode-17-letter-combinations-of-a-phone-number/
     */

    /**
     * Solution 1 : Permutation based DFS
     *
     * 1.Each element in mapping[] is the set of elements looping through for each level.
     * 2.No need for visited[] since looping set for each level is different
     *
     * Time : O(4 ^ n), n is length of given string. 4 - total number of string one char can generate.
     * for example, 2 -> 2, a, b, c.
     *
     * ?? O(3 ^ n): each number represents 3 chars
     *
     * Space : O(4 ^ n + n)
     **/
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) return res;

        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        helper(digits, res, new StringBuilder(), 0, mapping);
        return res;
    }

    private void helper(String s, List<String> res, StringBuilder sb, int idx, String[] mapping) {
        if (idx == s.length()) {
            res.add(sb.toString());
            return;
        }

        //!!!
        int x = Character.getNumericValue(s.charAt(idx));
        for (char c : mapping[x].toCharArray()) {
            sb.append(c);
            helper(s, res, sb, idx + 1, mapping);
            sb.setLength(idx);
        }
    }

    /**
     * Soltuion 2 : BFS
     * Time :  O(4 ^ n)
     * Space : O(2 * 4 ^ n)
     */
    public List<String> letterCombinations2(String digits) {
        //!!! linkedlist here acts both as container for final result and the queue to do BFS
        LinkedList<String> res = new LinkedList<>();
        if (null == digits || digits.length() == 0) return res;

        String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        int n = digits.length();

        //!!!
        res.offer("");

        for (int i = 0; i < n; i++) {
            int x = Character.getNumericValue(digits.charAt(i));

            while (res.peek().length() == i) {
                /**
                 * !!!
                 * using poll (remove from queue), so all intermediate strings will be removed from queue,
                 * in the end, only the valid results remain
                 **/
                String t = res.poll();
                for (char c : mapping[x].toCharArray()) {
                    res.add(t + c);
                }
            }
        }

        return res;
    }
}
