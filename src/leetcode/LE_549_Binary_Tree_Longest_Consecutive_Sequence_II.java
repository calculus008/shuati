package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 7/27/18.
 */
public class LE_549_Binary_Tree_Longest_Consecutive_Sequence_II {
    /**
         Given a binary tree, you need to find the length of Longest Consecutive Path in Binary Tree.

         Especially, this path can be either increasing or decreasing.
         For example, [1,2,3,4] and [4,3,2,1] are both considered valid,
         but the path [1,2,4,3] is not valid. On the other hand, the path can be in the child-Parent-child order,
         where not necessarily be parent-child order.

         Example 1:
         Input:
           1
          / \
         2   3
         Output: 2
         Explanation: The longest consecutive path is [1, 2] or [2, 1].

         Example 2:
         Input:
           2
          / \
         1   3
         Output: 3
         Explanation: The longest consecutive path is [1, 2, 3] or [3, 2, 1].

         Note: All the values of tree nodes are in the range of [-1e7, 1e7].
     */

    /**
     * LE_298_Binary_Tree_Longest_Consecutive_Sequence
     *
     * Solution 1
     */
    int res = 0;
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }

        helper1(root);
        return res;
    }

    public int[] helper1(TreeNode root) {
        if (root == null) {
            return new int[] {0, 0};
        }

        /**
         * increase and decrease : 从当前node往下一层看。
         *             3
         *  decr     / |   incr
         *          2  4
         *         /   |
         *        1    5
         *
         *             3
         *  incr     / |   decr
         *          4  2
         *         /   |
         *        5    1
         */
        int increase = 1, decrease = 1;

        if (root.left != null) {
            int[] l = helper1(root.left);
            if (root.val == root.left.val - 1) {
                increase = l[0] + 1;
            } else if (root.val == root.left.val + 1) {
                decrease = l[1] + 1;
            }
        }

        if (root.right != null) {
            int[] r = helper1(root.right);
            if (root.val == root.right.val - 1) {
                increase = Math.max(increase, r[0] + 1);
            } else if (root.val == root.right.val + 1) {
                decrease = Math.max(decrease, r[1] + 1);
            }
        }

        //increase + decrease, 多加了一次root, 所以要减一。
        res = Math.max(res, increase + decrease - 1);

        return new int[] {increase, decrease};
    }


    /**
     * Solution 2
     */
    class ResultType {
        public int max_length, max_down, max_up;
        ResultType(int len, int down, int up) {
            max_length = len;
            max_down = down;
            max_up = up;
        }
    }

    public int longestConsecutive2(TreeNode root) {
        return helper2(root).max_length;
    }

    ResultType helper2(TreeNode root) {
        if (root == null) {
            return new ResultType(0, 0, 0);
        }

        ResultType left = helper2(root.left);
        ResultType right = helper2(root.right);

        int down = 0, up = 0;
        if (root.left != null && root.left.val + 1 == root.val)
            down = Math.max(down, left.max_down + 1);

        if (root.left != null && root.left.val - 1 == root.val)
            up = Math.max(up, left.max_up + 1);

        if (root.right != null && root.right.val + 1 == root.val)
            down = Math.max(down, right.max_down + 1);

        if (root.right != null && root.right.val - 1 == root.val)
            up = Math.max(up, right.max_up + 1);

        /**
         * if both left and right are null, then len = 0 + 1 + 0 = 1
         *
         *         2 (3, 1, 1)
         *        / \
         *       1  3
         * (1,0,0)  (1,0,0)
         *
         */
        int len = down + 1 + up;
        len = Math.max(len, Math.max(left.max_length, right.max_length));

        return new ResultType(len, down, up);
    }
}
