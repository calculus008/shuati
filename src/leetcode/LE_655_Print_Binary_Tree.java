package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LE_655_Print_Binary_Tree {
    /**
     * Print a binary tree in an m*n 2D string array following these rules:
     *
     * The row number m should be equal to the height of the given binary tree.
     * The column number n should always be an odd number.
     * The root node's value (in string format) should be put in the exactly middle
     * of the first row it can be put. The column and the row where the root node belongs
     * will separate the rest space into two parts (left-bottom part and right-bottom part).
     * You should print the left subtree in the left-bottom part and print the right
     * subtree in the right-bottom part. The left-bottom part and the right-bottom part
     * should have the same size. Even if one subtree is none while the other is not,
     * you don't need to print anything for the none subtree but still need to leave
     * the space as large as that for the other subtree. However, if two subtrees are none,
     * then you don't need to leave space for both of them.
     *
     * Each unused space should contain an empty string "".
     *
     * Print the subtrees following the same rules.
     * Example 1:
     * Input:
     *      1
     *     /
     *    2
     * Output:
     * [["", "1", ""],
     *  ["2", "", ""]]
     *
     * Example 2:
     * Input:
     *      1
     *     / \
     *    2   3
     *     \
     *      4
     * Output:
     * [["", "", "", "1", "", "", ""],
     *  ["", "2", "", "", "", "3", ""],
     *  ["", "", "4", "", "", "", ""]]
     * Example 3:
     * Input:
     *       1
     *      / \
     *     2   5
     *    /
     *   3
     *  /
     * 4
     * Output:
     *
     * [["",  "",  "", "",  "", "", "", "1", "",  "",  "",  "",  "", "", ""]
     *  ["",  "",  "", "2", "", "", "", "",  "",  "",  "",  "5", "", "", ""]
     *  ["",  "3", "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]
     *  ["4", "",  "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]]
     *
     * Note: The height of binary tree is in the range of [1, 10].
     *
     * Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/tree/leetcode-655-print-binary-tree/
     *
     * Solution: Recursion
     *
     * Compute the layers h of the tree.
     *
     * shape of the output matrix: h * w (w = 2^h – 1)
     *
     * pre-order to fill the output matrix
     *
     * first layer’s root: y1 = 0, x1 = (l1 + r1) / 2 = (0 + w – 1) / 2 (center)
     *
     * first layer’s left child (2nd layer): y2 = 1, x2 = (l2 + r2) = (l1 + (x1 – 1)) / 2 (center of the left half)
     *
     * first layer’s right child(2nd layer): y1 = 2, x2 = (l2′ + r2′) = ((x1+1) + r1) / 2 (center of the right half)
     *
     * …
     *
     * Time  : O(m * n)
     * Space : O(m * n)
     */

    class Solution {
        public List<List<String>> printTree(TreeNode root) {
            List<List<String>> res = new ArrayList<>();
            if (root == null) {
                return res;
            }

            int h = getHeight(root);
            int w = (1 << h) - 1;

            /**
             * Must initialize res here since it requires "" for places that has null in tree
             */
            for (int i = 0;i < h; i++) {
                res.add(new ArrayList<>());
                List<String> cur = res.get(i);
                for (int j = 0; j < w; j++) {
                    cur.add("");
                }
            }

            fill(root, res, 0, 0, w - 1);

            return res;
        }

        private int getHeight(TreeNode root) {
            if (root == null) return 0;

            return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        }

        private void fill(TreeNode root, List<List<String>> res, int idx, int l, int r) {
            if (root == null) {
                return;
            }

            int mid = (l + r) / 2;
            /**
             * !!!
             * List<String>,must convert root.val to String when replace
             * it in list.
             */
            res.get(idx).set(mid, String.valueOf(root.val));

            fill(root.left, res, idx + 1, l, mid - 1);
            fill(root.right, res, idx + 1, mid + 1, r);
        }
    }


}