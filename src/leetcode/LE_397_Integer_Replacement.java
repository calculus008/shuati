package leetcode;

public class LE_397_Integer_Replacement {
    /**
     * Given a positive integer n and you can do operations as follow:
     *
     * If n is even, replace n with n/2.
     * If n is odd, you can replace n with either n + 1 or n - 1.
     * What is the minimum number of replacements needed for n to become 1?
     *
     * Example 1:
     * Input:
     * 8
     *
     * Output:
     * 3
     *
     * Explanation:
     * 8 -> 4 -> 2 -> 1
     *
     * Example 2:
     * Input:
     * 7
     *
     * Output:
     * 4
     *
     * Explanation:
     * 7 -> 8 -> 4 -> 2 -> 1
     * or
     * 7 -> 6 -> 3 -> 2 -> 1
     */

    /**
     * https://leetcode.com/problems/integer-replacement/discuss/87920/A-couple-of-Java-solutions-with-explanations
     *
     * Bit operation
     */

    public int integerReplacement1(int n) {
        int c = 0;
        while (n != 1) {
            if ((n & 1) == 0) {
                n >>>= 1;
            } else if (n == 3 || Integer.bitCount(n + 1) > Integer.bitCount(n - 1)) {
                --n;
            } else {
                ++n;
            }
            ++c;
        }
        return c;
    }

    public int integerReplacement2(int n) {
        int c = 0;
        while (n != 1) {
            if ((n & 1) == 0) {
                n >>>= 1;
            } else if (n == 3 || ((n >>> 1) & 1) == 0) {
                --n;
            } else {
                ++n;
            }
            ++c;
        }
        return c;
    }

    public int integerReplacement3(int n) {
        if (n == 1) return 0;
        else if (n == Integer.MAX_VALUE) return 32;//Integer.MAX_VALUE = 2147483647.
        else if (n % 2 == 0) return 1 + integerReplacement3(n / 2);
        else if (n == 3 || Integer.bitCount(n + 1) > Integer.bitCount(n - 1)) return 1 + integerReplacement3(n - 1);
        else return 1 + integerReplacement3(n + 1);
    }
}
