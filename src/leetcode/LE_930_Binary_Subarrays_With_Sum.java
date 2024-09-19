package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_930_Binary_Subarrays_With_Sum {
    /**
     * In an array A of 0s and 1s, how many non-empty subarrays have sum S?
     *
     * Example 1:
     *
     * Input: A = [1,0,1,0,1], S = 2
     * Output: 4
     * Explanation:
     * The 4 subarrays are bolded below:
     * [1,0,1,0,1]
     * [1,0,1,0,1]
     * [1,0,1,0,1]
     * [1,0,1,0,1]
     *
     * Note:
     *
     * A.length <= 30000
     * 0 <= S <= A.length
     * A[i] is either 0 or 1.
     *
     * Medium
     */

    class Solution_sliding_window {
        // Time: O(n), Space: O(1)
        public int numSubarraysWithSum(int[] nums, int goal) {
            int sum = 0;
            int res = 0;
            int count = 0;

            for (int i = 0, j = 0; i < nums.length; i++) {
                sum += nums[i];

                if(nums[i] == 1) {//!!!  ??
                    count = 0;
                }

                if(sum > goal) {//must shrink the window to get smaller sum
                    sum -= nums[j];
                    j++;
                }

                while(j <= i && sum == goal) {// count number of subarrays that sums up to goal
                    sum -= nums[j];
                    j++;
                    count++;
                }

                res += count;
            }
            return res;
        }
    }

    // Time and Space O(n)
    class Solution_prefixsum_hashmap_clean {
        public int numSubarraysWithSum(int[] nums, int goal) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 1);
            int sum = 0;
            int res = 0;

            for (int num : nums) {
                sum += num;
                if (map.containsKey(sum - goal)) {
                    res += map.get(sum - goal);
                }
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
            return res;
        }
    }


    /**
     * Optimal Solution
     *
     * Sliding Window, one pass
     * Time  : O(n)
     * Space : O(1)
     *
     * [1,0,1,0,1], S = 2
     *
     *  l   r
     * [1,0,1,0,1]  count = 1
     *  l     r
     * [1,0,1,0,1]  count = 2
     *    l     r
     * [1,0,1,0,1]  count = 3
     *
     *              count = 4
     */
    class Solution_Sliding_Window {
        public int numSubarraysWithSum(int[] A, int S) {
            int n = A.length;
            int left = 0, right = 0;
            int count = 0, sum = 0;

            for (; right < n; right++) {
                sum += A[right];

                while (left < right && sum > S) {
                    sum -= A[left++];
                }

                if (sum == S) {
                    count++;
                }

                /**
                 * 数当前有多少个0，但不能把Left指针也左移，因为不确定right右移之后
                 * 是否还有以当前Left开始的subarry满足target
                 */
                for (int i = left; sum == S && i < right && A[i] == 0; i++) {
                    count++;
                }
            }

            return count;
        }
    }

    /**
     * pre[j] - pre[i - 1] = S
     * pre[i - 1] = pre[j] - S
     *
     * So take the index of the current number in iteration as the end of a subarray,
     * look back, check in hashmap how many indices satisfy the above condition.
     *
     * Since keep looking back while moving forward, we can do prefix sum and lookup
     * in one iteration
     *
     * Time  : O(n)
     * Space : O(n), hashmap space
     **/
    class Solution_Presum_HashMap {
        public int numSubarraysWithSum(int[] A, int S) {
            int sum = 0;
            int res = 0;
            Map<Integer, Integer> presum = new HashMap<>();
            presum.put(0, 1);

            for (int num : A) {
                sum += num;

                int compliment = sum - S;

                if (presum .containsKey(compliment)) {
                    res += presum.get(compliment);
                }

                /**
                 * if we don't have the line "presum.put(0, 1)" above,
                 * we need to have the code below to deal with the case
                 * that the valid subarray starts from index 0.
                 */
//                if (sum == S) {
//                    res++;
//                }

                presum.put(sum, presum.getOrDefault(sum, 0) + 1);
            }

            return res;
        }
    }

    /**
     * Using a hashmap is an overkill in this problem since the only sums that are
     * possible are [0, 1, ..., n]. Therefore, we can just an array as our map instead.
     */
    class Solution_Presum_Array {
        public int numSubarraysWithSum(int[] A, int S) {
            int sum = 0;
            int res = 0;
            int[] presum = new int[A.length + 1];
            presum[0] = 1;//!!!

            for (int num : A) {
                sum += num;
                int compliment = sum - S;
                if (compliment >= 0) {//!!!Must do this check to make sure it's a valid index
                    res += presum[compliment];
                }
                presum[sum]++;
            }

            return res;
        }
    }

    /**
     * Almost the same solution as LE_1248_Count_Number_Of_Nice_Subarrays
     *
     * Time : O(n)
     * Space : O(1)
     */
    class Solution_Sliding_Window_AtMostK {
        public int numSubarraysWithSum(int[] A, int S) {
            return atMost(A, S) - atMost(A, S - 1);
        }

        public int atMost(int[] A, int S) {
            /**
             * !!!
             * There's an important difference bwteen this problem and LE_1248_Count_Number_Of_Nice_Subarrays.
             * In this problem S can be 0. Therefore we must check S here, if it's negative,
             * we should return 0 right away.
             */
            if (S < 0) return 0;

            int res = 0, j = 0, target = 0, n = A.length;

            for (int i = 0; i < n; i++) {
                target += A[i];
                while (target > S) {
                    target -= A[j++];
                }
                res += i - j + 1;
            }
            return res;
        }
    }


}
