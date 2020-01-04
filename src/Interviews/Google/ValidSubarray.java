package Interviews.Google;

public class ValidSubarray {
    /**
     * Find valid sub-arrays. Given an array A of length N. Each element A can only be 0 or 1.
     * If a sub-array only contains 0, we call it valid. How many sub-arrays are there in A?
     *
     * Input:
     * 1)一个数组A，这个数组只有0和1元素
     * Output:
     * 1)一个数字
     * Example：
     * input: A = [1, 0, 0, 0, 1, 0]
     * output: 6 + 1 = 7
     *
     * A variation of LE_413_Arithmetic_Slices
     */

    /**
     * 题解：
     * 1）如果有3个连续的0,那么可以凑出1+2+3=6个sub-array。如果n个连续的零，就是 (n+1)n/2
     * 2）遍历数组一次，统计出所有的连续的0即可
     */
    public static int validSubarray(int[] nums) {
        int count = 0;
        int res = 0;

        for (int num : nums) {
            if (num == 0) {
                count++;
                res += count;
            } else {
                count = 0;
            }
        }

        return res;
    }

    public static void main(String [] args) {
        int test[] ={0, 1, 1, 1, 0, 0, 0, 1, 0, 0};
        int s = 7;

        int res = validSubarray(test);

        System.out.println(res);
    }
}
