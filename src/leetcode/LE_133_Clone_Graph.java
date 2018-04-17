package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_133_Clone_Graph {
    /*
     Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
      */

    //Very Important

    //Solution 1: DFS
    Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();

    public UndirectedGraphNode cloneGraph1(UndirectedGraphNode node) {
        return helper(node);
    }

    public UndirectedGraphNode helper(UndirectedGraphNode node) {
        if (node == null) return null;
        if (map.containsKey(node)) return map.get(node);

        UndirectedGraphNode dup = new UndirectedGraphNode(node.label);

        //must be here.
        map.put(node, dup);

        for (UndirectedGraphNode neighbor : node.neighbors) {
            UndirectedGraphNode clone = helper(neighbor);
            dup.neighbors.add(clone);
        }

        return dup;
    }

    //Solution 2 : BFS
    //First go through graph using BFS, put all nodes in a set. Then clone, put into hashmap, link all neighbors
    public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
        if (node == null) return node;
        List<UndirectedGraphNode> nodes = getNodes(node);
        HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();

        for (UndirectedGraphNode cur : nodes) {
            map.put(cur, new UndirectedGraphNode(cur.label));
        }
        for (UndirectedGraphNode cur : nodes) {
            UndirectedGraphNode newNode = map.get(cur);
            for (UndirectedGraphNode neighbor : cur.neighbors) {
                newNode.neighbors.add(map.get(neighbor));//!!!map.get(neighbor)
            }
        }

        return map.get(node);
    }

    public List<UndirectedGraphNode> getNodes(UndirectedGraphNode node) {
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        Set<UndirectedGraphNode> set = new HashSet<>();
        queue.offer(node);
        set.add(node);

        while (!queue.isEmpty()) {
            UndirectedGraphNode cur = queue.poll();
            for (UndirectedGraphNode neighbor : cur.neighbors) {
                if (!set.contains(neighbor)) {
                    set.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return new ArrayList<>(set);
    }
}
