package leetcode;

/**
 * Created by yuank on 3/8/18.
 */
public class LE_206_Reverse_LinkedList {
    /*
    Reverse a singly linked list.
     */

    /*           h
        null     3 -> 4 -> 5
         p       c

                 h    p
                 3 -> 4 -> 5
                 c

                 h
                  _________
                 |         |
                 3    4 -> 5
                 c    p


                 h
                  _________
                 |         |
                 3 <- 4    5
                 c    p


                  _________
                 |         |
                 3 <- 4    5  =  4 -> 3 -> 5
                 c    p          p    c
                      h          h

        =============next loop================

                h
                4 -> 3 -> 5 -> NULL
                p    c
                      ___________
                h    |           |
                4 -> 3    5    NULL
                     c    p


                      ___________
                h    |           |
                4 -> 3    5    NULL
                     c    p
                |         |
                 ---------

                     ___________
                    |     h     |        h
                4 -> 3    5    NULL   =  5 -> 4 -> 3
                     c    p              p         c
                |         |
                 ---------
     */

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode cur = head;
        ListNode pre = null;
        while (cur != null && cur.next != null)
        {
            pre = cur.next;
            cur.next = pre.next;
            pre.next = head;
            head = pre;
        }

        return head;
    }
}
