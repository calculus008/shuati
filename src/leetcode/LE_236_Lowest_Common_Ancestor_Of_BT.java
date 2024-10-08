package leetcode;

import common.TreeNode;

import java.util.*;

/**
 * Created by yuank on 4/6/18.
 */
public class LE_236_Lowest_Common_Ancestor_Of_BT {
    /**
        Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

        According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between
        two nodes v and w as the lowest node in T that has both v and w as descendants (where we allow
        a node to be a descendant of itself).”

                _______3______
               /              \
            ___5__          ___1__
           /      \        /      \
           6      _2       0       8
                 /  \
                 7   4
        For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3.
        Another example is LCA of nodes 5 and 4 is 5,

        (!!! lca could be one of the nodes in p and q)

        since a node can be a descendant of itself according to the LCA definition.

        Note:
        All of the nodes' values will be unique. (!!!)

        !!!
        p and q are different and both values will exist (!!!) in the binary tree.

        Follow up : LI_578_Lowest_Common_Ancestor_III

        Medium

        https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree

        Related:
        LE_1650_Lowest_Common_Ancestor_Of_A_Binary_Tree_III
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
     * p and q may not exist
     * Solution for LI_578_Lowest_Common_Ancestor_III
     */
    class Solution1_A_B_May_Not_Exit {
        boolean foundP = false;
        boolean foundQ = false;

        public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
            TreeNode res = helper(root, p, q);
            if (foundP && foundQ) return res;

            return null;
        }

        private TreeNode helper(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) return root;

            TreeNode l = helper(root.left, p, q);
            TreeNode r = helper(root.right, p, q);

            if (root == p || root == q)  {
                foundP = foundP || (root == p);
                foundQ = foundQ || (root == q);
                return root;
            }

            if (l != null && r != null) {
                return root;
            } else if (l != null) {
                return l;
            } else if (r != null) {
                return r;
            }

            return null;
        }
    }

    /**
     * If it's N-arry Tree
     *         private TreeNode helper(TreeNode root, TreeNode p, TreeNode q) {
     *             if (root == null) return root;
     *
     *             List<TreeNode> res = new ArrayList<>();
     *             for (TreeNode child : root.children) {}
     *                TreeNode t = helper(child, p, q);
     *                if (t != null) {
     *                  res.add(t);
     *                }
     *             }
     *
     *             if (root == p || root == q)  {
     *                 foundP = foundP || (root == p);
     *                 foundQ = foundQ || (root == q);
     *                 return root;
     *             }
     *
     *             if (t.size() == 2) {
     *                 return root;
     *             } else if (t.size() == 1) {
     *                 return res.get(0);54
     *             }
     *
     *             return null;
     *         }
     */

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

    /**
     * If p and q may not exist in current BT !!!
     *
     * https://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/
     */
    class Solution4 {
        class Node {
            int data;
            Node left, right;

            Node(int value) {
                data = value;
                left = right = null;
            }
        }

        public class BT_NoParentPtr_Solution1 {
            Node root;
            private List<Integer> path1 = new ArrayList<>();
            private List<Integer> path2 = new ArrayList<>();

            // Finds the path from root node to given root of the tree.
            int findLCA(int n1, int n2) {
                path1.clear();
                path2.clear();
                return findLCAInternal(root, n1, n2);
            }

            private int findLCAInternal(Node root, int n1, int n2) {

                if (!findPath(root, n1, path1) || !findPath(root, n2, path2)) {
                    System.out.println((path1.size() > 0) ? "n1 is present" : "n1 is missing");
                    System.out.println((path2.size() > 0) ? "n2 is present" : "n2 is missing");
                    return -1;
                }

                int i;
                for (i = 0; i < path1.size() && i < path2.size(); i++) {

                    // System.out.println(path1.get(i) + " " + path2.get(i));
                    if (!path1.get(i).equals(path2.get(i)))
                        break;
                }

                return path1.get(i - 1);
            }

            // Finds the path from root node to given root of the tree, Stores the
            // path in a vector path[], returns true if path exists otherwise false
            private boolean findPath(Node root, int n, List<Integer> path) {
                // base case
                if (root == null) {
                    return false;
                }

                // Store this node . The node will be removed if
                // not in path from root to n.
                path.add(root.data);

                if (root.data == n) {
                    return true;
                }

                if (root.left != null && findPath(root.left, n, path)) {
                    return true;
                }

                if (root.right != null && findPath(root.right, n, path)) {
                    return true;
                }

                // If not present in subtree rooted with root, remove root from
                // path[] and return false
                path.remove(path.size() - 1);

                return false;
            }
        }
    }
}
