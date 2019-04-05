package Linkedin;

import common.TreeNode;

import java.util.Stack;

public class Build_Tree_With_Balanced_Parenthesis_II {
    /**
     *   You need to construct a Binary Tree from a string consisting of parenthesis and integers.

         The whole input represents a binary tree. It contains an integer followed by zero,
         one or two pairs of parenthesis. The integer represents the root's value and a pair
         of parenthesis contains a child binary tree with the same structure.

         You always start to construct the left child node of the parent first if it exists.

         Example:
         Input: "4(2(3)(1))(6(5))"
         Output: return the tree root node representing the following tree:

              4
            /   \
           2     6
          / \   /
         3   1 5

         Note:
         There will only be '(', ')', '-' and '0' ~ '9' in the input string.
         An empty tree is represented by "" instead of "()".
     */

    /**
     * Difference from Build_Tree_With_Balanced_Parenthesis_I
     * 1.This time we build a binary tree, not a nary tree
     * 2.Values for the tree node is an integer, need to extract the integer.
     *
     * Recursive
     */

    /**
     * Stack
     */
    TreeNode str2tree(String s) {
        if (s.length() == 0) {
            return null;
        }

        s = "(" + s + ")";

        TreeNode root = null;
        TreeNode parent = null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        int pos = 0;

        while (pos < s.length()) {
            char c = s.charAt(pos);

            if (c == '(') {
                pos++; // Next must be a number
                // If number could be multi digits, use a while loop here
                int num = 0;
                while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
                    num = num * 10 + (s.charAt(pos) - '0');
                    pos++;
                }
                pos--; // pos will point to the char immediately after last digit

                TreeNode node = new TreeNode(num);
                if (root == null) {
                    root = node; // Will come here only once
                }

                if (parent != null) {
                    // If the tree is guaranteed to be binary, you can easily change above code to
                    if (parent.left == null) {
                        parent.left = node;
                    } else {
                        parent.right = node;
                    }
                }

                parent = node;
                stack.push(node); // 记录一下
            } else if (c == ')') {
                stack.pop(); // 弹出来

                if (!stack.isEmpty()) {
                    parent = stack.peek(); // Change parent node, 1 level above
                }
            }

            pos++;
        }

        return root;
    }

    public TreeNode str2BinaryTree(String s) {
        /**
         * First extract number from input, create a new TreeNode
         */
        int left = 0, right = 0;

        while (right < s.length() && Character.isDigit(s.charAt(right))) {
            right++;
        } // Right is (

        int num = Integer.valueOf(s.substring(left, right));
        TreeNode root = new TreeNode(num);

        if (right < s.length()) {
            int count = 1;
            left = right; // left is (, move left to the next left parenthesis

            /**
             * use count to simulate stack, find the start and end index for the
             * recursion call
             */
            while (right + 1 < s.length() && count != 0) {
                right++;
                if (s.charAt(right) == '(') {
                    count++;
                }
                if (s.charAt(right) == ')') {
                    count--;
                }
            } // right is ) eventually

            root.left = str2BinaryTree(s.substring(left + 1, right));
        }

        if (right < s.length()) {
            root.right = str2BinaryTree(s.substring(right + 1, s.length() - 1));
        }

        return root;
    }
}
