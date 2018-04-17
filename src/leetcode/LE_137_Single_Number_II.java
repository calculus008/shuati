package leetcode;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_137_Single_Number_II {
    /**
        Given an array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

        Note:
        Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     */

    /**
       思路和136相似，通过位运算，让三个相同的数变为0，最后剩下只出现一次的数。
       定义ones, twos

       数字i出现：
       第一次 存入ones
       第二次 清空ones,存入twos
       第三次 tows清空

       例子：1 1 1 2

       1出现第一次
       ones  (0001 ^ 0000) & (~0000) = 0001 & 1111 = 0001  ("1"存入ones)
       twos  (0001 ^ 0000) & (~0001) = 0001 & 1110 = 0000  (还是0)

       1出现第二次
       ones  (0001 ^ 0001) & (~0000) = 0000 & 1111 = 0000  (清空ones)
       twos  (0001 ^ 0000) & (~0000) = 0001 & 1111 = 0001  (“1”存入twos)

       1出现第三次
       ones  (0001 ^ 0000) & (~0001) = 0001 & 1110 = 0000  (清空ones，实现三个相同的数变为0)
       twos  (0001 ^ 0001) & (~0000) = 0000 & 1111 = 0000  (存入twos)
    */

    public static int singleNumber(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            ones = (num ^ ones) & ~twos;
            twos = (num ^ twos) & ~ones;
        }
        return ones;
    }
}
