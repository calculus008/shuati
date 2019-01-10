package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yuank on 4/16/18.
 */
public class LE_272_Closest_BST_Value_II {
    /**
     *   Given a non-empty binary search tree and a target value, find k values in the BST that are closest to the target.

         Note:
         Given target value is a floating point.
         You may assume k is always valid, that is: k ≤ total nodes.
         You are guaranteed to have only one unique set of k values in the BST that are closest to the target.

         Follow up:
         Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?

         Hard
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
            } else {//!!!! MUST return here, otherwise, it will keep going on to execute the rest of the logic,  can't maintain the size in res as k
                return;
            }
        }
        res.add(root.val);

        helper(root.right, target, k, res);
    }

    /**
     * Solution 1_1, same logic as Solution 1, use generic list method
     * 中序遍历暴力解法简单易懂。队没满遇到一个node就塞进去；满了就把距离远的删了，距离近的塞进去。
     */
    public List<Integer> closestKValues1_1(TreeNode root, double target, int k) {
        List<Integer> ret = new LinkedList<Integer>();

        dfs(root, target, k, ret);
        return ret;
    }

    private void dfs(TreeNode root, double target, int k, List<Integer> ret) {
        if (root == null) {
            return;
        }

        dfs(root.left, target, k, ret);

        if (ret.size() < k) {
            ret.add(root.val);
        } else if (Math.abs(root.val - target) < Math.abs(ret.get(0) - target)) {
            ret.remove(0);
            ret.add(root.val);
        }

        dfs(root.right, target, k, ret);
    }

    /**
      Solution 2 :
     Same as Solution 4
     This is the best solution. Time :  O(log(n) + k)

     !!!Building each of the stacks takes O(log(n)) assuming BST is balanced.
     Each call to getNextPred/Succ has an amortized cost of O(1), since every node is pushed and popped at most once.

     https://leetcode.com/problems/closest-binary-search-tree-value-ii/discuss/70503/O(logN)-Java-Solution-with-two-stacks-following-hint
     */
    public List<Integer> closestKValues2(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> pred = new Stack<>(); //!!! "<common.TreeNode>"
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

         !!!So it does not find all pred and succ in the tree, instead, it finds the closed ones to target.

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

    /**
     * Solution 3 : Brute Force
     * Time and Space : O(n)

         先用 inorder traversal 求出中序遍历
         找到第一个 >= target 的位置 index
         从 index-1 和 index 出发，设置两根指针一左一右，获得最近的 k 个整数
     *
     */
    public List<Integer> closestKValues3_JiuZhang(TreeNode root, double target, int k) {
        List<Integer> values = new ArrayList<>();

        traverse(root, values);

        int i = 0, n = values.size();
        for (; i < n; i++) {
            if (values.get(i) >= target) {
                break;
            }
        }

        if (i >= n) {
            return values.subList(n - k, n);
        }

        int left = i - 1, right = i;
        List<Integer> result = new ArrayList<>();
        for (i = 0; i < k; i++) {
            if (left >= 0 && (right >= n || target - values.get(left) < values.get(right) - target)) {
                result.add(values.get(left));
                left--;
            } else {
                result.add(values.get(right));
                right++;
            }
        }

        return result;
    }

    private void traverse(TreeNode root, List<Integer> values) {
        if (root == null) {
            return;
        }

        traverse(root.left, values);
        values.add(root.val);
        traverse(root.right, values);
    }

    /**
     * Solution 4 :
     *
     * 最优算法，时间复杂度 O(k + logn)，空间复杂度 O(logn)
     *
     * Building each of the stacks takes O(log(n)) assuming BST is balanced.
     * Each call to getNext/goPrev has an amortized cost of O(1), since every node is pushed and popped at most once.
     *
     * 思路等同于从指定节点开始分别向前和向后遍历，直到找到k个最接近target的节点。
     * 使用prev和next两个栈分别记录前驱和后继，goPrev相当于反向中序遍历，goNext相当于正向中序遍历。
     * 相当于双指针。
     */

    public List<Integer> closestKValues4_JiuZhang(TreeNode root, double target, int k) {
        Stack<TreeNode> next = new Stack<TreeNode>();
        Stack<TreeNode> prev = new Stack<TreeNode>();
        TreeNode node = root;

        /**
         * find the nodes closest to target.
         * Notice : it does not iterate through all nodes.
         *          This steps takes O(logn), assume BST is balanced. Or O(h)
         **/
        while (node != null) {
            if (node.val < target) {
                prev.push(node);
                node = node.right;
            } else {
                next.push(node);
                node = node.left;
            }
        }

        List<Integer> ret = new LinkedList<Integer>();

        while (ret.size() < k) {
            /**
             * Emulate 2 pointers, get number from top of the stacks, calculate their absolute difference to target value
             * Be careful, need to deal with case that stack is empty.
             */
            double distp = prev.isEmpty() ? Integer.MAX_VALUE : Math.abs(prev.peek().val - target);
            double distn = next.isEmpty() ? Integer.MAX_VALUE : Math.abs(next.peek().val - target);

            /**
             * Emulate 2 pointers, compare and find the closest node, and move the corresponding stack.
             */
            if (distp < distn) {
                ret.add(0, prev.peek().val);
                goPrev(prev);
            } else {
                ret.add(next.peek().val);
                goNext(next);
            }
        }

        return ret;
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

         !!!So it does not find all pred and succ in the tree, instead, it finds the closed ones to target.

         核心的技巧： 无论是往左子树还是往右子树走，走之字型(先右后左，或者，先左后右）。因为每次都只走一个子树，所以在
         init阶段我么没有找出所有的pred和succ. 因此在下一步中(goNext and goPrev)，弹出stack顶部的元素，然后，同样走之字型
         找下一个元素，然后压入stack
     */

    private void goNext(Stack<TreeNode> st) {
        TreeNode r = st.pop().right;

        while (r != null) {
            st.push(r);
            r = r.left;
        }
    }

    private void goPrev(Stack<TreeNode> st) {
        TreeNode l = st.pop().left;

        while (l != null) {
            st.push(l);
            l = l.right;
        }
    }
}

