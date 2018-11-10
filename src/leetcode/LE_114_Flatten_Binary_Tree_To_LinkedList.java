package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_114_Flatten_Binary_Tree_To_LinkedList {
    /**
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

     /**
        Solution 1 : Recursive
        Time and Space : O(n)

      ``!!!
        Postorderd的变形， instead of left->right->cur, it goes right->left->cur.
        It goes right side first, save right side nodes in system stack. Then it links from back to start (goes backwards)

           2
          / \
        3   4

        Preorde r: 2 -> 3 -> 4, reverse it : 4 -> 3 -> 2, so it is postorder but visit right side first

        Solution 1: Recursion
        !!!All nodes are linked in "right" field, "left" field points to null!!!
        It requires that root first linked to right subtree in postorder sequence,
        then link to left substree in postorder sequence. Therefore we recurse to right side first,
        using stack provided in recursion to remember nodes in right substree.

                 1
                / \
               2   5
              / \   \
             3   4   6

            pre :  null, 6, 5, 1, 4, 3, 2

           cur node     set cur.right = pre                set pre = cur
            ----------------------------------------------------------
                                                            pre = null
            6 R-> null, 6 -> null,                          pre = 6
            5 R-> 6     5 -> 6 -> null                      pre = 5
            4 R-> null, 4 -> 5 -> 6 -> null                 pre = 4
            3 R-> 4,    3 -> 4 -> 5 -> 6 -> null            pre = 3
            2 R-> 3,    2 -> 3 -> 4 -> 5 -> 6 -> null       pre = 2
            1 R-> 2,    1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null  pre = 1
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

    /**
     * Solution 2 : Iterative
     *
     * Use a stack, first go to right side, so nodes in right substree will be saved at the bottom of the stack
     * and will be linked at the later stage (after left substree is processed).
     *
     * Difference from Solution 1 :
     * It links from start to end.
     *
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

    **/

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
