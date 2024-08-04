package leetcode;

import common.*;

import java.util.*;

public class LE_1302_Deepest_Leaves_Sum {
    /**
     * Given the root of a binary tree, return the sum of values of its deepest leaves.
     *
     * Example 1:
     * Input: root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
     * Output: 15
     *
     *  Example 2:
     * Input: root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
     * Output: 19
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 104].
     * 1 <= Node.val <= 100
     * Accepted
     * 232,083
     * Submissions
     *
     * Medium
     *
     * https://leetcode.com/problems/deepest-leaves-sum/
     */

    /**
     * 思路：
     *
     * Tree problem -> traversal, DFS, pre-order, in-order etc.
     * If we get the depth first, then try to get sum of the deepest level, need to do traverse the tree twice, not
     * efficient, so we want to find solution with just one pass. With one pass, we don't know that is the deepest level,
     * so natrual way is to calculate sums of all levels, so we need dynamically add values, hence use a list.
     *
     * We can also solve it with BFS, but it requires more code, DFS with recursion will be more concise. So pre-order,
     * in-order, post-order, which one to use? All logic happens when processing current node : add an element to list if
     * it does not exist, then add current node value to the element at idx level - 1. So we need to garuantee the element
     * is available when we process current node. Therefore, we will do pre-order, so whenever we go to a level, we make
     * sure we add element in list for that level.
     *
     * 68%
     */
    class Solution1 {
        List<Integer> sums = new ArrayList<>();

        public int deepestLeavesSum(TreeNode root) {
            if (root == null) return 0;

            dfs(root, 1);

            return sums.get(sums.size() - 1);
        }

        private void dfs(TreeNode root, int level) {
            if (root == null) return;

            if (sums.size() < level) {
                sums.add(0);
            }

            int idx = level - 1;
            sums.set(idx, sums.get(idx) + root.val);

            dfs(root.left, level + 1);
            dfs(root.right, level + 1);
        }
    }

    /**
     * Optimized from Solution1
     *
     * We remember what is the current max level, if current level is bigger than max level, we need to init a new calculation.
     * Otherwise, only add current node value to sum when it is at the max level.
     *
     * This way, we don't need to keep a list of sums for all levels and save the time and space for list operation, much
     * faster than Solution1. (100%)
     */
    class Solution2 {
        private int maxLevel = 0;
        private int sum = 0;

        public int deepestLeavesSum(TreeNode root) {
            if (root == null) return 0;
            dfs(root, 0);
            return sum;
        }

        private void dfs(TreeNode root, int level) {
            if (root == null) return;

            if (level > maxLevel) {
                sum = 0;
                maxLevel = level;
            }

            if (level == maxLevel) {
                sum = sum + root.val;
            }
            dfs(root.left, level + 1);
            dfs(root.right, level + 1);
        }
    }

    /**
     * BFS solution for reference
     * 66%
     */
    class Solution3 {
        public int deepestLeavesSum(TreeNode root) {
            int res = 0;

            Queue<TreeNode> q = new LinkedList<TreeNode>();
            q.add(root);

            while (!q.isEmpty()) {
                int size = q.size();
                res = 0;

                for (int i = 0; i < size; i++) {
                    TreeNode node = q.poll();
                    res += node.val;

                    if (node.right != null) q.add(node.right);
                    if (node.left  != null) q.add(node.left);
                }
            }
            return res;
        }
    }

    /**
     * Another form of BFS, save the nodes in the deepest level at the end of BFS, then sum them up, so no need
     * to do sum for every node.
     */
    class Solution4 {
        public int deepestLeavesSum(TreeNode root) {
            ArrayDeque<TreeNode>  nextLevel = new ArrayDeque(),
                    currLevel = new ArrayDeque();
            nextLevel.offer(root);

            while (!nextLevel.isEmpty()) {
                // prepare for the next level
                currLevel = nextLevel.clone();
                nextLevel.clear();

                for (TreeNode node: currLevel) {
                    // add child nodes of the current level
                    // in the queue for the next level
                    if (node.left != null) {
                        nextLevel.offer(node.left);
                    }
                    if (node.right != null) {
                        nextLevel.offer(node.right);
                    }
                }
            }
            int deepestSum = 0;
            for (TreeNode node: currLevel) {
                deepestSum += node.val;
            }
            return deepestSum;
        }
    }
}
