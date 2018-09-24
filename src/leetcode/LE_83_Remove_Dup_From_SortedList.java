package leetcode;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_83_Remove_Dup_From_SortedList {
    /**
        Given a sorted linked list, delete all duplicates such that each element appear only once.

        For example,
        Given 1->1->2, return 1->2.
        Given 1->1->2->3->3, return 1->2->3.
     */

    public static ListNode deleteDuplicates(ListNode head) {
        if (head.next == null || head == null) return head;

        ListNode cur = head;
        while (cur.next != null) {
            if(cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }

        return head;
    }
}
