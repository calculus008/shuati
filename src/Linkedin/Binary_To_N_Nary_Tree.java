package Linkedin;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Binary_To_N_Nary_Tree {
    /**
     * Convert a binary tree to a N-ary complete tree
     *
     * Given a root of a tree. The tree may be of any depth and width.
     * Transform it in a way that each node(except probably one) would
     * either have N or 0 children
     *
     * 可以把所有的节点先放到一个数组里面。然后把这些点连成一个N叉树。
     * 连起来的时候需要注意，因为你可以连成一个完全树，所以A[i]的N个儿子分别是i*N+1到i*N+N。
     * 这样一个个连起来就好了，
     *
     * 连起来很方便。所以就是
     * 1.按level 遍历存到数组里面，preorder应该也可以。这样保证不违反那个sibling的条件。
     * 2.在数组里面连出一个full N tree.
     *
     * 主要考察的是遍历和完全树的trick.
     * 如果实在要优化的话，可以把数组变成一个queue。然后用按level遍历。
     * 如果把A[i]和N个儿子连起来之后，可以从队列里面pop出A[i]。只是一点小优化而已。
     *
     * Question :
     *
     * In order to do so, total number of nodes should be m = 1 + N + k * N = 1 + (k - 1) * N.
     * So (m - 1) % N == 0
     */

    static class Node {
        int val;
        List<Node> children = new ArrayList<Node>();

        Node(int val) {
            this.val = val;
        }
    }

    public Node binaryToNaryTree(TreeNode root, int N) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        List<TreeNode> list = new ArrayList<TreeNode>();

        /**
         * put all binary tree nodes into queue
         */
        while (!queue.isEmpty()) {
            TreeNode now = queue.poll();
            list.add(now);

            if (now.left != null) {
                queue.offer(now.left);
            }
            if (now.right != null) {
                queue.offer(now.right);
            }
        }

        Node newRoot = null;
        /**
         * it saves all visited N-nary
         */
        Queue<Node> visitedNodes = new LinkedList<Node>();
        boolean done = true;

        for (int i = 0; i < list.size(); i++) {
            if (done) {
                break;
            }

            Node parent = null;

            if (i == 0) {
                parent = new Node(list.get(i).val);
                newRoot = parent;
            } else {
                parent = visitedNodes.poll(); // Always get from queue, no need to new
            }

            /**
             * !!!
             * Key :
             * i * N + 1  to i * N + N
             */
            for (int j = i * N + 1; j <= i * N + N; j++) { // Children section
                if (j >= list.size()) {
                    done = true;
                    break;
                }

                /**
                 * create new N-nary tree node
                 */
                Node nextNode = new Node(list.get(j).val);
                visitedNodes.offer(nextNode);

                parent.children.add(nextNode);
            }
        }

        return newRoot;
    }

    public static void main(String[] args) {
        TreeNode n3 = new TreeNode(3);
        TreeNode n9 = new TreeNode(9);
        TreeNode n20 = new TreeNode(20);
        TreeNode n15 = new TreeNode(15);
        TreeNode n7 = new TreeNode(7);

        n3.left = n9;
        n3.right = n20;
        n20.left = n15;
        n20.right = n7;

        Binary_To_N_Nary_Tree lot = new Binary_To_N_Nary_Tree();
        Node node = lot.binaryToNaryTree(n3, 3);

        System.out.println(node.val);
    }
}
