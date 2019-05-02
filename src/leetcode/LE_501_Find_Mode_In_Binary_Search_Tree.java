package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LE_501_Find_Mode_In_Binary_Search_Tree {
    /**
     * Given a binary search tree (BST) with duplicates, find all the mode(s)
     * (the most frequently occurred element) in the given BST.
     *
     * Assume a BST is defined as follows:
     *
     * The left subtree of a node contains only nodes with keys less than
     * or equal to the node's key.The right subtree of a node contains only
     * nodes with keys greater than or equal to the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     *
     * For example:
     * Given BST [1,null,2,2],
     *
     *    1
     *     \
     *      2
     *     /
     *    2
     *
     *
     * return [2].
     *
     * Note: If a tree has more than one mode, you can return them in any order.
     *
     * Follow up:
     * Could you do that without using any extra space? (Assume that the implicit
     * stack space incurred due to recursion does not count).
     */

    /**
     * One inorder traversal pass
     */
    public class Solution1 {
        Integer prev = null;
        int count = 1;
        int max = 0;

        public int[] findMode(TreeNode root) {
            if (root == null) return new int[0];

            List<Integer> list = new ArrayList<>();
            traverse(root, list);

            int[] res = new int[list.size()];

            for (int i = 0; i < list.size(); ++i) {
                res[i] = list.get(i);
            }

            return res;
        }

        private void traverse(TreeNode root, List<Integer> list) {
            if (root == null) return;

            traverse(root.left, list);

            if (prev != null) {
                if (root.val == prev) {
                    count++;
                } else {
                    count = 1;
                }
            }

            if (count > max) {
                max = count;
                list.clear();
                list.add(root.val);
            } else if (count == max) {
                list.add(root.val);
            }

            prev = root.val;

            traverse(root.right, list);
        }
    }

    /**
     * Time  : O(n)
     * Space : O(1)
     *
     * Here's a (two-pass) solution that I think can rightfully be called O(1) space.
     * Both passes keep track of the current value etc, and the second pass additionally
     * collects the modes in the result array.
     */

    public class Solution2 {
        private int currVal;
        private int currCount = 0;
        private int maxCount = 0;
        private int modeCount = 0;

        private int[] modes;

        public int[] findMode(TreeNode root) {
            inorder(root);
            modes = new int[modeCount];
            modeCount = 0;
            currCount = 0;
            inorder(root);
            return modes;
        }

        private void handleValue(int val) {
            if (val != currVal) {
                currVal = val;
                currCount = 0;
            }

            currCount++;
            if (currCount > maxCount) {
                maxCount = currCount;
                modeCount = 1;
            } else if (currCount == maxCount) {
                if (modes != null) {//for 2nd pass
                    modes[modeCount] = currVal;
                }
                modeCount++;
            }
        }

        private void inorder(TreeNode root) {
            if (root == null) return;
            inorder(root.left);
            handleValue(root.val);
            inorder(root.right);
        }
    }
}
