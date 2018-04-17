package leetcode;

/**
 * Created by yuank on 3/15/18.
 */
public class LE_129_Sum_Root_To_Leaf_Numbers {
    /*
        Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.

        An example is the root-to-leaf path 1->2->3 which represents the number 123.

        Find the total sum of all root-to-leaf numbers.

        For example,

            1
           / \
          2   3
        The root-to-leaf path 1->2 represents the number 12.
        The root-to-leaf path 1->3 represents the number 13.

        Return the sum = 12 + 13 = 25.
     */

    //Time and Space : O(n)

    //Solution 1
    int res;
    public int sumNumbers1(TreeNode root) {
        if (root == null) return 0;
        helper(root, 0);
        return res;
    }

    public void helper(TreeNode root, int sum) {
        if (root == null) return;

        helper(root.left, sum * 10 + root.val);
        helper(root.right, sum * 10 + root.val);

        if (root.right == null && root.left == null) {
            res += sum * 10 + root.val;
        }
    }

    //Solution 2
    public int sumNumbers2(TreeNode root) {
        return helper2(root, 0);
    }

    public int helper2(TreeNode root, int cur) {
        if (root == null) return 0;

        if (root.right == null && root.left == null) {
            return cur * 10 + root.val;
        }

        return helper2(root.left, cur * 10 + root.val) + helper2(root.right, cur * 10 + root.val);
    }
}
