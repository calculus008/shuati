package leetcode;

public class LE_777_Swap_Adjacent_In_LR_String {
    /**
     * In a string composed of 'L', 'R', and 'X' characters, like "RXXLRXRXL",
     * a move consists of either replacing one occurrence of "XL" with "LX",
     * or replacing one occurrence of "RX" with "XR". Given the starting string
     * start and the ending string end, return True if and only if there exists
     * a sequence of moves to transform one string to the other.
     *
     * Example:
     *
     * Input: start = "RXXLRXRXL", end = "XRLXXRRLX"
     * Output: True
     * Explanation:
     * We can transform start to end following these steps:
     * RXXLRXRXL ->
     * XRXLRXRXL ->
     * XRLXRXRXL ->
     * XRLXXRRXL ->
     * XRLXXRRLX
     * Note:
     *
     * 1 <= len(start) = len(end) <= 10000.
     * Both start and end will only consist of characters in {'L', 'R', 'X'}.
     *
     * Medium
     */

    /**
     * If start can be transferrred into end, 2 things should be true:
     *
     * 1.After removing "X" from start and end, the result strings should be equal.
     *   Basically, we need to guarantee that the relative location and length of subsequence which only
     *   includes 'R' and 'L' should be the same.
     * 2.Base on the way the chars move, for example, "XL" -> "LX", L can only move to its left side,
     *   so when compare start and end, 'L' can't be moved to its right side -> the index of 'L'
     *   in start string (i) should be larger than the index of the same 'L' in end string (j),
     *   if i < j, we return false. Similarly, for 'R', it can only moves to right, so if i > j, we
     *   can return false.
     *
     *   The key is to see through that replacing "XL" with "LX" literally means L can only
     *   moves to the left side.
     *
     *   Example :
     *   start : "XXRXXLXXXX"
     *   end   : "XXXXRXXLXX"
     *
     *   They meet the first condition (After "X" is removed, they are "RL").
     *   But, index of 'L' in start is 5, index of 'L' in end is 7, 5 < 7,
     *   it means L actually moves to the right side, which is impossible based
     *   on the given rules, therefore we should return false.
     *
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution {
        public boolean canTransform(String start, String end) {
            /**
             * "1 <= len(start) = len(end) <= 10000", with this guaranteed,we don't need to check length equality.
             */
            if !start.replace("X", "").equals(end.replace("X", ""))) {
                return false;
            }

            int j = 0;
            for (int i = 0; i < start.length(); i++) {
                if (start.charAt(i) == 'L') {
                    while (end.charAt(j) != 'L') {
                        j++;
                    }

                    if (i < j) {
                        return false;
                    }

                    j++;
                }
            }

            j = 0;
            for (int i = 0; i < start.length(); i++) {
                if (start.charAt(i) == 'R') {
                    while (end.charAt(j) != 'R') {
                        j++;
                    }

                    if (i > j) {
                        return false;
                    }

                    j++;
                }
            }

            return true;
        }
    }
}