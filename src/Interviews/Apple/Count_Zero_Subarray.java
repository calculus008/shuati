package Interviews.Apple;

public class Count_Zero_Subarray {
    /**
     * 一个arr 只有0， 1。count subarray with only 0。
     * 如：
     * arr = [1, 0, 1, 0, 0, 0]
     * subarray with only 0: [0],  [0]  [0]  [0]  [0,0]  [0,0]  [0,0,0]
     * return 7
     *
     * subarray这里注意是连续的。对于每个长度为k的全零array，最终结果的增量 就是比k-1的全零array 多了k。
     *
     * 每次多出一个0，新增加的0自己是一个新的subarray, 同时， 它又和前面的 k - 1 个subarrays 形成 k - 1 个
     * 新的subarrays, 所以总共增加了 k 个新的 subarrays.
     *
     * sliding window 走一遍就行
     *
     * follow up:
     * 2D array ？
     *
     * LE_1074_Number_Of_Submatrices_That_Sum_To_Target  with target value as 0
     */
    public static int countZeroSubarray(int[] nums) {
        int res = 0;
        int sum = 0;

        for (int i = 0, j = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sum == 0) {
                int n = i - j + 1;
                res += n;
            }

            while (sum != 0) {
                sum -= nums[j];
                j++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0};
        System.out.println(countZeroSubarray(nums));
    }
}
