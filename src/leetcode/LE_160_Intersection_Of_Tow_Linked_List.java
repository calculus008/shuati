package leetcode;

/**
 * Created by yuank on 3/19/18.
 */
public class LE_160_Intersection_Of_Tow_Linked_List {
    /*
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
}
