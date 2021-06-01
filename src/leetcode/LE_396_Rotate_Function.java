package leetcode;

public class LE_396_Rotate_Function {
    /**
     * You are given an integer array nums of length n.
     *
     * Assume arrk to be an array obtained by rotating nums by k positions clock-wise. We define the rotation function F on nums as follow:
     *
     * F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1].
     * Return the maximum value of F(0), F(1), ..., F(n-1).
     *
     * Example 1:
     * Input: nums = [4,3,2,6]
     * Output: 26
     * Explanation:
     * F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
     * F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
     * F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
     * F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
     * So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
     *
     * Example 2:
     * Input: nums = [1000000007]
     * Output: 0
     *
     *
     * Constraints:
     * n == nums.length
     * 1 <= n <= 105
     * -231 <= nums[i] <= 231 - 1
     *
     * Medium
     */

    /**
     * Math
     *
     * Brutal force is O(n ^ 2) : rotate n times, everytime do the calculation requires n iteration.
     * O(n) solution : 公式推导，找出每次计算之间的关系，so we avoid iterating n numbers for each round. Kind of DP
     *
     * Current round:
     * F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1]
     *
     * Previous round:
     * F(k-1) = 0 * Bk-1[0] + 1 * Bk-1[1] + ... + (n-1) * Bk-1[n-1]
     *        = 0 * Bk[1] + 1 * Bk[2] + ... + (n-2) * Bk[n-1] + (n-1) * Bk[0]
     *
     * F(k) - F(k-1) = Bk[1] + Bk[2] + ... + Bk[n-1] + (1-n)Bk[0]
     *               = (Bk[0] + ... + Bk[n-1]) - nBk[0]
     *               = sum - nBk[0]
     *
     * Thus:
     * F(k) = F(k-1) + sum - nBk[0]
     *
     * What is Bk[0]?
     * k = 0; B[0] = A[0];
     * k = 1; B[0] = A[len-1];
     * k = 2; B[0] = A[len-2];
     * ...
     *
     */
    public int maxRotateFunction(int[] A) {
        if (A == null || A.length < 1) return 0;

        int sum = 0;
        int t_pre = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            t_pre += i * A[i];
        }

        int max = t_pre;
        for (int i = A.length - 1; i >= 1; i--) {
            int current = t_pre + sum - A.length * A[i];
            max = Math.max(max, current);
            t_pre = current;
        }
        return max;
    }
}
