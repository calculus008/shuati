package lintcode;

import common.UndirectedGraphNode;

import java.util.*;

/**
 * Created by yuank on 7/18/18.
 */
public class LI_618_Search_Graph_Nodes {
    /**
         Given a undirected graph, a node and a target, return the nearest node to given node
        which value of it is target, return NULL if you can't find.

         There is a mapping store the nodes' values in the given parameters.

         Example
         2------3  5
         \     |  |
         \    |  |
         \   |  |
         \  |  |
         1 --4
         Give a node 1, target is 50

         there a hash named values which is [3,4,10,50,50], represent:
         Value of node 1 is 3
         Value of node 2 is 4
         Value of node 3 is 10
         Value of node 4 is 50
         Value of node 5 is 50

         Return node 4

         Medium
     */

    public UndirectedGraphNode searchNode(ArrayList<UndirectedGraphNode> graph,
                                          Map<UndirectedGraphNode, Integer> values,
                                          UndirectedGraphNode node,
                                          int target) {
        if (graph == null) {
            return null;
        }

        if (values.get(node) == target) {
            return node;
        }

        Set<UndirectedGraphNode> visited = new HashSet<>();
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.offer(node);

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                UndirectedGraphNode cur = q.poll();
                for (UndirectedGraphNode n  : cur.neighbors) {
                    if (values.get(n) == target) {
                        return n;
                    }

                    if (!visited.contains(n)) {
                        q.offer(n);
                        visited.add(n);
                    }
                }
            }
        }

        return null;
    }
}
