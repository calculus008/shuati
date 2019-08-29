package leetcode;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_116_Populating_Next_Right_Pointers_In_Each_Node {
    /**
        Given a binary tree

            struct leetcode.TreeLinkNode {
              leetcode.TreeLinkNode *left;
              leetcode.TreeLinkNode *right;
              leetcode.TreeLinkNode *next;
            }

        Populate each next pointer to point to its next right node.
        If there is no next right node, the next pointer should be set to NULL.

        Initially, all next pointers are set to NULL.

        Note:

        You may only use constant extra space.
        You may assume that it is a perfect binary tree
        (ie, all leaves are at the same level, and every parent has two children).

        For example,
        Given the following perfect binary tree,
                 1
               /  \
              2    3
             / \  / \
            4  5  6  7
        After calling your function, the tree should look like:
                 1 -> NULL
               /  \
              2 -> 3 -> NULL
             / \  / \
            4->5->6->7 -> NULL
     */

    /**
     * Huahua's version:
     * https://zxi.mytechroad.com/blog/tree/leetcode-116-populating-next-right-pointers-in-each-node/
     *
     * Time  : O(n)
     * Space : O(logn), height of the tree
     */
    class Solution {
        public TreeLinkNode connect(TreeLinkNode root) {
            /**
             * !!!
             * since it's a complete tree, if left child is null, right child must be null.
             * it is a leave node, we can return now.
             */
            if (root == null || root.left == null) return root;

            root.left.next = root.right;

            if (root.next != null) {
                root.right.next = root.next.left;
            }

            connect(root.left);
            connect(root.right);

            return root;
        }
    }

    //Time and Space : O(n)
    //Solution 1 : Recursion
    public static void connect1(TreeLinkNode root) {
        if (root == null) return;

        /**
         * 题意保证只要有一个child不为null,另一个也一定不为null.
         * 只有叶子节点例外。
         */
        if (root.left != null) {
            root.left.next = root.right;
        }

        /**
         * !!!root.right != null
         */
        if (root.next != null && root.right != null) {
            root.right.next = root.next.left;
        }

        connect1(root.left);
        connect1(root.right);
    }

    //Solution 2 : Iterative
    public void connect2(TreeLinkNode root) {
        if (root == null) return;
        TreeLinkNode start = root;

        while (start != null) {
            TreeLinkNode cur = start;

            /**
             * process each LEVEL
             */
            while (cur != null) {
                if (cur.left != null) {
                    cur.left.next = cur.right;
                }

                if (cur.right != null && cur.next != null) {
                    cur.right.next = cur.next.left;
                }

                cur = cur.next;
            }

            /**
             * move to the next level
             */
            start = start.left;
        }
    }
}
