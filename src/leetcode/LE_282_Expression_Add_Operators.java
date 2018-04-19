package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/18/18.
 */
public class LE_282_Expression_Add_Operators {
    /**
     * Given a string that contains only digits 0-9 and a target value,
     * return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

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

        for (int i = pos; i < num.length(); i++) {
            /**
             !!! "num.charAt(pos)", 如果开始为"0"而当前是非起始位（012）就不用再往后走了，“0XX”不是合法的数字。
             !!!“charAt(pos)"
             */
            if(num.charAt(pos) == '0' && i != pos) {
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
