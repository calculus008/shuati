package leetcode;

import java.util.*;

public class LE_1490_Clone_Nary_Tree {
    /**
     * Given a root of an N-ary tree, return a deep copy (clone) of the tree.
     *
     * Each node in the n-ary tree contains a val (int) and a list (List[Node]) of its children.
     *
     * class Node {
     *     public int val;
     *     public List<Node> children;
     * }
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated
     * by the null value (See examples).
     *
     * Medium
     *
     * https://leetcode.com/problems/clone-n-ary-tree/
     */


    class Node {
        public int val;
        public List<Node> children;


        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val,ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * Tree Post-order
     */
    class Solution1 {
        public Node cloneTree(Node root) {
            if (root == null) return null;

            /**
             * Can't be List<Node> because the constructor requires ArrayList<Node>
             */
            ArrayList<Node> copyChildren = new ArrayList<>();
            for (Node child : root.children) {
                copyChildren.add(cloneTree(child));
            }

            Node copyRoot = new Node(root.val, copyChildren);

            return copyRoot;
        }
    }

    class Solution2 {
        public Node cloneTree(Node root) {
            if (root == null) return null;
            Node copyRoot = new Node(root.val);

            for (Node child : root.children) {
                copyRoot.children.add(cloneTree(child));
            }

            return copyRoot;
        }
    }
}
