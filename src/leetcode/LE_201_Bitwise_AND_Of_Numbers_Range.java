package leetcode;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_201_Bitwise_AND_Of_Numbers_Range {
    /*
        Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.

        For example, given the range [5, 7], you should return 4.
     */

    //Time : < O(n), Space : O(1)
    public int rangeBitwiseAnd(int m, int n) {
        int offset = 0;
        while (m != n) {
            m >>= 1;
            n >>= 1;
            offset++;
        }

        return m << offset;
    }
}
