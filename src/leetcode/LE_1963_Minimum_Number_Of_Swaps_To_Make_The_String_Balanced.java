package leetcode;

public class LE_1963_Minimum_Number_Of_Swaps_To_Make_The_String_Balanced {
    /**
     * You are given a 0-indexed string s of even length n. The string consists of exactly n / 2 opening brackets '['
     * and n / 2 closing brackets ']'.
     *
     * A string is called balanced if and only if:
     *
     * It is the empty string, or
     * It can be written as AB, where both A and B are balanced strings, or
     * It can be written as [C], where C is a balanced string.
     * You may swap the brackets at any two indices any number of times.
     *
     * Return the minimum number of swaps to make s balanced.
     *
     *
     *
     * Example 1:
     * Input: s = "][]["
     * Output: 1
     * Explanation: You can make the string balanced by swapping index 0 with index 3.
     * The resulting string is "[[]]".
     *
     * Example 2:
     * Input: s = "]]][[["
     * Output: 2
     * Explanation: You can do the following to make the string balanced:
     * - Swap index 0 with index 4. s = "[]][][".
     * - Swap index 1 with index 5. s = "[[][]]".
     * The resulting string is "[[][]]".
     *
     * Example 3:
     * Input: s = "[]"
     * Output: 0
     * Explanation: The string is already balanced.
     *
     *
     * Constraints:
     *
     * n == s.length
     * 2 <= n <= 106
     * n is even.
     * s[i] is either '[' or ']'.
     * The number of opening brackets '[' equals n / 2, and the number of closing brackets ']' equals n / 2.
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-string-balanced
     */

    public int minSwaps(String s) {
        int l = 0, r= 0, res = 0;

        for (char c : s.toCharArray()) {
            if (c == '[') {
                l++;
            } else {
                r++;
            }

            if (l < r) {
                res++;
                l++; //!!!
                r--; //!!!
            }
        }

        return res;
    }
}

/**
 * We only care about the result - a balanced string, it does not matter how the swap is done.
 * At any index, number of left side brackets should always be greater than tne number of right side brackets.
 * 1.Iterate through chars in string, count left and right
 * 2.If left is smaller than right, update res, AND left and right(!!!)
 */