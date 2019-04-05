package google;

import common.TreeNode;

public class Redundant_Connection_BST {
    /**
     * Follow up for LE_685_Redundant_Connection_II
     *
     * 给一棵二叉搜索树，有一条多余边，删除它
     * 例子：
     *      7
     *    /  \
     *   5    9
     *  /  \ /
     * 3    8
     *
     * 对于多余边5->8，9->8此处的删除需要有选择，跟之前的题目找到多余边立马不分选择删除有区别
     *
     * 思路:  LC 98: Validate Binary Search Tree (LE_98_Validate_BST)
     * DFS，参数中带着左右边界，返回值为新树的根节点，如果当前节点不在当前树的范围内，返回null删除该边
     */
    public void deleteEdge(TreeNode root) {
        if(root == null) return;

        root = dfs(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode dfs(TreeNode root, int left, int right) {
        if(root == null) return null;

        if(root.val <= left || root.val >= right) {
            return null;
        }

        root.left = dfs(root.left, left, root.val);
        root.right = dfs(root.right, root.val, right);

        return root;
    }
}
