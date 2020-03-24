package leetcode;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_993_Cousins_In_Binary_Tree {
    /**
     * In a binary tree, the root node is at depth 0, and children of each depth k node are at depth k+1.
     *
     * Two nodes of a binary tree are cousins if they have the same depth, but have different parents.
     *
     * We are given the root of a binary tree with unique values, and the values x and y of two different nodes in the tree.
     *
     * Return true if and only if the nodes corresponding to the values x and y are cousins.
     *
     * Note:
     * The number of nodes in the tree will be between 2 and 100.
     * Each node has a unique integer value from 1 to 100.
     *
     * Easy
     */

    /**
     * BFS
     */
    class Solution1 {
        public boolean isCousins(TreeNode root, int A, int B) {
            if (root == null) return false;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int size = queue.size();

                /**
                 * !!!
                 * x and y must be at the same level, here, instead of calculating value of the depth,
                 * for each level, we set those 2 boolean values and check if x and y exist in current
                 * level.
                 */
                boolean isAexist = false;
                boolean isBexist = false;

                for (int i = 0; i < size; i++) {
                    TreeNode cur = queue.poll();
                    if (cur.val == A) isAexist = true;
                    if (cur.val == B) isBexist = true;

                    /**
                     * !!!
                     * Again, instead of remembering parent node in variables, we simply check if current
                     * node popped from queue has both x and y as children, if yes, just return FALSE
                     */
                    if (cur.left != null && cur.right != null) {
                        if (cur.left.val == A && cur.right.val == B) {
                            return false;
                        }
                        if (cur.left.val == B && cur.right.val == A) {
                            return false;
                        }
                    }

                    if (cur.left != null) {
                        queue.offer(cur.left);
                    }
                    if (cur.right != null) {
                        queue.offer(cur.right);
                    }
                }

                if (isAexist && isBexist)  return true;
            }

            return false;
        }
    }

    /**
     * DFS
     *
     * 1.Since we need to get both the depth and parent info, we either create a new class as return
     *   type, or as we do here, make them the global variables and set them when we find the target.
     * 2.Cousin: x and y are at the same depth, but have different parents.
     */
    class Solution2 {
        Integer xDepth = null;
        Integer yDepth = null;
        TreeNode xParent = null;
        TreeNode yParent = null;

        public boolean isCousins(TreeNode root, int x, int y) {
            /**
             * calling for root, depth is 0 and parent should be NULL(!!!)
             */
            helper(root, x, y, null, 0);
            return xDepth != null && yDepth != null && xDepth == yDepth && xParent != yParent;
        }

        private void helper(TreeNode node, int x, int y, TreeNode parent, int depth) {
            if (node == null) return;

            if (node.val == x) {
                xDepth = depth;
                xParent = parent;
            } else if (node.val == y) {
                yDepth = depth;
                yParent = parent;
            }

            helper(node.left, x, y, node, depth + 1);
            helper(node.right, x, y, node, depth + 1);
        }
    }
}
