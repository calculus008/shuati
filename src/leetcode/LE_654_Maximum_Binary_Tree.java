package leetcode;

import common.TreeNode;

public class LE_654_Maximum_Binary_Tree {
    /**
     * Given an integer array with no duplicates.
     * A maximum tree building on this array is defined as follow:
     *
     * The root is the maximum number in the array.
     * The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
     * The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
     * Construct the maximum tree by the given array and output the root node of this tree.
     *
     * Example 1:
     * Input: [3,2,1,6,0,5]
     * Output: return the tree root node representing the following tree:
     *
     *        6
     *      /   \
     *     3     5
     *     \    /
     *      2  0
     *       \
     *        1
     * Note:
     * The size of the given array will be in the range [1,1000].
     */

    /**
     * http://zxi.mytechroad.com/blog/leetcode/leetcode-654-maximum-binary-tree/
     *
     * Time  : O(nlogn) ~ O(n^2)
     * Space : O(logn) ~ O(n)
     */
    class Solution {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            if (null == nums || nums.length == 0) return null;

            /**
             * !!!
             * 左闭右开
             * [}, 0 ~ nums.length
             */
            return helper(nums, 0, nums.length);
        }

        private TreeNode helper(int[] nums, int l, int r) {
            if (l == r) {
                return null;
            }

            int idx = getMax(nums, l, r);
            TreeNode node = new TreeNode(nums[idx]);
            node.left = helper(nums, l, idx);
            node.right = helper(nums, idx + 1, r);

            return node;
        }

        private int getMax(int[] nums, int l, int r) {
            /**
             * !!!
             * res = l
             */
            int res = l;
            for (int i = l; i < r; i++) {
                /**
                 * !!!
                 * nums[i] > nums[res]
                 */
                if (nums[i] > nums[res]) {
                    res = i;
                }
            }

            return res;
        }
    }

    /**
     * version with range 0 to length - 1 (左闭右闭）
     */
    class Solution1 {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            if (null == nums || nums.length == 0) return null;

            return helper(nums, 0, nums.length - 1);//!!!
        }

        private TreeNode helper(int[] nums, int l, int r) {
            if (l > r) {//!!!
                return null;
            }

            int idx = getMax(nums, l, r);
            TreeNode node = new TreeNode(nums[idx]);
            node.left = helper(nums, l, idx - 1);//!!!
            node.right = helper(nums, idx + 1, r);

            return node;
        }

        private int getMax(int[] nums, int l, int r) {
            int res = l;
            for (int i = l; i <= r; i++) {//!!!
                if (nums[i] > nums[res]) {
                    res = i;
                }
            }

            return res;
        }
    }
}