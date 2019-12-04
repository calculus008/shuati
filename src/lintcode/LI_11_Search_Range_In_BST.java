package lintcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LI_11_Search_Range_In_BST {
    /**
     * Given a binary search tree and a range [k1, k2], return node values within a given range in ascending order.
     */

    public class Solution {
        public List<Integer> searchRange(TreeNode root, int k1, int k2) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;

            helper(root, k1, k2, res);
            return res;
        }

        private void helper(TreeNode root, int k1, int k2, List<Integer> res) {
            if (root == null) return;

            helper(root.left, k1, k2, res);

            if (root.val >= k1 && root.val <= k2) {
                res.add(root.val);
            }

            helper(root.right, k1, k2, res);
        }
    }
}
