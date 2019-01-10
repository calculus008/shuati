package lintcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 7/26/18.
 */
public class LI_246_Binary_Tree_Path_Sum_II {
    /**
         Your are given a binary tree in which each node contains a value.
         Design an algorithm to get all paths which sum to a given value.
         The path does not need to start or end at the root or a leaf,
         but it must go in a straight line down.

         Example
         Given a binary tree:

             1
            / \
           2   3
          /   /
         4   2
         for target = 6, return

         [
         [2, 4],
         [1, 3, 2]
         ]

         Medium
     */

    /**
     * 这道题最容易犯的错误就是，从每个节点新开始一个搜索，这样会在树的末端大量重复，
     * 而且这种重复没法用Set消除，因为可能存在多条路径整数序列一样。
     *
     * Preorder, 以当前node为终点，往回看，看是否存在要求的路径。
     */
    public List<List<Integer>> binaryTreePathSum2(TreeNode root, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        helper(root, target, res, new ArrayList<>());
        return res;
    }

    public void helper(TreeNode root, int target, List<List<Integer>> res, List<Integer> path) {
        if (root == null) {
            return;
        }

        path.add(root.val);
        int sum = 0;

        for (int i = path.size() - 1; i >= 0; i--) {
            sum += path.get(i);
            if (sum == target) {
                //new ArrayList<Integer>()
                res.add(new ArrayList<Integer>(path.subList(i, path.size())));
            }
        }

        helper(root.left, target, res, path);
        helper(root.right, target, res, path);

        path.remove(path.size() - 1);
    }
}
