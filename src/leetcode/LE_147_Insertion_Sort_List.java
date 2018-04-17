package leetcode;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_147_Insertion_Sort_List {
    /*
        Sort a linked list using insertion sort.
     */

    //Time : O(n ^ 2), Space : O(1)
    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0); //It's possible we need to insert at the starting position, we must use dummy here.
        dummy.next = head;
        ListNode temp = null, pre = null;
        ListNode cur = head;

        while (cur != null && cur.next != null) {
            if (cur.val <= cur.next.val) {
                cur = cur.next;
            } else {
                temp = cur.next;//move cur.next, 移动的node用temp记录。
                cur.next = temp.next;
                pre = dummy;//从头开始找inserttion point
                while (pre.next.val <= temp.val) {//看的是pre的下一个node的值（所以pre是从dummy开始的）。
                    pre = pre.next;
                }
                temp.next = pre.next;
                pre.next = temp;
            }
        }

        return dummy.next;
    }
}
