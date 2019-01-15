package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_113_Path_Sum_II {
    /**
        Given a binary tree and a sum, find all root-to-leaf paths
        where each path's sum equals the given sum.

        For example:
        Given the below binary tree and sum = 22,
                      5
                     / \
                    4   8
                   /   / \
                  11  13  4
                 /  \    / \
                7    2  5   1
        return
        [
           [5,4,11,2],
           [5,8,4,5]
        ]
     */

    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        helper(root, res, new ArrayList<>(), sum);
        return res;
    }

    public static void helper(TreeNode root, List<List<Integer>> res, List<Integer> temp, int sum) {
        if (root == null) return;

        temp.add(root.val);
        /**
         * for this version of DFS, this is not the base case - it won't return
         * the only return is when we hit null. The action below is part of backtracking
         */
        if (root.left == null && root.right == null) {
            if (sum == root.val) {
                res.add(new ArrayList<>(temp));
            }
            /**!!!
             *  Don't return here , if return, the "temp.remove(temp.size() - 1)" will not get executed and sequence is wrong
             **/
            // return;
        }

        helper(root.left, res, temp, sum - root.val);
        helper(root.right, res, temp, sum - root.val);
        temp.remove(temp.size() - 1);
    }

    /**
     * Another version of helper DFS method
     */
    public void helper2(TreeNode root, List<List<Integer>> res, List<Integer> temp, int sum) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            if (sum == root.val) {
                //temp.add(root.val);

                res.add(new ArrayList<>(temp));
                /**
                 * add the last element (leaf node) to valid path after it is added
                 * to res so that it won't impact the backtracking logic after return
                 */
                res.get(res.size() - 1).add(root.val);

                //temp.remove(temp.size() - 1);
            }

            /**
             * we can return here.
             */
            return;
        }

        temp.add(root.val);
        helper2(root.left, res, temp, sum - root.val);
        helper2(root.right, res, temp, sum - root.val);
        temp.remove(temp.size() - 1);
    }
}
