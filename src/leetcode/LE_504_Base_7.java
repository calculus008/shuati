package leetcode;

public class LE_504_Base_7 {
    /**
     * Given an integer num, return a string of its base 7 representation.
     *
     * Example 1:
     * Input: num = 100
     * Output: "202"
     * Example 2:
     *
     * Input: num = -7
     * Output: "-10"
     *
     * Constraints:
     * -107 <= num <= 107
     *
     * Easy
     */

    class Solution_oneline {
        public String convertToBase7(int n) {
            return Integer.toString(n, 7);
        }
    }

    class Solution_recursion {
        public String convertToBase7(int n) {
            if (n < 0) return "-" + convertToBase7(-n);
            if (n < 7) return Integer.toString(n);
            return convertToBase7(n / 7) + Integer.toString(n % 7);
        }
    }

    class Solution_iterative {
        public String convertTo7(int num) {
            if (num == 0) return "0";

            StringBuilder sb = new StringBuilder();
            boolean negative = num < 0 ? true : false;

            while (num != 0) {
                sb.append(Math.abs(num % 7));
                num = num / 7;
            }

            if (negative) {
                sb.append("-");
            }

            return sb.reverse().toString();
        }
    }
}
