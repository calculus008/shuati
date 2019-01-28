package leetcode;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_142_Linked_List_Cycle_II {
    /**
        Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

        Note: Do not modify the linked list.

        Follow up:
        Can you solve it without using extra space?
     */

    //Time : O(n), Space : O(1)

    //http://www.cnblogs.com/hiddenfox/p/3408931.html
    //因为fast的速度是slow的两倍，所以fast走的距离是slow的两倍，有 2(a+b) = a+b+c+b，可以得到a=c（这个结论很重要！）。

    //More concise version
    public ListNode detectCycle1(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                fast = head;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }

                return slow;
            }
        }

        return null;
    }

    public static ListNode detectCycle2(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) break;
        }

        if (slow == fast) {
            fast = head;
            while (fast != slow) {
                fast = fast.next;
                slow = slow.next;
            }

            return slow;
        }

        return null;
    }


}
