package leetcode;

public class LE_510_Inorder_Successor_In_BST_II {
    /**
     * Given a binary search tree and a node in it,
     * find the in-order successor of that node in the BST.
     *
     * The successor of a node p is the node with the smallest
     * key greater than p.val.
     *
     * (!!!)
     * You will have direct access to the node but not to the
     * root of the tree. Each node will have a reference to its parent node.(!!!)
     *
     * Medium
     *
     *  #1  x    y is the right child of x
     *      \
     *      y
     *
     *  #2    y    x is the left child of y
     *       /
     *      x
     *  For this case, current node has no right child, then its successor is somewhere upper in the tree.
     *  To find the successor, go up till the node that is left child of its parent. The answer is the parent.
     *  When we go up to parent, we don't know if current node is the left child, so we need to check if
     *  parent node val is bigger than current value.
     *
     *  #a. Successor of 25 is 33 (25 is left child of 33)
     *      2
     *     / \
     *    1  33 <-
     *       /
     *      25 <-
     *
     *  #b. Successor of 13 is 25 (13 is not left child of any node, keep moving up until find
     *      parent val that is bigger than 13)
     *        25 <-
     *        /
     *       11
     *      / \
     *     7  12
     *         \
     *         13 <-
     *
     *  Beware that there could be no successor (= null successor) in such a situation.
     *
     */

    /**
     * https://leetcode.com/articles/inorder-successor-in-a-bst-ii/
     *
     * Time  : O(h), h is height of the tree, worst case is O(n), average is O(logn)
     * Space : O(1)
     */
    class Solution {
        class Node {
            public int val;
            public Node left;
            public Node right;
            public Node parent;
        }

        public Node inorderSuccessor(Node x) {
            Node res = null;
            if (x.right != null) {//#1, 走之字
                res = x.right;
                while (res.left != null) {
                    res = res.left;
                }
            } else {//#2
                res = x.parent;
                while (res != null && res.val < x.val) {
                    res = res.parent;
                }
            }
            return res;
        }
    }
}
