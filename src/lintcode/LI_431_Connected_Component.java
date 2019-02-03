package lintcode;

import common.UndirectedGraphNode;

import java.util.*;

/**
 * Created by yuank on 7/18/18.
 */
public class LI_431_Connected_Component {
    /**
         431. Connected Component in Undirected Graph
         Find the number connected component in the undirected graph. Each node in the graph contains a
         label and a list of its neighbors. (a connected component (or just component) of an undirected
         graph is a subgraph in which any two vertices are connected to each other by paths, and which
         is connected to no additional vertices in the supergraph.)

         Example
         Given graph:

         A------B  C
         \     |  |
         \    |  |
         \   |  |
         \  |  |
         D   E
         Return {A,B,D}, {C,E}. Since there are two connected component which is {A,B,D}, {C,E}

         Medium
     */

    public List<List<Integer>> connectedSet(List<UndirectedGraphNode> nodes) {
        List<List<Integer>> res = new ArrayList<>();
        if (nodes == null || nodes.size() == 0) {
            return res;
        }

        /**
         * Alternative way of record visited node:
         *
         * HashMap<UndirectedGraphNode, Boolea> map = new HashMap<>();
         */

        Set<UndirectedGraphNode> visited = new HashSet<>();
        Queue<UndirectedGraphNode> q = new LinkedList<>();

        for (UndirectedGraphNode n : nodes) {
            /**
             * !!!
             */
            if (visited.size() == nodes.size()) {
                break;

            }
            if (visited.contains(n)) {
                continue;
            }

            List<Integer> list = new ArrayList<>();
            q.offer(n);
            visited.add(n);
            list.add(n.label);

            while (!q.isEmpty()) {
                UndirectedGraphNode cur = q.poll();
                for (UndirectedGraphNode node : cur.neighbors) {
                    if (!visited.contains(node)) {
                        q.offer(node);
                        visited.add(node);
                        list.add(node.label);
                    }
                }
            }

            Collections.sort(list);
            res.add(list);
        }

        return res;
    }
}
