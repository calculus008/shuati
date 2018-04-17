package leetcode;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_143_Reorder_List {
    /*
        Given a singly linked list L: L0→L1→…→Ln-1→Ln,
        reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

        You must do this in-place without altering the nodes' values.

        For example,
        Given {1,2,3,4}, reorder it to {1,4,2,3}.
     */

    /*
        1.split the list in half.
        2.reverse the 2nd half of the list
        3.merge two lists
    */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode slow = head;
        ListNode fast = head;
        ListNode temp = null;//the position of the last node in the first half of the list

        while (fast != null && fast.next != null) {
            temp = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        temp.next = null; //unlink the first half with the second half
        ListNode l1 = head;
        ListNode l2 = reverse(slow);

        merge (l1, l2);
    }

    //See LE_206
    public ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode pre = null;
        ListNode cur = head;

        while (cur != null && cur.next != null) {//!!!
            pre = cur.next;
            cur.next = pre.next;
            pre.next = head;
            head = pre;
        }

        return head;
    }

    /*
        1 -> 2 -> 3 -> 4

        l1 : 1 -> 2
        l2 : 4 -> 3

    First loop:
              .   n1
        l1 : 1 -> 2
        l2 : 4 -> 3
                  n2

                  n1
        l1 : 1    2
             |
        l2 : 4 -> 3
                  n2

                 n1                  l1
        l1 : 1    2        1 -> 4 -> 2
             |  /     =>
        l2 : 4    3        3
                  n2       l2

    Second loop:
          .       l1    n1
        1 -> 4 -> 2 -> null

        l2    n2
        3 -> null

        1 -> 4 -> 2 -> 3  ("if (n1 == null) break;", merge complets)

    */
    public void merge(ListNode l1, ListNode l2) {
        while (l1 != l2) {
            ListNode n1 = l1.next;
            ListNode n2 = l2.next;
            l1.next = l2;
            if (n1 == null) break;
            l2.next = n1;

            l1 = n1;
            l2 = n2;
        }
    }
}
