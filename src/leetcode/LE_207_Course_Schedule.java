package leetcode;

import java.util.*;

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

        Medium

        https://leetcode.com/problems/course-schedule
     **/

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
    Topological sorting, https://www.geeksforgeeks.org/topological-sorting/
    Time : O(V + E), Space : O(n)
    https://www.youtube.com/watch?v=3gelaRgXRpA&index=7&list=PLvyIyKZVcfAkKE4fx9dz12HnEn-uzxrIK

     For a better topological sort implementation, refers to LE_1136_Parallel_Courses, which uses map and is a more
     generic solution (does not depend on course number starting from 0)

     面经变形题 - 处理make file dependency
    */

    /**
     * This solution is modified from solution for LE_1136_Parallel_Courses.
     *
     * The advantages:
     * This solution uses HashMap to store graph and node in-degree info (instead of 2D array), it is clearer. Using 2D
     * array has to deal with using course number as index, so it could be 0 or 1 based. If the course number is random,
     * then 2d array won't work.
     *
     * The changes made from LE_1136_Parallel_Courses:
     * 1.Be careful how the relationship is defined, in this problem, in prerequisites, {a, b}, b is prerequisite of a,
     *   b -> a (b is parent of a), in LE_1136_Parallel_Courses it is the reverse.
     * 2.Remove "result" related logic, it is only for LE_1136_Parallel_Courses.
     */
    class Solution {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            Map<Integer, List<Integer>> graph = new HashMap<>();  // Maps a course to a list of courses that have it as a prerequisite.
            Map<Integer, Integer> inDegree = new HashMap<>();     // Maps a node to the number of its remaining prerequisites.

            /**
             * "There are a total of n courses you have to take, labeled from 0 to n - 1."
             * Without this condition, we have to iterate through relations first, put unique course number in a set
             */
            for (int i = 0; i < numCourses; ++i) {
                graph.put(i, new ArrayList<>());
                inDegree.put(i, 0);
            }

            for (int[] edge : prerequisites) {
                // We assume that there are no duplicate edges.
                graph.get(edge[1]).add(edge[0]);
                inDegree.put(edge[0], inDegree.get(edge[0]) + 1);
            }

            Deque<Integer> queue = new ArrayDeque<>();
            // Add all courses with no prerequisites. These are all the courses that can be done in the first semester.
            for (int key : graph.keySet()) {
                if (inDegree.get(key) == 0) queue.addLast(key);// addLast
            }

//            int result = 0;
            int finishedCourses = 0;
            while (queue.size() > 0) {
                // All courses that are currently in the queue can be done in the current semester.
                finishedCourses += queue.size();

//                result++;

                for (int i = queue.size(); i > 0; --i) {
                    int currCourse = queue.removeFirst();// removeFirst
                    // Finish currCourse and remove it as a prerequisite. Add courses that now have 0 prerequisites to the queue.
                    for (int adjacentNode : graph.get(currCourse)) {
                        inDegree.put(adjacentNode, inDegree.get(adjacentNode) - 1);
                        if (inDegree.get(adjacentNode) == 0) queue.addLast(adjacentNode); //!!!
                    }
                }
            }
//            return finishedCourses == numCourses ? result : -1;
            return finishedCourses == numCourses;
        }
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

        //Find starting nodes which have 0 in-degree
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
