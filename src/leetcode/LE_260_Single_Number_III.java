package leetcode;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_260_Single_Number_III {
    /**
        Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice.
        Find the two elements that appear only once.

        For example:

        Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].

        Note:
        The order of the result is not important. So in the above example, [5, 3] is also correct.
        Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?

        Single Number I and II : LE_136, LE_137
     */

    /**
         In the first pass, we XOR all elements in the array, and get the XOR of the two numbers we need to find.
         Note that since the two numbers are distinct, so there must be a set bit (that is, the bit with value ‘1’) in the XOR result.
        Find out an arbitrary set bit (for example, the rightmost set bit).

        In the second pass, we divide all numbers into two groups, one with the aforementioned bit set,
        another with the aforementinoed bit unset. Two different numbers we need to find must fall into thte two distrinct groups.
        XOR numbers in each group, we can find a number in either group.

         Complexity:

         Time: O (n)

         Space: O (1)
     */

    public int[] singleNumber(int[] nums) {
        int diff = 0;

        //Get XOR of the 2 distinct numbers
        for (int num : nums) {
            diff ^= num;
        }

        //Get its last set bit
        diff &= -diff;

        int[] res = new int[2];
        for (int num : nums) {
            if ((num & diff) == 0) {
                res[0] ^= num;
            } else {
                res[1] ^= num;
            }
        }
        return res;
    }
}
