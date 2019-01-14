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

    /**
     *
     */
    class Solution1 {
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
     *
     */
    class Solution2 {
        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) return root;

            if (key > root.val) {
                root.right = deleteNode(root.right, key);
            } else if (key < root.val) {
                root.left = deleteNode(root.left, key);
            } else {//==
                /**
                 * Only part deffers from Solution1
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
                    TreeNode newNode = root.right;
                    while (newNode.left != null) {
                        parent = newNode;
                        newNode = newNode.left;
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
                     *
                     *         If we don' have the if condition here :
                     *         parent.left = newNode.right; //node 3 left point to null,
                     *         newNode.right = root.right;  //node4 right point to itself
                     *
                     *         It will cause memory leak.
                     */
                    if (parent != root) {
                        parent.left = newNode.right;
                        newNode.right = root.right;
                    }

                    newNode.left = root.left;
                    root = newNode;
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
}