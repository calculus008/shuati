package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_662_Maximum_Width_Of_Binary_Tree {
    /**
     * Given a binary tree, write a function to get the maximum width of the given tree.
     * The width of a tree is the maximum width among all levels. The binary tree has
     * the same structure as a full binary tree, but some nodes are null.
     *
     * The width of one level is defined as the length between the end-nodes (the leftmost
     * and right most non-null nodes in the level, where the null nodes between the end-nodes
     * are also counted into the length calculation.
     *
     * Example 1:
     *
     * Input:
     *
     *            1
     *          /   \
     *         3     2
     *        / \     \
     *       5   3     9
     *
     * Output: 4
     * Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).
     * Example 2:
     *
     * Input:
     *
     *           1
     *          /
     *         3
     *        / \
     *       5   3
     *
     * Output: 2
     * Explanation: The maximum width existing in the third level with the length 2 (5,3).
     * Example 3:
     *
     * Input:
     *
     *           1
     *          / \
     *         3   2
     *        /
     *       5
     *
     * Output: 2
     * Explanation: The maximum width existing in the second level with the length 2 (3,2).
     * Example 4:
     *
     * Input:
     *
     *           1
     *          / \
     *         3   2
     *        /     \
     *       5       9
     *      /         \
     *     6           7
     * Output: 8
     * Explanation:The maximum width existing in the fourth level with the length 8 (6,null,null,null,null,null,null,7).
     *
     * Medium
     */

    /**
     * The main idea in this question is to give each node a position value.
     * If we go down the left neighbor, then position -> position * 2; and
     * if we go down the right neighbor, then position -> position * 2 + 1.
     * This makes it so that when we look at the position values L and R of
     * two nodes with the same depth, the width will be R - L + 1.
     *
     *           1
     *    2            3
     *  4   5        6   7
     * 8 9 10 11  12 13 14 15
     */
    class Solution {
        int res;
        Map<Integer, Integer> map;

        public int widthOfBinaryTree(common.TreeNode root) {
            if (root == null) return 0;

            res = 0;
            map = new HashMap<>();

            helper(root, 0, 0);

            return res;
        }

        private void helper(common.TreeNode root, int level, int pos ) {
            if (root == null) return;

            /**
             * !!!
             * Map :
             * key - level
             * value - pos value of its left most node
             *
             * By natrual of pre order DFS, left most node will
             * appear first for that level, so we only need to
             * add it to map when we first reaches that level
             */
            if (!map.containsKey(level)) {
                map.put(level, pos);
            }

            res = Math.max(res, pos - map.get(level) + 1);

            helper(root.left, level + 1, 2 * pos);
            helper(root.right, level + 1, 2 * pos + 1);
        }
    }
}
