package leetcode;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_82_Remove_Dup_From_SortedList_II {
    /*
        Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

        For example,
        Given 1->2->3->3->4->4->5, return 1->2->5.
        Given 1->1->1->2->3, return 2->3.
     */

    //A follow up question after LE_83
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) return head;

        //kye is to creae a dummy node and its next field points to the head because the head node could be deleted, just having a cur node point to head is not enough.
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode p = dummy;

        while (p.next != null && p.next.next != null) {
            if (p.next.val == p.next.next.val) {
                int num = p.next.val;
                while (p.next != null && p.next.val == num) {
                    p.next = p.next.next;
                }
            } else {
                p = p.next;
            }
        }
        return dummy.next;
    }
}
