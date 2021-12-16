package leetcode;

import java.util.*;

public class LE_1697_Checking_Existence_Of_Edge_Length_Limited_Paths {
    /**
     * An undirected graph of n nodes is defined by edgeList, where edgeList[i] = [ui, vi, disi] denotes an edge between
     * nodes ui and vi with distance disi. Note that there may be multiple edges between two nodes.
     *
     * Given an array queries, where queries[j] = [pj, qj, limitj], your task is to determine for each queries[j] whether
     * there is a path between pj and qj such that each edge on the path has a distance strictly less than limitj .
     *
     * Return a boolean array answer, where answer.length == queries.length and the jth value of answer is true if there
     * is a path for queries[j] is true, and false otherwise.
     *
     * Example 1:
     * Input: n = 3, edgeList = [[0,1,2],[1,2,4],[2,0,8],[1,0,16]], queries = [[0,1,2],[0,2,5]]
     * Output: [false,true]
     * Explanation: The above figure shows the given graph. Note that there are two overlapping edges between 0 and 1 with distances 2 and 16.
     * For the first query, between 0 and 1 there is no path where each distance is less than 2, thus we return false for this query.
     * For the second query, there is a path (0 -> 1 -> 2) of two edges with distances less than 5, thus we return true for this query.
     *
     * Example 2:
     * Input: n = 5, edgeList = [[0,1,10],[1,2,5],[2,3,9],[3,4,13]], queries = [[0,4,14],[1,4,13]]
     * Output: [true,false]
     * Exaplanation: The above figure shows the given graph.
     *
     * Constraints:
     * 2 <= n <= 105
     * 1 <= edgeList.length, queries.length <= 105
     * edgeList[i].length == 3
     * queries[j].length == 3
     * 0 <= ui, vi, pj, qj <= n - 1
     * ui != vi
     * pj != qj
     * 1 <= disi, limitj <= 109
     * There may be multiple edges between two nodes.
     *
     * Hard
     *
     * https://leetcode.com/problems/checking-existence-of-edge-length-limited-paths/
     */

    /**
     * Sorting + UnionFind
     *
     * Observation
     *
     * The key here is to notice that the queries are offline which means that we can reorganize them however we want.
     *
     * "each edge on the path has a distance strictly less than limit":
     * Now to answer the question, whether there is a path between any two nodes where the maximum edge length or weight
     * is less than limit, we can join all the edges whose weight is less than limit and if we are still not able to reach
     * one node from the other it essentially means that there is no path between them where edge weight is less than limit.
     *
     * In other words, for a given limit in a query, we sort the edge by length, add them into UnionFind if the length smaller than
     * limit (add all possible edge candidates to UnionFind). Then check the two nodes in the query, if their parents are
     * not the same, it means they are not corrected. So answer is false, otherwise answer is true.
     *
     * So we need to sort both queries and edges by length (limit), then we can seek answer from the small to large.
     *
     * Time: O(ElogE + QlogQ).
     *       Where E is the number of edges in edgeList and Q is the number of queries. This comes from sorting both inputs.
     * Space: O(n). Where n is the number of nodes.
     */
    class Solution {
        public class UnionFind {
            int[] parents;
            int[] ranks;

            public UnionFind(int n) {
                parents = new int[n];
                ranks = new int[n];

                for (int i = 0; i < n; i++) {
                    parents[i] = i;
                    ranks[i] = 1;
                }
            }

            public void union(int u, int v) {
                int pu = find(u);
                int pv = find(v);

                if (pu == pv) return;

                /**
                 * For this problem is seems we have to use path compression, otherwise it will TLE.
                 */
                if (ranks[pu] > ranks[pv]) {
                    parents[pv] = pu;
                } else if (ranks[pv] > ranks[pu]) {
                    parents[pu] = pv;
                } else {
                    parents[pv] = pu;
                    ranks[pu]++;
                }
            }

            public int find(int x) {
                return parents[x] == x ? x : find(parents[x]);
            }
        }

        public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
            int q_len = queries.length;

            /**
             * We want to sort by length for edgeList and by limit for queries. But for queries, after it is sorted,
             * we still need to know its original index (think it as query ID) so that we can put answer into the result
             * array. Therefore we need to create a new array orderedQ which has one more element to keep its index.
             */
            int[][] orderedQ = new int[q_len][4];
            for (int i = 0; i < q_len; i++) {
                int[] q = queries[i];
                orderedQ[i] = new int[] {q[0], q[1], q[2], i};
            }

            Arrays.sort(orderedQ, (a, b) -> Integer.compare(a[2], b[2]));
            Arrays.sort(edgeList, (a, b) -> Integer.compare(a[2], b[2]));

            /**
             * This is where param "n" is used (number of nodes): init UnionFindSet
             */
            UnionFind uf = new UnionFind(n);
            boolean[] res = new boolean[q_len];

            /**
             * Tricky part:
             * i -> idx for orderedQ
             * j -> idx for edgeList
             *
             * i keeps increasing as part of the outter for loop.
             * j only increases in inner while loop
             *
             * Don't confuse those tow idx and apply it to the correct array.
             */
            for (int i = 0, j = 0; i < q_len; i++) {
                int limit = orderedQ[i][2];

                while (j < edgeList.length && edgeList[j][2] < limit) {
                    uf.union(edgeList[j][0], edgeList[j][1]);
                    j++;
                }

                if (uf.find(orderedQ[i][0]) == uf.find(orderedQ[i][1])) {
                    /**
                     * !!!
                     * This is why we create orderedQ array from queries array, the index of orderedQ is not the original
                     * index in queries, therefore we can't use it to fill in our answer. We must retrieve it from the 4th
                     * element of orderedQ[i]
                     */
                    int queryIndex = orderedQ[i][3];
                    res[queryIndex] = true;
                }
            }

            return res;
        }
    }
}
