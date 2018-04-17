package leetcode;

/**
 * Created by yuank on 3/29/18.
 */
public class LE_222_Count_Complete_Tree_Nodes {
    /*
        Given a complete binary tree, count the number of nodes.

        Definition of a complete binary tree from Wikipedia:
        In a complete binary tree every level, except possibly the last, is completely filled,
        and all nodes in the last level are as far left as possible.
        It can have between 1 and 2h nodes inclusive at the last level h.
     */

    //Time : O(logn * logn), Space : O(n) or O(logn)?
    public int countNodes(TreeNode root) {
        //!!! can't have the line here, lead to TLE
        // if (root == null) return 0;

        // int right = getRightDepth(root);//!!! root
        // int left = getLeftDepth(root);//!!! root

        int left = getDepth(root, true);
        int right = getDepth(root, false);

        if (left == right) {
            return (1 << right) - 1;
        }

        return countNodes(root.left) + 1 + countNodes(root.right);
    }

    private int getRightDepth(TreeNode root) {
        int res = 0;
        while (root != null) {
            root = root.right;
            res++;
        }
        return res;
    }

    private int getLeftDepth(TreeNode root) {
        int res = 0;
        while (root != null) {
            root = root.left;
            res++;
        }
        return res;
    }

    private int getDepth(TreeNode root, boolean isLeft) {
        if (root == null) return 0;
        return (isLeft ? getDepth(root.left, true) : getDepth(root.right, false)) + 1;
    }
}
