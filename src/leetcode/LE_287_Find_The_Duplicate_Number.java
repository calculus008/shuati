package leetcode;

/**
 * Created by yuank on 4/19/18.
 */
public class LE_287_Find_The_Duplicate_Number {
    /**
         Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
            prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

         Note:
         You must not modify the array (assume the array is read only).
         You must use only constant, O(1) extra space.
         Your runtime complexity should be less than O(n2).
         There is only one duplicate number in the array, but it could be repeated more than once.

        Medium
     */

    //Important

    //This one works, but it needs Space O(n).So it does not meet the requirement
    public int findDuplicate1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] dup = new int[nums.length];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (++dup[nums[i]] > 1) {
                res = nums[i];
                break;
            }
        }

        return res;
    }

    /**
     *
     * @param nums
     * @return
     *
     * Time : O(n), Space : O(1)
     *
     *  idx 0 1 2 3
     * nums 2 1 3 1
     *
     * idx
     *  0 - 2
     *  1 - 1
     *  2 - 3
     *  3 - 1
     *
     *  0 - 2 - 3 - 1 - 1 - 1
     *  There's a loop
     *
     *  Check LE_142_Linked_List_Cycle_II
     *
     *  https://segmentfault.com/a/1190000003817671
     *
     */
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[nums[0]];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        fast = 0;

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}
