package leetcode;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_201_Bitwise_AND_Of_Numbers_Range {
    /**
        Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers i
        n this range, inclusive.

        For example, given the range [5, 7], you should return 4.
     */

    /**
     * https://leetcode.com/problems/bitwise-and-of-numbers-range/discuss/56729/Bit-operation-solution(JAVA)
     *
     * The idea is very simple:
     *
     * 1.last bit of (odd number & even number) is 0.
     * 2.when m != n, There is at least an odd number and an even number, so the last bit position result is 0.
     * 3.Move m and n rigth a position.
     *
     * Keep doing step 1,2,3 until m equal to n, use a factor to record the iteration time.
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
