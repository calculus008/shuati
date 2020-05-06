package leetcode;

import common.TreeNode;

public class LE_450_Delete_Node_In_A_BST {
    /**
         Given a root node reference of a BST and a key, delete the node with the given key in the BST.
         Return the root node reference (possibly updated) of the BST.

         Basically, the deletion can be divided into two stages:

         Search for a node to remove.
         If the node is found, delete the node.
         Note: Time complexity should be O(height of tree).

         Example:

         root = [5,3,6,2,4,null,7]
         key = 3

              5
             / \
            3   6
           / \   \
         2   4   7

         Given key to delete is 3. So we find the node with value 3 and delete it.

         One valid answer is [5,4,6,2,null,null,7], shown in the following BST.

              5
             / \
           4   6
          /     \
         2       7

         Another valid answer is [5,2,6,null,4,null,7].

           5
          / \
         2   6
         \   \
         4   7

         Medium
     */

    class Solution {
        /**
         * One step right and then always left
         */
        public int successor(TreeNode root) {
            root = root.right;
            while (root.left != null) root = root.left;
            return root.val;
        }

        /**
         * One step left and then always right
         */
        public int predecessor(TreeNode root) {
            root = root.left;
            while (root.right != null) root = root.right;
            return root.val;
        }

        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) return null;

            if (key > root.val) {// delete from the right subtree
                root.right = deleteNode(root.right, key);
            } else if (key < root.val) {// delete from the left subtree
                root.left = deleteNode(root.left, key);
            } else { // delete the current node
                if (root.left == null && root.right == null) {// the node is a leaf
                    root = null;
                } else if (root.right != null) {// the node is not a leaf and has a right child
                    root.val = successor(root);
                    root.right = deleteNode(root.right, root.val);
                } else { // the node is not a leaf, has no right child, and has a left child
                    root.val = predecessor(root);
                    root.left = deleteNode(root.left, root.val);
                }
            }

            return root;
        }
    }


    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-450-delete-node-in-a-bst/
     *
     * See Slides above fo all cases
     *
     * Important idea : for each recursion call, we only look at the local state (tree stars from given root)
     *                  as for how current root will be linked to upper level, it will be taken care of when
     *                  recursion returns the previous level.
     * Time  : O(h)
     * Space : O(h)
     */

    class Solution_Recursive_Copy_Delete {
        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) return root;

            if (key > root.val) {
                root.right = deleteNode(root.right, key);
            } else if (key < root.val) {
                root.left = deleteNode(root.left, key);
            } else {//==
                if (root.left != null && root.right != null) {
                    //find the smallest element of its right subtree
                    TreeNode newNode = root.right;
                    while (newNode.left != null) {
                        newNode = newNode.left;
                    }

                    /**
                     * Copy value and delete :
                     * here just copy value of newNode to root, then
                     * recursively delete node with newNode value in
                     * substring root.right.
                     *
                     * There's problem with this solution :
                     * The node actually got deleted is newNode, not root,
                     * as it is supposed to, so all outside reference to
                     * newNode will be NULL, my cause issues.
                     **/
                    root.val = newNode.val;
                    root.right = deleteNode(root.right, newNode.val);

                } else if (root.left != null) {
                    root = root.left;
                } else if (root.right != null) {
                    root = root.right;
                } else {
                    root = null;
                }
            }

            return root;
        }
    }


    /**
     * Postorder
     */
    class Solution_Recursive_Move_And_Delete {
        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) return root;

            if (key > root.val) {
                root.right = deleteNode(root.right, key);
            } else if (key < root.val) {
                root.left = deleteNode(root.left, key);
            } else {//==
                /**
                 * Only part differs from Solution1
                 * Actually delete root and move the newNode
                 * to the place of root.
                 */
                if (root.left != null && root.right != null) {
                    //find the smallest element of its right substree
                    /**
                     * !!!
                     * parent starts from root
                     */
                    TreeNode parent = root;
                    TreeNode newRoot = root.right;
                    while (newRoot.left != null) {
                        parent = newRoot;
                        newRoot = newRoot.left;
                    }

                    /**
                     * !!!
                     * "if (parent != root)"
                     *
                     *               5
                     *              / \
                     *             3   6
                     *            / \   \
                     *          2   4   7
                     *
                     *  Delete node 3.
                     *
                     *  Here : parent = node 3
                     *         newNode = node 4
                     *
                     *         parent == root, we only need to set node 4 left child to point to 2.
                     *                   5
                     *                  / \
                     *                 4   6
                     *                /    \
                     *               2     7
                     *
                     *         If we don' have the if condition here :
                     *         parent.left = newNode.right; //node 3 left point to null,
                     *         newNode.right = root.right;  //node4 right point to itself
                     *
                     *         It will cause memory leak.
                     *
                     *                 5
                     *               /  \
                     *              3    10
                     *             / \  / \
                     *            2  4 8   12
                     *                 \
                     *                 9
                     *  Delete 5, find successor -> 8, parent = 10, newRoot = 8
                     *  "parent.left = newRoot.right" : 10 points to 9 on left
                     *  "newRoot.right = root.right"  :  8 points to 10 on right
                     *  "newRoot.left = root.left"    :  8 points to 3 on left
                     *
                     *                 8
                     *               /  \
                     *              3    10
                     *             / \  / \
                     *            2  4 9  12
                     *
                     *
                     */
                    if (parent != root) {
                        parent.left = newRoot.right;
                        newRoot.right = root.right;
                    }

                    newRoot.left = root.left;
                    root = newRoot;
                } else if (root.left != null) {
                    root = root.left;
                } else if (root.right != null) {
                    root = root.right;
                } else {//If the node is a leaf, the delete process is straightforward : root = null
                    root = null;
                }
            }

            return root;
        }
    }
}