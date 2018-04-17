package leetcode;

/**
 * Created by yuank on 3/12/18.
 */
public class LE_109_Convert_Sorted_List_To_BST {
    /*
        Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

        For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.


        Example:

        Given the sorted linked list: [-10,-3,0,5,9],

        One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

              0
             / \
           -3   9
           /   /
         -10  5
     */

    //Important
    public static TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        return helper(head, null);
    }

    public static TreeNode helper(ListNode head, ListNode tail) {
        if (head == tail) return null;

        ListNode slow = head;
        ListNode fast = head;
        while (fast != tail && fast.next != tail) {//!!!!!
            fast = fast.next.next;
            slow = slow.next;
        }

        TreeNode node = new TreeNode(slow.val);
        node.left = helper(head, slow);
        node.right = helper(slow.next, tail);
        return node;
    }
}
