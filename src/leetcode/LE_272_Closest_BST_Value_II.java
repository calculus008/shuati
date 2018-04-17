package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yuank on 4/16/18.
 */
public class LE_272_Closest_BST_Value_II {
    /**
     * Given a non-empty binary search tree and a target value, find k values in the BST that are closest to the target.

     Note:
     Given target value is a floating point.
     You may assume k is always valid, that is: k ≤ total nodes.
     You are guaranteed to have only one unique set of k values in the BST that are closest to the target.

     Follow up:
     Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?
     */

    /**
      Solution 1 : Time : O(n), Space : O(n)
     */
    public List<Integer> closestKValues1(TreeNode root, double target, int k) {
        LinkedList<Integer> res = new LinkedList<>();
        if (root == null) return res;
        helper(root, target, k, res);
        return res;
    }

    public void helper(TreeNode root, double target, int k, LinkedList<Integer> res) {
        if (root == null) return;

        /**
            Inorder proeprty used here : inorder of BST is sorted.
            Therefore the k elements in list is always sorted. That's why we can do removeFirst()
         */
        helper(root.left, target, k, res);
        if (res.size() == k) {//!!! "size()"
            if (Math.abs(target - res.peekFirst()) > Math.abs(target - root.val)) {
                res.removeFirst();
            } else {//!!!! MUST return here, otherwise, can't maintain the size in res as k
                return;
            }
        }
        res.add(root.val);

        helper(root.right, target, k, res);
    }

    /**
      Solution 2 : Time : O(klogn)
     https://leetcode.com/problems/closest-binary-search-tree-value-ii/discuss/70503/O(logN)-Java-Solution-with-two-stacks-following-hint
     */
    public List<Integer> closestKValues2(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> pred = new Stack<>(); //!!! "<leetcode.TreeNode>"
        Stack<TreeNode> succ = new Stack<>();
        initPred(root, target, pred);
        initSucc(root, target, succ);

        //remove duplicates between pred and succ
        if(!pred.isEmpty() && !succ.isEmpty() && pred.peek().val == succ.peek().val) {
            findNext(pred, false);
        }

        while (k-- > 0) {
            if (succ.isEmpty()) {
                res.add(findNext(pred, false));
            } else if (pred.isEmpty()) {
                res.add(findNext(succ, true));
            } else {
                double predDiff = Math.abs((double)target - pred.peek().val);//!!! "peek()", NOT, "pop()"
                double succDiff = Math.abs((double)target - succ.peek().val);//!!! "peek()", NOT, "pop()"
                if (predDiff < succDiff) {
                    res.add(findNext(pred, false));
                } else {
                    res.add(findNext(succ, true));
                }
            }
        }

        return res;
    }

    /**
     *
     * @param stack
     * @param isSucc
     * @return
     */
    public int findNext(Stack<TreeNode> stack, boolean isSucc) {
        TreeNode cur = stack.pop();
        int res = cur.val;

        if (isSucc) {
            cur = cur.right;
        } else {
            cur = cur.left;
        }

        while (cur != null) {
            stack.push(cur);
            if (isSucc) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return res;
    }

    /**
     *      8
           / \
          3   9
         / \
        2  7

         target = 6
         succ : 8 7
         pred : 3

         So it does not find all pred and succ in the tree, instead, it finds the closed ones to target.

         核心的技巧： 无论是往左子树还是往右子树走，走之字型(先右后左，或者，先左后右）。因为每次都只走一个子树，所以在
         init阶段我么没有找出所有的pred和succ. 因此在下一步中，用findNext()，弹出stack顶部的元素，然后，同样走之字型
         找下一个元素，然后压如stack
     */
    public void initPred(TreeNode root, double target, Stack<TreeNode> pred) {
        while (root != null) {
            if (root.val == target) {
                pred.push(root);
                break;//!!!
            } else if (root.val < target) {
                pred.push(root);
                root = root.right;
            } else {
                root = root.left;
            }
        }
    }

    public void initSucc(TreeNode root, double target, Stack<TreeNode> succ) {
        while (root != null) {
            if (root.val == target) {
                succ.push(root);
                break;
            } else if (root.val > target) {
                succ.push(root);
                root = root.left;
            } else {
                root = root.right;
            }
        }
    }
}
