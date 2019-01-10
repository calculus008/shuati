package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 4/20/18.
 */
public class LE_298_Binary_Tree_Longest_Consecutive_Sequence {
    /**
         Given a binary tree, find the length of the longest consecutive sequence path.

         The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections.
         The longest consecutive path need to be from parent to child (cannot be the reverse).

         For example,
           1
           \
           3
          / \
         2   4
         \
         5
         Longest consecutive sequence path is 3-4-5, so return 3.
             2
             \
             3
            /
           2
          /
         1
         Longest consecutive sequence path is 2-3,not3-2-1, so return 2.

         Medium
     */

    //Time : O(n), Space : O(n)
    int res = 0;

    public int longestConsecutive1(TreeNode root) {
        if (root == null) return 0;
        helper(root, 0, root.val);
        return res;
    }

    /**
     *
     * @param root
     * @param curMax     Current max consecutive sequence length, if not consectutive,
     *                need to start from current node, so max set to 1
     * @param target  If consecutive, target should be current node val plus 1
     */
    public void helper(TreeNode root, int curMax, int target) {
        if (root == null) return;

        if (target == root.val) {
            curMax++;
        } else {
            curMax = 1;
        }
        res = Math.max(res, curMax);

        helper(root.left, curMax, root.val + 1);
        helper(root.right, curMax, root.val + 1);
    }


    /**
     * Solution 2
     **/
    public int longestConsecutive2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        helper(root, 1);
        return res;
    }

    public void helper(TreeNode root, int len) {
        res = Math.max(res, len);//!!!

        if (root.left != null) {
            if (root.val + 1 == root.left.val) {
                helper(root.left, len + 1);
            } else {
                helper(root.left, 1);
            }
        }

        if (root.right != null) {
            if (root.val + 1 == root.right.val) {
                helper(root.right, len + 1);
            } else {
                helper(root.right, 1);
            }
        }
    }
}
