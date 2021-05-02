package leetcode;

import java.util.*;

public class LE_1136_Parallel_Courses {
    /**
     * You are given an integer n which indicates that we have n courses, labeled from 1 to n. You are also given an
     * array relations where relations[i] = [a, b], representing a prerequisite relationship between course a and
     * course b: course a has to be studied before course b.
     *
     * In one semester, you can study any number of courses as long as you have studied all the prerequisites for the
     * course you are studying.
     *
     * Return the minimum number of semesters needed to study all courses. If there is no way to study all the courses,
     * return -1.
     *
     * Example 1:
     * Input: n = 3, relations = [[1,3],[2,3]]
     * Output: 2
     * Explanation: In the first semester, courses 1 and 2 are studied. In the second semester, course 3 is studied.
     *
     * Example 2:
     * Input: n = 3, relations = [[1,2],[2,3],[3,1]]
     * Output: -1
     * Explanation: No course can be studied because they depend on each other.
     *
     * Constraints:
     * 1 <= n <= 5000
     * 1 <= relations.length <= 5000
     * 1 <= a, b <= n
     * a != b
     * All the pairs [a, b] are unique.
     *
     * Medium
     */

    /**
     * Topological sort
     * Space and Time : O(V + E)
     * LE_207_Course_Schedule
     *
     * https://leetcode.com/problems/parallel-courses/discuss/344808/Java-Topological-Sort-%2B-BFS-w-comment-and-analysis.
     *
     * "for non existed node, they are not added to the graph to begin with, so this check is required either way. Though I agree there is no need to remove node from graph"
     *
     * Google follow up questions : If only pick at most k courses in one semester, the shortest time to finish all courses.ø
     * 1494. Parallel Courses II
     */

    class Solution {
        public int minimumSemesters(int N, int[][] relations) {
            Map<Integer, List<Integer>> graph = new HashMap<>();  // Maps a course to a list of courses that have it as a prerequisite.
            Map<Integer, Integer> inDegree = new HashMap<>();     // Maps a node to the number of its remaining prerequisites.

            /**
             * "...n courses, labeled from 1 to n".
             * Without this condition, we have to iterate through relations first, put unique course number in a set
             */
            for (int i = 1; i <= N; ++i) {
                graph.put(i, new ArrayList<>());
                inDegree.put(i, 0);
            }

            for (int[] edge : relations) {
                // We assume that there are no duplicate edges.
                graph.get(edge[0]).add(edge[1]);
                inDegree.put(edge[1], inDegree.get(edge[1]) + 1);
            }

            Deque<Integer> queue = new ArrayDeque<>();
            // Add all courses with no prerequisites. These are all the courses that can be done in the first semester.
            for (int key : graph.keySet()) {
                if (inDegree.get(key) == 0) queue.addLast(key);
            }

            int result = 0, finishedCourses = 0;
            while (queue.size() > 0) {
                // All courses that are currently in the queue can be done in the current semester.
                finishedCourses += queue.size();
                result++;

                for (int i = queue.size(); i > 0; --i) {
                    int currCourse = queue.removeFirst();
                    // Finish currCourse and remove it as a prerequisite. Add courses that now have 0 prerequisites to the queue.
                    for (int adjacentNode : graph.get(currCourse)) {
                        inDegree.put(adjacentNode, inDegree.get(adjacentNode) - 1);
                        if (inDegree.get(adjacentNode) == 0) queue.addLast(adjacentNode);
                    }
                }
            }
            return finishedCourses == N ? result : -1;
        }
    }
}

