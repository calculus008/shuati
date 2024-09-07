package leetcode;

import java.util.*;

public class LE_532_K_Diff_Pairs_In_An_Array {
    /**
     * Given an array of integers and an integer k, you need to find the number
     * of UNIQUE k-diff pairs in the array. Here a k-diff pair is defined as an
     * integer pair (i, j), where i and j are both numbers in the array and
     * their absolute difference is k.
     *
     * Example 1:
     * Input: [3, 1, 4, 1, 5], k = 2
     * Output: 2
     * Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
     * Although we have two 1s in the input, we should only return the number of unique pairs.
     *
     * Example 2:
     * Input:[1, 2, 3, 4, 5], k = 1
     * Output: 4
     * Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
     *
     * Example 3:
     * Input: [1, 3, 1, 5, 4], k = 0
     * Output: 1
     * Explanation: There is one 0-diff pair in the array, (1, 1).
     *
     * Note:
     * The pairs (i, j) and (j, i) count as the same pair.
     * The length of the array won't exceed 10,000.
     * All the integers in the given input belong to the range: [-1e7, 1e7].
     *
     * Easy
     */

    /**
     *  HashMap
     *  Time and Space : O(n)
     */
    public int findPairs_clean(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        int res = 0;
        for (int key : map.keySet()) {
            if (k == 0) {
                if (map.get(key) >= 2) res++;
            } else {
                if (map.containsKey(key + k)) {
                    res++;
                }
            }
        }

        return res;
    }

    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0)   return 0;

        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            /**
             * !!! this is the key point or tricky part of this question, must deal with k as 0 separately.
             * If not for this special case, we can only use a set, no need to use HashMap to count the frequency.
             */
            if (k == 0) {
                //count how many elements in the array that appear more than twice.
                if (entry.getValue() >= 2) {//!!! ">=", not "=="
                    count++;
                }
            } else {
                if (map.containsKey(entry.getKey() + k)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Sort and two pointers
     *
     * Time : O(nlogn)
     * Space : O(1)
     */
    public int findPairs_sort_tow_pointers(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;

        int l = 0;
        int r = 1;
        int res = 0;

        while (l < n && r < n) {
            if (l == r || nums[r] - nums[l] < k) {  //!!! "||", not "&&"
                r++;
            } else if (nums[r] - nums[l] > k) {
                l++;
            } else {
                l++;
                res++;
                while(l < n && nums[l] == nums[l - 1]) {
                    l++;
                }
            }
        }

        return res;
    }
}
