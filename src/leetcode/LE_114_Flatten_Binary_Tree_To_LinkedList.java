package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_114_Flatten_Binary_Tree_To_LinkedList {
    /*
        Given a binary tree, flatten it to a linked list in-place.

        For example,
        Given

                 1
                / \
               2   5
              / \   \
             3   4   6
        The flattened tree should look like:
           1
            \
             2
              \
               3
                \
                 4
                  \
                   5
                    \
                     6

     */

    //Time and Space : O(n)
     /*
        Solution 1: Recursion
        All nodes are linked in "right" field, "left" field points to null. It requries that root first linked to left subtree in preorder sequence,
        then link to right substree in preorder sequence. Therefore we recurse to right side first, using stack provided in recursion
        to remember nodes in right substree.

                 1
                / \
               2   5
              / \   \
             3   4   6

            pre :  null, 6, 5, 1, 4, 3, 2

            6 R-> null, pre = 6        6-> null
            5 R-> 6     pre = 5        5 -> 6 -> null
            4 R-> null, pre = 4        4 -> 5 -> 6 -> null
            3 R-> 4,    pre = 3        3 -> 4 -> 5 -> 6 -> null
            2 R-> 3,    pre = 2        2 -> 3 -> 4 -> 5 -> 6 -> null
            1 R-> 2,    pre = 1        1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
      */

    TreeNode pre = null;
    public void flatten1(TreeNode root) {
        if (root == null) return;
        flatten1(root.right);
        flatten1(root.left);
        root.right = pre;
        root.left = null;
        pre = root;
    }

    //Solution 2 : Iterative
    /*
                 1
                / \
               2   5
              / \   \
             3   4   6

                 stack     cur    link
                 1
                 5, 2       1     1 -> 2,
                 5, 4, 3    2     1 -> 2 -> 3
                 5, 4,      3     1 -> 2 -> 3 -> 4
                 5          4     1 -> 2 -> 3 -> 4 -> 5
                 6          5     1 -> 2 -> 3 -> 4 -> 5 -> 6
                            6

    */

    public void flatten2(TreeNode root) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            if (cur.right != null) stack.push(cur.right);
            if (cur.left != null) stack.push(cur.left);

            //!!!
            if (!stack.isEmpty()) {
                cur.right = stack.peek();
            }

            cur.left = null;
        }
    }
}
