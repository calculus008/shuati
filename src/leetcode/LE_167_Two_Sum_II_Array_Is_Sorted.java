package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_167_Two_Sum_II_Array_Is_Sorted {
    /**
        Given an array of integers that is already sorted in ascending order,
        find two numbers such that they add up to a specific target number.

        The function twoSum should return indices of the two numbers such that
        they add up to the target, where index1 must be less than index2. Please
        note that your returned answers (both index1 and index2) are not zero-based.

        You may assume that each input would have exactly one solution and you may not use the same element twice.

        Input: numbers={2, 7, 11, 15}, target=9
        Output: index1=1, index2=2

        Medium

        https://leetcode.com/problems/two-sum-ii-input-array-is-sorted
     */

    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        int[] res = new int[2];

        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                res[0] = left + 1;
                res[1] = right + 1;
                break;
            }
        }

        return res;
    }
}
