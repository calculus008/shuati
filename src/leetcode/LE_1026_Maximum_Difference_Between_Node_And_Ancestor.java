package leetcode;

import common.*;

public class LE_1026_Maximum_Difference_Between_Node_And_Ancestor {
    /**
     * Given the root of a binary tree, find the maximum value v for which there exist different nodes a and b
     * where v = |a.val - b.val| and a is an ancestor of b.
     *
     * A node a is an ancestor of b if either: any child of a is equal to b or any child of a is an ancestor of b.
     *
     * Example 1:
     * Input: root = [8,3,10,1,6,null,14,null,null,4,7,13]
     * Output: 7
     * Explanation: We have various ancestor-node differences, some of which are given below :
     * |8 - 3| = 5
     * |3 - 7| = 4
     * |8 - 1| = 7
     * |10 - 13| = 3
     * Among all possible differences, the maximum value of 7 is obtained by |8 - 1| = 7.
     *
     * Example 2:
     * Input: root = [1,null,2,null,0,3]
     * Output: 3
     *
     * Constraints:
     * The number of nodes in the tree is in the range [2, 5000].
     * 0 <= Node.val <= 105
     *
     * Medium
     *
     * https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
     */

    /**
     * Pre-order DFS, for each node, check what is max and min values so far, calculate diff between current max and min,
     * if it is the max so far, keep it. After DFS is done, the diff is the answer.
     *
     * !!!
     * The key is that we pass the current max and min as parameters to recursion method, it guarantees the max and min
     * will be passed from above, hence they have ancestor-to-child relation.
     */
    class Solution {
        int res = 0;

        public int maxAncestorDiff(TreeNode root) {
            if (root == null) return 0;

            dfs(root, root.val, root.val);

            return res;
        }

        private void dfs(TreeNode root, int min, int max) {
            if (root == null) return;

            min = Math.min(root.val, min);
            max = Math.max(root.val, max);

            if (max - min > res) res = max - min;

            dfs(root.left, min, max);
            dfs(root.right, min, max);
        }
    }
}
