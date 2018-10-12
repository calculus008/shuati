package leetcode;

import common.UnionFindSet;

/**
 * Created by yuank on 4/23/18.
 */
public class LE_261_Graph_Valid_Tree {
    /**
         Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
         write a function to check whether these edges make up a valid tree.

         For example:

         Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.

         Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

         Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected,
         [0, 1] is the same as [1, 0] and thus will not appear together in edges.

        Medium
     */

    /**
     * Uion Find
     *
     * Time : O(E + V)
     * Space : O(n)
     */

    public boolean validTree(int n, int[][] edges) {
        if (n == 1 && edges.length == 0) return true;
        //!!! if there's no loop, number of edges equals number of nodes minus 1
        if (n < 1 || edges.length != n - 1) return false;

        UnionFindSet ufs = new UnionFindSet(n);
        for (int[] edge : edges) {
            //if two nodes are already in he same cluster - meaning thew edge forms a loop, union() will return false
            if (!ufs.union(edge[0], edge[1])) {
                return false;
            }
        }

        return true;
    }
}
