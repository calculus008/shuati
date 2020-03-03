package src.Interviews.Indeed;

import java.util.*;

public class Root_To_Leave_Min_Cost_DAG {
    /**
     * =================================================
     * follow-up：
     * 改树为DAG，那leaf变成了出度为0的node，同样求实现方法。
     *
     * #1.
     * 如果不是binary tree的结构，而是任意的单向图，问代码还是否work（yes）
     * 有没有优化的地方？（我用hashmap存下每个节点到叶节点的distance，这样再次访问到该节点就不必dfs下去）。
     * 时间复杂度？
     * (优化后是O（V+E））//说的真好
     *
     * https://www.techiedelight.com/topological-sorting-dag/
     *
     * https://leetcode.com/problems/course-schedule/discuss/287866/DFS-on-DAG
     *
     * #2.
     * 其实就是个dag的dp 做拓拓扑排序的时候dp就⾏了，我看⽹上的题解都是dijkstra，但我觉得拓拓扑排序可做⽽而且复 杂度还低
     *
     * https://www.geeksforgeeks.org/shortest-path-for-directed-acyclic-graphs/
     *
     */

    /**
     * DAG Topologic Sort + DP
     *
     * Time : O(E + V)
     * Space : O(V)
     */

    /**
     * Assume we have total n node and each node has an unique nodeId.
     */
    class Edge{
        int nodeId; //表示这个edge的尾巴指向哪里。
        int cost;

        public Edge(int nodeId, int cost) {
            this.nodeId = nodeId;
            this.cost = cost;
        }
    }

    class Node3 {
        int nodeId;
        List<Edge> edges; //表示从这个头出发的所有edge

        public Node3(int nodeId){
            this.nodeId = nodeId;
            this.edges = new ArrayList<>();
        }
    }

    /**
     * Suppose input is a graph as List, the index of the graph is nodeId and
     * the list<Edge> is the out-going edge of the node.
     *
     * !!!
     * Key insights:
     * For topological sort, we start from nodes that have 0 indegree (meaning nodes
     * with no dependence). Here, we do it reversely, we start from leave nodes, which
     * has zero out-degree.
     */
    public List<Integer> getMinCostPath(List<List<Edge>> graph) {
        int size = graph.size();

        /**
         * Since we start from leave nodes, we need to reverse the graph.
         */
        List<List<Edge>> rev = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Edge> l = new ArrayList<>();
            rev.add(l);
        }

        /**
         * min cost to leaves for each node, nodeId is index
         */
        int[] ans = new int[size];
        Arrays.fill(ans, Integer.MAX_VALUE);

        /**
         * remember previous nodeId along the path, use to
         * backtrack and create the min path
         */
        int[] pre = new int[size];
        Arrays.fill(pre, -1);

        /**
         * out-degree of a node, nodeId as index,
         * in-degree for reversed graph
         */
        int[] degree = new int[size];

        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            degree[i] = graph.get(i).size();
            if (degree[i] == 0) {
                /**
                 * starting point, cost is zero
                 */
                ans[i] = 0;
                q.offer(i);
            }

            /**
             * create reversed graph, direction is reversed for the edges,
             * so that we can use it to do topological sort
             */
            for (int j = 0; j < graph.get(j).size(); j++) {
                int id = graph.get(i).get(j).nodeId;
                int cost = graph.get(i).get(j).cost;

                rev.get(id).add(new Edge(i, cost));
            }
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int i = 0; i < rev.get(cur).size(); i++) {
                int v = rev.get(cur).get(i).nodeId;
                int w = rev.get(cur).get(i).cost;

                /**
                 * dp part, calculate min cost
                 */
                if (ans[cur] != Integer.MAX_VALUE && ans[cur] + w < ans[v]) {
                    ans[v] = ans[cur] + w;
                    pre[v] = cur;
                }

                degree[v]--;
                if (degree[v] == 0) {
                    q.offer(v);
                }
            }
        }

        List<Integer> res = new ArrayList<>();

        /**
         * Here we assume we want to get the min cost path starting from node with id 0,
         * the min cost value is in ans[0]. Use pre to backtrack and build the path.
         */

        int x = 0;
        while (x != -1) {
            res.add(x);
            x = pre[x];
        }

        return res;
    }

}
