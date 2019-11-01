package src.leetcode;

public class LE_1145_Binary_Tree_Coloring_Game {
    /**
     * Two players play a turn based game on a binary tree.  We are given the root of this binary tree,
     * and the number of nodes n in the tree.  n is odd, and each node has a distinct value from 1 to n.
     *
     * Initially, the first player names a value x with 1 <= x <= n, and the second player names a value
     * y with 1 <= y <= n and y != x.  The first player colors the node with value x red, and the second
     * player colors the node with value y blue.
     *
     * Then, the players take turns starting with the first player.  In each turn, that player chooses
     * a node of their color (red if player 1, blue if player 2) and colors an uncolored neighbor of
     * the chosen node (either the left child, right child, or parent of the chosen node.)
     *
     * If (and only if) a player cannot choose such a node in this way, they must pass their turn.
     * If both players pass their turn, the game ends, and the winner is the player that colored more nodes.
     *
     * You are the second player.  If it is possible to choose such a y to ensure you win the game, return true.
     * If it is not possible, return false.
     *
     * Constraints:
     *
     * root is the root of a binary tree with n nodes and distinct node values from 1 to n.
     * n is odd.
     * 1 <= x <= n <= 100
     *
     * Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/tree/leetcode-1145-binary-tree-coloring-game/
     */
    class Solution {
        int red_left;
        int red_right;

        public boolean btreeGameWinningMove(common.TreeNode root, int n, int x) {
            count(root, x);

            /**
             * paint the parent
             */
            if (1 + red_left + red_right <= n / 2) return true;

            /**
             * paint one of the children
             */
            if (Math.max(red_left, red_right) > n / 2) return true;

            return false;
        }

        private int count(common.TreeNode root, int x) {
            if (root == null) return 0;

            int l = count(root.left, x);
            int r = count(root.right, x);

            if (root.val == x) {
                red_left = l;
                red_right = r;
            }

            return l + r + 1;
        }
    }
}

