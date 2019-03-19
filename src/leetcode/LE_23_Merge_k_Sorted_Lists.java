package leetcode;

import java.util.PriorityQueue;

/**
 * Created by yuank on 8/17/18.
 */
public class LE_23_Merge_k_Sorted_Lists {
    /**
         Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

         Example:

         Input:
         [
         1->4->5,
         1->3->4,
         2->6
         ]
         Output: 1->1->2->3->4->4->5->6
     */

    /**
     * Time : O(nlogk), n - number of lists, k - length of list
     * **/
    class Solution1 {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) return null;
            PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val); //!!!"<>"

            /**
             * !!!
             */
            ListNode dummy = new ListNode(0);
            ListNode cur = dummy;

            for (ListNode list : lists) {
                if (list != null) {//!!!
                    pq.add(list);
                }
            }

            while (!pq.isEmpty()) {//!!!
                cur.next = pq.poll();
                cur = cur.next;
                if (cur.next != null) {
                    pq.add(cur.next);
                }
            }

            return dummy.next;
        }
    }

    /**
     * Time : O(kn)
     * Space : O(1)
     */
    class Solution2 {
        public ListNode mergeKLists(ListNode[] lists) {
            if (null == lists || lists.length == 0) {
                return null;
            }

            ListNode dummy = new ListNode(0);
            ListNode cur = dummy;

            int k = lists.length;
            while (true) {
                int min = Integer.MAX_VALUE;
                int minRow = -1;

                for (int i = 0; i < k; i++) {
                    if (lists[i] != null) {
                        if (min > lists[i].val) {
                            min = lists[i].val;
                            minRow = i;
                        }
                    }
                }

                if (minRow < 0) {
                    break;
                }

                cur.next = lists[minRow];
                cur = cur.next;
                lists[minRow] = lists[minRow].next;
            }

            return dummy.next;
        }
    }
}
