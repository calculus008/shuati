package leetcode;

import common.TreeNode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LE_449_Serialize_And_Deserialize_BST {
    /**
         Serialization is the process of converting a data structure or object into a sequence
         of bits so that it can be stored in a file or memory buffer, or transmitted across
         a network connection link to be reconstructed later in the same or another computer environment.

         Design an algorithm to serialize and deserialize a binary search tree. There is no restriction
         on how your serialization/deserialization algorithm should work. You just need to ensure that a
         binary search tree can be serialized to a string and this string can be deserialized to the original tree structure.

         The encoded string should be as compact as possible.

         Note:
         Do not use class member/global/static variables to store states.
         Your serialize and deserialize algorithms should be stateless.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-449-serialize-and-deserialize-bst/
     *
     * Can use exact code of LE_297_Serialize_And_Deserialize_Binary_Tree to solve it.
     *
     * !!!
     * Optimization is to use property of BST :
     * Save the space to store NULL node in encode(), when doing decode(),
     * use range condition guaranteed by BST to tell if we have a NULL.
     */

    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuffer sb = new StringBuffer();
            encode(root, sb);
            return sb.toString();
        }

        private void encode(TreeNode root, StringBuffer sb) {
            if (root == null) {
                /**
                 * !!!
                 * for , we do "sb.append("#").append(" ")", here we just return.
                 */
                return;
            }

            sb.append(root.val).append(" ");
            encode(root.left, sb);
            encode(root.right, sb);
            return;
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if(null == data || ("".equals(data))) {
                return null;
            }

            Queue<String> q = new LinkedList<>();
            q.addAll(Arrays.asList(data.split(" ")));

            /**
             * !!!
             * Here, implicitly, we pass min and max as NULL for Integer (NOT int)
             * This is a better way of setting initial value for min and max.
             * Use Integer.MIN_VALUE and Integer.MAX_VALUE has issues since those
             * 2 values may exist in the tree.
             *
             * This is the trick from LE_98_Validate_BST
             */
            return decode(q, null, null);
        }

        private TreeNode decode(Queue<String> q, Integer min, Integer max) {
            //!!!
            if(q.isEmpty()) return null;

            int cur = Integer.valueOf(q.peek());

            /**
             * !!! Use BST range condition to tell if it's NULL
             */
            if((max != null && cur > max) || (min != null && cur< min)) {
                return null;
            }

            q.poll();

            TreeNode node =new TreeNode(cur);
            node.left = decode(q, min, cur);
            node.right = decode(q, cur, max);

            return node;
        }
    }
}