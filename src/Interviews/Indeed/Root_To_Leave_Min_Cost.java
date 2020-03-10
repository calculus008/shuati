package Interviews.Indeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第一题是Tree找minimal path cost leaf，就是一个Tree，每个edge都有cost，让找到从root到leaf一路上path cost最小的那个leaf。
 * Tree不一定是Binary Tree。用DFS做了，途中不断更新当前找到的minimal path cost node和对应的cost。然后面试官问如果cost都是
 * positive能不能优化，回答发现一旦node的cost已经大于当前找到的minimal cost的话就不必再在这条path上走下去。然后加了两行代码
 * 实现了下。
 *
 * 第二题，其实算是第一题的follow up，但我相当于是完全重写的，不知道是不是跑偏了？内容是，如果这不是一个Tree而是一个
 * Undirected Graph，给一个source和一个destination，找到连接这两点的minimal cost path。我用了Uniform Cost Search来做，
 * 跟BFS差不多，只不过不是用fifo保存node而是用priority queue，同时也要存下到这个node为止的cost和path，然后根据到每个
 * nodecost来排序，先visit当前cost最小的node，这样当遇到destination的时候，对应的path就是cost最小的。Follow up是因
 * 为我有一个visited set，凡是visit过的node就不再走了，面试官问如果经过这个node有多个path那后面的不就被忽略了吗？
 * 我说的是第一次遇到一个node时是path cost最小的，后面如果还有path路过它那cost肯定比第一次大，就不用考虑了。面试官似乎是认可了的。
 *
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
     *
     * 每条edge上有weight，问题就是找到所有root to leaf path里sum of weight的最小值。需要自己设计数据结构。我就设计了Node，
     * 里面有一个child_to_weight_map。然后就是dfs。面试官又说有没有什么优化，我说如果所有的weight都是正值可以剪枝什么的，
     * 倒是不用写代码。问完了tree又问dag，唯一的区别可能就是会有重复访问的问题。我就在Node里又加了一个min，记录如果从这个点开始
     * 能达到的最小值，遍历完更新一下。下次再到这个点如果发现有值了就不用继续了。面试官说行，我就写了。最后问了问复杂度什么的。
     * 题目挺清楚的，就用了dfs，面试官也没有刁难。
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

        /**
         * if edge cost is positive, pruning by returning when current cost is already bigger than minCost.
         */
        if (curCost > minCost) return;

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
     * 3面是Root to Leaf Min Cost那题。两个美国小哥，都挺年轻的。我用的bottom-up的方法，改成 DAG后加了个memorization来存储之前的结果
     * ，其他什么都不用变。不提dijkstra完全没问题。有 趣的是我cache的是边，主面试官表示赞同，shadow说这样cache不行，要cache node。
     * 但主面试 官表示可以，然后他们争论了一分钟，结论是我的方案可行。 其实这题我也没写过，随手写的，感谢主面试官让我不用推倒重写。
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
