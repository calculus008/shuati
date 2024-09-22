package leetcode;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by yuank on 3/9/18.
 */
public class LE_99_Recover_BST {
    /**
        Two elements of a binary search tree (BST) are swapped by mistake.

        Recover the tree without changing its structure.

        Note:
        A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?

     */
    class Solution1_clean {// inorder traversal, find the two elements that are not sorted, then swap
        TreeNode first = null;
        TreeNode second = null;
        TreeNode pre = null;

        public void recoverTree1(TreeNode root) {
            if (root == null) return;

            helper(root);
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }

        public void helper(TreeNode root) {
            if (root == null) return;

            helper(root.left);

            if (pre != null && root.val <= pre.val) {
                if (first == null) {
                    first = pre;
                }
                if (first != null) {
                    second = root;
                }
            }

            pre = root;

            helper(root.right);
        }
    }

    /**
     * Solution 1 : Recursion
     *
     * Time : O(n)
     * Space : O(h), h - height of the tree
     *
     * https://leetcode.com/problems/recover-binary-search-tree/discuss/32535/No-Fancy-Algorithm-just-Simple-and-Powerful-In-Order-Traversal
     *
     * Still the basic inorder traversal of a BST. If every element in BST is in right place, inorder traversal output is sorted.
     * Since we have 2 elements swapped (at the wrong places), therefore, we will find two adjacent elements during inorder traversal
     * are NOT in sorted order. Find those 2 and swap back.
     **/
    class Solution1 {
        TreeNode first = null;
        TreeNode second = null;
        TreeNode pre = null;

        public void recoverTree1(TreeNode root) {
            if (root == null) return;

            helper(root);
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }

        public void helper(TreeNode root) {
            if (root == null) return;

            helper(root.left);

            if (pre != null && root.val <= pre.val) {
                /**
                 * !!!
                 * Must write 2 separate "if", CAN'T use "if-else".
                 * This is for the case that the 2 swapped nodes are adjacent to each other and in pre and root.
                 * For example :
                 * 1 3 2 4
                 *
                 * When pre is 3, root is 2. We find first and second in the same recursion level, etc -
                 * we first set first to 2, then check if first is null immediately, set second to 2.
                 *
                 * If use "if - else", we will not be able to find second for this case
                 *
                 */
                if (first == null) {
                    first = pre;
                }
                if (first != null) {
                    second = root;
                }
            }

            pre = root;

            helper(root.right);
        }

        class Solution2 {
            public void swap(TreeNode a, TreeNode b) {
                int tmp = a.val;
                a.val = b.val;
                b.val = tmp;
            }

            public void recoverTree(TreeNode root) {
                Deque<TreeNode> stack = new ArrayDeque();
                TreeNode first = null, second = null, pred = null;

                while (!stack.isEmpty() || root != null) {
                    while (root != null) {
                        stack.add(root);
                        root = root.left;
                    }

                    root = stack.removeLast();
                    if (pred != null && root.val < pred.val) {
                        second = root;

                        if (first == null) {
                            first = pred;
                        } else {
                            break;
                        }
                    }
                    pred = root;
                    root = root.right;
                }

                swap(first, second);
            }
        }
    }

    //Solution 2: Constant space, use Morris Traversal
    //https://leetcode.com/problems/recover-binary-search-tree/discuss/32559/detail-explain-about-how-morris-traversal-finds-two-incorrect-pointer

}
