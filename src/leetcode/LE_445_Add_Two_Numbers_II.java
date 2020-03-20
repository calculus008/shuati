package leetcode;

import java.util.Stack;

public class LE_445_Add_Two_Numbers_II {
    /**
     * You are given two non-empty linked lists representing two non-negative integers.
     * The most significant digit comes first and each of their nodes contain a single
     * digit. Add the two numbers and return it as a linked list.
     *
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     *
     * Follow up:
     * What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
     *
     * Example:
     *
     * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 8 -> 0 -> 7
     *
     * Medium
     */

    /**
     * Compare with LE_02_Add_Two_Numbers:
     *
     * 1.Not in reverse order. Can use stack instead of reversing the linked list.
     * 2.Insert node at the head, not at the tail
     */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) return null;

        Stack<ListNode> s1 = new Stack<>();
        Stack<ListNode> s2 = new Stack<>();

        ListNode t1 = l1;
        ListNode t2 = l2;

        while (t1 != null) {
            s1.push(t1);
            t1 = t1.next;
        }

        while (t2 != null) {
            s2.push(t2);
            t2 = t2.next;
        }

        int sum = 0;
        ListNode dummy = new ListNode(0);
        // ListNode cur = dummy;

        while (!s1.isEmpty() || !s2.isEmpty()) {
            if (!s1.isEmpty()) {
                sum += s1.pop().val;
            }

            if (!s2.isEmpty()) {
                sum += s2.pop().val;
            }

            ListNode n = new ListNode(sum % 10);
            n.next = dummy.next;
            dummy.next = n;
            sum = sum / 10;
        }

        if (sum != 0) {
            ListNode n = new ListNode(1);
            n.next = dummy.next;
            dummy.next = n;
        }

        return dummy.next;
    }
}
