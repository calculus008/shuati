package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/9/18.
 */
public class LE_99_Recover_BST {
    /**
        Two elements of a binary search tree (BST) are swapped by mistake.

        Recover the tree without changing its structure.

        Note:
        A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?

     */

    //Solution 1 : Recurrsion Time and Space : O(n)
    // https://leetcode.com/problems/recover-binary-search-tree/discuss/32535/No-Fancy-Algorithm-just-Simple-and-Powerful-In-Order-Traversal
    TreeNode first = null;
    TreeNode second = null;
    TreeNode pre = null;

    public void recoverTree1(TreeNode root) {
        if (root == null) return;

        helper(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    public void helper(TreeNode root) {
        if (root == null) return;

        helper(root.left);
        if (pre != null && root.val <= pre.val) {
            if (first == null) first = pre;
            if (first != null) second = root;
        }
        pre = root;
        helper(root.right);
    }

    //Solution 2: Constant space, use Morris Traversal
    //https://leetcode.com/problems/recover-binary-search-tree/discuss/32559/detail-explain-about-how-morris-traversal-finds-two-incorrect-pointer

}
