package Interviews.Apple;

import java.util.*;

public class Three_Sum_Smaller {
    /**
     * Given an unsorted int array, find the number of 3 numbers in array that the sum is smaller than k
     */

    //Time: O(n ^ 2)
    public class ThreeSumSmaller {

        public int threeSumSmaller(int[] nums, int target) {
            Arrays.sort(nums);  // Sort the array
            int count = 0;

            // Iterate through the array
            for (int i = 0; i < nums.length - 2; i++) {
                count += twoSumSmaller(nums, i + 1, target - nums[i]);
            }

            return count;
        }

        // Helper function to find pairs with sum smaller than target starting from index 'start'
        private int twoSumSmaller(int[] nums, int start, int target) {
            int count = 0;
            int left = start, right = nums.length - 1;

            while (left < right) {
                if (nums[left] + nums[right] < target) {
                    // If the sum is less than target, all pairs between left and right are valid
                    count += right - left;
                    left++;
                } else {
                    right--;
                }
            }

            return count;
        }

//        public static void main(String[] args) {
//            ThreeSumSmaller solution = new ThreeSumSmaller();
//            int[] nums = {3, 1, 0, -2};
//            int target = 2;
//            System.out.println("Number of triplets with sum less than " + target + ": " + solution.threeSumSmaller(nums, target));
//        }
    }

}
