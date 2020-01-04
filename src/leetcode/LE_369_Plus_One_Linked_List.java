package leetcode;

public class LE_369_Plus_One_Linked_List {
    /**
     * Given a non-negative integer represented as non-empty a singly linked list of digits, plus one to the integer.
     *
     * You may assume the integer do not contain any leading zero, except the number 0 itself.
     *
     * The digits are stored such that the most significant digit is at the head of the list.
     *
     * Example :
     *
     * Input: [1,2,3]
     * Output: [1,2,4]
     *
     * Medium
     */

    /**
     * Key insight:
     * We know 9 + 1 will have carry to the next digit, since we only add 1,
     * the only case that a digit will generate carry is that it is 9.
     *
     * Therefore, if we have multiple continous 9 at the end, they will all be
     * set to 0, then we only need find the first none 9 digit (from head to tail)
     * and add one to it.
     *
     * Example:
     * Given
     * 1->3->5->9->9->9
     *
     * target
     *    |
     * dummy(0)->1->9->5->9->9->9
     *            target
     *              |
     * dummy(0)->1->9->5->9->9->9
     *
     * dummy(0)->1->9->6->9->9->9
     *
     * dummy(0)->1->9->6->0->0->0
     *           ^
     *
     * Given
     * 9->9->9
     *
     * dummy(0)->9->9->9
     *
     * target
     *   |
     * dummy(0)->9->9->9
     *
     * dummy(1)->9->9->9
     *
     * dummy(1)->0->0->0
     *   ^
     */
    public class Solution {
        public ListNode plusOne(ListNode head) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;

            ListNode lastNotNine = dummy;
            ListNode node = head;

            while (node != null) {
                if (node.val != 9) {
                    lastNotNine = node;
                }
                node = node.next;
            }

            lastNotNine.val++;
            node = lastNotNine.next;

            while (node != null) {
                node.val = 0;
                node = node.next;
            }

            return dummy.val == 1 ? dummy : dummy.next;
        }
    }

    /**
     * Reverse twice, not very efficient
     */
    class Solution_My {
        public ListNode plusOne(ListNode head) {
            if (head == null) return head;

            ListNode a = reverse(head);
            ListNode pre = a;
            ListNode h = a;

            int cur = 0, carry = 0;

            carry = (a.val + 1) / 10;
            a.val = (a.val + 1) % 10;
            a = a.next;

            while ( a != null && carry != 0) {
                int sum = a.val + carry;
                cur = sum % 10;
                carry = sum / 10;
                a.val = cur;
                pre = a;
                a = a.next;
            }

            if (carry != 0) {
                ListNode node = new ListNode(carry);
                pre.next = node;
            }

            return reverse(h);
        }

        private ListNode reverse(ListNode head) {
            ListNode pre = null;
            ListNode cur = head;

            while (cur != null && cur.next != null) {
                pre = cur.next;
                cur.next = pre.next;
                pre.next = head;
                head = pre;
            }

            return head;
        }
    }
}
