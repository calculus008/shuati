package leetcode;

import java.util.Random;

public class LE_382_Linked_List_Random_Node {
    /**
     * Given a singly linked list, return a random node's value from the linked list.
     * Each node must have the same probability of being chosen.
     *
     * Follow up:
     * What if the linked list is extremely large and its length is unknown to you?
     * Could you solve this efficiently without using extra space?
     *
     * Example:
     *
     * // Init a singly linked list [1,2,3].
     * ListNode head = new ListNode(1);
     * head.next = new ListNode(2);
     * head.next.next = new ListNode(3);
     * Solution solution = new Solution(head);
     *
     * // getKthSmallest() should return either 1, 2, or 3 randomly.
     * Each element should have equal probability of returning.
     * solution.getKthSmallest();
     */

    /**
     * https://leetcode.com/problems/linked-list-random-node/discuss/85659/Brief-explanation-for-Reservoir-Sampling
     *
     * Reservoir Sampling:
     * Choose k entries from n numbers. Make sure each number is selected with the probability of k/n
     * This problem is the special case where k=1.
     *
     * https://gregable.com/2007/10/reservoir-sampling.html
     * http://blog.jobbole.com/42550/
     */
    public class Solution {
        ListNode head;
        Random random;

        public Solution(ListNode h) {
            head = h;
            random = new Random();
        }

        public int getRandom() {
            ListNode c = head;
            int res = c.val;

            for (int i = 1; c.next != null; i++) {
                c = c.next;

                if (random.nextInt(i + 1) == i) {
                    res = c.val;
                }
            }

            return res;
        }

        public int getRandom1() {
            Random rand = new Random();
            int count = 0;
            ListNode node = head;
            ListNode candidate = head;

            while (true) {
                if (node == null) {
                    break;
                }

                if (rand.nextInt(count + 1) == count) {
                    candidate = node;
                }

                node = node.next;
                count++;
            }
            return candidate.val;
        }
    }
}
