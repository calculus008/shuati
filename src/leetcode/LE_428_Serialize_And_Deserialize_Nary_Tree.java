package leetcode;

import java.util.*;

public class LE_428_Serialize_And_Deserialize_Nary_Tree {
    /**
     * Serialization is the process of converting a data structure or object into a
     * sequence of bits so that it can be stored in a file or memory buffer, or
     * transmitted across a network connection link to be reconstructed later
     * in the same or another computer environment.
     *
     * Design an algorithm to serialize and deserialize an N-ary tree. An N-ary
     * tree is a rooted tree in which each node has no more than N children.
     * There is no restriction on how your serialization/deserialization algorithm
     * should work. You just need to ensure that an N-ary tree can be serialized
     * to a string and this string can be deserialized to the original tree structure.
     *
     *
     * Note:
     *
     * N is in the range of [1, 1000]
     * Do not use class member/global/static variables to store states.
     * Your serialize and deserialize algorithms should be stateless.
     * <p>
     * Hard
     */

    /**
     * preorder recursive traversal; add number of children after root val,
     * in order to know when to terminate.
     *
     * Example: The example in description is serialized as: "1,3,3,2,5,0,6,0,2,0,4,0"
     */
    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public String serialize(Node root) {
        List<String> list = new LinkedList<>();
        serializeHelper(root, list);
        /**
         * !!!
         * list is List<String>, String.join can only deal
         * with collections of String
         */
        return String.join(",", list);
    }

    private void serializeHelper(Node root, List<String> list) {
        if (root == null) {
            return;
        } else {
            list.add(String.valueOf(root.val));
            list.add(String.valueOf(root.children.size()));
            for (Node child : root.children) {
                serializeHelper(child, list);
            }
        }
    }

    // Decodes your encoded data to tree.
    public Node deserialize(String data) {
        if (data.isEmpty()) {
            return null;
        }

        String[] ss = data.split(",");
        /**
         * !!!
         * LinkedList constructor can only accept collection,
         * so need to first transform ss into List.
         */
        Queue<String> q = new LinkedList<>(Arrays.asList(ss));

        return deserializeHelper(q);
    }

    /**
     * "1,3,3,2,5,0,6,0,2,0,4,0"
     *
     *  In q:
     *  (1,3), (3,2), (5,0), (6,0), (2,0), (4,0)
     *               (1, 3)
     *           /     \     \
     *        (3, 2)  (2,0) (4,0)
     *       /     \
     * (5, 0)     (6, 0)
     * node : 5   node : 6
     *
     */
    private Node deserializeHelper(Queue<String> q) {
        Node root = new Node();
        root.val = Integer.parseInt(q.poll());
        int size = Integer.parseInt(q.poll());
        root.children = new ArrayList<Node>(size);

        for (int i = 0; i < size; i++) {
            root.children.add(deserializeHelper(q));
        }
        return root;
    }
}
