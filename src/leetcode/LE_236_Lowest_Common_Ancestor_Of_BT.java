package leetcode;

import common.TreeNode;

import java.util.*;

/**
 * Created by yuank on 4/6/18.
 */
public class LE_236_Lowest_Common_Ancestor_Of_BT {
    /**
        Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

        According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes v and w as
        the lowest node in T that has both v and w as descendants (where we allow a node to be a descendant of itself).”

                _______3______
               /              \
            ___5__          ___1__
           /      \        /      \
           6      _2       0       8
                 /  \
                 7   4
        For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3. Another example is LCA of nodes 5 and 4 is 5,
        since a node can be a descendant of itself according to the LCA definition.
     */

    /**
     * if both p and q exist in Tree rooted at root, then return their LCA
     * if neither p and q exist in Tree rooted at root, then return null
     * if only one of p or q (NOT both of them), exists in Tree rooted at root, return it
     *
     * Time and Space : O(n)
     */
    class Solution1 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            /**
             * when p or q equals root, return root.
             */
            if (root == null || root == p || root == q) return root;

            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);

            /**
             * this condition actually is that p and q are under root.
             */
            if (left != null && right != null) {
                return root;
            }

            /**
             * then here, we just have one not null answer among left and right
             */
            return left == null ? right : left;
        }
    }

    /**
     * Iterative
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution2 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // Stack for tree traversal
            Deque<TreeNode> stack = new ArrayDeque<>();

            // HashMap for parent pointers
            Map<TreeNode, TreeNode> parent = new HashMap<>();

            parent.put(root, null);
            stack.push(root);

            // Iterate until we find both the nodes p and q
            while (!parent.containsKey(p) || !parent.containsKey(q)) {
                TreeNode node = stack.pop();

                // While traversing the tree, keep saving the parent pointers.
                if (node.left != null) {
                    parent.put(node.left, node);
                    stack.push(node.left);
                }

                if (node.right != null) {
                    parent.put(node.right, node);
                    stack.push(node.right);
                }
            }

            // Ancestors set() for node p.
            Set<TreeNode> ancestors = new HashSet<>();

            // Process all ancestors for node p using parent pointers.
            while (p != null) {
                ancestors.add(p);
                p = parent.get(p);
            }

            // The first ancestor of q which appears in
            // p's ancestor set() is their lowest common ancestor.
            while (!ancestors.contains(q)) {
                q = parent.get(q);
            }

            return q;
        }

    }


    /**
     * With parent pointer
     */
    class Solution2_1 {
         class Node {
            Node left;
            Node right;
            Node parent;
            int val;
        }

        public Node lowestCommonAncestorWithParent(Node root, Node p, Node q) {
            int depthP = getDepth(p);
            int depthQ = getDepth(q);

            // Make them in the same level from root
            while (depthP > depthQ) {
                p = p.parent;
                depthP--;
            }
            // No need to check who is bigger, just use 2 while loops
            while (depthQ > depthP) {
                q = q.parent;
                depthQ--;
            }

            // p, q move upward together
            while (p != null && q != null) {
                if (p == q) {
                    return p;
                }

                p = p.parent;
                q = q.parent;
            }

            return null;
        }

        int getDepth(Node node) {
            int depth = 0;

            while (node != null) {
                depth++;
                node = node.parent;
            }

            return depth;
        }

        public Node lowestCommonAncestorWithParentUsingHashMap(Node root, Node p, Node q) {
            Set<Node> visited = new HashSet<Node>();

            while (p != null) {
                visited.add(p);
                p = p.parent;
            }

            while (q != null) {
                if (visited.contains(q)) {
                    return q;
                }

                q = q.parent;
            }

            return null;
        }
    }

    /**
     * Iterative, without parent pointer
     *
     */
    class Solution3 {
        class Pair<TreeNode, Integer>{
            TreeNode node;
            Integer status;
            public Pair(TreeNode node, Integer status) {
                this.node = node;
                this.status = status;
            }

            TreeNode getKey() {
                return node;
            }

            Integer getValue() {
                return status;
            }

        }
        // Three static flags to keep track of post-order traversal.

        // Both left and right traversal pending for a node.
        // Indicates the nodes children are yet to be traversed.
        private int BOTH_PENDING = 2;

        // Left traversal done.
        private int LEFT_DONE = 1;

        // Both left and right traversal done for a node.
        // Indicates the node can be popped off the stack.
        private int BOTH_DONE = 0;

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

            Stack<Pair<TreeNode, Integer>> stack = new Stack<Pair<TreeNode, Integer>>();

            // Initialize the stack with the root node.
            stack.push(new Pair<TreeNode, Integer>(root, BOTH_PENDING));

            // This flag is set when either one of p or q is found.
            boolean one_node_found = false;

            // This is used to keep track of the LCA.
            TreeNode LCA = null;

            // Child node
            TreeNode child_node = null;

            // We do a post order traversal of the binary tree using stack
            while (!stack.isEmpty()) {

                Pair<TreeNode, Integer> top = stack.peek();
                TreeNode parent_node = top.getKey();
                int parent_state = top.getValue();

                // If the parent_state is not equal to BOTH_DONE,
                // this means the parent_node can't be popped off yet.
                if (parent_state != BOTH_DONE) {

                    // If both child traversals are pending
                    if (parent_state == BOTH_PENDING) {

                        // Check if the current parent_node is either p or q.
                        if (parent_node == p || parent_node == q) {

                            // If one_node_found was set already, this means we have found
                            // both the nodes.
                            if (one_node_found) {
                                return LCA;
                            } else {
                                // Otherwise, set one_node_found to True,
                                // to mark one of p and q is found.
                                one_node_found = true;

                                // Save the current top element of stack as the LCA.
                                LCA = stack.peek().getKey();
                            }
                        }

                        // If both pending, traverse the left child first
                        child_node = parent_node.left;
                    } else {
                        // traverse right child
                        child_node = parent_node.right;
                    }

                    // Update the node state at the top of the stack
                    // Since we have visited one more child.
                    stack.pop();
                    stack.push(new Pair<TreeNode, Integer>(parent_node, parent_state - 1));

                    // Add the child node to the stack for traversal.
                    if (child_node != null) {
                        stack.push(new Pair<TreeNode, Integer>(child_node, BOTH_PENDING));
                    }
                } else {

                    // If the parent_state of the node is both done,
                    // the top node could be popped off the stack.
                    // Update the LCA node to be the next top node.
                    if (LCA == stack.pop().getKey() && one_node_found) {
                        LCA = stack.peek().getKey();
                    }

                }
            }

            return null;
        }
    }
}
