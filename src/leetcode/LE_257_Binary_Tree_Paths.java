package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_257_Binary_Tree_Paths {
    /**
         Given a binary tree, return all root-to-leaf paths.

         For example, given the following binary tree:

         1
         /   \
         2     3
         \
         5
         All root-to-leaf paths are:

         ["1->2->5", "1->3"]
     */

    /**
     * Time and Space : O(n)
     */

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;
        helper(root, res, "");
        return res;
    }

    public void helper(TreeNode root, List<String> res, String path) {
        if (root.left == null && root.right == null) {
            res.add(path + root.val);
            return;
        }

        if(root.left != null) {
            helper(root.left, res, path + root.val + "->");
        }

        if(root.right != null) {
            helper(root.right, res, path + root.val + "->");
        }
    }

    /**
     * StringBuilder version, the trick is :
     * 1.Remember length of StringBuilder before recursion starts.
     * 2.Use "setLength" to recover to original state after EACH recursion of the next level returns.
     */
    public List<String> binaryTreePaths_2(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;

        helper(root, res, new StringBuilder());
        return res;
    }

    public void helper(TreeNode root, List<String> res, StringBuilder sb) {
        /**
         * 1.Base case : check if right and left children are both null
         */
        if (root.right == null && root.left == null) {
            res.add(sb.append(root.val).toString());
            return;
        }

        /**
         * 3.Record stringbuilder length before next level recursion
         */
        int len = sb.length();

        /**
         * 2.Add check null for left and right children before recursion :
         *   a.avoid null pointer exception in the next level recursion;
         *   b."sb.append(root.val).append("->")", if child is empty, this line won't execute,
         *      so we don't need to worry about path ending like "1->2->3->"
         */
        if (root.left != null) {
            helper(root.left, res, sb.append(root.val).append("->"));
        }

        /**
         * 3.Recover stringbuilder value after each recursion
         */
        sb.setLength(len);

        if (root.right != null) {
            helper(root.right, res, sb.append(root.val).append("->"));
        }

        /**
         * 3.Recover stringbuilder value after each recursion
         */
        sb.setLength(len);
    }
}
