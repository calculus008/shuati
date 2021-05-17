package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LE_1382_Balance_Binary_Search_Tree {
    /**
     * Given a binary search tree, return a balanced binary search
     * tree with the same node values.
     *
     * A binary search tree is balanced if and only if the depth of
     * the two subtrees of every node never differ by more than 1.
     *
     * If there is more than one answer, return any of them.
     *
     * Constraints:
     *
     * The number of nodes in the tree is between 1 and 10^4.
     * The tree nodes will have distinc
     *
     * Medium
     */

    /**
     * inorder, put all number into array, then create a balanced BST from array (LE_108_Convert_Sorted_Array_To_BST)
     *
     * Time and Space : O(n)
     */
    class Solution1 {
        List<Integer> arr;

        public void dfs(TreeNode root) {
            if(root == null) {
                return;
            }

            dfs(root.left);
            this.arr.add(root.val);
            dfs(root.right);
        }

        /**
         * LE_108_Convert_Sorted_Array_To_BST
         */
        public TreeNode build(List<Integer> arr, int st, int end) {
            if(st > end) return null;

            int mid = (st + end)/2;
            TreeNode root = new TreeNode(arr.get(mid));
            root.left = build(arr, st, mid - 1);
            root.right = build(arr, mid + 1, end);

            return root;
        }

        public TreeNode balanceBST(TreeNode root) {
            arr = new ArrayList<>();
            dfs(root);
            return build(arr, 0, arr.size() - 1);
        }
    }

    /**
     * https://leetcode.com/problems/balance-a-binary-search-tree/discuss/541785/C%2B%2BJava-with-picture-DSW-O(n)orO(1)
     *
     * DSW Algorithm
     * https://csactor.blogspot.com/2018/08/dsw-day-stout-warren-algorithm-dsw.html
     *
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution2 {
        public TreeNode balanceBST(TreeNode root) {
            TreeNode pseudoRoot = new TreeNode(0);
            pseudoRoot.right = root;

            int nodeCount = flattenTreeWithRightRotations(pseudoRoot);
            int heightOfTree = (int)(Math.log(nodeCount + 1) / Math.log(2));
            int numOfNodesInTree = (int) Math.pow(2, heightOfTree) - 1;

            createBalancedTreeWithLeftRotation(pseudoRoot, nodeCount - numOfNodesInTree);
            for (int numOfNodesInSubTree = (numOfNodesInTree/2); numOfNodesInSubTree > 0; numOfNodesInSubTree /= 2)
                createBalancedTreeWithLeftRotation(pseudoRoot, numOfNodesInSubTree);
            return pseudoRoot.right;
        }


        public int flattenTreeWithRightRotations(TreeNode root) {
            int nodeCount = 0;
            TreeNode pseudoRoot = root.right;
            while (pseudoRoot != null) {
                if (pseudoRoot.left != null) {
                    TreeNode oldPseudoRoot = pseudoRoot;
                    pseudoRoot = pseudoRoot.left;
                    oldPseudoRoot.left = pseudoRoot.right;
                    pseudoRoot.right = oldPseudoRoot;
                    root.right = pseudoRoot;
                } else {
                    ++nodeCount;
                    root = pseudoRoot;
                    pseudoRoot = pseudoRoot.right;
                }
            }
            return nodeCount;
        }

        void createBalancedTreeWithLeftRotation(TreeNode root, int numOfNodesInSubTree) {
            TreeNode pseudoRoot = root.right;
            while (numOfNodesInSubTree-- > 0) {
                TreeNode oldPseudoRoot = pseudoRoot;
                pseudoRoot = pseudoRoot.right;

                root.right = pseudoRoot;
                oldPseudoRoot.right = pseudoRoot.left;
                pseudoRoot.left = oldPseudoRoot;
                root = pseudoRoot;
                pseudoRoot = pseudoRoot.right;
            }
        }
    }
}
