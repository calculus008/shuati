package leetcode;

import common.TreeNode;

import java.util.*;

public class LE_919_Complete_Binary_Tree_Inserter {
    /**
     * A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, and
     * all nodes are as far left as possible.
     *
     * Write a data structure CBTInserter that is initialized with a complete binary tree and supports the following operations:
     *
     * CBTInserter(TreeNode root) initializes the data structure on a given tree with head node root;
     * CBTInserter.insert(int v) will insert a TreeNode into the tree with value node.val = v so that the tree remains
     * complete, and returns the value of the parent of the inserted TreeNode;
     * CBTInserter.get_root() will return the head node of the tree.
     *
     *
     * Example 1:
     * Input: inputs = ["CBTInserter","insert","get_root"], inputs = [[[1]],[2],[]]
     * Output: [null,1,[1,2]]
     *
     * Example 2:
     * Input: inputs = ["CBTInserter","insert","insert","get_root"], inputs = [[[1,2,3,4,5,6]],[7],[8],[]]
     * Output: [null,3,4,[1,2,3,4,5,6,7,8]]
     *
     * Note:
     * The initial given tree is complete and contains between 1 and 1000 nodes.
     * CBTInserter.insert is called at most 10000 times per test case.
     * Every value of a given or inserted node is between 0 and 5000.
     *
     * Medium
     */

    /**
     * This solution uses array representation of a Complete Binary Tree (similar idea used in heap implementation)
     *
     * Assume that these are the nodes stored in an array. So, the root will be the first element, and other elements are
     * stored in the level order traversal in an array.
     *
     *!!!Node at the nth index will have their children stored at 2n + 1th and 2n + 2th index in the array.
     *
     * 1.Initialize the array with the given binary tree using level order traversal
     * 2.when the new value is inserted, add it to the end of the list, get its parent, form the connection between parent and new node.
     * 3.0th element will always be the node.
     */
    class CBTInserter {
        List<TreeNode> tree;

        public CBTInserter(TreeNode root) {
            tree = new ArrayList<>();

            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                TreeNode cur = q.poll();
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
                tree.add(cur);
            }
        }

        public int insert(int v) {
            TreeNode node = new TreeNode(v);
            tree.add(node);
            int n = tree.size();

            /**
             * !!!
             * (n - 2) / 2
             */
            TreeNode parent = tree.get((n - 2) / 2);

            if (parent.left == null) {
                parent.left = node;
            } else {
                parent.right = node;
            }

            return parent.val;
        }

        public TreeNode get_root() {
            return tree.get(0);
        }
    }

    /**
     * Use Deque
     *
     * Consider all the nodes numbered first by level and then left to right. Call this the "number order" of the nodes.
     *
     * At each insertion step, we want to insert into the node with the lowest number (that still has 0 or 1 children).
     *
     * By maintaining a deque (double ended queue) of these nodes in number order, we can solve the problem. After inserting
     * a node, that node now has the highest number and no children, so it goes at the end of the deque. To get the node
     * with the lowest number, we pop from the beginning of the deque.
     */
    class CBTInserter2 {
        TreeNode root;
        Deque<TreeNode> deque;

        public CBTInserter2(TreeNode root) {
            this.root = root;
            deque = new LinkedList();
            Queue<TreeNode> q = new LinkedList();
            q.offer(root);

            // BFS to populate deque
            while (!q.isEmpty()) {
                TreeNode node = q.poll();
                /**
                 * only add node that has 0 or 1 child to deque, in other words, if the node has two children (so it
                 * is not possible to add child to it), we ignore it.
                 */
                if (node.left == null || node.right == null) deque.offerLast(node);

                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }

        public int insert(int v) {
            /**
             * Get the parent node of the newly inserted node
             */
            TreeNode parent = deque.peekFirst();
            /**
             * insert new noce into tree (Add to deque)
             */
            deque.offerLast(new TreeNode(v));

            if (parent.left == null) {
                parent.left = deque.peekLast();
            } else {
                parent.right = deque.peekLast();
                /**
                 * After a node is added to parent's right child position, the parent node is full (not possible to
                 * add new child), remove it from deque.
                 */
                deque.pollFirst();
            }

            return parent.val;
        }

        public TreeNode get_root() {
            return root;
        }
    }
}
