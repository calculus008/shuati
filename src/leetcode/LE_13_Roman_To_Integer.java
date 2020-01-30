package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_13_Roman_To_Integer {
    /**
     * Roman numerals are represented by seven different
     * symbols: I, V, X, L, C, D and M.
     *
     * Symbol       Value
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * For example, two is written as II in Roman numeral, just two one's
     * added together. Twelve is written as, XII, which is simply X + II.
     * The number twenty seven is written as XXVII, which is XX + V + II.
     *
     * Roman numerals are usually written largest to smallest from left to right.
     * However, the numeral for four is not IIII. Instead, the number four is
     * written as IV. Because the one is before the five we subtract it making
     * four. The same principle applies to the number nine, which is written as IX.
     * There are six instances where subtraction is used:
     *
     * I can be placed before V (5) and X (10) to make 4 and 9.
     * X can be placed before L (50) and C (100) to make 40 and 90.
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     * Given a roman numeral, convert it to an integer. Input is guaranteed
     * to be within the range from 1 to 3999.
     *
     * Example 1:
     * Input: "III"
     * Output: 3
     *
     * Example 2:
     * Input: "IV"
     * Output: 4
     *
     * Example 3:
     * Input: "IX"
     * Output: 9
     *
     * Example 4:
     * Input: "LVIII"
     * Output: 58
     * Explanation: L = 50, V= 5, III = 3.
     *
     * Example 5:
     * Input: "MCMXCIV"
     * Output: 1994
     * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
     *
     * Medium
     */

    /**
     * Huahua's version
     */
    class Solution {
        public int romanToInt(String s) {
            Map<Character, Integer> map = new HashMap<>();

            map.put('I', 1);
            map.put('V', 5);
            map.put('X', 10);
            map.put('L', 50);
            map.put('C', 100);
            map.put('D', 500);
            map.put('M', 1000);

            int pre = 0;
            int sum = 0;
            char[] chs = s.toCharArray();
            for (int i = 0; i < s.length(); i++) {
                int cur = map.get(chs[i]);
                sum += cur;
                if (i > 0 && pre < cur) {
                    sum -= 2 * pre;
                }
                pre = cur;
            }

            return sum;
        }
    }

    /**
     * key is to handle special case: IV (5 -1 = 4), XL (50 -10 = 40), CD (500 - 100 = 400)
     * whenever a value smaller than its right side value, do minus.
     */
    public int romanToInt(String s) {
        int res = 0;
        //!!! move from right to left
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            switch(c) {
                case 'I' :
                    res += (res >= 5 ? -1 : 1);
                    break;
                case 'V' :
                    res += 5;
                    break;
                case 'X' :
                    res += (res >= 50 ? -1 : 1) * 10;
                    break;
                case 'L' :
                    res += 50;
                    break;
                case 'C' :
                    res += (res >= 500 ? -1 : 1) * 100;
                    break;
                case 'D' :
                    res += 500;
                    break;
                case 'M'  :
                    res += 1000;
                    break;
            }
        }

        return res;
    }
}
