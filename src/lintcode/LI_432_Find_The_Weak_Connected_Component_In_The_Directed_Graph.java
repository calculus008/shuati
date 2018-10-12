package lintcode;

import common.DirectedGraphNode;
import common.UnionFindInMap;
import common.UnionFindInMap1;

import java.util.*;

/**
 * Created by yuank on 10/10/18.
 */
public class LI_432_Find_The_Weak_Connected_Component_In_The_Directed_Graph {
    /**
     Find the number Weak Connected Component in the directed graph.
     Each node in the graph contains a label and a list of its neighbors.
     (a connected set of a directed graph is a subgraph in which any two
     vertices are connected by direct edge path.)

     Example
     Given graph:

     A----->B  C
     \     |  |
     \    |   |
     \   |    |
     \  v     v
     ->D      E <- F

     Return {A,B,D}, {C,E,F}. Since there are two connected component
     which are {A,B,D} and {C,E,F}

     Notice
     Sort the element in the set in increasing order
     */

    /**
     * Different from LE_323_Number_Of_Connected_Components_In_Undirected_Graph
     *
     * 1.Output is the nodes in each connected component, not just the number of connected component
     * 2.Label for each node is not just positive integers, it can be negative,
     *   therefore we need to use UnionFindInMap1, which use HashMap as "parents" internally
     */
    public List<List<Integer>> connectedSet2(ArrayList<DirectedGraphNode> nodes) {
        List<List<Integer>> res = new ArrayList<>();
        if (nodes == null || nodes.size() == 0) return res;

        UnionFindInMap1 uf = new UnionFindInMap1();

        for (DirectedGraphNode node : nodes) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                uf.union(node.label, neighbor.label);
            }
        }

        Map<Integer, TreeSet<Integer>> map = new HashMap<>();
        for (DirectedGraphNode node : nodes) {
            int id= uf.query(node.label);
            if (!map.containsKey(id)) {
                map.put(id, new TreeSet<>());
            }
            map.get(id).add(node.label);
        }

        for (TreeSet<Integer> value : map.values()) {
            List<Integer> s = new ArrayList(value);
            res.add(s);
        }

        return res;
    }
}
