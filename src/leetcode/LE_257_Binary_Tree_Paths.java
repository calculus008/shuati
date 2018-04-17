package leetcode;

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
}
