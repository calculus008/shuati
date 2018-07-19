package lintcode;

import java.util.*;

/**
 * Created by yuank on 7/19/18.
 */
public class LI_178_Graph_Valid_Tree {
    /**
         Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
         write a function to check whether these edges make up a valid tree.

         Example
         Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.

         Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

         Medium
     */

    /**
     * BFS
     * 因为在n个节点，n-1条边的情况下，只有两种可能：1、树 2、几个有环的不联通的图 因此从节点0出发，最后得到的hash size不是n的话，一定不是树
     *
     * 因为这个题并不需要判断是不是有环。如果按照判断环的思路就只能用DFS。但是这里的话是在判断所有节点能不能构成一棵树，
     * 所以可以通过判断边的数量和节点数量的关系以及是否全连同来判断是否是树。
     *
     * 两个条件判断是否为树：
     * （1）无环
     * （2）连通。
     *
     * 首先判断无环：如果是树的话，两个点之间有且只有一条path，所以如果有n个点，那么一定有且只有n-1条边。
     * 然后用BFS判断是否连通，如果连通，访问到的点的数量一定等于n。
     *
     */
    public boolean validTree_JiuZhang(int n, int[][] edges) {
        if (n == 0) {
            return false;
        }

        if (edges.length != n - 1) {
            return false;
        }

        Map<Integer, Set<Integer>> graph = initializeGraph(n, edges);

        // bfs
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> hash = new HashSet<>();

        queue.offer(0);

        /**
         * !!! 千万不能忘
         */
        hash.add(0);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (Integer neighbor : graph.get(node)) {
                /**
                 * !!! 千万不能忘
                 */
                if (hash.contains(neighbor)) {
                    continue;
                }

                hash.add(neighbor);
                queue.offer(neighbor);
            }
        }

        return (hash.size() == n);
    }

    //!!!
    private Map<Integer, Set<Integer>> initializeGraph(int n, int[][] edges) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<Integer>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        return graph;
    }

    public boolean validTree_myversion(int n, int[][] edges) {
        if (n == 0) return false;

        if (edges.length != n - 1 ) return false;

        Set<Integer> visited = new HashSet<>();
        HashMap<Integer, Set<Integer>> map = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();

        q.offer(0);
        visited.add(0);//!!!

        map = buildGraph(edges);

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (!map.containsKey(cur)) {
                continue;
            }

            for (int i : map.get(cur)) {
                if (visited.contains(i)) {//!!!
                    continue;
                }

                visited.add(i);
                q.offer(i);
            }
        }

        return visited.size() == n;
    }

    public HashMap<Integer, Set<Integer>> buildGraph(int[][] edges) {
        HashMap<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] e : edges) {
            map.putIfAbsent(e[0], new HashSet<>());
            map.putIfAbsent(e[1], new HashSet<>());
            map.get(e[0]).add(e[1]);
            map.get(e[1]).add(e[0]);
        }

        return map;
    }
}
