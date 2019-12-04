package leetcode;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_203_Remove_Linked_List_Elements {
    /*
        Remove all elements from a linked list of integers that have value val.

        Example
        Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6
        Return: 1 --> 2 --> 3 --> 4 --> 5
     */

    /*
        Case 1:
        cur
        dummy --> 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6
                 cur
        dummy --> 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6
                       cur
        dummy --> 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6
                       cur
        dummy --> 1 --> 2     6 --> 3 --> 4 --> 5 --> 6
                        |___________|
                             cur
        dummy --> 1 --> 2 --> 3 --> 4 --> 5 --> 6
                                   cur
        dummy --> 1 --> 2 --> 3 --> 4 --> 5 --> 6
                                        cur
        dummy --> 1 --> 2 --> 3 --> 4 --> 5 --> 6 --> null
                                         cur
        dummy --> 1 --> 2 --> 3 --> 4 --> 5 --> null

        Case 2:
        cur
        dummy --> 1 --> 1 --> null
        cur
        dummy     1 --> 1 --> null
          |_____________|
        cur
        dummy     1 --> 1 --> null
          |____________________|
        Here, cur does not move at all
    */

    /**
     * 考点 ：head 有可能就是要被remove的 （case 2 above)
     */
    public ListNode removeElements1(ListNode head, int val) {
        if (head == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        while (cur.next != null) {
            if (cur.next.val == val) {//!!!when we remove node, we don't move cur, just change next pointer
                cur.next = cur.next.next;
            } else {//!!!
                cur = cur.next;
            }
        }

        return dummy.next;//!!! 用dummy 一定要注意返回 dummy.next
    }

    //Recursive Solution
    public ListNode removeElements2(ListNode head, int val) {
        if (head == null) return head;
        head.next = removeElements2(head.next, val);
        return head.val == val ? head.next : head;
    }
}
