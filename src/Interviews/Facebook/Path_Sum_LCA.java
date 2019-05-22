package Interviews.Facebook;

import common.TreeNode;

public class Path_Sum_LCA {
    /**
     * 一个二叉树，给出两个node和最近公共祖先之间（两条path）的sum。
     * 写了两个方法， 方法A找最近公共祖先，方法B算二叉树两个node之间的和。
     * 先跑方法A，再跑两次方法B。
     */

    public int getSum(TreeNode root, TreeNode p, TreeNode q) {
        if (p == null || q == null) return 0;

        TreeNode lca = lca(root, p, q);

        /**
         * if lca is one of p or q
         */
        if (lca == p || lca == q) {
            TreeNode start = (lca == p ? p : q);
            TreeNode end = (lca == p ? q : p);
            return getPathSum(start, end);
        }

        int sum1 = getPathSum(lca, p);
        int sum2 = getPathSum(lca, q);

        return sum1 + sum2 - lca.val;
    }

    //LE_236_Lowest_Common_Ancestor_Of_BT
    private TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode l = lca(root.left, p, q);
        TreeNode r = lca(root.right, p, q);

        if (l != null && r != null) return root;

        return l == null ? r : l;
    }

    private int getPathSum(TreeNode root, TreeNode n) {
        if (root == null) return -1;
        if (root == n) return n.val;

        int l = getPathSum(root.left, n);
        int r = getPathSum(root.right, n);

        if (l == -1 && r == -1) return -1;

        return (l == -1 ? r : l) + root.val;
    }
}
