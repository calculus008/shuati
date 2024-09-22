package leetcode;

import common.TreeNode;

import java.util.Stack;

/**
 * Created by yuank on 3/23/18.
 */
public class  LE_173_Binary_Search_Tree_Iterator {
    /**
        Implement an iterator over a binary search tree (BST).
        Your iterator will be initialized with the root node of a BST.

        Calling next() will return the next smallest number in the BST.

        Note:
        next() and hasNext() should run in average O(1) time and uses O(h) memory,
        where h is the height of the tree.
     **/

    /**
     * https://leetcode.com/articles/binary-search-tree-iterator/
     *
     * For next(), time is O(1)
     * the important thing to note here is that we only make call to goLeft() for nodes which have a right child.
     * Otherwise, we simply return. Also, even if we end up calling the helper function, it won't always process
     * N nodes. They will be much lesser. Only if we have a skewed tree would there be N.
     *
     * Thus, the amortized (average) time complexity for this function would still be O(1) which is what the
     * question asks for. We don't need to have a solution which gives constant time operations for every call.
     * We need that complexity on average and that is what we get.
     */
    class BSTIterator_Official_Solution {
        Stack<TreeNode> stack;

        public BSTIterator_Official_Solution(TreeNode root) {
            stack = new Stack<>();
            goLeft(root);
        }

        private void goLeft(TreeNode root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        public int next() {
            TreeNode cur = stack.pop();

            if (cur.right != null) {
                goLeft(cur.right);
            }

            return cur.val;
        }

        public boolean hasNext() {
            return stack.size() > 0;
        }
    }


    /**
     * !!!
     * This basically is a variation of iterative version of inorder traversal
     *
     * Refer to LE_94_Binary_Tree_Inorder_Traversal inorder iterative traversal
     *
     * Note :
     * Space : O(h), h is height of BST.
     */
    public class BSTIterator {
        private Stack<TreeNode> stack;
        private TreeNode cur;

        public BSTIterator(TreeNode root) {
            stack = new Stack<>();
            cur = root;
        }

        /**
         * return whether we have a next smallest number
         */
        public boolean hasNext() {
            /**
             * This is actually the outer while loop condition in LE_94_Binary_Tree_Inorder_Traversal
             */
            if (!stack.isEmpty() || cur != null) {
                return true;
            }

            return false;
        }

        /** @return the next smallest number
         * This is the steps inside the while loop in LE_94_Binary_Tree_Inorder_Traversal,
         * each call to this method will just move 1 step in BST.
         */
        public int next() {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            int val = cur.val;
            cur = cur.right;
            return val;
        }
    }

    public class BSTReverseIterator {
        private Stack<TreeNode> stack;
        private TreeNode cur;

        public BSTReverseIterator(TreeNode root) {
            stack = new Stack<>();
            cur = root;
        }

        public boolean hasNext() {
            if (!stack.isEmpty() || cur != null) return true;
            return false;
        }

        public int next() {
            while (cur != null) {
                stack.push(cur);
                cur = cur.right;
            }

            cur = stack.pop();
            int val = cur.val;
            cur = cur.left;
            return val;
        }
    }

    /**
     * 这道题常规解法用个vector存储inorder transverse的node value. 复杂度是O(1), O(n)
     * 我用的mirrors traversal（大家上讨论区直接搜就能搜到）， 把BST转换成sorted Linked
     * list.复杂度瞬减O(1), 教导了小哥一波。
     *
     * 大概就是用 LE_426_Convert_Binary_Search_Tree_To_Sorted_Doubly_Linked_List，
     * 把 BST convert to double linked list, 然后iterate.
     *
     * 如果要求 space O(1) 而且可以改变给定的BST, 可以用这个方法。
     */

}
