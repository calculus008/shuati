package Interviews.Linkedin;

import common.TreeNode;

public class Convert_A_Tree_To_Forest_Of_Even_Nodes {
    /**
     * Given a tree of n even nodes. The task is to find the maximum number
     * of edges to be removed from the given tree to obtain forest of trees
     * having even number of nodes. This problem is always solvable as given
     * graph has even nodes.
     *
     * https://www.geeksforgeeks.org/convert-tree-forest-even-nodes/
     */

    /**
     * Find a subtree with even number of nodes and remove it from rest of tree
     * by removing the edge connecting it. After removal, we are left with tree
     * with even node only because initially we have even number of nodes in the
     * tree and removed subtree has also even node. Repeat the same procedure
     * until we left with the tree that cannot be further decomposed in this manner.
     *
     * To do this, the idea is to use Depth First Search to traverse the tree.
     * Implement DFS function in such a manner that it will return number of
     * nodes in the subtree whose root is node on which DFS is performed. If
     * the number of nodes is even then remove the edge, else ignore.
     */

    int count = 0;
    int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int left = dfs(node.left);
        int right = dfs(node.right);
        int res = left + right + 1; // How many nodes under this root, including itself

        if (res % 2 == 0) {
            count++;
        }

        return res;
    }
}
