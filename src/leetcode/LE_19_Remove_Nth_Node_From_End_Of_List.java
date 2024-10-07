package leetcode;

/**
 * Created by yuank on 9/5/18.
 */
public class LE_19_Remove_Nth_Node_From_End_Of_List {
    /**
         Given a linked list, remove the n-th node from the end of list and return its head.

         Example:

         Given linked list: 1->2->3->4->5, and n = 2.

         After removing the second node from the end, the linked list becomes 1->2->3->5.
         Note:

         Given n will always be valid.

         Follow up:

         Could you do this in one pass?

         Medium
     */

    public ListNode removeNthFromEnd(ListNode head, int n) {
        //!!! Must use dummy, bcause the input may have only one node and we try to delete it
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy; //dummy
        ListNode fast = dummy; //dummy

        //!!! 要check fast是否为NUll
        for (int i = 0; i <= n && fast != null; i++) {
            fast = fast.next;
        }

        //!!!"fast != null", NOT "fast.next != null),because you need to do "fast = fast.next".
        //   If fast is null, "fast = fast.next" will throw null pointer exception
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }
}
