package leetcode;

import java.util.*;

public class LE_1882_Process_Tasks_Using_Servers {
    /**
     * You are given two 0-indexed integer arrays servers and tasks of lengths n and m respectively. servers[i] is the
     * weight of the i server, and tasks[j] is the time needed to process the j task in seconds.
     *
     * Tasks are assigned to the servers using a task queue. Initially, all servers are free, and the queue is empty.
     *
     * At second j, the jth task is inserted into the queue (starting with the 0th task being inserted at second 0).
     * As long as there are free servers and the queue is not empty, the task in the front of the queue will be assigned
     * to a free server with the smallest weight, and in case of a tie, it is assigned to a free server with the smallest index.
     *
     * If there are no free servers and the queue is not empty, we wait until a server becomes free and immediately assign
     * the next task. If multiple servers become free at the same time, then multiple tasks from the queue will be assigned
     * in order of insertion following the weight and index priorities above.
     *
     * A server that is assigned task j at second t will be free again at second t + tasks[j].
     *
     * Build an array ans of length m, where ans[j] is the index of the server the j task will be assigned to.
     *
     * Return the array ans.
     *
     * Example 1:
     * Input: servers = [3,3,2], tasks = [1,2,3,2,1,2]
     * Output: [2,2,0,2,1,2]
     * Explanation: Events in chronological order go as follows:
     * - At second 0, task 0 is added and processed using server 2 until second 1.
     * - At second 1, server 2 becomes free. Task 1 is added and processed using server 2 until second 3.
     * - At second 2, task 2 is added and processed using server 0 until second 5.
     * - At second 3, server 2 becomes free. Task 3 is added and processed using server 2 until second 5.
     * - At second 4, task 4 is added and processed using server 1 until second 5.
     * - At second 5, all servers become free. Task 5 is added and processed using server 2 until second 7.
     *
     * Example 2:
     * Input: servers = [5,1,4,3,2], tasks = [2,1,2,4,5,2,1]
     * Output: [1,4,1,4,1,3,2]
     * Explanation: Events in chronological order go as follows:
     * - At second 0, task 0 is added and processed using server 1 until second 2.
     * - At second 1, task 1 is added and processed using server 4 until second 2.
     * - At second 2, servers 1 and 4 become free. Task 2 is added and processed using server 1 until second 4.
     * - At second 3, task 3 is added and processed using server 4 until second 7.
     * - At second 4, server 1 becomes free. Task 4 is added and processed using server 1 until second 9.
     * - At second 5, task 5 is added and processed using server 3 until second 7.
     * - At second 6, task 6 is added and processed using server 2 until second 7.
     *
     * Constraints:
     * servers.length == n
     * tasks.length == m
     * 1 <= n, m <= 2 * 105
     * 1 <= servers[i], tasks[j] <= 2 * 105
     *
     * Medium
     *
     * https://leetcode.com/problems/process-tasks-using-servers/
     */


    /**
     * PriorityQueue
     * Time: O((M+N)*logN)
     * Space: O(N)
     */
    class Solution1 {
        public int[] assignTasks(int[] servers, int[] tasks) {
            /**
             * element int[] {idx, weight finish_time}
             * sort by weight, then by idx
             */
            PriorityQueue<int[]> idle = new PriorityQueue<>((a, b) -> a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));

            /**
             * !!!
             *  sort by finish_time, then by weight, then by idx
             *  Compared with solution2, since the logic below assign server directly from busy dq, so we have to
             *  sort the busy dq by finish_time, weight and idx, NOT just finish_time.
             */
            PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> a[2] != b[2] ? Integer.compare(a[2], b[2]) : (a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0])));

            int n = servers.length;
            //O(nlogn)
            for (int i = 0; i < n; i++) {
                idle.offer(new int[]{i, servers[i], 0});
            }

            int m = tasks.length;
            int[] res = new int[m];

            /**
             * Nice solution which only needs to iterate through tasks array and don't need a Queue for tasks.
             *
             * The key is that, when there's no idel server available, we don't need to wait, we just get the top
             * element from busy pq, virtually catch up to its finish_time, then assign task and put it back to
             * busy pq.
             *
             * Since the logic below assign server directly from busy dq, so we have to sort the busy pq by
             * finish_time, weight and idx, NOT just finish_time.
             */
            //O(mlogn)
            for (int i = 0; i < m; i++) {
                while (!busy.isEmpty() && busy.peek()[2] <= i) {
                    idle.offer(busy.poll());
                }

                if (idle.isEmpty()) {
                    int[] s = busy.poll();
                    res[i] = s[0];
                    s[2] += tasks[i];
                    busy.offer(s);
                } else {
                    int[] s = idle.poll();
                    res[i] = s[0];
                    s[2] = i + tasks[i];
                    busy.offer(s);
                }
            }

            return res;
        }
    }

    /**
     * Iterate over time
     * 1.Free server that finished tasks at given time
     * 2.add task to queue
     * 3.Assign server for tasks.
     *
     * But you need to take care of time catching up scenario, otherwise it will be TLE. Also the while loop conditions
     * are complicated and hard to get it right.
     */
    class Solution2 {
        public int[] assignTasks(int[] servers, int[] tasks) {
            /**
             * idle pq with free servers.
             * int[] {server_idx, server_weight}
             */
            PriorityQueue<int[]> idle = new PriorityQueue<>((a, b) -> a[1] == b[1] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
            for (int i = 0; i < servers.length; i++) {
                idle.offer(new int[]{i, servers[i]});
            }

            /**
             * busy pq with servers that are busy
             * int[] {server_idx, task_finish_time, server_weight}
             */
            PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

            /**
             * queue for tasks. Notice, task can only be put in queue when it is its time (time is its idx)
             */
            Queue<int[]> q = new LinkedList<>();
            int n = tasks.length;

            int[] res = new int[n];
            int i = 0;

            /**
             * !!!
             * Can't just iterate through tasks index, process time may be very long and it may finish long after
             * the last task is put into queue. We also need to wait until the task queue is empty (all tasks are
             * assigned with servers)
             */
            while (i < n || !q.isEmpty()) {
                /**
                 * enqueue task when it comes its time.
                 */
                if (i < n)  {
                    q.offer(new int[]{i, tasks[i]});
                }

                /**
                 * Release all servers that finished their tasks at current time
                 * and put into idle pq.
                 */
                while (!busy.isEmpty() && busy.peek()[1] == i) {
                    int[] s = busy.poll();
                    idle.offer(new int[]{s[0], s[2]});
                }

                /**
                 * Assign tasks to servers, put servers to busy pq.
                 * Because we always assign server from idle pq, it's already properly sorted based on weight and idx,
                 * so the busy pq only needs to be sorted by finish_time.
                 */
                while (!idle.isEmpty() && !q.isEmpty()) {
                    int[] server = idle.poll();
                    int[] task = q.poll();
                    res[task[0]] = server[0];
                    busy.offer(new int[]{server[0], i + task[1], server[1]});
                }

                /**
                 * Speed up the iteration for test case like:
                 * Servers : [1,2,3,4,5,6,7,8,9,10]
                 * Tasks : [90000,90001,90002,90003,90004,90005,90006,90007,90008,90009,....]
                 *
                 * After all tasks are put into q, find the finish time of the top element in busy pq, catch up with it.
                 *
                 * Without this logic, it will TLE.
                 */
                if (i >= n && !busy.isEmpty() && busy.peek()[1] > i) {
                    i = busy.peek()[1];
                } else {
                    i++;
                }
            }

            return res;
        }
    }
}
