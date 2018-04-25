package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 4/23/18.
 */
public class LE_547_Friend_Circles {
    /**
         There are N students in a class. Some of them are friends, while some are not.
         Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C,
         then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.

         Given a N*N matrix M representing the friend relationship between students in the class.
         If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not.
         And you have to output the total number of friend circles among all the students.

         Example 1:
         Input:
         [[1,1,0],
         [1,1,0],
         [0,0,1]]
         Output: 2
         Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
         The 2nd student himself is in a friend circle. So return 2.

         Example 2:
         Input:
         [[1,1,0],
         [1,1,1],
         [0,1,1]]
         Output: 1
         Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends,
         so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.

         Note:
         N is in range [1,200].
         M[i][i] = 1 for all students.
         If M[i][j] = 1, then M[j][i] = 1.

        Medium

        Same as LE_323 Number of Connected Components in Undirected Graph
     */


    /**
     * Solution 1
     *
     * Union Find
     *
     * Time : O(E + V)
     * Space : O(n)
     *
     */

    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0 || M[0].length == 0) return 0;

        UnionFindSet ufs = new UnionFindSet(M.length);
        int n = M.length;
        for (int i = 0; i < n; i++) {
            //!!! "j = i + 1", just need to go over half of the matrix
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] == 1) {
                    ufs.union(i, j);
                }
            }
        }

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add(ufs.find(i));
        }

        return set.size();
    }


    /**
     * Solution 2 : DFS
     *
     * Time : O(n ^ 2)
     * Space : O(n)
     */

    public int findCircleNumDFS(int[][] M) {
        int n = M.length;
        int[] visited = new int[n];
        int res = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] != 0) continue;
            dfs(M, visited, i, n);
            res++;
        }

        return res;
    }

    public void dfs(int[][] M, int[] visited, int cur, int n) {
        if (visited[cur] == 1) return;
        visited[cur] = 1;

        for (int j = 0; j < n; j++) {
            if (M[cur][j] == 1 && visited[j] == 0) {
                dfs(M, visited, j, n);
            }
        }
    }
}
