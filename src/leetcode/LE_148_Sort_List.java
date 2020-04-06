package leetcode;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_148_Sort_List {
    /**
        Sort a linked list in O(n log n) time using constant space complexity.
     */

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode slow = head;
        ListNode fast = head;
        ListNode pre = null;
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next; //!!! fast.next.next
        }
        pre.next = null;

        return merge(sortList(head), sortList(slow));
    }

    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }

        return dummy.next;
    }
}
