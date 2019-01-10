package leetcode;

import java.util.*;

public class LE_817_Linked_List_Components {
    /**
         We are given head, the head node of a linked list containing unique integer values.

         We are also given the list G, a subset of the values in the linked list.

         Return the number of connected components in G, where two values are connected
         if they appear consecutively in the linked list.

         Example 1:
         Input:
         head: 0->1->2->3
         G = [0, 1, 3]
         Output: 2
         Explanation:
         0 and 1 are connected, so [0, 1] and [3] are the two connected components.

         Example 2:
         Input:
         head: 0->1->2->3->4
         G = [0, 3, 1, 4]
         Output: 2
         Explanation:
         0 and 1 are connected, 3 and 4 are connected, so [0, 1] and [3, 4] are the two
         connected components.

         Note:
         If N is the length of the linked list given by head, 1 <= N <= 10000.
         The value of each node in the linked list will be in the range [0, N - 1].
         1 <= G.length <= 10000.
         G is a subset of all values in the linked list.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/graph/leetcode-817-linked-list-components/
     *
     * Two scans, use linked list property.
     * 1.1st scan, set node value to 1 if it in the dict, otherwise 0
     * 2.2nd scan, increase count when current node is 1 but next node is 0 or null.
     *
     * Time and Space : O(n)
     */
    class Solution1 {
        public int numComponents(ListNode head, int[] G) {
            if (null == head || G.length == 0) {
                return 0;
            }

            int res = 0;

            Set<Integer> set = new HashSet<>();
            for (int g : G) {
                set.add(g);
            }

            ListNode cur = head;
            while (cur != null) {
                if (set.contains(cur.val)) {
                    cur.val = 1;
                } else {
                    cur.val = 0;
                }
                cur = cur.next;
            }

            cur = head;
            while (cur != null) {
                if (cur.val == 1 && (cur.next == null || cur.next.val == 0)) {
                    res++;
                }
                cur = cur.next;
            }

            return res;
        }
    }

    /**
     * Graph Solution
     * First build graph, then DFS to count number of connected components
     *
     * We should build an undirected graph.
     */
    class Solution {
        public int numComponents(ListNode head, int[] G) {
            if (null == head || G.length == 0) {
                return 0;
            }

            Set<Integer> set = new HashSet<>();
            for (int g : G) {
                set.add(g);
            }

            Map<Integer, List<Integer>> graph = new HashMap<>();
            buildGraph(head, G, graph, set);

            Set<Integer> visited = new HashSet<>();
            int res = 0;

            /**
             * No need to iterate through linked list here,
             * we only care for valid elements which are in G,
             * so just iterate G. For case : 0 -> 1 -> 2 - >3, [0, ,1, 3],
             * 3 is the last element in linked list, so it's not in graph map.
             * But res still increases since it's a valid number in G.
             */
            for (int g : G) {
                if (visited.contains(g)) {
                    continue;
                }

                dfs(graph, g, visited);
                res++;
            }

            return res;
        }

        /**
         * Build an undirected graph
         */
        private void buildGraph(ListNode head, int[] G, Map<Integer, List<Integer>> graph, Set<Integer> set) {
            ListNode cur = head;
            while (cur.next != null) {
                int u = cur.val;
                int v = cur.next.val;

                if (set.contains(u) && set.contains(v)) {
                    if (!graph.containsKey(u)) {
                        graph.put(u, new ArrayList<>());
                    }
                    graph.get(u).add(v);

                    if (!graph.containsKey(v)) {
                        graph.put(v, new ArrayList<>());
                    }
                    graph.get(v).add(u);
                }
                cur = cur.next;
            }
        }

        private void dfs(Map<Integer, List<Integer>> graph, int key, Set<Integer> visited) {
            if (visited.contains(key)) {
                return;
            }

            if (graph.containsKey(key)) {
                visited.add(key);
                for (int next : graph.get(key)) {
                    dfs(graph, next, visited);
                }
            }
        }
    }
}