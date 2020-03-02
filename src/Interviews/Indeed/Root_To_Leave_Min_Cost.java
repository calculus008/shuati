package Interviews.Indeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Edge和Node2都是给好的，里面的变量类型到时候和面试官讨论吧。
 **/
class Edge{
    Node2 node; //表示这个edge的尾巴指向哪里。
    int cost;

    public Edge(Node2 n, int cost) {
        this.node = n;
        this.cost = cost;
    }
}

class Node2 {
    List<Edge> edges; //表示从这个头出发的所有edge

    public Node2(){
        this.edges = new ArrayList<>();
    }
}

public class Root_To_Leave_Min_Cost {
    /**
     * =============================================================================
     * Question Description
     * =============================================================================
     * Given a tree, every tree edge has a cost， find the least
     * cost or find the leaf node that the cost of path that from root to leaf is the
     * least.
     */

    /**
     * The time complexity of DFS if the entire tree is traversed is O(V) where V is the number of nodes.
     * In the case of a graph, the time complexity is O(V+E) where V is the number of vertexes and E is
     * the number of edges.
     */

    /**
     * !!!
     */
    int minCost = Integer.MAX_VALUE;

    /**
     * !!!
     * Return a list of Edge, NOT node!!!
     */
    public List<Edge> getMinCostPath(Node2 root) {
        List<Edge> res = new ArrayList<>();
        if (root == null) return res;

        dfs(root, res, new ArrayList<>(), 0);

        return res;
    }

    public void dfs(Node2 root, List<Edge> res, List<Edge> temp, int curCost) {
        if (root == null) return;

        if (root.edges.size() == 0) {
            if (curCost < minCost) {
                minCost = curCost;
                res.clear();
                res.addAll(temp);
            }

            return;
        }

        for (Edge e : root.edges) {
            temp.add(e);
            dfs(e.node, res, temp, curCost + e.cost);
            temp.remove(temp.size() - 1);
        }
    }

    public int getMinCost(Node2 root) {
        if (root == null) return 0;

        helper(root, 0);

        return minCost;
    }

    public void helper(Node2 root, int curCost) {
        if (root == null) return;

        if (root.edges.size() == 0) {
            minCost = Math.min(minCost, curCost);
            return;
        }

        for (Edge e : root.edges) {
            helper(e.node, curCost + e.cost);
        }
    }

    /**
     * =================================================
     * follow-up：
     * 改树为DAG，那leaf变成了出度为0的node，同样求实现方法。
     *
     * 如果不是binary tree的结构，而是任意的单向图，问代码还是否work（yes）
     * 有没有优化的地方？（我用hashmap存下每个节点到叶节点的distance，这样再次访问到该节点就不必dfs下去）。
     * 时间复杂度？
     * (优化后是O（V+E））//说的真好
     *
     * https://www.techiedelight.com/topological-sorting-dag/
     *
     * https://leetcode.com/problems/course-schedule/discuss/287866/DFS-on-DAG
     *
     */
    Map<Node2, Integer> dist = new HashMap<>();
    Map<Node2, Integer> visited = new HashMap<>();

    public int getMinCostDAG(Node2 src) {
        if (src == null) return 0;

        helper1(src, 0);

        return minCost;
    }

    /**
     *     void dfs(int src, vector<vector<int>> &al, vector<int> &visited)
     *     {
     *         if(visited[src] != 0) return;
     *         visited[src] = 1;
     *         for(int i = 0; i < al[src].size(); i++)
     *         {
     *             int curr = al[src][i];
     *             if(visited[curr] == 0)
     *             {
     *                 dfs(curr, al, visited);
     *             }
     *             else if(visited[curr] == 1)
     *             {
     *                 cycle = true;
     *                 return;
     *             }
     *         }
     *         visited[src] = 2;
     *     }
     */
    public int helper1(Node2 root, int curCost) {
//        if (root == null) return ;
        if (dist.containsKey(root)) return curCost + dist.get(root);

        visited.put(root, 1);

        /**
         * leaf node
         */
        if (root.edges.size() == 0) {
            minCost = Math.min(minCost, curCost);
            return curCost;
        }

        /**
         * The min cost to leaf that goes through current node
         */
        int nodeCost = Integer.MAX_VALUE;
        for (Edge e : root.edges) {
            if (!visited.containsKey(e.node)) {
                nodeCost = Math.min(nodeCost, helper1(e.node, curCost + e.cost));
            }
            //else if visited is 1, there' a cycle
        }

        visited.put(root, 2);
        dist.put(root, nodeCost - curCost);

        return nodeCost;
    }

    /**
     * Toplogoical sort + DP
     */


    public static void main(String[] args) {
        Root_To_Leave_Min_Cost test = new Root_To_Leave_Min_Cost();

        //       n1
        //   e1 /  \ e3
        //     n2   n3
        // e2 /
        //   n4
        //

        Node2 n1 = new Node2();
        Node2 n2 = new Node2();
        Node2 n3 = new Node2();
        Node2 n4 = new Node2();
        Edge e1 = new Edge(n2,1);
        Edge e2 = new Edge(n4,2);
        Edge e3 = new Edge(n3,5);
        n1.edges.add(e1);
        n1.edges.add(e3);
        n2.edges.add(e2);

        int res = test.getMinCost(n1);
        System.out.println("3 = "+res);
    }
}
