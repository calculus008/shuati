package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_61_Rotate_List {
    /*
        Given a list, rotate the list to the right by k places, where k is non-negative.

        Example:

        Given 1->2->3->4->5->NULL and k = 2,

        return 4->5->1->2->3->NULL.
     */

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0 ) return head;

        ListNode runner = head;
        int len = 1;
        while (runner.next != null) {
            runner = runner.next;
            len++;
        }

        runner.next = head;
        //!!! i starts at "1", head already points to the first element.
        for (int i = 1; i < len - k % len; i++) {
            head = head.next;
        }

        ListNode res = head.next;
        head.next = null;

        return res;
    }
}
