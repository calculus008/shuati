package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class LE_636_Exclusive_Time_Of_Functions {
    /**
     * Given the running logs of n functions that are executed in a nonpreemptive
     * single threaded CPU, find the exclusive time of these functions.
     *
     * Each function has a unique id, start from 0 to n-1. A function may be
     * called recursively or by another function.
     *
     * A log is a string has this format : function_id:start_or_end:timestamp.
     * For example, "0:start:0" means function 0 starts from the very beginning o
     * f time 0. "0:end:0" means function 0 ends to the very end of time 0.
     *
     * Exclusive time of a function is defined as the time spent within this
     * function, the time spent by calling other functions should not be considered
     * as this function's exclusive time. You should return the exclusive time of
     * each function sorted by their function id.
     *
     * Example 1:
     * Input:
     * n = 2
     * logs =
     * ["0:start:0",
     *  "1:start:2",
     *  "1:end:5",
     *  "0:end:6"]
     * Output:[3, 4]
     *
     * Explanation:
     * Function 0 starts at time 0, then it executes 2 units of time and reaches the end of time 1.
     * Now function 0 calls function 1, function 1 starts at time 2, executes 4 units of time and end at time 5.
     * Function 0 is running again at time 6, and also end at the time 6, thus executes 1 unit of time.
     * So function 0 totally execute 2 + 1 = 3 units of time, and function 1 totally execute 4 units of time.
     *
     * Note:
     * Input logs will be sorted by timestamp, NOT log id.
     * Your output should be sorted by function id, which means the 0th element of your output
     * corresponds to the exclusive time of function 0.
     * Two functions won't start or end at the same time.
     * Functions could be called recursively, and will always end.
     * 1 <= n <= 100
     */

    /**
     * Stack
     *
     * Time and Space : O(n)
     *
     * Stack saves function ID.
     */
    class Solution1 {
        public int[] exclusiveTime(int n, List<String> logs) {
            int[] res = new int[n];
            Deque<Integer> stack = new ArrayDeque<>(n);

            int prevTime = 0;

            for (String log : logs) {
                String[] parts = log.split(":");

                if (!stack.isEmpty()) {
                    res[stack.peek()] += Integer.parseInt(parts[2]) - prevTime;
                }

                prevTime = Integer.parseInt(parts[2]);

                /**
                 * If start, push in function id
                 */
                if (parts[1].equals("start")) {
                    stack.push(Integer.parseInt(parts[0]));
                } else {//stop event, pop, time for stopped function increase by 1
                    res[stack.pop()]++;
                    prevTime++;
                }
            }
            return res;
        }
    }

    public class Solution2 {
        public int[] exclusiveTime(int n, List<String> logs) {
            Deque<Integer> stack = new ArrayDeque<>(n);
            int[] res = new int[n];

            /**
             * Get first line of log
             *
             * s[0] : task id
             * s[1] : start or end
             * s[2] : timestamp
             */
            String[] s = logs.get(0).split(":");

            /**
             * stack saves task id
             */
            stack.push(Integer.parseInt(s[0]));

            int i = 1;

            /**
             * prev : the timestamp of the previous event
             */
            int prev = Integer.parseInt(s[2]);

            /**
             * iterate through each line of logs, starting from line 2
             */
            while (i < logs.size()) {
                s = logs.get(i).split(":");

                /**
                 * process based on action type (start or end)
                 */
                if (s[1].equals("start")) {
                    if (!stack.isEmpty()) {
                        /**
                         * stack only keeps timestamp for "start" event.
                         * So we have another "start", for the last task
                         * in stack, we can calculate its exclusive run time.
                         */
                        res[stack.peek()] += Integer.parseInt(s[2]) - prev;
                    }

                    stack.push(Integer.parseInt(s[0]));
                    prev = Integer.parseInt(s[2]);//!!!
                } else {
                    res[stack.peek()] += Integer.parseInt(s[2]) - prev + 1;//"end" is still the end of stop time stamp
                    stack.pop();
                    prev = Integer.parseInt(s[2]) + 1;//!!!
                }

                /**
                 * !!!
                 * Don't forget!!!
                 */
                i++;
            }
            return res;
        }
    }

    /**
     * FB
     * 给了陆仨露的变体和多线程环境下不同的执行情况的题，总共两道一下给的，复习的时候就当成露三路就可以了。
     */
}
