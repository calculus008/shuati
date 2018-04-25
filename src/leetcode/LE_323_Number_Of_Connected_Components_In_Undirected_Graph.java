package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 4/24/18.
 */
public class LE_323_Number_Of_Connected_Components_In_Undirected_Graph {
    /**
         Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
        write a function to find the number of connected components in an undirected graph.

         Example 1:
         0          3
         |          |
         1 --- 2    4
         Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.

         Example 2:
         0           4
         |           |
         1 --- 2 --- 3
         Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.

        Medium
     */

    /**
     * Same as LE_547 Friends Circle
     *
     * Union Find
     *
     * Time : O(E + V)
     * Space : O(n)
     */
    public int countComponents(int n, int[][] edges) {
        if (n < 1) return 0;

        UnionFindSet ufs = new UnionFindSet(n);
        for (int[] edge : edges) {
            ufs.union(edge[0], edge[1]);
        }

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add(ufs.find(i));
        }

        return set.size();
    }

}
