package leetcode;

import common.TreeNode;

public class LE_814_Binary_Tree_Pruning {
    /**
     * Given the root of a binary tree, return the same tree where every subtree (of the given tree) not containing a 1 has been removed.
     *
     * A subtree of a node node is node plus every node that is a descendant of node.
     *
     * Example 1:
     * Input: root = [1,null,0,0,1]
     * Output: [1,null,0,null,1]
     * Explanation:
     * Only the red nodes satisfy the property "every subtree not containing a 1".
     * The diagram on the right represents the answer.
     *
     * Example 2:
     * Input: root = [1,0,1,0,0,0,1]
     * Output: [1,null,1,null,1]
     *
     * Example 3:
     * Input: root = [1,1,0,1,1,0,1,0]
     * Output: [1,1,0,1,1,null,1]
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 200].
     * Node.val is either 0 or 1.
     *
     * Medium
     */

    /**
     * Postorder recursion
     *
     * Prue left and right, return result, if both returned node is null (both children are pruned), check
     * current root val, if it is 0, it means root can be pruned, return null.
     *
     * Otherwise, set left and right to returned value and return root.
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) return root;

        TreeNode l = pruneTree(root.left);
        TreeNode r = pruneTree(root.right);

        if (l == null && r == null) {
            if (root.val == 0) return null;
        }

        root.left = l;
        root.right = r;
        return root;
    }
}
