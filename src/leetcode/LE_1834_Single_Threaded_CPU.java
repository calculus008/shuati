package leetcode;

import java.util.*;

public class LE_1834_Single_Threaded_CPU {
    /**
     * You are given n tasks labeled from 0 to n - 1 represented by a 2D integer array tasks,
     * where tasks[i] = [enqueueTimei, processingTimei] means that the ith task will be available to process at
     * enqueueTimei and will take processingTimei to finish processing.
     *
     * You have a single-threaded CPU that can process at most one task at a time and will act in the following way:
     *
     * If the CPU is idle and there are no available tasks to process, the CPU remains idle.
     * If the CPU is idle and there are available tasks, the CPU will choose the one with the shortest processing time.
     * If multiple tasks have the same shortest processing time, it will choose the task with the smallest index.
     *
     * Once a task is started, the CPU will process the entire task without stopping.
     * The CPU can finish a task then start a new one instantly.
     * Return the order in which the CPU will process the tasks.
     *
     * Example 1:
     * Input: tasks = [[1,2],[2,4],[3,2],[4,1]]
     * Output: [0,2,3,1]
     * Explanation: The events go as follows:
     * - At time = 1, task 0 is available to process. Available tasks = {0}.
     * - Also at time = 1, the idle CPU starts processing task 0. Available tasks = {}.
     * - At time = 2, task 1 is available to process. Available tasks = {1}.
     * - At time = 3, task 2 is available to process. Available tasks = {1, 2}.
     * - Also at time = 3, the CPU finishes task 0 and starts processing task 2 as it is the shortest. Available tasks = {1}.
     * - At time = 4, task 3 is available to process. Available tasks = {1, 3}.
     * - At time = 5, the CPU finishes task 2 and starts processing task 3 as it is the shortest. Available tasks = {1}.
     * - At time = 6, the CPU finishes task 3 and starts processing task 1. Available tasks = {}.
     * - At time = 10, the CPU finishes task 1 and becomes idle.
     *
     * Example 2:
     * Input: tasks = [[7,10],[7,12],[7,5],[7,4],[7,2]]
     * Output: [4,3,2,0,1]
     * Explanation: The events go as follows:
     * - At time = 7, all the tasks become available. Available tasks = {0,1,2,3,4}.
     * - Also at time = 7, the idle CPU starts processing task 4. Available tasks = {0,1,2,3}.
     * - At time = 9, the CPU finishes task 4 and starts processing task 3. Available tasks = {0,1,2}.
     * - At time = 13, the CPU finishes task 3 and starts processing task 2. Available tasks = {0,1}.
     * - At time = 18, the CPU finishes task 2 and starts processing task 0. Available tasks = {1}.
     * - At time = 28, the CPU finishes task 0 and starts processing task 1. Available tasks = {}.
     * - At time = 40, the CPU finishes task 1 and becomes idle.
     *
     *
     * Constraints:
     * tasks.length == n
     * 1 <= n <= 105
     * 1 <= enqueueTimei, processingTimei <= 109
     *
     * Medium
     */

    /**
     * 需要多重排序，所以很明显要用PriorityQueue.
     *
     * 第一次要对enqueueTime排序，也可以用PriotityQueue并且用一个Task Class, 那样要多写很多code, 还是用extTasks数组好，可以
     * 直接用"Arrays.sort()"
     *
     * 第二次排序是对processTime和index, 用PriorityQueue.但是，tricky的地方在于有time的因素要考虑。time有两种情况：
     * 1.当pq为空，需要读取extTasks中当前元素中的time的值。
     * 2.当往res数组中写的时候，要对time进行累加，因为此时该任务完成，时间要往后移动。
     */
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        int[] res = new int[n];
        int[][] extTasks = new int[n][3];

        for (int i = 0; i < n; i++) {
            extTasks[i] = new int[]{i, tasks[i][0], tasks[i][1]};
        }

        Arrays.sort(extTasks, (a, b) -> Integer.compare(a[1], b[1]));

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] == b[2] ? Integer.compare(a[0], b[0]): Integer.compare(a[2], b[2]));

        int time = extTasks[0][1];
        int i = 0;
        int resIdx = 0;

        while (resIdx < n ) {
            /**
             * " <= time" !!!
             */
            while (i < n && extTasks[i][1] <= time) {
                pq.offer(extTasks[i]);
                i++;
            }

            /**
             * MUST be here, after the inner while loop
             * You have to check if you can add something to the queue. And you can only 'move' time and skip idle
             * period if the queue is empty AND current task is not yet available:
             */
            if (pq.isEmpty()) {
                /**
                 * set with enqueueTime
                 */
                time = extTasks[i][1];
                continue;
            }

            int[] cur = pq.poll();
            res[resIdx++] = cur[0];
            /**
             * plus processTime
             */
            time += cur[2];
        }

        return res;
    }
}
