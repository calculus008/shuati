package leetcode;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_958_Check_Completeness_Of_A_Binary_Tree {
    /**
     * Given a binary tree, determine if it is a complete binary tree.
     *
     * Definition of a complete binary tree from Wikipedia:
     * In a complete binary tree every level, except possibly the last, is completely filled,
     * and all nodes in the last level are as far left as possible. It can have between 1 and
     * 2h nodes inclusive at the last level h.
     *
     * Medium
     */

    /**
     * BFS
     *
     * Use BFS to do a level order traversal,
     * add children to the bfs queue,
     * until we met the first empty node.
     *
     * For a complete binary tree,
     * there should not be any node after we met an empty one.
     */
    class Solution_BFS_1 {
        public boolean isCompleteTree(TreeNode root) {
            if (root == null) return false;

            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while (q.peek() != null) {
                TreeNode cur = q.poll();
                q.offer(cur.left);
                q.offer(cur.right);
            }

            while (!q.isEmpty() && q.peek() == null) {
                q.poll();
            }

            return q.isEmpty();
        }
    }

    /**
     * This BFS solution ends the loop as early as possible
     */
    class Solution_BFS_2 {
        public boolean isCompleteTree(TreeNode root) {
            if (root == null) {
                return true;
            }

            boolean nullNodeFound = false;
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                TreeNode node = q.poll();

                if (node == null) {
                    nullNodeFound = true;
                } else {
                    if (nullNodeFound) {
                        return false;
                    }
                    q.offer(node.left);
                    q.offer(node.right);
                }
            }
            return true;
        }
    }

    /**
     *              0
     *            /  \
     *           1    2
     *          / \  / \
     *         3  4 5   6
     *        / \  \
     *       7  8   9
     *
     *  For a complete tree, if a node idx is i, its left child should be 2 * i + 1, right is 2 * i + 2
     *
     *  For the given example above, when we come to node 4, go to its right child:
     *  node is not null, i is 2 * 4 + 2 = 10, 10 > 9, so it returns false;
     *
     *  The drawback is that it needs to traverse the tree twice (1st one to get total number of nodes)
     */
    class Solution_DFS_1 {
        public boolean isCompleteTree(TreeNode root) {
            int total = countNodes(root);
            return isCompleteTree(root,0, total);
        }

        private boolean isCompleteTree(TreeNode root, int i, int n) {
            if (root == null)  {
                return true;
            } else if (i >= n) {
                return false;
            }

            return isCompleteTree(root.left, 2 * i + 1, n) && isCompleteTree(root.right, 2 * i + 2, n);
        }

        private  int countNodes(TreeNode root) {
            if (root == null) return 0;
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
    }

    /**
     * DFS
     *
     * We traverse the tree and track the current height h. The very
     * first leaf is the leftmost node, so its height sets target_height.
     *
     * For remaining leaves, the height should be the same as target_height.
     * Since the last level may not be filled, our height can become target_height - 1.
     * In that case, we set last_level_filled to 1, and check that the height of
     * remaining leaves equals target_height - last_level_filled.
     */
    class Solution_DFS_2 {
        private int target_height = 0, last_level_filled = 0;

        public boolean isCompleteTree(TreeNode root) {
            return dfs(root, 0);
        }

        /**
         * pre-order
         */
        private boolean dfs(TreeNode root, int h) {
            if (root == null) {
                /**
                 * target_height == 0 means this null is the left pointer of the left
                 * most leaf.
                 */
                if (target_height == 0) {
                    target_height = h;
                } else if (h == target_height - 1) {
                    last_level_filled = 1;
                }

                return h == target_height - last_level_filled;
            }

            return dfs(root.left, h + 1) && dfs(root.right, h + 1);
        }
    }
}
