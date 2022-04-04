package leetcode;

import common.*;

public class LE_979_Distribute_Coins_In_Binary_Tree {
    /**
     * You are given the root of a binary tree with n nodes where each node in the tree has node.val coins. There are n coins in total throughout the whole tree.
     * In one move, we may choose two adjacent nodes and move one coin from one node to another. A move may be from parent to child, or from child to parent.
     * Return the minimum number of moves required to make every node have exactly one coin.
     *
     * Example 1:
     * Input: root = [3,0,0]
     * Output: 2
     * Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.
     *
     * Example 2:
     * Input: root = [0,3,0]
     * Output: 3
     * Explanation: From the left child of the root, we move two coins to the root [taking two moves]. Then, we move one
     * coin from the root of the tree to the right child.
     *
     * Constraints:
     * The number of nodes in the tree is n.
     * 1 <= n <= 100
     * 0 <= Node.val <= n
     * The sum of all Node.val is n.
     *
     * Medium
     *
     * https://leetcode.com/problems/distribute-coins-in-binary-tree/
     */

    /**
     * A variation of LE_124_Binary_Tree_Max_Path_Sum and LE_687_Longest_Univalue_Path
     *
     * Tree problem, again, the correct solution is going with recursion. But the real tricky and difficult part is that
     * you first think that you need to go both down and up to move coins. That could lead you to track path up and down
     * , which is an absolute wrong approach.
     *
     * First observation, total number of nodes n is not given, which indicates it is not needed. There are n nodes, so
     * in the end, each node should have 1 coin.
     *
     * Second, how to recurse? This is the kind of tree recursion problem that the final answer and the return value of
     * the recursive function are different, similar to the problem LE_124_Binary_Tree_Max_Path_Sum. It looks as if it is
     * about paths, actually it is just about an accumulated number.
     *
     * We traverse children first (post-order traversal!!!), and return the balance of coins. For example, if we get '+3'
     * from the left child, that means that the left subtree has 3 EXTRA coins to move OUT. If we get '-1' from the right
     * child, we need to move 1 coin IN. So, we increase the number of moves by 4 (3 moves out left + 1 moves in right).
     * We then return the final balance: r->val (coins in the root) + 3 (left) + (-1) (right) - 1 (keep one coin for the root).
     *
     * https://leetcode.com/problems/distribute-coins-in-binary-tree/discuss/221939/C%2B%2B-with-picture-post-order-traversal
     *
     * From a local view, for a given node root, with the number of coins in root, we can't know the moves, since the extra
     * coins (if there's any) can go up or down. So we need to first look its children, each subtree with its root as left
     * child or right child, say, it has x nodes and y coins, extra coins z = y - x. When z is positive, there are z coins needs
     * to be moved OUT (up) of the subtree, if z is negative, it means z coins should be moved IN (down). Therefore, regardless
     * the number of nodes in the subtree, ABS(z) is the number of moves we want to get. For example:
     *
     *   say we have a subtree that only has left children, each node has 1 coin, except the leave node has 3. How many
     *   moves do we pass:
     *                      1 0
     *                    /   \
     *                  1  2   0 -2
     *                 /        \
     *               1  2        0 -1
     *              /
     *            1  2
     *           /
     *         1  2
     *        /
     *       3 2
     *
     *  Time  : O(n)
     *  Space : O(h), h is height of the tree
     */
    class Solution {
        int res = 0;

        public int distributeCoins(TreeNode root) {
            if (root == null) return 0;

            helper(root);
            return res;
        }

        public int helper(TreeNode root) {
            if (root == null) return 0;

            int left = helper(root.left);
            int right = helper(root.right);

            res += Math.abs(left) + Math.abs(right);

            /**
             * !!!
             * Return balance of coins from root down.
             */
            return root.val + left + right - 1;
        }
    }
}
