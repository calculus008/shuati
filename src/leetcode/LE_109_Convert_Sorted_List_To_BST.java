package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/12/18.
 */
public class LE_109_Convert_Sorted_List_To_BST {
    /**
        Given a singly linked list where elements are sorted in ascending order,
        convert it to a height balanced BST.

        For this problem, a height-balanced binary tree is defined as a binary
        tree in which the depth of the two subtrees of every node never differ
        by more than 1.


        Example:

        Given the sorted linked list: [-10,-3,0,5,9],

        One possible answer is: [0,-3,9,-10,null,5],
        which represents the following height balanced BST:

              0
             / \
           -3   9
           /   /
         -10  5
     */

    /**
     * Time and Space : O(n)
     *
     * Use O(n) extra space (convert to arraylist)
     */
    class Solution2_Simplified {
        public TreeNode sortedListToBST(ListNode head) {
            List<Integer> list = new ArrayList<Integer>();

            while (head != null) {
                list.add(head.val);
                head = head.next;
            }

            return buildTree(list, 0, list.size() - 1);
        }

        public TreeNode buildTree(List<Integer> arr, int start, int end) {
            if (start > end) return null;

            int mid = start + (end - start) / 2;
            TreeNode node = new TreeNode(arr.get(mid));

            node.left = buildTree(arr, start, mid - 1);
            node.right = buildTree(arr, mid + 1, end);

            return node;
        }
    }

    /**
     * Simulate inorder and construct BST
     *
     * Solution3 in https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/solution/
     *
     * Could be the optimal solution, it achieves O(n) time like Solution2 but only uses O(logn) recursion
     * space like Solution1.
     *
     * The invariance that we maintain in this algorithm is that whenever we are done building the left half
     * of the BST, the head pointer in the linked list will point to the root node or the middle node
     * (which becomes the root). So, we simply use the current value pointed to by head as the root node and
     * progress the head node by once i.e. head = head.next
     *
     * 实际上就是按in order顺序移动head, 然后创建TreeNode (因为list是sorted).
     *
     * The only tricky part : how we can know when the recursion ends.
     *
     * That's why we first need to find out the length of the list, then pass start and end to recursion.
     * Here we don't really use start and end to restrive the node, it only helps us to know when to
     * end the recursion (start > end), in other words, start and end values only service as control
     * mechanism.
     *
     * Time : O(n)
     * Space : O(logn)
     */
    class Solution3 {
        ListNode head;

        public TreeNode sortedListToBST(ListNode head) {
            if (head == null) return null;

            this.head = head;
            int len = getSize(head);

            return helper(0, len - 1);
        }

        private int getSize(ListNode head) {
            ListNode runner = head;
            int count = 0;

            while (runner != null) {
                runner = runner.next;
                count++;
            }

            return count;
        }

        private TreeNode helper(int start, int end) {
            if (start > end) return null;

            int mid = start + (end - start) / 2;
            TreeNode left = helper(start, mid - 1);

            TreeNode root = new TreeNode(this.head.val);
            root.left = left;
            this.head = head.next;//!!!

            root.right = helper(mid + 1, end);

            return root;
        }
    }

    /**
     * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/solution/
     *
     * Time  : O(nlogn)
     * Space : O(n)
     *
     * Time Complexity: O(NlogN)
     * Suppose our linked list consists of N elements. For every list we pass to our recursive function,
     * we have to calculate the middle element for that list. For a list of size N, it takes N / 2 steps
     * to find the middle element i.e. O(N) to find the mid. We do this for every half of the original
     * linked list. From the looks of it, this seems to be an O(N^2) algorithm. However, on closer analysis,
     * it turns out to be a bit more efficient than O(N^2).
     *
     * Let's look at the number of operations that we have to perform on each of the halves of the linked list.
     * As we mentioned earlier, it takes N/2 steps to find the middle of a linked list with N elements.
     * After finding the middle element, we are left with two halves of size N / 2 each. Then, we find the middle
     * element for both of these halves and it would take a total of 2 times N / 4 steps for that. And similarly
     * for the smaller sublists that keep forming recursively. This would give us the following series of
     * operations for a list of size N.
     *
     * N / 2  + 2 * N / 4  + 4 * N / 8  + 8 * N / 16  ....
     *
     * Essentially, this is done logN times since we split the linked list in half every time.
     * Hence, the above equation becomes O(NlogN)
     *
     *
     * Space Complexity: O(logN).
     * Since we are resorting to recursion, there is always the added space complexity of the recursion stack
     * that comes into picture. This could have been O(N) for a skewed tree, but the question clearly states
     * that we need to maintain the height balanced property. This ensures the height of the tree to be bounded
     * by O(logN)). Hence, the space complexity is O(logN).
     *
     * The main problem with the above solution seems to be the middle element computation.
     * That takes up a lot of unnecessary time and this is due to the nature of the linked
     * list data structure.
     */
    class Solution1 {
        public TreeNode sortedListToBST(ListNode head) {
            if (head == null) return null;
            return helper(head, null);
        }

        public TreeNode helper(ListNode head, ListNode tail) {
            /**
             * !!!
             * head == tail
             */
            if (head == tail) return null;

            ListNode slow = head;
            ListNode fast = head;

            /**
             * !!!
             * list, use slow and fast pointers to find mid
             *
             * !!!
             * "fast != tail && fast.next != tail"
             *
             * NOT "fast != null && fast.next != null"
             */
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

    /**
     * Time and Space : O(n)
     *
     * Convert linked list into a list (ArrayList) so that we can access the mid with
     * index directly.
     *
     * Since we don't know the length of the linked list, we can't convert it to an
     * array in 1 pass. Therefore, use dynamic array (ArrayList) instead. This changes
     * the problem into LE_108_Convert_Sorted_Array_To_BST.
     *
     * Time Complexity: The time complexity comes down to just O(N) now since we convert
     * the linked list to an array initially and then we convert the array into a BST.
     * Accessing the middle element now takes O(1) time and hence the time complexity
     * comes down.
     *
     * Space Complexity: Since we used extra space to bring down the time complexity,
     * the space complexity now goes up to O(N) as opposed to just O(logN) in the
     * previous solution. This is due to the array we construct initially.
     */
    class Solution2 {

        private List<Integer> values;

        public Solution2() {
            this.values = new ArrayList<Integer>();
        }

        private void mapListToValues(ListNode head) {
            while (head != null) {
                this.values.add(head.val);
                head = head.next;
            }
        }

        private TreeNode convertListToBST(int left, int right) {
            // Invalid case
            if (left > right) {
                return null;
            }

            // Middle element forms the root.
            int mid = (left + right) / 2;
            TreeNode node = new TreeNode(this.values.get(mid));

            // Base case for when there is only one element left in the array
            if (left == right) {
                return node;
            }

            // Recursively form BST on the two halves
            node.left = convertListToBST(left, mid - 1);
            node.right = convertListToBST(mid + 1, right);
            return node;
        }

        public TreeNode sortedListToBST(ListNode head) {

            // Form an array out of the given linked list and then
            // use the array to form the BST.
            this.mapListToValues(head);

            // Convert the array to
            return convertListToBST(0, this.values.size() - 1);
        }
    }
}
