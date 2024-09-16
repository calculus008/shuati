package leetcode;

public class LE_1248_Count_Number_Of_Nice_Subarrays {
    /**
     * Given an array of integers nums and an integer k. A subarray is called nice if there are k odd numbers on it.
     *
     * Return the number of nice sub-arrays.
     *
     * Example 1:
     * Input: nums = [1,1,2,1,1], k = 3
     * Output: 2
     * Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].
     *
     * Example 2:
     * Input: nums = [2,4,6], k = 1
     * Output: 0
     * Explanation: There is no odd numbers in the array.
     *
     * Example 3:
     * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
     * Output: 16
     *
     * Constraints:
     *
     * 1 <= nums.length <= 50000
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= nums.length
     *
     * Medium
     *
     * https://leetcode.com/problems/count-number-of-nice-subarrays
     */

    class Solution_clean {
        public int numberOfSubarrays_Mine(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k < 1) return 0;

            int target = 0;
            int count = 0;
            int res = 0;
            int n = nums.length;

            for (int i = 0, j = 0; i < n; i++) {
                if (nums[i] % 2 == 1) {
                    target++;
                    count = 0;
                }

                while (target == k) {
                    count++;
                    if (nums[j] % 2 == 1) {
                        target--;
                    }
                    j++;
                }

                res += count;
            }

            return res;
        }
    }
    /**
     * Optimal Solution
     *
     * Sliding Window, one pass
     * Time : O(n)
     * Space : O(1)
     *
     * Same solution as in LE_930_Binary_Subarrays_With_Sum.
     *
     * It can also handle the case that S = 0.
     */
    class Solution_Sliding_Window {
        public int numberOfSubarrays(int[] A, int S) {
            int n = A.length;
            int left = 0, right = 0;
            int count = 0, sum = 0;

            for (; right < n; right++) {
                sum += A[right] % 2;

                while (left < right && sum > S) {
                    sum -= A[left++] % 2;
                }

                if (sum == S) {
                    count++;
                }

                /**
                 * So we first find a window that sums to S, then we check its prefix -
                 * all 0s starting from left to the index of first 1. For example:
                 * S = 2,
                 * l         r
                 * 0 0 0 1 0 1
                 *
                 * When r moves to the last index, sum == 2, count increase by one,
                 * it actually counts the subarray from idx 3 to 5. It has 3 0s before
                 * the first 1 (at idx 3). So we increase count by 3, count = 4
                 */
                for (int i = left; sum == S && i < right && A[i] % 2 == 0; i++) {
                    count++;
                }
            }

            return count;
        }
    }

        /**
     * Same at most K two passes solution as LE_992_Subarrays_With_K_Different_Integers
     *
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution_Sliding_Window_AtMostK {
        public int numberOfSubarrays(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k < 1) return 0;

            return atMostK(nums, k) - atMostK(nums, k - 1);
        }

        private int atMostK(int[] nums, int k) {
            int count = 0;
            int res = 0;
            int n = nums.length;

            for (int i = 0, j = 0; i < n; i++) {
                if (nums[i] % 2 == 1) {
                    count++;
                }

                while (count > k) {
                    if (nums[j] % 2 == 1) {
                        count--;
                    }
                    j++;
                }

                res += i - j + 1;
            }

            return res;
        }
    }

    /**
     * One pass solution
     * https://leetcode.com/problems/count-number-of-nice-subarrays/discuss/419378/JavaC%2B%2BPython-Sliding-Window-O(1)-Space
     *
     * 3 pointers, sliding windoer + one counter for prefix.
     *
     * prefix + core window + postfix
     * core window : min window that has k odd numbers.
     *
     * For every core window :
     * Base = number of prefix + 1
     * Res =  Base * number of postfix
     *
     * For example :
     * k = 2
     * {2,2,2,1,2,2,1,2,2,2}
     *  |___| |_____| |___|
     *
     *  Base = 3 + 1 = 4
     *  res = 4 * 4 = 16
     *
     * Time  : O(n)
     * Space : O(1)
     *
     * Notice :
     * This solution won't work for LE_930_Binary_Subarrays_With_Sum,
     * because it allows target number to be 0. For this problem:
     * 1 <= k <= nums.length
     */
    class Solution2 {
        public int numberOfSubarrays(int[] A, int k) {
            int res = 0, i = 0, count = 0, n = A.length;
            for (int j = 0; j < n; j++) {
                if (A[j] % 2 == 1) {
                    --k;
                    count = 0;
                }

                while (k == 0) {
                    k += A[i++] & 1;
                    ++count;
                }

                res += count;
            }
            return res;
        }

        public int numberOfSubarrays_Mine(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k < 1) return 0;

            int target = 0;
            int count = 0;
            int res = 0;
            int n = nums.length;

            for (int i = 0, j = 0; i < n; i++) {
                /**
                 * Or
                 * if ((nums[i] & 1) == 1)
                 * Bit operation is mush faster
                 */
                if (nums[i] % 2 == 1) {
                    target++;
                    count = 0;
                }

                while (target == k) {
                    if (nums[j] % 2 == 1) {
                        target--;
                    }
                    count++;
                    j++;
                }

                res += count;
            }

            return res;
        }
    }

    public static  int numberOfSubarrays_Mine(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1) return 0;

        int target = 0;
        int count = 0;
        int res = 0;
        int n = nums.length;

        for (int i = 0, j = 0; i < n; i++) {
            if (nums[i] % 2 == 1) {
                target++;
                count = 0;
            }

            while (target == k) {
                if (nums[j] % 2 == 1) {
                    target--;
                }
                count++;
                j++;
            }

            res += count;
        }

        return res;
    }

    public static int numberOfSubarrays_2(int[] A, int S) {
        int n = A.length;
        int left = 0, right = 0;
        int count = 0, sum = 0;

        for (; right < n; right++) {
            System.out.println("A["+right+"]=" + A[right] + ", left="+left + ", right=" + right + ", sum="+sum +",count="+count);
            sum += A[right] % 2;

            while (left < right && sum > S) {
                sum -= A[left++] % 2;
            }

            if (sum == S) {
                count++;
                System.out.println("hit S, count="+ count);
            }

            for (int i = left; sum == S && i < right && A[i] % 2 == 0; i++) {
                count++;
                System.out.println("count prefix, count="+ count);
            }
        }

        return count;
    }

    public static void main(String[] args) {
        /**
         * input : {2,2,2,1,2,2,1,2,2,2}, k = 2
         *
         * 16 subarrays:
         *
         * {2,2,2,1,2,2,1}
         * {2,2,1,2,2,1}
         * {2,1,2,2,1}
         * {1,2,2,1}
         *
         * This is the base, 4 subarrays between idx 0 and 6.
         *
         * After it, every time we add one more char at the end of {2,2,2,1,2,2,1},
         * it will generate 4 more subarrays:
         *
         * {2,2,2,1,2,2,1,2}
         * {2,2,1,2,2,1,2}
         * {2,1,2,2,1,2}
         * {1,2,2,1,2}
         *
         * {2,2,2,1,2,2,1,2,2}
         * {2,2,1,2,2,1,2,2}
         * {2,1,2,2,1,2,2}
         * {1,2,2,1,2,2}
         *
         * {2,2,2,1,2,2,1,2,2,2}
         * {2,2,1,2,2,1,2,2,2}
         * {2,1,2,2,1,2,2,2}
         * {1,2,2,1,2,2,2}
         */
//        int[] nums = {2,2,2,1,2,2,1,2,2,2};
//        int[] nums = {2,2,2,1,2,2,1,2,2,2,1,2};
        int[] nums = {0,0,0,0};
//        int[] nums = {2,2,1,2,1,2,1,2};
//        System.out.println(numberOfSubarrays_Mine(nums, 0));

        System.out.println(numberOfSubarrays_2(nums, 0));
    }
}
