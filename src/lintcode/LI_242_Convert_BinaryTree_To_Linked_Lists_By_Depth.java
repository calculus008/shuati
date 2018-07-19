package lintcode;

import common.ListNode;
import leetcode.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by yuank on 7/18/18.
 */
public class LI_242_Convert_BinaryTree_To_Linked_Lists_By_Depth {
    /**
         Given a binary tree, design an algorithm which creates a linked list of all the nodes at each depth
         (e.g., if you have a tree with depth D, you'll have D linked lists).

         Example
         Given binary tree:

             1
            / \
           2   3
          /
         4
         return

         [
         1->null,
         2->3->null,
         4->null
         ]

         Easy
     */

    public List<ListNode> binaryTreeToLists(TreeNode root) {
        List<ListNode> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            ListNode dummy = new ListNode(0);
            ListNode pre = null;

            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();

                ListNode node = new ListNode(cur.val);
                if (i == 0) {
                    dummy.next = node;
                } else {
                    pre.next = node;
                }

                pre = node;

                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }

            res.add(dummy.next);
        }

        return res;
    }

    public List<ListNode> binaryTreeToLists_JiuZhang(TreeNode root) {
        List<ListNode> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        ListNode dummy = new ListNode(0);
        ListNode pre = null;

        while (!q.isEmpty()) {
            int size = q.size();
            dummy.next = null;
            pre = dummy;

            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();

                pre.next = new ListNode(cur.val);
                pre = pre.next;

                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }

            res.add(dummy.next);
        }

        return res;
    }
}
