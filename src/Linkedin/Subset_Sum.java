package Linkedin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subset_Sum {
    /**
     * Given an array of size N and a sum, the task is to check
     * whether some array elements can be added to sum to N .
     *
     * Note:
     * At least one element should be included to form
     * the sum.(i.e. sum cant be zero)
     *
     * Examples:
     *
     * Input: array = -1, 2, 4, 121, N = 5
     * Output: YES
     * The array elements 2, 4, -1 can be added to sum to N
     *
     * Input: array = 1, 3, 7, 121, N = 5
     * Output:NO
     */

    /**
     * Solution1
     *
     * Bit
     */
    class Solution1 {
        public void find(int[] arr, int length, int s) {
            // loop for all 2^n combinations
            for (int i = 1; i <= (1 << length); i++) {

                // sum of a combination
                int sum = 0;

                for (int j = 0; j < length; j++)
                    if (((i >> j) & 1) % 2 == 1) {
                        sum += arr[j];
                    }

                if (sum == s) {
                    System.out.println("YES");
                    return;
                }
            }

            // else print no
            System.out.println("NO");
        }

        /**
         * Time : O(n * 2 ^ n)
         * Space : O(n * 2 ^ n)
         */
        public List<List<Integer>> findSets(int[] nums, int length, int target) {
            List<List<Integer>> res = new ArrayList<>();

            for (int i = 1; i <= (1 << length); i++) {
                int sum = 0;
                List<Integer> subset = new ArrayList<>();

                for (int j = 0; j < length; j++)
                    if ((i & (1 << j)) != 0) {//!!! "!=0", NOT "==1"
                        sum += nums[j];
                        /**
                         * only if nums does not contain negative number
                         */
//                    if (sum > target) {
//                        break;
//                    }

                        if (sum == target) {
                            res.add(subset);
                            subset.add(nums[j]);

                            /**
                             * if given set contains only positive number, we can break here.
                             * if there are negative numbers, we need to go further.
                             */
                        }
                    }
            }

            return res;
        }
    }

    /**
     * DFS, assume there are duplicates and all positive numbers
     */
    class Solution2 {
        List<List<Integer>> res;
        int target;
        int[] input;

        public List<List<Integer>> subsetsSum(int[] nums, int target) {
            res = new ArrayList<>();
            if (nums == null || nums.length == 0) {
                return res;
            }

            this.target = target;
            this.input = nums;
            Arrays.sort(input);

            helper(0, new ArrayList<>(), 0);

            return res;
        }

        public void helper(int pos, List<Integer> cur, int sum) {
            if (sum > target) {
                return;
            }

            if (pos == input.length) {
                return;
            }

            if (sum == target) {
                res.add(new ArrayList<>(cur));
                return;
            }

            for (int i = pos; i < input.length; i++) {
                cur.add(input[i]);
                helper(i, cur, sum + input[i]);
                cur.remove(cur.size() - 1);
            }
        }
    }

}
