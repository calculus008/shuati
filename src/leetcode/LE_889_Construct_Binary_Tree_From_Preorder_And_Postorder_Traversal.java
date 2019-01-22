package leetcode;

import common.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class LE_889_Construct_Binary_Tree_From_Preorder_And_Postorder_Traversal {
    /**
     * Return any binary tree that matches the given preorder and postorder traversals.
     * Values in the traversals pre and post are DISTINCT positive integers.
     *
     * Example 1:
     *
     * Input: pre = [1,2,4,5,3,6,7], post = [4,5,2,6,7,3,1]
     * Output: [1,2,3,4,5,6,7]
     *
     *
     * Note:
     *
     * 1 <= pre.length == post.length <= 30
     * pre[] and post[] are both permutations of 1, 2, ..., pre.length.
     * It is guaranteed an answer exists. If there exists multiple answers,
     * you can return any of them.
     */

    /**
     * https://zxi.mytechroad.com/blog/tree/leetcode-889-construct-binary-tree-from-preorder-and-postorder-traversal/
     *
     * pre = [(root) (left-child) (right-child)]
     *
     * post = [(left-child) (right-child) (root)]
     *
     * We need to recursively find the first node in pre.left-child from post.left-child
     *
     * e.g.
     *
     * pre = [(1), (2,4,5), (3,6,7)]
     *
     * post = [(4,5,2), (6,7,3), (1)]
     *
     * First element of left-child is 2 and the length of it is 3.
     *
     * root = new TreeNode(1)
     * root.left = build((2,4,5), (4,5,2))
     * root.right = build((3,6,7), (6,7,3))
     *
     * If use O(n) look up to find left root:
     * Time  : O(logn) ~ O(n ^ 2)
     * Space : O(logn) ~ O(n)
     *
     * For the following solution with HashMap to speed up look up:
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution {
        public TreeNode constructFromPrePost(int[] pre, int[] post) {
            if (pre == null || post == null) return null;

            /**
             * speed up looking up for left root in post[] by putting them
             * in map, key is value in post[], value is its index.
             */
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < post.length; i++) {
                map.put(post[i], i);
            }

            return helper(pre, 0, pre.length - 1, post, 0, post.length - 1, map);
        }

        private TreeNode helper(int[] pre, int preStart, int preEnd, int[] post, int postStart, int postEnd, Map<Integer, Integer> map) {
            if (preStart > preEnd || postStart > postEnd) {
                return null;
            }


            int cur = pre[preStart];
            TreeNode root = new TreeNode(cur);

            /**
             * !!!
             * This if condition is unique among the 3 constructing binary tree problems.
             *
             * Notice it is "preStart == preEnd", NOT pre.length - 1!!!
             */
            if (preStart == preEnd) {
                root.left = null;
                root.right = null;
                return root;
            }

            int leftRoot = pre[preStart + 1];
            int leftEndInPost = map.get(leftRoot);

            int leftStartInPre = preStart + 1;
            int leftEndInPre = leftStartInPre + (leftEndInPost - postStart);

            root.left = helper(pre,  leftStartInPre, leftEndInPre,
                               post, postStart,      leftEndInPost,
                               map);

            root.right = helper(pre,   leftEndInPre + 1,           preEnd,
                                post, leftEndInPost + 1, postEnd - 1,
                                map);

            return root;
        }
    }
}