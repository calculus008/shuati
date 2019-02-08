package leetcode;

import common.TreeNode;

public class LE_988_Smallest_String_Starting_From_Leaf {
    /**
     * Given the root of a binary tree, each node has a value from 0 to 25 representing
     * thel etters 'a' to 'z': a value of 0 represents 'a', a value of 1 represents
     * 'b', and so on.
     *
     * Find the lexicographically smallest string that starts at a leaf of this tree
     * and ends at the root.
     *
     * (As a reminder, any shorter prefix of a string is lexicographically smaller:
     * for example, "ab" is lexicographically smaller than "aba".  A leaf of a node
     * is a node that has no children.)
     *
     * Example 1:
     * Input: [0,1,2,3,4,3,4]
     * Output: "dba"
     *
     * Example 2:
     * Input: [25,1,3,1,3,0,2]
     * Output: "adz"
     *
     * Example 3:
     * Input: [2,2,1,null,1,0,null,0]
     * Output: "abc"
     *
     * Note:
     * The number of nodes in the given tree will be between 1 and 1000.
     * Each node in the tree will have a value between 0 and 25.
     *
     * Medium
     */

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    class Solution {
        public String smallestFromLeaf(TreeNode root) {
            if (root == null) {
                return "";
            }

            /**!!!
             * Notice we have to recover char value from root's val, it's not char.
             *
             * Also, need to do type cast.
             **/
            char cur = (char)('a' + root.val);

            String l = smallestFromLeaf(root.left);
            String r = smallestFromLeaf(root.right);

            if (l.equals("")) {
                return r + cur;//"r + cur"
            }

            if (r.equals("")) {
                return l + cur;//"l + cur"
            }

            return l.compareTo(r) > 0 ? r + cur : l + cur;
        }
    }
}
