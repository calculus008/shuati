package Interviews.Linkedin;

import common.TreeNode;

import java.util.*;

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

    public class GenerateBinaryTree_1 {

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

    public class GenerateBinaryTree_2 {
        // parent, child, isLeft
        TreeNode buildTree(List<Integer[]> relation, List<Boolean> list) {
            Map<Integer, TreeNode> map = new HashMap<Integer, TreeNode>();
            Set<TreeNode> hasParent = new HashSet<TreeNode>();

            for (int i = 0; i < relation.size(); i++) {
                int parent = relation.get(i)[0];
                int child = relation.get(i)[1];
                boolean isLeft = list.get(i);

                TreeNode parentNode = null;
                if (map.containsKey(parent)) {
                    parentNode = map.get(parent);
                } else {
                    parentNode = new TreeNode(parent);
                    map.put(parent, parentNode);
                }

                TreeNode childNode = null;
                if (map.containsKey(child)) {
                    childNode = map.get(child);
                } else {
                    childNode = new TreeNode(child);
                    map.put(child, childNode);
                }

                if (isLeft) {
                    parentNode.left = childNode;
                } else {
                    parentNode.right = childNode;
                }

                hasParent.add(childNode);
            }

            // map.size() == hasParent.size() + 1
            for (TreeNode node : map.values()) {
                if (!hasParent.contains(node)) {
                    return node;
                }
            }

            return null;
        }
    }


    public void main(String[] args) {
        List<Integer[]> relation = new ArrayList<Integer[]>();
        List<Boolean> list = new ArrayList<Boolean>();

        Integer[] pair1 = {2, 5};
        Integer[] pair2 = {2, 4};
        Integer[] pair3 = {4, 3};
        Integer[] pair4 = {3, 1};

        relation.add(pair1);
        relation.add(pair2);
        relation.add(pair3);
        relation.add(pair4);

        list.add(true);
        list.add(false);
        list.add(false);
        list.add(true);

        TreeNode root = new GenerateBinaryTree_2().buildTree(relation, list);
        System.out.println(root.val);
    }
}
