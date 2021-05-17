package leetcode;

import common.TreeNode;

import java.util.PriorityQueue;

public class LE_1120_Maximum_Average_Subtree {
    /**
     * Given the root of a binary tree, find the maximum average value of any subtree of that tree.
     *
     * (A subtree of a tree is any node of that tree plus all its descendants. The average value
     * of a tree is the sum of its values, divided by the number of nodes.)
     *
     * Note:
     *
     * The number of nodes in the tree is between 1 and 5000.
     * Each node will have a value between 0 and 100000.
     * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
     *
     * Medium
     */

    /**
     * Post order
     *
     * dfs() returns Pair which is the sum and count of the current subtree.
     * At the same time, main a max value in res.
     *
     * Time : O(n)
     */
    class Solution {
        class Pair {
            int sum;
            int count;

            public Pair(int sum, int count) {
                this.sum = sum;
                this.count = count;
            }
        }

        double res = Double.MIN_VALUE;

        public double maximumAverageSubtree(TreeNode root) {
            if (root == null) return 0.0;

            dfs(root);

            return res;
        }

        private Pair dfs(TreeNode root) {
            if (root == null)  {
                return new Pair(0, 0);
            }

            Pair p1 = dfs(root.left);
            Pair p2 = dfs(root.right);

            int sum = root.val + p1.sum + p2.sum;
            int count = 1 + p1.count + p2.count;

            res = Math.max(res, (double)sum / (double)count);

            return new Pair(sum, count);
        }
    }

    class Solution_Get_Kth {
        /**
         * Follow up: get average for
         */
        PriorityQueue<Double> pq = new PriorityQueue<>();
        int k;

        class Pair {
            int sum;
            int count;

            public Pair(int sum, int count) {
                this.sum = sum;
                this.count = count;
            }
        }

        public double KthAverageSubtree(TreeNode root, int k) {
            if (root == null) return 0.0;
            this.k = k;

            dfs(root);

            return pq.peek();
        }

        private Pair dfs(TreeNode root) {
            if (root == null)  {
                return new Pair(0, 0);
            }

            Pair p1 = dfs(root.left);
            Pair p2 = dfs(root.right);

            int sum = root.val + p1.sum + p2.sum;
            int count = 1 + p1.count + p2.count;

            double average = (double)sum / (double)count;
            pq.offer(average);
            if (pq.size() > k) {
                pq.poll();
            }

            return new Pair(sum, count);
        }
    }
}
