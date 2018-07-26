package leetcode;

/**
 * Created by yuank on 3/19/18.
 */
public class LE_160_Intersection_Of_Tow_Linked_List {
    /**
        Write a program to find the node at which the intersection of two singly linked lists begins.


        For example, the following two linked lists:

        A:          a1 → a2
                           ↘
                             c1 → c2 → c3
                           ↗
        B:     b1 → b2 → b3
        begin to intersect at node c1.


        Notes:

        If the two linked lists have no intersection at all, return null.
        The linked lists must retain their original structure after the function returns.
        You may assume there are no cycles anywhere in the entire linked structure.
        Your code should preferably run in O(n) time and use only O(1) memory.
     */

    //Time : O(m + n), Space : O(1)

    /**
     * Solution 1
     * Get length of both lists, move on the longer one until it is the same length as the shorter one,
     * then move both until the nodes from the 2 lists equal for the first time.
     */
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        int lenA = 1, lenB = 1;
        ListNode la = headA;
        ListNode lb = headB;
        while (la != null) {
            la = la.next;
            lenA++;
        }
        while (lb != null) {
            lb = lb.next;
            lenB++;
        }

        la = headA;
        lb = headB;
        if (lenA > lenB) {
            while (lenA != lenB) {
                la = la.next;
                lenA--;
            }
        } else {
            while (lenA != lenB) {
                lb = lb.next;
                lenB--;
            }
        }

        while (la != lb) {
            la = la.next;
            lb = lb.next;
        }

        return la;
    }

    /**
     * Solution 2
     *
     * 1.Intersection exists
     *   For each list, when come to the end, go to the head of the other list (create a circle of length m + n if intersection exists)
     *
     *     ____________________
     *    |                    |
     * A  1 -> 2 -> 3          |
     *               \         |
     *               7 -> 8 -> 9
     *              /          |
     * B      5 -> 6           |
     *        |________________|
     *
     * 2.No intersection
     *  "la == lb" happens when it reaches the end of the list, la is null
     *
     * It works because pointer A walks through List A and List B (since once it hits null, it goes to List B's head).
     * Pointer B also walks through List B and List A.
     * Regardless of the length of the two lists, the sum of the lengths are the same (i.e. a+b = b+a),
     * which means that the pointers sync up at the point of intersection.
     * If the lists never intersected, it's fine too, because they'll sync up at the end of each list, both of which are null.
     *
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode la = headA;
        ListNode lb = headB;

        while (la != lb) {
            la = la == null ? headB : la.next;
            lb = lb == null ? headA : lb.next;
        }

        return la;
    }
}
