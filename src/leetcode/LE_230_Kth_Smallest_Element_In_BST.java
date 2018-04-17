package leetcode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by yuank on 3/30/18.
 */
public class LE_230_Kth_Smallest_Element_In_BST {
    /*
        Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

        Note:
        You may assume k is always valid, 1 ≤ k ≤ BST's total elements.

        Follow up:
        What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently?
        How would you optimize the kthSmallest routine?
     */

    /*
       For follow up:
       we can keep both the kth smallest element and (k-1)th smallest element. If we insert or delete an element larger than the kth smallest elemen,
       the result remains unaffected. If something smaller than is inserted, compare it with the (k-1)th smallest element. The larger one becomes
       the new kth smallest element and adjust (k-1)th element accordingly.We may also need to keep track of the (k+1)th smallest element in case
       of deleting a node smaller than the kth element.
     */

    //Solution 1 : DFS inorder traversal
    //Time and Space : O(n)
    private int count;
    private int res;

    public int kthSmallest(TreeNode root, int k) {
        if (root == null) return 0;
        count = k;
        helper(root);
        return res;
    }

    private void helper(TreeNode root) {
        if (root == null) return;

        helper(root.left);
        count--;
        if (count == 0) {
            res = root.val;
        }
        helper(root.right);
    }

    //Solution 2 : DFS inorder iterative
    public int kthSmallest2(TreeNode root, int k) {
        Stack<TreeNode> st = new Stack<>();

        while (root != null) {
            st.push(root);
            root = root.left;
        }

        while (k != 0) {
            TreeNode n = st.pop();
            k--;
            if (k == 0) return n.val;
            TreeNode right = n.right;
            while (right != null) {
                st.push(right);
                right = right.left;
            }
        }

        return -1; // never hit if k is valid
    }

    //Solution 3 : Time : O(k), Space : O(k)
    public int kthSmallest3(TreeNode root, int k) {
        ArrayList<Integer> buffer = new ArrayList<Integer>();
        inorderSearch(root, buffer, k);
        return buffer.get(k-1);
    }
    public void inorderSearch(TreeNode node, ArrayList<Integer> buffer, int k){
        if(buffer.size() >= k)
            return;
        if(node.left != null){
            inorderSearch(node.left, buffer, k);
        }
        buffer.add(node.val);
        if(node.right != null){
            inorderSearch(node.right, buffer, k);
        }
    }

}
