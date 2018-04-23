package leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 4/21/18.
 */
public class LE_297_Serialize_And_Deserialize_Binary_Tree {
    /**
         Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer,
         or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

         Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should
         work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

         For example, you may serialize the following tree

         1
         / \
         2   3
         / \
         4   5
         as "[1,2,3,null,null,4,5]", just the same as how LeetCode OJ serializes a binary tree.
         You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

         Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

        Hard
     */

    /**
     * Very Important
     */

    /**
     * Solution 1 : Iterative, BFS
     * @param root
     * @return
     */
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";//!!!

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur == null) {
                sb.append("null ");
                continue;
            }
            sb.append(cur.val + " ");
            queue.offer(cur.left);
            queue.offer(cur.right);
        }

        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) return null;//!!!

        String[] str = data.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(str[0]));

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        for (int i = 1; i < str.length; i++) {
            TreeNode cur = queue.poll();
            if (!str[i].equals("null")) {
                cur.left = new TreeNode(Integer.parseInt(str[i]));
                queue.offer(cur.left);
            }

            i++;//!!!

            if (!str[i].equals("null")) {
                cur.right = new TreeNode(Integer.parseInt(str[i]));
                queue.offer(cur.right);
            }
        }

        return root;
    }

    /**
     * Solution 2, recursion DFS, preorder
     */

    public String serialize1(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        encode(root, sb);
        return sb.toString();
    }

    private void encode(TreeNode root, StringBuilder sb) {
        if(root == null) {
            sb.append("#").append(" ");
            return;
        }

        sb.append(root.val).append(" ");
        encode(root.left, sb);
        encode(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize1(String data) {
        //No need to use Deque, declare it as LinkedList is ok
        //Queue interface does not have "addAll" method
        Deque<String> q = new LinkedList<>();
        //!!!
        q.addAll(Arrays.asList(data.split(" ")));

        return decode(q);
    }

    private TreeNode decode(Deque<String> q) {
        if(q.isEmpty()) return null;

        String s = q.remove();
        if(s.equals("#")) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(s));
        node.left = decode(q);
        node.right = decode(q);

        return node;
    }
}
