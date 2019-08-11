package leetcode;

/**
 * Created by yuank on 8/29/18.
 */
public class LE_02_Add_Two_Numbers {
    /**
         You are given two non-empty linked lists representing two non-negative integers.
         The digits are stored in reverse order and each of their nodes contain a single digit.
         Add the two numbers and return it as a linked list.

         You may assume the two numbers do not contain any leading zero, except the number 0 itself.

         Example:

         Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
         Output: 7 -> 0 -> 8
         Explanation: 342 + 465 = 807.

         Medium
     */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int sum = 0;

        /**
         * "||" : it will take care the case that one number is longer than the other one
         */
        while (p1 != null || p2 != null) {
            if (p1 != null) {
                sum += p1.val;
                p1 = p1.next;
            }
            if (p2 != null) {
                sum += p2.val;
                p2 = p2.next;
            }

            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            sum = sum / 10;
        }

        if (sum != 0) {
            cur.next = new ListNode(1);
        }

        /**
         * !!!
         * return dummuy.next, NOT dummy
         */
        return dummy.next;
    }
}
