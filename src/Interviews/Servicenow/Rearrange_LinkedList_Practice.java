package Interviews.Servicenow;

import common.ListNode;

public class Rearrange_LinkedList_Practice {
    public ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode cur = head;
        ListNode pre = null;

        while (cur != null && cur.next != null) {
            pre = cur.next;
            cur.next = pre.next;
            pre.next = head;
            head = pre;
        }

        return head;
    }

    public void rearrange(ListNode head) {
        if (head == null) return;

        /**
         * Find middle of list
         */
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        /**
         * Break the list in two halfs
         */
        ListNode l1 = head;
        ListNode l2 = slow.next;
        slow.next = null;

        /**
         * reverse the 2nd half
         */
        l2 = reverse(l2);

        /**
         * Merge two list
         */
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while (l1 != null || l2 != null) {
            if (l1 != null) {
                cur.next = l1;
                l1 = l1.next;
                cur = cur.next;
            }

            if (l2 != null) {
                cur.next = l2;
                l2 = l2.next;
                cur = cur.next;
            }
        }

        head = dummy.next;
    }

}
