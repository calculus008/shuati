package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 4/20/18.
 */
public class LE_294_Flip_Game_II {
    /**
         You are playing the following Flip Game with your friend:
         Given a string that contains only these two characters: + and -,
         you and your friend take turns to flip two consecutive "++" into "--".
         The game ends when a person can no longer make a move and therefore the other person will be the winner.

         Write a function to determine if the starting player can guarantee a win.

         For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".

         Follow up:
         Derive your algorithm's runtime complexity.

         Medium
     */

    /**
     Backtracking
     Time and Space : O(2 ^ n)
     */

    public boolean canWin(String s) {
        if (s == null || s.length() == 0) return false;
        Map<String, Boolean> map = new HashMap<>();
        return helper(s, map);
    }

    public boolean helper(String s, Map<String, Boolean> map) {
        if (map.containsKey(s)) {
            return map.get(s);
        }

        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '+' && s.charAt(i + 1) == '+') {
                String next = s.substring(0, i) + "--" + s.substring(i + 2, s.length());
                /**
                 关键 ：当前player是否能赢，取决于下一个player能否用转换后的字符串(next)取得胜利。
                 所以，只有当“helper(next, dist)"返回FALSE时，当前player才能赢。
                 */
                if (!helper(next, map)) {
                    map.put(s, true); //!!! put s, NOT next
                    return true;
                }
            }
        }

        map.put(s, false);
        return false;
    }
}
