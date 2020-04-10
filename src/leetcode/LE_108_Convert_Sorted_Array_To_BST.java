package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/12/18.
 */
public class LE_108_Convert_Sorted_Array_To_BST {
    /**
        Given an array where elements are sorted in ascending order,
        convert it to a height balanced BST.

        For this problem, a height-balanced binary tree is defined
        as a binary tree in which the depth of the two subtrees of
        every node never differ by more than 1.


        Example:

        Given the sorted array: [-10,-3,0,5,9],

        One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

              0
             / \
           -3   9
           /   /
         -10  5
     */

    /**
     * Pre order
     *
     * Time : O(n)
     * Space : If we consider space for output, it is O(n).
     *         If we don't consider space for output, it is O(logn)
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return helper(nums, 0, nums.length - 1);
    }

    public static TreeNode helper(int[] nums, int start, int end) {
        if (start > end) return null;

        int mid = (end - start) / 2 + start;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = helper(nums, start, mid - 1);
        root.right = helper(nums, mid + 1, end);

        return root;
    }
}
