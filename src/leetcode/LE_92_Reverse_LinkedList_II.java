package leetcode;

/**
 * Created by yuank on 3/8/18.
 */
public class LE_92_Reverse_LinkedList_II {
    /*
        Reverse a linked list from position m to n. Do it in-place and in one-pass.

        For example:
        Given 1->2->3->4->5->NULL, m = 2 and n = 4,

        return 1->4->3->2->5->NULL.

        Note:
        Given m, n satisfy the following condition:
        1 ≤ m ≤ n ≤ length of list.
     */


    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = dummy.next;

        for (int i = 1; i < m; i++) {
            cur = cur.next;
            pre = pre.next;
        }

        //use the code from LE_206
        ListNode h = cur; //相当于206中的head. 最后是反转部分的第一个node.
        ListNode p = pre; //记住反转部分的前一个node,反转后再与h连接。
        for (int i = 0; i < n - m; i++) {
            pre = cur.next;
            cur.next = pre.next;
            pre.next = h; //!!! set pre points to h, not cur
            h = pre;
        }
        p.next = h;
        return dummy.next;
    }
}
