package leetcode;

public class LE_708_Insert_Into_A_Sorted_Circular_Linked_List {
    /**
     * Given a node from a Circular Linked List which is sorted in ascending order,
     * write a function to insert a value insertVal into the list such that it remains
     * a sorted circular list. The given node can be a reference to any single node
     * in the list, and may not be necessarily the smallest value in the circular list.
     *
     * If there are multiple suitable places for insertion, you may choose any place
     * to insert the new value. After the insertion, the circular list should remain
     * sorted.
     *
     * If the list is empty (i.e., given node is null), you should create a new single
     * circular list and return the reference to that single node. Otherwise, you should
     * return the original given node.
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/solution/
     *
     * 1.Find insertion point :
     * preVal < insertVal && insertVal <= curVal)
     *
     * insertVal = 4
     *      pre cur
     * 1->2->3->5->6->7
     * |______________|
     *
     * 1.1 insertVal == preVal
     *
     *
     * 2.insertVal is out of the range:
     *  2.1 insertVal is bigger than the largest
     *  curVal < preVal && preVal < insertVal
     *                   inerstVal = 8
     * cur             pre
     * 1->2->3->5->6->7
     * |______________|
     *
     *  2.2 insertVal is smaller thatn the smallest
     *  insertVal < curVal && curVal < preVal
     *
     *  insertVal = 0
     *   cur             pre
     *    1->2->3->5->6->7
     *    |______________|
     *
     * 3.Uni value in list
     *
     * 1->1->1->1->1
     * |___________|
     *
     * 4.Empty list
     */
    class Solution {
        class Node {
            public int val;
            public Node next;

            public Node() {}

            public Node(int _val) {
                val = _val;
            }

            public Node(int _val, Node _next) {
                val = _val;
                next = _next;
            }
        }

        public Node insert(Node head, int insertVal) {
            if (head == null) {//#4
                Node start = new Node(insertVal);
                start.next = start;
                return start;
            }

            Node pre = head;
            Node cur = head.next;

            while (cur != head) {
                int preVal = pre.val;
                int curVal = cur.val;

                if ((preVal <= insertVal && insertVal <= curVal)
                        || (curVal < preVal && (insertVal > preVal || insertVal < curVal))){
                    break;
                }

                pre = cur;
                cur = cur.next;
            }

            Node n = new Node(insertVal);
            pre.next = n;
            n.next = cur;

            return head;
        }
    }
}
