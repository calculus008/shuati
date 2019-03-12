package Linkedin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Generate_Binary_Tree_By_Parent_Child {
    /**
     * Given a list of child->parent relationships, build a binary tree out of it.
     * All the element Ids inside the tree are UNIQUE.
     *
     * Example:
     *
     * Given the following relationships:
     *
     * Child Parent IsLeft
     * 15 20 true
     * 19 80 true
     * 17 20 false
     * 16 80 false
     * 80 50 false
     * 50 null false
     * 20 50 true
     *
     * You should return the following tree:
     *
     *     50
     *    /  \
     *   20   80
     *  / \   / \
     * 15 17 19 16
     */

    public class GenerateBinaryTree {

        public class Relation {
            public Integer parent, child;
            public boolean isLeft;

            public Relation(Integer parent, Integer child, boolean isLeft) {
                this.parent = parent;
                this.child = child;
                this.isLeft = isLeft;
            }
        }

        public class Node {
            public Integer val;
            public Node left, right;

            public Node(Integer val, Node left, Node right) {
                this.val = val;
                this.left = left;
                this.right = right;
            }
        }

        /**
         * Key - val in Node, since all values are unique, we can use it as key
         * Val - Node with key val
         */
        private HashMap<Integer, Node> mapOfNodes;

        public Node buildTree(List<Relation> relations) {
            mapOfNodes = new HashMap<Integer, Node>();

            Iterator<Relation> relationsIterator = relations.iterator();
            Relation relation;
            Node root = null;

            while (relationsIterator.hasNext()) {
                Node parentNode, childNode;
                relation = relationsIterator.next();

                /**
                 * !!!
                 * root
                 */
                if (relation.parent == null) {
                    root = getChildNode(relation.child);
                    continue;
                }

                if (mapOfNodes.containsKey((relation.parent))) {
                    parentNode = mapOfNodes.get(relation.parent);
                    childNode = getChildNode(relation.child);
                    if (relation.isLeft) {
                        parentNode.left = childNode;
                    } else {
                        parentNode.right = childNode;
                    }
                } else {
                    childNode = getChildNode(relation.child);
                    parentNode = new Node(relation.parent, relation.isLeft ? childNode : null, relation.isLeft ? null : childNode);
                    mapOfNodes.put(relation.parent, parentNode);
                }
            }

            return root;
        }

        /**
         * Map Integer input to a Node
         */
        private Node getChildNode(Integer child) {
            Node childNode;
            if (mapOfNodes.containsKey((child))) {
                childNode = mapOfNodes.get(child);
            } else {
                childNode = new Node(child, null, null);
                mapOfNodes.put(child, childNode);
            }
            return childNode;
        }
    }
}
