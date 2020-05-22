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
     *
     * Hard
     */

    class Node {
        public int val;
        // public Integer val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }

        public Node(int _val) {
            val = _val;
        }
    }

    /**
     * preorder recursive traversal; add number of children after root val,
     * in order to know when to terminate.
     *
     * Example: The example in description is serialized as: "1,3,3,2,5,0,6,0,2,0,4,0"
     *
     * Compare with LE_297_Serialize_And_Deserialize_Binary_Tree, since we put number of children
     * in the serialized string, we don't need to have a special character that mark a NULL child,
     * as LE_297_Serialize_And_Deserialize_Binary_Tree does.
     */
    class Solution_DFS {
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
            /**
             * !!!
             */
            root.children = new ArrayList<Node>(size);

            for (int i = 0; i < size; i++) {
                root.children.add(deserializeHelper(q));
            }
            return root;
        }
    }

    /**
     * https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree/solution/
     *
     * Solution 2
     */
    class Solution_DFS_1 {
        class WrappableInt {
            private int value;

            public WrappableInt(int x) {
                this.value = x;
            }

            public int getValue() {
                return this.value;
            }

            public void increment() {
                this.value++;
            }
        }

        // Encodes a tree to a single string.
        public String serialize(Node root) {
            StringBuilder sb = new StringBuilder();
            this._serializeHelper(root, sb);
            return sb.toString();
        }

        private void _serializeHelper(Node root, StringBuilder sb) {
            if (root == null) {
                return;
            }

            // Add the value of the node
            sb.append((char) (root.val + '0'));

            // Add the number of children
            sb.append((char) (root.children.size() + '0'));

            // Recurse on the subtrees and build the string accordingly
            for (Node child : root.children) {
                this._serializeHelper(child, sb);
            }
        }

        // Decodes your encoded data to tree.
        public Node deserialize(String data) {
            if(data.isEmpty()) return null;

            return this._deserializeHelper(data, new WrappableInt(0));
        }

        private Node _deserializeHelper(String data, WrappableInt index) {
            if (index.getValue() == data.length()) {
                return null;
            }

            // The invariant here is that the "index" always
            // points to a node and the value next to it
            // represents the number of children it has.
            Node node = new Node(data.charAt(index.getValue()) - '0', new ArrayList<Node>());
            index.increment();
            int numChildren = data.charAt(index.getValue()) - '0';
            for (int i = 0; i < numChildren; i++) {
                index.increment();
                node.children.add(this._deserializeHelper(data, index));
            }

            return node;
        }
    }

    class Solution_BFS {
        // Encodes a tree to a single string.
        public String serialize(Node root) {
            if (root == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            Queue<Node> queue = new LinkedList<Node>();
            queue.offer(root);
            sb.append(root.val).append(",").append(root.children.size()).append(",");
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                for (Node node : cur.children) {
                    queue.offer(node);
                    sb.append(node.val).append(",").append(node.children.size()).append(",");
                }
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public Node deserialize(String data) {
            if (data.length() == 0) {
                return null;
            }

            String[] nodes = data.split(",");
            Queue<Node> queue = new LinkedList<Node>();
            Queue<Integer> childQueue = new LinkedList<Integer>();
            Node root = new Node(Integer.valueOf(nodes[0]));
            queue.offer(root);
            childQueue.offer(Integer.valueOf(nodes[1]));
            int i = 2;

            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                cur.children = new ArrayList<>();
                int n = childQueue.poll();
                for (int j = 0; j < n; j++) {
                    Node child = new Node(Integer.valueOf(nodes[i++]));
                    childQueue.offer(Integer.valueOf(nodes[i++]));
                    queue.offer(child);
                    cur.children.add(child);
                }
            }
            return root;
        }
    }
}
