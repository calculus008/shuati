package leetcode;

import java.util.ArrayList;
import java.util.HashSet;

public class LE_834_Sum_Of_Distances_In_Tree {
    /**
     * An undirected, connected tree with n nodes labelled 0...n-1 and n-1 edges are given.
     *
     * The ith edge connects nodes edges[i][0] and edges[i][1] together.
     *
     * Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.
     *
     * Example 1:
     *
     * Input: n = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
     * Output: [8,12,6,10,10,10]
     * Explanation:
     * Here is a diagram of the given tree:
     *   0
     *  / \
     * 1   2
     *    /|\
     *   3 4 5
     * We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
     * equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
     *
     * Note: 1 <= n <= 10000
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/sum-of-distances-in-tree/discuss/130583/C%2B%2BJavaPython-Pre-order-and-Post-order-DFS-O(N)
     *
     * Intuition
     * What if given a tree, with a certain root 0?
     * In O(N) we can find sum of distances in tree from root and all other nodes.
     * Now for all N nodes?
     * Of course, we can do it N times and solve it in O(N^2).
     * C++ and Java may get accepted luckily, but it's not what we want.
     *
     * When we move our root from one node to its connected node,
     * one part of nodes get closer, one the other part get further.
     *
     * If we know exactly how many nodes in both parts, we can solve this problem.
     *
     * With one single traversal in tree, we should get enough information for it and
     * don't need to do it again and again.
     *
     * Explanation
     * Let's solve it with node 0 as root.
     *
     * Initial an array of hashset tree, tree[i] contains all connected nodes to i.
     * Initial an array count, count[i] counts all nodes in the subtree i.
     * Initial an array of res, res[i] counts sum of distance in subtree i.
     *
     * Post order dfs traversal, update count and res:
     * count[root] = sum(count[i]) + 1
     * res[root] = sum(res[i]) + sum(count[i])
     *
     * Pre order dfs traversal, update res:
     * When we move our root from parent to its child i, count[i] points get 1 closer to root, n - count[i] nodes get 1 futhur to root.
     * res[i] = res[root] - count[i] + N - count[i]
     *
     * return res, done.
     *
     *
     * Time Complexity:
     * dfs: O(N) time
     * dfs2: O(N) time
     */
    class Solution {
        ArrayList<HashSet<Integer>> tree;
        int[] count;
        int[] res;

        public int[] sumOfDistancesInTree(int n, int[][] edges) {
            res = new int[n];
            count = new int[n];

            tree = new ArrayList<HashSet<Integer>>();
            for (int i = 0; i < n ; ++i) {
                tree.add(new HashSet<Integer>());
            }
            for (int[] e : edges) {
                tree.get(e[0]).add(e[1]);
                tree.get(e[1]).add(e[0]);
            }

            dfs(0, -1);

            dfs2(0, -1);

            return res;
        }

        /**
         * postorder DFS
         */
        private void dfs(int root, int pre) {
            for (int i : tree.get(root)) {
                if (i == pre) continue;

                dfs(i, root);
                count[root] += count[i];
                res[root] += res[i] + count[i];
            }
            count[root]++;
        }


        /**
         * preorder DFS
         */
        private void dfs2(int root, int pre) {
            for (int i : tree.get(root)) {
                if (i == pre) continue;

                res[i] = res[root] - count[i] + count.length - count[i];
                dfs2(i, root);
            }
        }
    }
}
