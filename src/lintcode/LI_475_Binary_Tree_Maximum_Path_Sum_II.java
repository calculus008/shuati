package lintcode;

import common.TreeNode;

/**
 * Created by yuank on 7/26/18.
 */
public class LI_475_Binary_Tree_Maximum_Path_Sum_II {
    /**
         Given a binary tree, find the maximum path sum from root.

         The path may end at any node in the tree and contain at least one node in it.

         Example
         Given the below binary tree:

           1
          / \
         2   3
         return 4. (1->3)

         Easy
     */

    /**
     *  For this problem, the path has to start from root.
     *
     *  Compare with :
     *  LE_124_Binary_Tree_Max_Path_Sum, the path does not need to
     *  start from root
     *
     **/
    public int maxPathSum2(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        int left = Math.max(0, maxPathSum2(root.left));
        int right = Math.max(0, maxPathSum2(root.right));
        return Math.max(left, right) + root.val;
    }
}
