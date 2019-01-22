package leetcode;

/**
 * Created by yuank on 4/17/18.
 */
public class LE_280_Wiggle_Sort {
    /**
     * Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....

     For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
     */

    /**
     Time : O(n), Space : O(1)

     Key insights:
     For end result such as :
     col  0  1  2  3  4  5
     [1, 6, 2, 5, 3, 4]

     Peak values are at index 1, 3, 5. So the peak values always appear at odd number indexes.

     input : [3, 5, 2, 1, 6, 4]
     i = 1 : [3, 5, 2, 1, 6, 4]
     i = 2 : [3, 5, 2, 1, 6, 4]
     i = 3 : [3, 5, 1, 2, 6, 4]
     When we switch nums[2] and nums[3], the last loop already garauntees that nums[2] (2) is smaller than nums[1] (5),
     Here, we do switch becasue "(i % 2 == 1 && nums[i - 1] > nums[i])" => nums[2] > nums[3], therefore, current nums[3]
     must be smaller than nums[1]. So this one way switch won't break the wiggle sort pattern created so far.
     i = 4 : [3, 5, 1, 6, 2, 4]
     i = 5 : [3, 5, 1, 6, 2, 4]


     */
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        for (int i = 1; i < nums.length; i++) {
            if ((i % 2 == 1 && nums[i - 1] > nums[i]) || (i % 2 == 0 && nums[i - 1] < nums[i])) {
                int temp = nums[i];
                nums[i] = nums[i - 1];
                nums[i - 1] = temp;
            }
        }
    }
}
