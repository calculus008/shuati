package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_207_Course_Schedule {
    /**
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
     **/

    /**
    Topological sorting, https://www.geeksforgeeks.org/topological-sorting/
    Time : O(V + E), Space : O(n)
    https://www.youtube.com/watch?v=3gelaRgXRpA&index=7&list=PLvyIyKZVcfAkKE4fx9dz12HnEn-uzxrIK

     面经变形题 - 处理make file dependency
    */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        int res = numCourses;

        //Get indegree for all nodes
        for (int[] pair : prerequisites) {
            indegree[pair[0]]++;
        }

        //Find starting nodes which have 0 indegree
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

    /**
     * The 1st Solution TLE on lintcode for a test case with extreme large data set.
     * The reason is :
     * it does not have a data structure that saves neighbour info (the node I point to),
     * instead, it uses for loop to go through entire given array.
     *
     * The following solution adds "edges" (array of ArrayList) for storing neighbours
     */
    public boolean canFinish_JiuZhang(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        int res = numCourses;

        /**
         * Create data structure to store neighbour info
         * Array of list, which contains neighbors ids.
         **/
        ArrayList[] edges = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            edges[i] = new ArrayList<Integer>();
        }

        //Get indegree for all nodes
        for (int[] pair : prerequisites) {
            indegree[pair[0]]++;
            /**
             * new:
             * 我的邻居是我指向的那个
             */
            edges[pair[1]].add(pair[0]);
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

            /**
             * new
             * Check neighbours directly
             */
            ArrayList<Integer> neighour = edges[cur];
            for (int e : neighour) {
                indegree[e]--;
                if (indegree[e] == 0) {
                    queue.offer(e);
                }
            }
        }

        return res == 0;
    }
}
