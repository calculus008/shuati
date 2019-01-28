package leetcode;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_141_Linked_List_Cycle {
    /**
        Given a linked list, determine if it has a cycle in it.

        Follow up:
        Can you solve it without using extra space?
     */

    //Time : O(n), Space : O(1)
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast)
                return true;

        }

        return false;
    }

    public boolean hasCycle1(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
