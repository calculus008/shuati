package Interviews.DoorDash;

import java.util.*;

import static java.util.Collections.binarySearch;

public class Consecutive_Sequence {
    /**
     * 最长连续序列。 (exactly one larger than the one that came before it)
     * ex: 1, 2, 5, 6 -> 1, 2
     * ex: 3, 5, 6, 7, 12 -> 5, 6, 7
     * LE_674_Longest_Continuous_Increasing_Subsequence
     *
     * (!!!)
     * follow up 变形：(exactly one larger than some number that came before it)
     * ex: 1, 6, 2, 7, 3 -> 1, 2, 3
     * ex: 3, 5, 6, 7, 12 -> 5, 6, 7
     *
     * Note
     * It is NOT LE_128_Longest_Consecutive_Sequence, which does not care about index
     * sequence.
     *
     * It is this one:
     * Longest Increasing consecutive subsequence
     * https://www.geeksforgeeks.org/longest-increasing-consecutive-subsequence/
     */

    public int findLengthOfLCIS_SPACE_O_N(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] dp = new int[n];

        dp[0] = 1;
        int res = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }

            res = Math.max(res, dp[i]);
        }

        return res;
    }

    public int findLengthOfLCIS_SPALCE_O_1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;

        int count = 1;
        int res = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }

            res = Math.max(res, count);
        }

        return res;
    }

    public int longestConsecutive_LE_128(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int res = 0;
        for (int num : nums) {
            int up = num + 1;
            while (set.contains(up)) {
                set.remove(up);
                up++;
            }

            int down = num - 1;
            while (set.contains(down)) {
                set.remove(down);
                down--;
            }

            res = Math.max(res, (up - 1) - (down + 1) + 1);
        }

        return res;
    }

    /**
     * Longest Increasing consecutive subsequence
     *
     * DP
     * dp[i] : the length of the longest subsequence which ends with nums[i].
     *
     * Transition :
     * if nums[i]-1 is present before i-th index:
     *     DP[i] = DP[ index of value nums[i]-1) ] + 1
     * else:
     *     DP[i] = 1
     *
     * Key Insights:
     * 意味顺序是要考虑的，所以用HashMap存数值和index.
     * 关键是，对于有重复的数值，我们只用存最近看见的那个index, 有点类似贪心，
     *
     */
    public static int longestIncreasingConsecutiveSubsequence(int[] nums) {
         if (nums == null || nums.length == 0)  return 0;

         int n = nums.length;

         Map<Integer, Integer> map = new HashMap<>();

         int[] dp = new int[n];
         dp[0] = 1;

         map.put(nums[0], 1);
         int res = 0;

         for (int i = 1; i < n; i++) {
             if (map.containsKey(nums[i] - 1)) {
                 int idx = map.get(nums[i] - 1);
                 dp[i] = dp[idx] + 1;
             } else {
                 dp[i] = 1;
             }

             map.put(nums[i], i);
             res = Math.max(res, dp[i]);
         }

         return res;
    }

    public static void main(String[] args) {
        int a1[] = { 3, 10, 3, 11, 4, 5, 6, 7, 8, 12 };
        System.out.println(longestIncreasingConsecutiveSubsequence(a1));
    }
}