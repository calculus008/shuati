package leetcode;

/**
 * Created by yuank on 4/6/18.
 */
public class LE_250_Count_Univalue_Subtrees {
    /**
        Given a binary tree, count the number of uni-value subtrees.

        A Uni-value subtree means all nodes of the subtree have the same value.

        For example:
        Given binary tree,
                      5
                     / \
                    1   5
                   / \   \
                  5   5   5
        return 4.
     */

    /**
        Postorder traversal
              5
             / \
            1   5
           / \   \
          5   5   5

          root left right res return
           5    T     T    1    T
           5    T     T    2    T
           1    T     T    0    F
           5    T     T    3    T
           5    T     T    4    T
           5    F     T    4    F


        Time and Space : O(n)
    */

    int res;

    public int countUnivalSubtrees(TreeNode root) {
        res = 0;
        helper(root);
        return res;
    }

    public boolean helper(TreeNode root) {
        if (root == null) return true;

        boolean left = helper(root.left);
        boolean right = helper(root.right);

        if(left && right) {
            if (root.left != null && root.val != root.left.val) {
                return false;
            }
            if (root.right != null && root.val != root.right.val) {
                return false;
            }
            res++;
            return true;
        }

        return false;
    }
}
