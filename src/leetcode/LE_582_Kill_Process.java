package leetcode;

import java.util.*;

public class LE_582_Kill_Process {
    /**
     * You have n processes forming a rooted tree structure. You are given two integer arrays pid and ppid, where pid[i]
     * is the ID of the ith process and ppid[i] is the ID of the ith process's parent process.
     *
     * Each process has only one parent process but may have multiple children processes. Only one process has ppid[i] = 0,
     * which means this process has no parent process (the root of the tree).
     *
     * When a process is killed, all of its children processes will also be killed.
     *
     * Given an integer kill representing the ID of a process you want to kill, return a list of the IDs of the
     * that will be killed. You may return the answer in any order.
     *
     * Example 1:
     * Input: pid = [1,3,10,5], ppid = [3,0,5,3], kill = 5
     * Output: [5,10]
     * Explanation: The processes colored in red are the processes that should be killed.
     *
     * Example 2:
     * Input: pid = [1], ppid = [0], kill = 1
     * Output: [1]
     *
     * Constraints:
     * n == pid.length
     * n == ppid.length
     * 1 <= n <= 5 * 104
     * 1 <= pid[i] <= 5 * 104
     * 0 <= ppid[i] <= 5 * 104
     * Only one process has no parent.
     * All the values of pid are unique.
     * kill is guaranteed to be in pid.
     *
     * Medium
     *
     * https://leetcode.com/problems/kill-process/
     */

    /**
     * HashMap + DFS
     *
     * Time and Space : O(n)
     */
    class Solution_DFS {
        public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
            if (kill== 0) return pid;

            /**
             * 关键是如何理解给定的pid和ppid, 以及如何创建HashMap.
             * map :
             * key -> parent id
             * val -> list that contains id of all parent's children
             *
             * !!!
             * The map not just create the tree structure, it also finds "kill" node so that
             * you can do dfs() next starting from "kill" node.
             */
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < pid.size(); i++) {
                map.computeIfAbsent(ppid.get(i), l -> new ArrayList<>()).add(pid.get(i));
            }

            List<Integer> res = new ArrayList<>();

            /**
             * After we have a map, you simply can find kill and its children, so the dfs() simple
             * traverse node starting from kill and add all of them.
             */
            dfs(kill, map, res);

            return res;
        }

        private void dfs(int id, Map<Integer, List<Integer>> map, List<Integer> res) {
            res.add(id);

            /**
             * This is a nice way to handle null value, if entry does not exist, just
             * create an empty list, so the for loop will not run.
             */
            List<Integer> next = map.getOrDefault(id, new ArrayList<Integer>());
            for (int n : next) {
                dfs(n, map, res);
            }
        }
    }

    public class Solution_BFS{
        public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
            if (kill == 0) return pid;

            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < pid.size(); ++i) {
                map.putIfAbsent(ppid.get(i), new ArrayList<>()).add(pid.get(i));
            }

            List<Integer> res = new ArrayList<>();
            Queue<Integer> q = new ArrayDeque<>();
            q.add(kill);

            while (!q.isEmpty()) {
                int n = q.poll();
                res.add(n);
                if (map.containsKey(n)) {
                    q.addAll(map.get(n));
                }
            }
            return res;
        }
    }
}
