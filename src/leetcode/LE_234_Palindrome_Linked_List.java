package leetcode;

/**
 * Created by yuank on 4/2/18.
 */
public class LE_234_Palindrome_Linked_List {
    /**
        Given a singly linked list, determine if it is a palindrome.

        Follow up:
        Could you do it in O(n) time and O(1) space?
     */

    public boolean isPalindrome(ListNode head) {
        if (head == null) return true;

        ListNode middle = findMiddle(head);
        middle.next = reverse(middle.next);

        ListNode p = head;
        ListNode q = middle.next;
        while (p != null && q != null) {
            System.out.println("p=" + p.val + ",q="+q.val);
            if (p.val != q.val) return false;
            p = p.next;
            q = q.next;
        }

        return true;
    }

    /*
        s
        1 -> 2 -> 2 -> 1 -> null
             f
             s
        1 -> 2 -> 2 -> 1 -> null
                       f

        s
        1 -> 2 -> 3 -> 2 -> 1 -> null
             f
             s
        1 -> 2 -> 3 -> 2 -> 1 -> null
                       f
                  s
        1 -> 2 -> 3 -> 2 -> 1 -> null
                                  f
        关键是fast的初始值设在head.next,这样第二部分的开始的node是在slow的下一个。

        如果slow和fast都从head出发，奇数个node和偶数个node的结果有区别，偶数个的时候，slow 已经指向正确的node, 奇数时，则为slow.next
        s
        1 -> 2 -> 2 -> 1 -> null
        f
             s
        1 -> 2 -> 2 -> 1 -> null
                  f
                  s
        1 -> 2 -> 2 -> 1 -> null
                             f

        s
        1 -> 2 -> 3 -> 2 -> 1 -> null
        f
             s
        1 -> 2 -> 3 -> 2 -> 1 -> null
                  f
                  s
        1 -> 2 -> 3 -> 2 -> 1 -> null
                            f

    */
    public ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //LE_206
    public ListNode reverse(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;

        while (cur != null && cur.next != null) {
            pre = cur.next;
            cur.next = pre.next;
            pre.next = head;
            head = pre;
        }

        return head;
    }
}
