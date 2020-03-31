package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LE_1161_Maximum_Level_Sum_Of_A_Binary_Tree {
    /**
     * Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.
     *
     * Return the smallest level X such that the sum of all the values of nodes at level X is maximal.
     *
     * Medium
     */

    class Solution_BFS {
        public int maxLevelSum(TreeNode root) {
            if (root == null) return -1;

            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            int res = 0;
            int level = 0;
            int max = Integer.MIN_VALUE;

            while (!q.isEmpty()) {
                level++;
                int size = q.size();

                int sum = 0;
                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();
                    sum += cur.val;

                    if (cur.left != null) q.offer(cur.left);
                    if (cur.right != null) q.offer(cur.right);
                }

                if (sum > max) {
                    max = sum;
                    res = level;
                }
            }

            return res;
        }
    }

    class Solution_DFS {
        public int maxLevelSum(TreeNode root) {
            if (root == null) return -1;

            List<Integer> list = new ArrayList<>();
            dfs(root, 0, list);

            int max = Integer.MIN_VALUE;
            int res = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > max) {
                    max = list.get(i);
                    res = i + 1;
                }
            }

            return res;
        }

        private void dfs(TreeNode root, int level, List<Integer> list) {
            if (root == null) return;

            if (list.size() == level) {
                list.add(root.val);
            } else {
                list.set(level, list.get(level) + root.val);
            }

            dfs(root.left, level + 1, list);
            dfs(root.right, level + 1, list);
        }
    }
}
