package leetcode;

public class LE_1247_Minimum_Swaps_To_Make_Strings_Equal {
    /**
     * You are given two strings s1 and s2 of equal length consisting of
     * letters "x" and "y" only. Your task is to make these two strings
     * equal to each other. You can swap any two characters that belong
     * to different strings, which means: swap s1[i] and s2[j].
     *
     * Return the minimum number of swaps required to make s1 and s2 equal,
     * or return -1 if it is impossible to do so.
     *
     * Example 1:
     * Input: s1 = "xx", s2 = "yy"
     * Output: 1
     * Explanation:
     * Swap s1[0] and s2[1], s1 = "yx", s2 = "yx".
     *
     * Example 2:
     * Input: s1 = "xy", s2 = "yx"
     * Output: 2
     * Explanation:
     * Swap s1[0] and s2[0], s1 = "yy", s2 = "xx".
     * Swap s1[0] and s2[1], s1 = "xy", s2 = "xy".
     * Note that you can't swap s1[0] and s1[1] to make s1 equal to "yx", cause we can only swap chars in different strings.
     *
     * Example 3:
     * Input: s1 = "xx", s2 = "xy"
     * Output: -1
     * Example 4:
     *
     * Input: s1 = "xxyyxyxyxx", s2 = "xyyxyxxxyx"
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= s1.length, s2.length <= 1000
     * s1, s2 only contain 'x' or 'y'.
     *
     * Medium
     */

    public int minimumSwap(String s1, String s2) {
        int x1 = 0; // number of 'x' in s1 (skip equal chars at same index)
        int y1 = 0; // number of 'y' in s1 (skip equal chars at same index)
        int x2 = 0; // number of 'x' in s2 (skip equal chars at same index)
        int y2 = 0; // number of 'y' in s2 (skip equal chars at same index)

        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 == c2) { // skip chars that are equal at the same index in s1 and s2
                continue;
            }
            if (c1 == 'x') {
                x1++;
            } else {
                y1++;
            }
            if (c2 == 'x') {
                x2++;
            } else {
                y2++;
            }
        }

        /**
         * After skip "c1 == c2", check the number of  'x' and 'y' left in s1 and s2.
         **/
        if ((x1 + x2) % 2 != 0 || (y1 + y2) % 2 != 0) {
            /**
             * if number of 'x' or 'y' is odd, we can not make s1 equals to s2
             */
            return -1;
        }

        /**
         * Cases to do 1 swap:
         * "xx" => x1 / 2 => how many pairs of 'x' we have ?
         * "yy" => y1 / 2 => how many pairs of 'y' we have ?
         *
         * Cases to do 2 swaps:
         * "xy" or "yx" =>  x1 % 2
         */
        int swaps = x1 / 2 + y1 / 2 + (x1 % 2) * 2;

        return swaps;
    }


    /**
     * https://leetcode.com/problems/minimum-swaps-to-make-strings-equal/discuss/419351/Java-Solution-with-detailed-comments
     *
     * Observation from given example
     * case 1:
     * xx
     * yy => minimum swap is 1
     *
     * case 2:
     * xy
     * yx => minimum swap is 2
     *
     * case 3:
     * xx
     * xy => not possible [If we have odd no of sum of x/y in both strings then it is impossible to make them equal ]
     *
     * Steps:
     * 1. Find sum of x and y in both string, return -1 if case 3 [We can ignore count of x and y which are equal
     * on same index in both string]
     * 2. Reduce the problem to case 1 and case 2.
     *
     * Eg:
     * s1= xxyyxyxyxx
     * s2= xyyxyxxxyx
     *
     * step 1:
     * xs1 =3, ys1=3
     * xs2 =3, ys2=3
     *
     * step 2:
     *
     * > now we can try to reduce problem to case1 and case 2,
     * as there sum is even we can run loop by subtracting 2 with each [adding 1 to answer , see code below]
     *
     * > we will left with  xs1 =1, ys1=1, xs2 =1, ys2=1 => case 2 [add two to answer]
     */
    public int minimumSwap1(String s1, String s2) {
        int x1 = 0, y1 = 0;
        int x2 = 0, y2 = 0;

        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 == c2) { // skip chars that are equal at the same index in s1 and s2
                continue;
            }
            if (c1 == 'x') {
                x1++;
            } else {
                y1++;
            }
            if (c2 == 'x') {
                x2++;
            } else {
                y2++;
            }
        }

        //step 1
        if ((x1 + x2) % 2 != 0 || (y1 + y2) % 2 != 0)
            return -1;

        //step 2

        int ans = 0;

        /**
         * case 1, try all pairs first
         **/
        boolean flag = true;
        while (flag) {
            flag = false;
            //try pairs like xx(from s1), yy(from s2)
            if ((x1 - 2) >= 0 && (y2 - 2) >= 0) {
                ans++;
                x1 -= 2;
                y2 -= 2;

                flag = true;
            }

            //try pairs like yy(from s1), xx(from s2),
            if ((x2 - 2) >= 0 && (y1 - 2) >= 0) {
                ans++;
                x2 -= 2;
                y1 -= 2;

                flag = true;
            }
        }

        /**
         * case 2
         * we will be left with x1 =1, y1 =1 x2 =1, y2 =1
         **/
        if (x1 == 1 && y1 == 1 && x2 == 1 && y2 == 1) {
            ans += 2;
        }

        return ans;
    }
}


