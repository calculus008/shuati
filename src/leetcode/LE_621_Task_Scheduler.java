package leetcode;

import java.util.*;

public class LE_621_Task_Scheduler {
    /**
     * Given a char array representing tasks CPU need to do. It contains capital
     * letters A to Z where different letters represent different tasks. Tasks
     * could be done without original order. Each task could be done in one interval.
     * For each interval, CPU could finish one task or just be idle.
     *
     * However, there is a non-negative cooling interval n that means between two
     * same tasks, there must be at least n intervals that CPU are doing different
     * tasks or just be idle.
     *
     * You need to return the least number of intervals the CPU will take to finish
     * all the given tasks.
     *
     * Example:
     *
     * Input: tasks = ["A","A","A","B","B","B"], n = 2
     * Output: 8
     * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
     *
     * Note:
     *
     * The number of tasks is in the range [1, 10000].
     * The integer n is in the range [0, 100].
     *
     * Medium
     */

    /**
     * To work on the same task again, CPU has to wait for time n, therefore we can
     * think of as if there is a cycle, of time n+1, regardless whether you schedule
     * some other task in the cycle or not.
     *
     * To avoid leave the CPU with limited choice of tasks and having to sit there
     * cooling down frequently at the end, it is critical the keep the diversity of
     * the task pool for as long as possible.
     *
     * In order to do that, we should try to schedule the CPU to always try round robin
     * between the most popular tasks at any time.
     *
     *
     */
    public class Solution1 {
        public int leastInterval(char[] tasks, int n) {
            Map<Character, Integer> counts = new HashMap<Character, Integer>();
            for (char t : tasks) {
                counts.put(t, counts.getOrDefault(t, 0) + 1);
            }

            PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
            pq.addAll(counts.values());

            int alltime = 0;
            int cycle = n + 1;

            while (!pq.isEmpty()) {
                int worktime = 0;
                List<Integer> tmp = new ArrayList<Integer>();

                for (int i = 0; i < cycle; i++) {
                    if (!pq.isEmpty()) {
                        tmp.add(pq.poll());
                        worktime++;
                    }
                }

                for (int cnt : tmp) {
                    if (--cnt > 0) {
                        pq.offer(cnt);
                    }
                }
                alltime += !pq.isEmpty() ? cycle : worktime;
            }

            return alltime;
        }
    }

    public class Solution2 {
        public int leastInterval(char[] tasks, int n) {
            int[] c = new int[26];

            for (char a : tasks) {
                c[a - 'A']++;
            }

            Arrays.sort(c);

            int i = 25;
            while (i > 0 && c[i] == c[25]) {
                i--;
            }

            return Math.max(tasks.length, (c[25] - 1) * (n + 1) + (25 - i));
        }
    }
}
