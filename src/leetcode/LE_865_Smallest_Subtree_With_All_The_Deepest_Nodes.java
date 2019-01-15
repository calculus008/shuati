package leetcode;

import common.TreeNode;

public class LE_865_Smallest_Subtree_With_All_The_Deepest_Nodes {
    /**
         Given a binary tree rooted at root, the depth of each node is the shortest distance to the root.
         A node is deepest if it has the largest depth possible among any node in the entire tree.
         The subtree of a node is that node, plus the set of all descendants of that node.
         Return the node with the largest depth such that it contains all the deepest nodes in its subtree.

         Example 1:

         Input: [3,5,1,6,2,0,8,null,null,7,4]
         Output: [2,7,4]
         Explanation:

         We return the node with value 2, colored in yellow in the diagram.
         The nodes colored in blue are the deepest nodes of the tree.
         The input "[3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]" is a serialization of the given tree.
         The output "[2, 7, 4]" is a serialization of the subtree rooted at the node with value 2.
         Both the input and output have TreeNode type.

         Note:
         The number of nodes in the tree will be between 1 and 500.
         The values of each node are unique.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-865-smallest-subtree-with-all-the-deepest-nodes/
     *
     * Use subtree solution to construct final solution.
     * Each leaf node is a solution with depth as 0.
     * For each subtree, look local result, we need two important info : current depth and current solution node.
     *
     * Time and Space : O(n)
     **/
    class Solution {
        class Pair {
            int depth;
            TreeNode node;

            public Pair(int depth, TreeNode node) {
                this.depth = depth;
                this.node = node;
            }
        }

        public TreeNode subtreeWithAllDeepest(TreeNode root) {
            if (root == null) {
                return null;
            }

            return helper(root).node;
        }

        private Pair helper(TreeNode root) {
            if (root == null) {
                /**
                 * "new Pair(-1, null)"
                 */
                return new Pair(-1, null);
            }

            if (root.left == null && root.right == null) {
                /**
                 * "new Pair(1, root)"
                 */
                return new Pair(1, root);
            }

            Pair pl = helper(root.left);
            Pair pr = helper(root.right);

            if (pl.depth == pr.depth) {
                return new Pair(pl.depth + 1, root);
            } else if (pl.depth > pr.depth) {
                return new Pair(pl.depth + 1, pl.node);
            } else {
                return new Pair(pr.depth + 1, pr.node);
            }
        }
    }
}