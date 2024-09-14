package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/18/18.
 */
public class LE_282_Expression_Add_Operators {
    /**
     * Given a string that contains only digits 0-9 and a target value,
     * return all possibilities to add binary operators (not unary) +, -, or *
     * between the digits so that they evaluate to the target value.

         Examples:
         "123", 6 -> ["1+2+3", "1*2*3"]
         "232", 8 -> ["2*3+2", "2+3*2"]
         "105", 5 -> ["1*0+5","10-5"]
         "00", 0 -> ["0+0", "0-0", "0*0"]
         "3456237490", 9191 -> []

         Hard
     */


    /**
        Backtracking
        Time : Not sure
        Space : O(n)

        http://zxi.mytechroad.com/blog/searching/leetcode-282-expression-add-operators/

        Test Run:
        num = "123", target = 6

        pos = 0, val = 0, pre = 0

        cur = 1, call (pos = 1, val = 1, pre = 1)
            cur = 2, call (pos = 2, val = 1 + 2, pre = 2)
                 cur = 3, call (pos = 3, val = 1 + 2 + 3, pre = 3),      val == target add "1+2+3" return
                 cur = 3, call (pos = 3, val = 1 + 2 - 3, pre = -3),     val != target return
                 cur = 3, call (pos = 3, val = 3 - 2 + 2 * 3,  pre = 3), val != target return

            cur = 2, pos = 2, val = 1 - 2, pre = 2
                 cur = 3, call (pos = 3, val = 1 - 2 + 3, pre = 3),       val != target return
                 cur = 3, call (pos = 3, val = 1 - 2 - 3, pre = -3)       val != target return
                 cur = 3, call (pos = 3, val = -1 - 2 + 2 * 3,  pre = 3), val != target return

            cur = 2, pos = 2, val = 1 * 2, pre = 2
                 cur = 3, call (pos = 3, val = 1 * 2 + 3, pre = 3),       val != target return
                 cur = 3, call (pos = 3, val = 1 * 2 - 3, pre = -3)       val != target return
                 cur = 3, call (pos = 3, val = 2 - 2 + 2 * 3,  pre = 3),  val == target add "1*2*3" return

     */

    public class Solution_Practice {
        public List<String> addOperators(String num, int target) {
            List<String> res = new ArrayList<>();
            if (num == null || num.length() == 0) return res;

            helper(num, res, target, "", 0, 0, 0);

            return res;
        }

        /**
         * !!!
         * long for "pre" and "val"
         */
        private void helper(String s, List<String> res, int target, String temp, int pos, long pre, long val) {
            if (pos == s.length()) {
                if (val == target) {
                    res.add(temp);
                }
                return;
            }

            /**
             * !!!
             * long
             */
            long cur = 0;

            for (int i = pos; i < s.length(); i++) {
                if (s.charAt(pos) == '0' && i != pos) {
                    break;
                }

                cur = cur * 10 + s.charAt(i) - '0';

                if (pos == 0) {
                    helper(s, res, target, temp + cur, i + 1, cur, cur);
                    continue;
                }

                helper(s, res, target, temp + "+" + cur, i + 1, cur, val + cur);
                helper(s, res, target, temp + "-" + cur, i + 1, -cur, val - cur);
                helper(s, res, target, temp + "*" + cur, i + 1, pre * cur, val - pre + pre * cur);
            }
        }
    }

    /**
     * Solution with no optimization
     *
     * Time  : O(n ^ 2 * 4 ^ (n - 1))
     *         n ^ 2 : in each helper() call, do substring in for loop is O(n ^ 2)
     *         4 ^ (n - 1) : For num with length n, there are n - 1 spaces to fill with 4 possible ops:
     *                       '+', '-', '*', ''(empty, meaning assemble with the previous chars as one number)
     *
     * Space : O(n ^ 2)
     *
     * 256 ms
     */
    class Solution1 {
        public List<String> addOperators(String num, int target) {
            List<String> res = new ArrayList<>();
            if (num == null || num.length() == 0) return res;

            helper(res, num, target, "", 0, 0, 0);
            return res;
        }

        public void helper(List<String> res, String num, int target, String path, int pos, long val, long pre) {
            if (pos == num.length()) {
                /**
                 !!! NOT the following one:
                 if (target == val) {
                 res.add(path);
                 return;
                 }

                 If you put "return" inside 2nd if, it won't return for the case that target != val

                 You also can't write it as :
                 "if (pos == num.length() && target == val) {res.add(path); return}" for the same reason
                 */
                if (target == val) {
                    res.add(path);
                }
                return;
            }

            /**
             * !!!
             * This for loop is really doing the work of filling "" between digits. For example:
             *
             * 1 2 3, it will try in cur with substring():
             * 1
             * 12
             * 123
             */
            for (int i = pos; i < num.length(); i++) {
                /**
                 !!! "num.charAt(pos)", 如果开始为"0"而当前是非起始位（012）就不用再往后走了，“0XX”不是合法的数字。
                 !!!“charAt(pos)"
                 */
                if (num.charAt(pos) == '0' && i != pos) {
                    break;
                }

                long cur = Long.parseLong(num.substring(pos, i + 1));

                if (pos == 0) {
                    helper(res, num, target, path + cur, i + 1, cur, cur);
                } else {
                    helper(res, num, target, path + "+" + cur, i + 1, val + cur, cur);

                    /**
                     !!!", -cur)" 把"-"看作负数，这样才能在“*”时做“val - pre".
                     */
                    helper(res, num, target, path + "-" + cur, i + 1, val - cur, -cur);

                    /**
                     !!!"val - pre + pre * cur"
                     */
                    helper(res, num, target, path + "*" + cur, i + 1, val - pre + pre * cur, pre * cur);

                }
            }
        }
    }


    /**
     * Solution optimized with StringBuilder
     *
     * 115 ms
     *
     * 坑 ：
     * 1.With passing StringBuilder, since content is changed in sb for each recursion call to helper(),
     * after each helper() returns, need to do "sb.setLength(n)"
     *
     * 2.Number "0XX" is invalid. When starting digit is '0' and current index is not starting index,
     * it's case for "0XX", just break.
     *
     * 3.Special processing when pos at the beginning of num (pos == 0)
     *   helper(num, target, res, sb.append(cur), i + 1, cur, cur);
     *
     * 4.In each helper() call, pos is set to "i + 1"
     *
     */
    class Solution2 {
        public List<String> addOperators(String num, int target) {
            List<String> res = new ArrayList<>();
            if (null == num || num.length() == 0) {
                return res;
            }

            helper(num, target, res, new StringBuilder(), 0, 0, 0);

            return res;
        }

        private void helper(String num, int target, List<String> res, StringBuilder sb, int pos, long val, long pre) {
            if (pos == num.length()) {
                if (val == target) {
                    res.add(sb.toString());
                }

                return;
            }

            for (int i = pos; i < num.length(); i++) {
                //!!! "=='0'"
                if (num.charAt(pos) == '0' && i != pos) {
                    break;
                }

                long cur = Long.parseLong(num.substring(pos, i + 1));

                int n = sb.length();

                if (pos == 0) {
                    helper(num, target, res, sb.append(cur), i + 1, cur, cur);
                    sb.setLength(n);//!!!
                    continue;
                }

                helper(num, target, res, sb.append('+').append(cur), i + 1, val + cur, cur);
                sb.setLength(n);//!!!

                helper(num, target, res, sb.append('-').append(cur), i + 1, val - cur, -cur);
                sb.setLength(n);//!!!

                helper(num, target, res, sb.append('*').append(cur), i + 1, val - pre + pre * cur, pre * cur);
                sb.setLength(n);//!!!
            }
        }
    }

    /**
     * Optimized by removing using substring() in helper()
     * Time  : O(n * 4 ^ (n - 1))
     * Space : O(n)
     *
     * 53 ms
     */
    class Solution {
        public List<String> addOperators(String num, int target) {
            List<String> res = new ArrayList<>();
            if (null == num || num.length() == 0) {
                return res;
            }

            helper(num, target, res, new StringBuilder(), 0, 0, 0);

            return res;
        }

        private void helper(String num, int target, List<String> res, StringBuilder sb, int pos, long val, long pre) {
            if (pos == num.length()) {
                if (val == target) {
                    res.add(sb.toString());
                }

                return;
            }

            long cur = 0;
            for (int i = pos; i < num.length(); i++) {
                if (num.charAt(pos) == '0' && i != pos) {
                    /**
                     * !!!
                     * "break", not "continue"
                     */
                    break;
                }

                /**
                 * rolling sum, avoid using substring() and parsing it into long
                 */
                cur = cur * 10 + num.charAt(i) - '0';
                // long cur = Long.parseLong(num.substring(pos, i + 1));

                int n = sb.length();

                if (pos == 0) {
                    /**
                     * "helper(num, target, res, sb.append(cur), i + 1, cur, cur);"
                     *
                     * When pos == 0, special process :
                     * 1.No operand yet, just append "cur" in sb.
                     * 2.Both current value and pre value are "cur"
                     */
                    helper(num, target, res, sb.append(cur), i + 1, cur, cur);
                    /**
                     * !!!
                     */
                    sb.setLength(n);
                    continue;
                }

                helper(num, target, res, sb.append('+').append(cur), i + 1, val + cur, cur);
                sb.setLength(n);

                helper(num, target, res, sb.append('-').append(cur), i + 1, val - cur, -cur);
                sb.setLength(n);

                helper(num, target, res, sb.append('*').append(cur), i + 1, val - pre + pre * cur, pre * cur);
                sb.setLength(n);
            }
        }
    }

    /**
     * Optimized version by Huahua
     *
     * http://zxi.mytechroad.com/blog/searching/leetcode-282-expression-add-operators/
     *
     * Time  : O(n * 4 ^ (n - 1))
     *         With n digits, it actually try to put 4 kinds of chars in each of the n - 1 positions between those
     *         n digits : "+", "-", "*", "", notice "" means the two digits merged together to from a larger number.
     *         Therefore, O(4 ^ (n - 1)). We also need to evaluate the value of the expression, which takes O(n).
     *
     * Space : O(n)
     *
     * 15 ms
     */
    class Solution4 {
        private List<String> ans;
        private char[] num;
        private char[] exp;
        private int target;

        public List<String> addOperators(String num, int target) {
            this.num = num.toCharArray();
            this.ans = new ArrayList<>();
            this.target = target;

            /**
             * get result from this char array
             */
            this.exp = new char[num.length() * 2];

            dfs(0, 0, 0, 0);

            return ans;
        }

        private void dfs(int pos, int len, long prev, long curr) {
            if (pos == num.length) {
                if (curr == target)
                    ans.add(new String(exp, 0, len));//!!!
                return;
            }

            int s = pos;
            int l = len;
            if (s != 0) ++len;

            long n = 0;
            while (pos < num.length) {
                if (num[s] == '0' && pos - s > 0) {
                    break; // 0X...
                }

                n = n * 10 + (int) (num[pos] - '0');

                if (n > Integer.MAX_VALUE) {
                    break; // too long
                }

                exp[len++] = num[pos++]; // copy exp
                if (s == 0) {
                    dfs(pos, len, n, n);
                    continue;
                }

                exp[l] = '+';
                dfs(pos, len, n, curr + n);
                exp[l] = '-';
                dfs(pos, len, -n, curr - n);
                exp[l] = '*';
                dfs(pos, len, prev * n, curr - prev + prev * n);
            }
        }
    }
}
