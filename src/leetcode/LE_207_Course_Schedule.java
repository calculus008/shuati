package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_207_Course_Schedule {
    /*
        There are a total of n courses you have to take, labeled from 0 to n - 1.

        Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

        Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

        For example:

        2, [[1,0]]
        There are a total of 2 courses to take. To take course 1 you should have finished course 0. So it is possible.

        2, [[1,0],[0,1]]
        There are a total of 2 courses to take. To take course 1 you should have finished course 0,
        and to take course 0 you should also have finished course 1. So it is impossible.

        Note:
        The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
        You may assume that there are no duplicate edges in the input prerequisites.
     */

    /**
    Topological sorting, https://www.geeksforgeeks.org/topological-sorting/
    Time : O(V + E), Space : O(n)
    https://www.youtube.com/watch?v=3gelaRgXRpA&index=7&list=PLvyIyKZVcfAkKE4fx9dz12HnEn-uzxrIK
    */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        int res = numCourses;

        //Get indgree for all nodes
        for (int[] pair : prerequisites) {
            indegree[pair[0]]++;
        }

        //Find starting nodes which have 0 indgree
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res--;
            for (int[] pair : prerequisites) {
                if (pair[1] == cur) {
                    indegree[pair[0]]--;
                    if (indegree[pair[0]] == 0) {
                        queue.offer(pair[0]);
                    }
                }
            }
        }

        return res == 0;
    }
}
