package leetcode;

public class LE_1483_Kth_Ancestor_Of_A_Tree_Node {
    /**
     * You are given a tree with n nodes numbered from 0 to n - 1 in the form of a parent array parent where parent[i]
     * is the parent of ith node. The root of the tree is node 0. Find the kth ancestor of a given node.
     *
     * The kth ancestor of a tree node is the kth node in the path from that node to the root node.
     *
     * Implement the TreeAncestor class:
     * TreeAncestor(int n, int[] parent) Initializes the object with the number of nodes in the tree and the parent array.
     * int getKthAncestor(int node, int k) return the kth ancestor of the given node node. If there is no such ancestor, return -1.
     *
     * Example 1:
     * Input
     * ["TreeAncestor", "getKthAncestor", "getKthAncestor", "getKthAncestor"]
     * [[7, [-1, 0, 0, 1, 1, 2, 2]], [3, 1], [5, 2], [6, 3]]
     * Output
     * [null, 1, 0, -1]
     *
     * Explanation
     * TreeAncestor treeAncestor = new TreeAncestor(7, [-1, 0, 0, 1, 1, 2, 2]);
     * treeAncestor.getKthAncestor(3, 1); // returns 1 which is the parent of 3
     * treeAncestor.getKthAncestor(5, 2); // returns 0 which is the grandparent of 5
     * treeAncestor.getKthAncestor(6, 3); // returns -1 because there is no such ancestor
     *
     *
     * Constraints:
     * 1 <= k <= n <= 5 * 104
     * parent.length == n
     * parent[0] == -1
     * 0 <= parent[i] < n for all 0 < i < n
     * 0 <= node < n
     * There will be at most 5 * 104 queries.
     *
     * Hard
     *
     * https://leetcode.com/problems/kth-ancestor-of-a-tree-node/
     */

    /**
     * Binary Lifting
     * https://zxi.mytechroad.com/blog/tree/leetcode-1483-kth-ancestor-of-a-tree-node/
     *
     * Core idea: For each node, precompute its ancestor at (2^0, 2^1, 2^(height of tree)) distance. Then, to find out
     * nth ancestor, we can make large jumps in order of 2 and compute its ancestor in log(k).
     *
     * Bonus: This technique is also used to find the LCA of any two nodes in a tree quickly. Read more here:
     * https://cp-algorithms.com/graph/lca_binary_lifting.html
     *
     * DP
     * dp[i][j] : node j's 2 ^ i -th ancestor
     * dp[i][j] = dp[i - 1][dp[i - 1][j]] => A(j, 2 ^ i) = A(A(j, 2 ^ (i - 1)), 2 ^ (i - 1))
     *
     * Time  : O(logn)
     * Space : O(logn)
     */
    class TreeAncestor {
        int[][] dp;
        int maxLevel;

        public TreeAncestor(int n, int[] parent) {
            /**
             *  log_base_2(n) = log_base_10(n) / log_base_10(2)
             */
            maxLevel = (int) (Math.log(n) / Math.log(2)) + 1;
            dp = new int[maxLevel][n];
            /**
             * !!!
             */
            dp[0] = parent;

            for (int i = 1; i < maxLevel; i++) {
                for (int j = 0; j < n; j++) {
                    int pre = dp[i - 1][j];
                    dp[i][j] = pre == -1 ? -1 : dp[i - 1][pre];
                }
            }
        }

        /**
         * start from the right most position and move to left
         */
        public int getKthAncestor(int node, int k) {
            for (int i = 0; i < this.maxLevel && node != -1; i++) {
                if ((k & (1 << i)) != 0) {
                    node = dp[i][node];
                }
            }
            return node;
        }
    }

    /**
     * Brutal Force
     *
     * Since There will be at most 5*10^4 queries for method getKthAncestor,
     * Let's set queries called time as N,
     * It means, the brute force's complexity will become O(NK)......-> TLE!
     */
    class TreeAncestor2 {
        int[] p;

        public TreeAncestor2(int n, int[] parent) {
            p = parent;
        }

        public int getKthAncestor(int node, int k) {
            if (k == 0 || k >= p.length) return -1;

            int count = 1;
            int cur = node;
            int parent = p[node];

            while (count < k && parent != -1) {
                cur = parent;
                parent = p[cur];
                count++;
            }

            return count != k ? -1 : parent;
        }
    }
}
