package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/29/18.
 */
public class LE_222_Count_Complete_Tree_Nodes {
    /**
     * Given a complete binary tree, count the number of nodes.
     *
     * Note:
     *
     * Definition of a complete binary tree from Wikipedia:
     * In a complete binary tree every level, except possibly the last,
     * is completely filled, and all nodes in the last level are as far
     * left as possible. It can have between 1 and 2 ^ h nodes inclusive
     * at the last level h.
     *
     * Example:
     *
     * Input:
     *     1
     *    / \
     *   2   3
     *  / \  /
     * 4  5 6
     *
     * Output: 6
     */

    /**
     * https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)
     *
     * Tricky part for this solution is that it has 2 recursion function:
     *
     * getDepth()
     * countNodes()
     *
     */
    //Time : O(logn * logn), Space : O(n) or O(logn)?
    public int countNodes(TreeNode root) {
        //!!! can't have the line here, lead to TLE
        // if (root == null) return 0;

        // int right = getRightDepth(root);//!!! root
        // int left = getLeftDepth(root);//!!! root

        /**
         * get depth by going all the way to right
         */
        int left = getDepth(root, true);
        /**
         * get depth by going all the way to left
         */
        int right = getDepth(root, false);

        /**
         * A full tree
         */
        if (left == right) {
            return (1 << right) - 1;
        }

        return countNodes(root.left) + 1 + countNodes(root.right);
    }

//    private int getRightDepth(TreeNode root) {
//        int res = 0;
//        while (root != null) {
//            root = root.right;
//            res++;
//        }
//        return res;
//    }
//
//    private int getLeftDepth(TreeNode root) {
//        int res = 0;
//        while (root != null) {
//            root = root.left;
//            res++;
//        }
//        return res;
//    }

    /**
     * One method to check depth of left and right subtrees, use isLeft as flag
     * to tell if it is on left or right
     */
    private int getDepth(TreeNode root, boolean isLeft) {
        if (root == null) return 0;
        return (isLeft ? getDepth(root.left, true) : getDepth(root.right, false)) + 1;
    }
}
