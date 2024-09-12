package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_15_3Sum {
    /**
        Given an array S of n integers, are there elements a, b, c in S such that
        a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

        Note: The solution set must not contain duplicate triplets.

        For example, given array S = [-1, 0, 1, 2, -1, -4],

        A solution set is:
        [
          [-1, 0, 1],
          [-1, -1, 2]
        ]

        LE_259

        Medium

        https://leetcode.com/problems/3sum
     */


    class Solution_clean_version {
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length < 3) return res;

            Arrays.sort(nums);
            int n = nums.length;

            for (int i = 0; i < n - 2; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) continue;

                int l = i + 1;
                int r = n - 1;
                while(l < r) {
                    if (nums[l] + nums[r] == - nums[i]) {
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));

                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while(l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] > - nums[i]) {
                        r--;
                    } else{
                        l++;
                    }
                }
            }

            return res;
        }
    }

    /**
     * Time : O(n ^ 2)
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        //!!!
        Arrays.sort(nums);

        for (int i = 0 ; i < nums.length - 2; i++) {
            //!!!去重
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int sum = 0 - nums[i];
            int start = i + 1;
            int end = nums.length - 1;

            //Same as 2sum
            while (start < end) {
                if (nums[start] + nums[end] == sum) {
                    res.add(Arrays.asList(nums[i], nums[start], nums[end]));

                    //!!!去重
                    while (start < end && nums[start] == nums[start + 1]) start++;
                    while (start < end && nums[end] == nums[end - 1]) end--;
                    start++;
                    end--;
                } else if (nums[start] + nums[end] < sum) {
                    start++;
                } else {
                    end--;
                }
            }
        }

        return res;
    }

    class Solution_Practice {
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length < 3) return res;

            Arrays.sort(nums);
            int n = nums.length;

            for (int i = 0; i < n - 2; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) {
                    /**
                     * !!!
                     * "continue", NOT "i++"!!!
                     */
                    continue;
                }

                int l = i + 1;
                int r = n - 1;

                while (l < r) {
                    if (nums[l] + nums[r] == -nums[i]) {
                        /**
                         * !!!
                         * Arrays.asList
                         */
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));

                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while(l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }

                        /**
                         * !!!
                         * l moves to left and r moves to right, one more step!!!
                         */
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] > -nums[i]) {
                        r--;
                    } else {
                        l++;
                    }
                }
            }

            return res;
        }
    }



}
