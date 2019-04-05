package Linkedin;

import java.util.*;

public class Build_Tree_With_Balanced_Parenthesis_I {
    /**
     * Given a tree string expression in balanced parenthesis format:
     * [A[B[C][D]][E][F]].
     * Construct a tree and return the root of the tree (Nary Tree).
     *
     *                 A
     *             /   |  \
     *           B    E   F
     *          / \
     *        C   D
     */

    public class NaryTreeNode {
        public char val;
        public List<NaryTreeNode> children;

        public NaryTreeNode(char val) {
            this.val = val;
        }
    }

    class Solution1 {
        public NaryTreeNode buildGrah(String s) {
            int count = 0;
            Map<Integer, ArrayList<NaryTreeNode>> map = new HashMap<>();

            for (char c : s.toCharArray()) {
                /**
                 * !!!
                 */
                if (c == '[') {
                    count++;
                } else if (c == ']') {
                    count--;
                } else {
                    if (!map.containsKey(count)) {
                        map.put(count, new ArrayList<NaryTreeNode>());
                    }

                    /**
                     * it seems the assumption is that node value is a single char.
                     */
                    NaryTreeNode n = new NaryTreeNode(c);

                    /**
                     * add new node n to its current level
                     */
                    map.get(count).add(n);

                    /**
                     * add new node to the children list of its parent
                     */
                    if (map.containsKey(count - 1)) {
                        /**
                         * get the list of nodes in current level,
                         * get the last node in the level,
                         * add new node to its children list.
                         */
                        ArrayList<NaryTreeNode> cur = map.get(count - 1);
                        cur.get(cur.size() - 1).children.add(n);
                    }
                }

            }

            NaryTreeNode root = map.get(1).iterator().next();

            return root;
        }
    }

    /**
     * Stack solution
     */
    class Solution2 {

        NaryTreeNode deserialize(String s) {
            NaryTreeNode root = null;
            NaryTreeNode parent = null;
            Stack<NaryTreeNode> stack = new Stack<NaryTreeNode>();
            int pos = 0;

            while (pos < s.length()) {
                char c = s.charAt(pos);

                if (c == '(') {
                    pos++; // Next must be value
                    // If number could be multi digits, use a while loop here
                    NaryTreeNode node = new NaryTreeNode(s.charAt(pos));
                    if (root == null) {
                        root = node; // Will come here only once
                    }

                    /**
                     * add to its parent's children list
                     */
                    if (parent != null) {
                        parent.children.add(node);
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

//    public static void main(String[] args) {
//        String s = "[A[B[C][D]][E][F]]";
//        Groph g = new Groph();
//        g.buildGra(s);
//    }
    }
}
