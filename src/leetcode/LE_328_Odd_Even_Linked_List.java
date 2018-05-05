package leetcode;

/**
 * Created by yuank on 5/4/18.
 */
public class LE_328_Odd_Even_Linked_List {
    /**
         Given a singly linked list, group all odd nodes together followed by the even nodes.
         Please note here we are talking about the node number and not the value in the nodes.

         You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.

         Example:
         Given 1->2->3->4->5->NULL,
         return 1->3->5->2->4->NULL.

         Note:
         The relative order inside both the even and odd groups should remain as it was in the input.
         The first node is considered odd, the second node even and so on ...

         Medium
     */

    //Time L: O(n), Space : O(1)
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenhead = even;

        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }
        odd.next = evenhead;

        return head;
    }
}
