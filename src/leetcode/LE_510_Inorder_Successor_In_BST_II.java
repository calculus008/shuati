package leetcode;

public class LE_510_Inorder_Successor_In_BST_II {
    /**
     * Given a binary search tree and a node in it,
     * find the in-order successor of that node in the BST.
     *
     * The successor of a node p is the node with the smallest
     * key greater than p.val.
     *
     * You will have direct access to the node but not to the
     * root of the tree. Each node will have a reference to its parent node.
     *
     * Medium
     *
     *       #2
     *      /
     *     x
     *      \
     *      #1
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
            if (x.right != null) {//#1
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
