package leetcode;

/**
 * Created by yuank on 10/1/18.
 */
public class LE_21_Merge_Two_Sorted_Lists {
    /**
         Merge two sorted linked lists and return it as a new list.
         The new list should be made by splicing together the nodes of the first two lists.

         Example:

         Input: 1->2->4, 1->3->4
         Output: 1->1->2->3->4->4
     */

    /**
     * Solution 1
     * Recurssion,
     * Time : O(n),
     * Space : O(1)
     * **/
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val < l2.val) {
            l1.next = mergeTwoLists1(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists1(l1, l2.next);
            return l2;
        }
    }

    /**
     * Solution 2
     * Iterative
     */

    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode last = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                last.next = l1;
                l1 = l1.next;
            } else {
                last.next = l2;
                l2 = l2.next;
            }
            last = last.next;//!!!
        }

        if (l1 != null) {
            last.next = l1;
        }

        if (l2 != null) {
            last.next = l2;
        }

        return dummy.next;
    }
}
