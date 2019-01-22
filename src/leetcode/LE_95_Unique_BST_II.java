package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/9/18.
 */
public class  LE_95_Unique_BST_II {
    /**
     * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
     *
     * Example:
     *
     * Input: 3
     * Output:
     * [
     *   [1,null,3,2],
     *   [3,2,null,1],
     *   [3,1,null,null,2],
     *   [2,1,3],
     *   [1,null,2,null,3]
     * ]
     * Explanation:
     * The above output corresponds to the 5 unique BST's shown below:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
     */

    public static List<TreeNode> generateTrees(int n) {
        List<TreeNode> res = new ArrayList<>();
        if (n == 0) return res;

        return helper(1, n);
    }

    /**
     * start and end are used to ensure BST property
     */
    public static List<TreeNode> helper(int start, int end) {
        List<TreeNode> res = new ArrayList<>();
        if(start > end) {
            res.add(null);
            return res;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> leftList = helper(start, i - 1);
            List<TreeNode> rightList = helper(i + 1, end);
            for(TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }
        return res;
    }
}
