package leetcode;

public class LE_24_Swap_Nodes_In_Pairs {
    /**
     * Given a linked list, swap every two adjacent nodes and return its head.
     *
     * You may not modify the values in the list's nodes, only nodes itself may be changed.
     *
     * Example:
     *
     * Given 1->2->3->4, you should return the list as 2->1->4->3.
     */
    class Solution {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) return head;

            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode p1 = dummy;
            ListNode p2 = head;

            while (p2 != null && p2.next != null) {
                ListNode nextStart = p2.next.next;
                p1.next = p2.next;
                p2.next.next = p2;
                p2.next = nextStart;
                p1 = p2;
                p2 = p2.next;
            }

            return dummy.next;
        }
    }
}
