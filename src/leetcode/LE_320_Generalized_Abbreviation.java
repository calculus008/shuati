package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 5/3/18.
 */
public class LE_320_Generalized_Abbreviation {
    /**
         Write a function to generate the generalized abbreviations of a word.

         Example:
         Given word = "word", return the following list (order does not matter):
         ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
         Seen this question in a real interview before?
         Difficulty:Medium

         Medium
     */

    /**
     Backtracking
     Time  : O(2 ^ n)
     Space : O(n)

     Solution 1
     */
    class Solution1 {
        public List<String> generateAbbreviations1(String word) {
            List<String> res = new ArrayList<>();
            //!!! 如果没有字符，结果应该为[""], 而不是[]
            // if (word == null || word.length() == 0) return res;
            helper(word, res, 0, "", 0);
            return res;
        }

        public void helper(String word, List<String> res, int pos, String cur, int count) {
            if (pos == word.length()) {
                if (count > 0) {//!!!
                    cur += count;
                }
                res.add(cur);
                return;
            }

            /**
             对每一个char有两种处理：1.保留， 2.压缩
             */
            //#2
            helper(word, res, pos + 1, cur, count + 1);
            //!!! "cur + (count > 0 ? count : "") + word.charAt(pos)"
            //#1, !!!要把count置为0
            helper(word, res, pos + 1, cur + (count > 0 ? count : "") + word.charAt(pos), 0);
        }
    }

    /**
        Solution 2, better performance with StringBuilder
     */
    class Solution2 {
        public List<String> generateAbbreviations2(String word) {
            List<String> res = new ArrayList<>();
            helper(word, res, new StringBuilder(), 0, 0);
            return res;
        }

        public void helper(String word, List<String> res, StringBuilder sb, int pos, int count) {
            int len = sb.length();

            if (pos == word.length()) {
                if (count > 0) {
                    sb.append(count);
                }

                res.add(sb.toString());
            } else {
                /**
                 Each recursion has two possible paths:
                 1.Keep counting at the current position, and go to the next level of recursion
                 2.End counting (set count to 0), assemble the current string (which is current string appended the current count,
                 then append char at the current position), and go to the next level of recursion
                 */
                helper(word, res, sb, pos + 1, count + 1);

                if (count > 0) {
                    sb.append(count);
                }
                sb.append(word.charAt(pos));
                helper(word, res, sb, pos + 1, 0);
            }

            //!!!
            sb.setLength(len);
        }
    }

    public class Solution_Practice_1 {
        public List<String> generateAbbreviations(String word) {
            List<String> res = new ArrayList<>();
            if (word == null || word.length() == 0) return res;

            helper(word, res, new StringBuilder(), 0, 0);

            return res;
        }

        private void helper(String word, List<String> res, StringBuilder sb, int pos, int count) {
            if (pos == word.length()) {
                if (count > 0) {
                    sb.append(count);
                }
                res.add(sb.toString());
                return;
            }

            int len = sb.length();

            if (count > 0) {
                sb.append(count);
            }
            sb.append(word.charAt(pos));
            helper(word, res, sb, pos + 1, 0);
            sb.setLength(len);

            /**
             * !!!
             * count + 1, NOT count++
             */
            helper(word, res, sb, pos + 1, count + 1);

            sb.setLength(len);
        }
    }

    public class Solution_Practice_2 {
        public List<String> generateAbbreviations(String word) {
            List<String> res = new ArrayList<>();
            if (word == null || word.length() == 0) return res;

            helper(word, res, "", 0, 0);

            return res;
        }

        private void helper(String word, List<String> res, String cur, int pos, int count) {
            if (pos == word.length()) {
                if (count > 0) {
                    cur = cur + count;
                }
                res.add(cur);
                return;
            }

            /**
             * cur + (count > 0 ? count : "") + word.charAt(pos)
             */
            helper(word, res, cur + (count > 0 ? count : "") + word.charAt(pos), pos + 1, 0);
            helper(word, res, cur, pos + 1, count + 1);
        }
    }

}
