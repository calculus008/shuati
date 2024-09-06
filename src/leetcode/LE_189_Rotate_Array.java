package leetcode;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_189_Rotate_Array {
    /**
        Rotate an array of n elements to the right by k steps.

        For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].

        Note:
        Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.

         Hint:
         Could you do it in-place with O(1) extra space?
         Related problem:
            LE_151_Reverse_Words_In_A_String
            LE_186_Reverse_Words_In_String_II
            LE_557_Reverse_Words_In_A_String_III

        Medium

        https://leetcode.com/problems/rotate-array
     */

    /**
        Solution 1 : 3 swaps, Time : O(n), Space : O(1)

        k = 3
            1 2 3 4 5 6 7

        #1  7 6 5 4 3 2 1
            -------------
        #2  5 6 7 4 3 2 1
            -----
        #3  5 6 7 1 2 3 4
                  -------
     */
    public void rotate1(int[] nums, int k) {
        if (nums == null || nums.length == 0) return;

        int n = nums.length;
        int m = k % n;

        swap(nums, 0, n - 1);
        swap(nums, 0, m - 1);
        swap(nums, m, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        while (i < j) {
            int temp = nums[i];
            nums[i++] = nums[j];
            nums[j--] = temp;
        }
    }

    //solution 2 with extra space
    //Time : O(n), Space : O(n)
    public void rotate(int[] nums, int k) {
        int[] old = nums.clone();

        k = k % nums.length;
        for (int i = 0; i < nums.length; i++) {
            nums[(i + k) % nums.length] = old[i];
        }
    }
}
