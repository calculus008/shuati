package Linkedin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Build_Tree_With_Balanced_Parenthesis {
    /**
     * Given a tree string expression in balanced parenthesis format:
     * [A[B[C][D]][E][F]].
     * Construct a tree and return the root of the tree.
     *
     *                 A
     *             /   |  \
     *           B    E   F
     *          / \
     *        C   D
     */

    public class Node {
        public char val;
        public List<Node> children;

        public Node(char val) {
            this.val = val;
        }
    }

    public Node buildGrah(String s) {
        int count = 0;
        Map<Integer, ArrayList<Node>> map = new HashMap<>();

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
                    map.put(count, new ArrayList<Node>());
                }

                Node n = new Node(c);
                map.get(count).add(n);
                if (map.containsKey(count - 1)) {
                    ArrayList<Node> cur = map.get(count - 1);
                    cur.get(cur.size() - 1).children.add(n);
                }
            }

        }

        Node root = map.get(1).iterator().next();

        return root;

//        for (int i = 0; i < root.children.size(); i++) {
//            Node nn = root.children.get(i);
//            System.out.println(nn.val);
//            root = nn;
//        }
    }

//    public static void main(String[] args) {
//        String s = "[A[B[C][D]][E][F]]";
//        Groph g = new Groph();
//        g.buildGra(s);
//    }
}
