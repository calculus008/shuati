package leetcode;

import common.TreeNode;

import java.util.Stack;

public class LE_1214_Two_Sum_BSTs {
    /**
     * Given two binary search trees, return True if and only if there is a node in the
     * first tree and a node in the second tree whose values sum up to a given integer target.
     *
     * Constraints:
     * Each tree has at most 5000 nodes.
     * -10^9 <= target, node.val <= 10^9
     *
     * Medium
     */

    /**
     * Two Sum Variation
     *
     * Traverse root 1 from smallest value to node to largest.
     * Traverse root 2 from largest value node to smallest.
     * Sum up the corresponding node’s value : If sum == target return true
     * If target > sum,
     * then move to the inorder successor of the current node of root1,
     * else
     * move to the inorder predecessor of the current node of root2.
     *
     * Time  : O(n1 + n2)
     * Space : O(h1 + h2)
     *
     * Compare with official solution : https://leetcode.com/problems/two-sum-bsts/solution/
     * it saves space. Official solution space is O(n1 + n2)
     */
    class Solution {
        public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
            if (root1 == null || root2 == null) return false;

            Stack<TreeNode> s1 = new Stack<>();
            Stack<TreeNode> s2 = new Stack<>();

            TreeNode n1, n2;

            while (true) {
                while (root1 != null) {
                    s1.push(root1);
                    root1 = root1.left;
                }

                while (root2 != null) {
                    s2.push(root2);
                    root2 = root2.right;
                }

                if (s1.isEmpty() || s2.isEmpty()) break;

                n1 = s1.peek();
                n2 = s2.peek();

                if ((n1.val + n2.val) == target) return true;

                if ((n1.val + n2.val) < target) {
                    s1.pop();
                    root1 = n1.right;
                } else if ((n1.val + n2.val) > target) {
                    s2.pop();
                    root2 = n2.left;
                }
            }

            return false;
        }
    }
}
