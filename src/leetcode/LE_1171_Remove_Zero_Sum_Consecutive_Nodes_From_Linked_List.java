package leetcode;

import java.util.*;

public class LE_1171_Remove_Zero_Sum_Consecutive_Nodes_From_Linked_List {
    /**
     * Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0
     * until there are no such sequences.
     *
     * After doing so, return the head of the final linked list.  You may return any such answer.
     *
     * (Note that in the examples below, all sequences are serializations of ListNode objects.)
     *
     * Example 1:
     * Input: head = [1,2,-3,3,1]
     * Output: [3,1]
     * Note: The answer [1,2,1] would also be accepted.
     *
     * Example 2:
     * Input: head = [1,2,3,-3,4]
     * Output: [1,2,4]
     *
     * Example 3:
     * Input: head = [1,2,3,-3,-2]
     * Output: [1]
     *
     * Constraints:
     * The given linked list will contain between 1 and 1000 nodes.
     * Each node in the linked list has -1000 <= node.val <= 1000.
     *
     * Medium
     *
     * https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/
     */

    /**
     *
     */
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    /**
     * Key Insights:
     * 1.If there is a section of the list sums to 0, then the accumulated sum value will appear multiple times.
     * 2.Need to consider the case that the given head will be deleted. Therefore need to have a dummy node which
     *   points to head.
     * 3.When there's a section summing to 0, for example:
     *  Given values   [3,4,2,-6,1,1, 5,-6]
     *  Sums           [3,7,9, 3,4,5,10, 4]
     *                  *      * *       *
     *  After deleting [3,       1,     -6]
     *
     *  You delete the section from the next element of repeating sum value x to the FINAL node that has
     *  accumulated sum as x. Note: you only care about the last one among the all nodes that have accumulated
     *  sum as x.
     *
     */
    class Solution {
        public ListNode removeZeroSumSublists(ListNode head) {
            if (head == null) return head;

            Map<Integer, ListNode> map = new HashMap<>();

            /**
             * !!!
             * Use dummy node in front of the list to deal with the case that the given head is deleted.
             */
            ListNode dummy = new ListNode(0);
            map.put(0, dummy);

            dummy.next = head;
            ListNode runner = head;

            /**
             * First pass, get assumulated sum into a map, since it is a linked list, so there's no index,
             * just save the ListNode itself instead (!!!)
             * You will only save the last node that has accumulated sum as x.
             */
            int sum = 0;
            while (runner != null) {
                sum += runner.val;
                map.put(sum, runner);
                runner = runner.next;
            }

            /**
             * 2nd pass, delete nodes.
             */
            sum = 0;
            runner = dummy;
            while (runner != null) {
                sum += runner.val;
                /**
                 * There's no repeating accumulated sum x, this step just pointing runner node to its next one.
                 */
                runner.next = map.get(sum).next;
                runner = runner.next;
            }

            return dummy.next;
        }
    }
}
