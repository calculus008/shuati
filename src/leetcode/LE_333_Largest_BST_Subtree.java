package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 5/13/18.
 */
public class LE_333_Largest_BST_Subtree {
    /**
         Given a binary tree, find the largest subtree which is a Binary Search Tree (BST),
         where largest means subtree with largest number of nodes in it.

         Note:
         A subtree must include all of its descendants.
         Here's an example:
         10
         / \
         5  15
         / \   \
         1   8   7
         The Largest BST Subtree in this case is the highlighted one.
         The return value is the subtree's size, which is 3.

         Medium
     */

    /**
     1.substree : use post order, from bottom up
     2.Can't tell if it is a BST during traversal : create new class SearchNode to store substree info
     3.BST

     Time : O(n)
     Space : O(n)
     **/
    class SearchNode {
        int size;
        int lower;
        int upper;

        public SearchNode(int size, int lower, int upper) {
            this.size = size;
            this.lower = lower;
            this.upper = upper;
        }
    }

    int res = 0;

    public int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        helper(root);

        return res;
    }

    private SearchNode helper(TreeNode root) {
        if (root == null) {
            //!!!Bottom up, so this is the starting point (0, max, min)
            return new SearchNode(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        SearchNode left = helper(root.left);
        SearchNode right = helper(root.right);

        if (left.size == -1 || right.size == -1 || root.val <= left.upper || root.val >= right.lower) {
            //!!! use -1 to tell that this is not a BST, therefore the value for lower and upper does not matter, just use 0.
            return new SearchNode(-1, 0, 0);
        }

        SearchNode cur = new SearchNode(left.size + right.size + 1,
                Math.min(root.val, left.lower),
                Math.max(root.val, right.upper)
        );
        res = Math.max(res, cur.size);

        return cur;
    }
}
