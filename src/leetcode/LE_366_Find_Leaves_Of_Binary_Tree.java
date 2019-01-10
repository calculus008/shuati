package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 7/28/18.
 */
public class LE_366_Find_Leaves_Of_Binary_Tree {
    /**
         Given a binary tree, collect a tree's nodes as if you were doing this:
         Collect and remove all leaves, repeat until the tree is empty.

         Example:
         Given binary tree
             1
            / \
           2   3
          / \
         4   5
         Returns [4, 5, 3], [2], [1].

         Explanation:
         1. Removing the leaves [4, 5, 3] would result in this tree:

           1
          /
         2

         2. Now removing the leaf [2] would result in this tree:

         1

         3. Now removing the leaf [1] would result in the empty tree:

         []
         Returns [4, 5, 3], [2], [1].

         Medium
     */

    //Solution 1
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        helper(root, res);
        return res;
    }


    public int helper(TreeNode root, List<List<Integer>> res) {
        if (root == null) return -1;

        int left = helper(root.left, res);
        int right = helper(root.right, res);

        /**
         * 求出每个Node的height就是它在list中相对应的位置
         */
        int level = Math.max(left, right) + 1;
        if (res.size() == level) {
            res.add(new ArrayList<>());
        }

        res.get(level).add(root.val);
        /**
           题意要求是remove, 所以在postorder的最后一步把左右指针设为零
         **/
        root.left = null;
        root.right = null;

        /**
         level returned : 0, 1, 2,...., it is the index in res.
         **/
        return level;
    }

    /**Solution 2
     **/
    public List<List<Integer>> findLeaves2_JiuZhang(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        Map<Integer, List<Integer>> depth = new HashMap<>();
        int max_depth = dfs(root, depth);

        for (int i = 1; i <= max_depth; i++) {
            ans.add(depth.get(i));
        }
        return ans;
    }


    /**
     * This method is to save leaves list of each level in a map
     */
    int dfs(TreeNode cur, Map<Integer, List<Integer>> depth) {
        if (cur == null) {
            return 0;
        }
        int d = Math.max(dfs(cur.left, depth), dfs(cur.right, depth)) + 1;

        depth.putIfAbsent(d, new ArrayList<>());
        depth.get(d).add(cur.val);
        return d;
    }


}
