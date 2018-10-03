package lintcode;

/**
 * Created by yuank on 10/2/18.
 */
public class LI_43_Maximum_Subarray_III {
    /**
         Given an array of integers and a number k,
         find k non-overlapping subarrays which have the largest sum.

         The number in each subarray should be contiguous.

         Return the largest sum.

         Example
         Given [-1,4,-2,3,-2,3], k=2, return 8

         Notice :
         The subarray should contain at least one number

         Hard
     */

    public int maxSubArray(int[] nums, int k) {
        if (nums.length < k) {
            return 0;
        }
        int len = nums.length;


        int[][] globalMax = new int[k + 1][len + 1];
        int[][] localMax = new int[k + 1][len + 1];

        for (int i = 1; i <= k; i++) {
            localMax[i][i-1] = Integer.MIN_VALUE;
            //小于 i 的数组不能够partition
            for (int j = i; j <= len; j++) {
                localMax[i][j] = Math.max(localMax[i][j-1], globalMax[i - 1][j-1]) + nums[j-1];
                if (j == i)
                    globalMax[i][j] = localMax[i][j];
                else
                    globalMax[i][j] = Math.max(globalMax[i][j-1], localMax[i][j]);
            }
        }
        return globalMax[k][len];
    }
}
