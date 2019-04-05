package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LE_1019_Next_Greater_Node_In_Linked_List {
    /**
     * We are given a linked list with head as the first node.
     * Let's number the nodes in the list: node_1, node_2, node_3, ... etc.
     *
     * Each node may have a next larger value: for node_i, next_larger(node_i)
     * is the node_j.val such that j > i, node_j.val > node_i.val, and j is
     * the smallest possible choice.  If such a j does not exist, the next
     * larger value is 0.
     *
     * Return an array of integers answer, where answer[i] = next_larger(node_{i+1}).
     *
     * Note that in the example inputs (not outputs) below, arrays such as [2,1,5]
     * represent the serialization of a linked list with a head node value of 2,
     * second node value of 1, and third node value of 5.
     *
     * Example 1:
     *
     * Input: [2,1,5]
     * Output: [5,5,0]
     *
     * Example 2:
     *
     * Input: [2,7,4,3,5]
     * Output: [7,0,5,5,0]
     *
     * Example 3:
     *
     * Input: [1,7,5,1,9,2,5,1]
     * Output: [7,9,9,9,0,5,0,0]
     *
     *
     * Note:
     *
     * 1 <= node.val <= 10^9 for each node in the linked list.
     * The given list has length in the range [0, 10000].
     *
     * Medium
     */

    /**
     * Mono Stack
     * TIme and Space : O(n)
     * https://zxi.mytechroad.com/blog/uncategorized/leetcode-weekly-contest-130/
     *
     * example :
     *
     * [2, 7, 4, 3, 5]
     *
     * res[4] = 0
     * push 5,
     * Stack: 5
     *
     * res[3] = 5
     * push 3,
     * Stack : 3, 5
     *
     * pop 3,
     * res[2] = 5
     * push 4,
     * Stack : 4, 5
     *
     * pop 4, 5
     * res[1] = 0
     * push 7
     * Stack : 7
     *
     * res[0] = 7
     * push 2
     * Stack : [2, 7]
     *
     * res = [7, 0, 5, 5, 0]
     *
     */
    class Solution {
        public int[] nextLargerNodes(ListNode head) {
            if (head == null) {
                return new int[]{};
            }

            /**
             * Need to process from right to left,
             * so must first put all ll elements into
             * a list
             */
            List<Integer> list = new ArrayList<>();
            ListNode cur = head;
            while (cur != null) {
                list.add(cur.val);
                cur = cur.next;
            }

            int n = list.size();
            int[] res = new int[n];

            Deque<Integer> stack = new ArrayDeque(n);
            /**
             * start from the end of the list
             */
            for (int i = n - 1; i >= 0; i--) {
                while (!stack.isEmpty() && stack.peek() <= list.get(i)) {
                    stack.pop();
                }
                res[i] = stack.isEmpty() ? 0 : stack.peek();
                stack.push(list.get(i));
            }

            return res;
        }
    }
}
