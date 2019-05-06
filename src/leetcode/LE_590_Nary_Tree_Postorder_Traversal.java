package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class LE_590_Nary_Tree_Postorder_Traversal {
    /**
     * Given an n-ary tree, return the postorder traversal of its nodes' values.
     *
     * Easy
     */

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * Iterative
     */
    class Solution1 {
        public List<Integer> postorder(Node root) {
            List<Integer> list = new ArrayList<>();
            if (root == null) {
                return list;
            }

            Stack<Node> stack = new Stack<>();
            stack.add(root);

            while(!stack.isEmpty()) {
                root = stack.pop();
                list.add(root.val);

                for(Node node: root.children) {
                    stack.add(node);
                }
            }

            Collections.reverse(list);
            return list;
        }
    }

    /**
     * Recursive
     */
    class Solution {
        List<Integer> list = new ArrayList<>();

        public List<Integer> postorder(Node root) {
            if (root == null) {
                return list;
            }

            for(Node node: root.children) {
                postorder(node);
            }

            list.add(root.val);

            return list;
        }
    }
}
