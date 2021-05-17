package leetcode;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.NOTATIONDatatypeValidator;
import common.TreeNode;

public class LE_968_Binary_Tree_Cameras {
    /**
     * Given a binary tree, we install cameras on the nodes of the tree.
     * Each camera at a node can monitor its parent, itself, and its immediate children.
     * Calculate the minimum number of cameras needed to monitor all nodes of the tree.
     *
     * Hard
     */

    /**
     * Greedy (Post Order bottom up)
     * Time : O(n)
     * Space : O(1), if considering system stack, O(logn)
     *
     * https://leetcode.com/problems/binary-tree-cameras/discuss/211966/Super-Clean-Java-solution-beat-100-DFS-O(n)-time-complexity
     *
     * Key observations:
     * If a node has its children covered and has a parent, then it is strictly better to place the camera at this node's parent.
     *
     * Or, think it in another way:
     *
     * We go to a leaf node:
     * If we set a camera at the leaf, the camera can cover the leaf and its parent.
     * If we set a camera at its parent, the camera can cover the leaf, its parent and its sibling.
     * We can see that the second plan is always better than the first.
     *
     * Hence, here is our greedy solution:
     *
     * Set cameras on all leaves' parents, then remove all covered nodes.
     * Repeat step 1 until all nodes are covered, so it is Post Order traversal.
     */
    private int MONITORED_NOCAM = 0;
    private int MONITORED_WITHCAM = 1;
    private int NOT_MONITORED = 2;
    private int res = 0;

    public int minCameraCover(TreeNode root) {
        if (root == null) return 0;
        int status = dfs(root);
        if (status == NOT_MONITORED) res++;
        return res;
    }

    private int dfs(TreeNode root) {
        /**
         * base case, think of a leave node which both l and r are null, so we want to pass status
         * NOT_MONITORED up to its parent.
         */
        if (root == null) return MONITORED_NOCAM;
        int l = dfs(root.left);
        int r = dfs(root.right);

        if (l == MONITORED_NOCAM && r == MONITORED_NOCAM) {
            return NOT_MONITORED;
        } else if (l == NOT_MONITORED || r == NOT_MONITORED) {
            res++;
            return MONITORED_WITHCAM;
        } else {
            return MONITORED_NOCAM;
        }
    }
}
