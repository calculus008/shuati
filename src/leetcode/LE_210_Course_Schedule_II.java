package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_210_Course_Schedule_II {
    /**
        There are a total of n courses you have to take, labeled from 0 to n - 1.

        Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
        which is expressed as a pair: [0,1]

        Given the total number of courses and a list of prerequisite pairs, return the ordering of courses
        you should take to finish all courses.

        There may be multiple correct orders, you just need to return one of them. If it is impossible to
        finish all courses, return an empty array.

        For example:

        2, [[1,0]]
        There are a total of 2 courses to take. To take course 1 you should have finished course 0.
        So the correct course order is [0,1]

        4, [[1,0],[2,0],[3,1],[3,2]]
        There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2.
        Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3].
        Another correct ordering is[0,2,1,3].

        Note:
        The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
        Read more about how a graph is represented.
        You may assume that there are no duplicate edges in the input prerequisites.
     */

    /**
     * Almost the same as LE_207_Course_Schedule, same conditions, only return result is different.
     * Here we need to return one possible ordering of courses to finish all courses.
     *
     * Time : O(V + E), Space : O(n)
     * https://www.youtube.com/watch?v=6BCRaQcx0Ng&list=PLvyIyKZVcfAkKE4fx9dz12HnEn-uzxrIK&index=10
     **/
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        int[] res = new int[numCourses];
        int k = 0;

        //Get indgree for all nodes
        for (int[] pair : prerequisites) {
            indegree[pair[0]]++;
        }

        //Find starting nodes which have 0 indgree
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                res[k++] = i;//!!!
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int[] pair : prerequisites) {
                if (pair[1] == cur) {
                    indegree[pair[0]]--;
                    if (indegree[pair[0]] == 0) {
                        queue.offer(pair[0]);
                        res[k++] = pair[0];//!!!
                    }
                }
            }
        }

        //!!!
        return k == numCourses ? res : new int[0];
    }

    /**
     * Similar to LE_207_Course_Schedule, 1st TLE for large input data set.
     *
     * This solution works because it builds the neighbors list (neighbours[])
     * so that we don't need to iterate through all input data to update neighbors in degree
     */
    public int[] findOrder_JiuZhang(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        int[] indegree = new int[numCourses];
        int count = 0;

        /**
         * define as generic ArrayList array
         * or can use HashMap
         */
        ArrayList[] neighbors = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            neighbors[i] = new ArrayList<>();
        }

        for (int[] pair : prerequisites) {
            indegree[pair[0]]++;
            neighbors[pair[1]].add(pair[0]);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res[count++] = cur;//!!!

            /**
             * cast as ArrayList for Integer
             */
            ArrayList<Integer> neighbours = neighbors[cur];
            for (int neighbour : neighbours) {
                indegree[neighbour]--;
                if (indegree[neighbour] == 0) {
                    queue.offer(neighbour);
                }
            }
        }

        return count == numCourses ? res : new int[]{};
    }
}
