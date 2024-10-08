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
     *   Given a non-empty binary search tree and a target value,
     *   find k values in the BST that are closest to the target.

         Note:
         Given target value is a floating point.
         You may assume k is always valid, that is: k ≤ total nodes.
         You are guaranteed to have only one unique set of k values in the BST that are closest to the target.

         Follow up:
         Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?

         Hard
     */

    class Solution4_Practice { // two stacks, two pointers, Time : O(log(n) + k)
        public List<Integer> closestKValues(TreeNode root, double target, int k) {
            List<Integer> res = new ArrayList<>();
            if (root == null || k == 0) {
                return res;
            }

            Stack<TreeNode> pre = new Stack<>();
            Stack<TreeNode> suc = new Stack<>();

            while (root != null) {
                if (root.val < target) {
                    pre.push(root);
                    root = root.right;
                } else {
                    suc.push(root);
                    root = root.left;
                }
            }

            while (res.size() < k) {
                double d1 = pre.isEmpty() ? Double.MAX_VALUE : Math.abs(pre.peek().val - target);//!!!peek()
                double d2 = suc.isEmpty() ? Double.MAX_VALUE : Math.abs(suc.peek().val - target);//!!!peek()

                int next = 0;
                if (d1 > d2) {
                    next = suc.peek().val;
                    goSuc(suc);
                    res.add(next);
                } else {
                    next = pre.peek().val;
                    goPre(pre);
                    res.add(0, next);
                }

                // res.add(next);
            }

            return res;
        }

        private void goSuc(Stack<TreeNode> stack) {
            TreeNode node = stack.pop().right;//!!!pop().right
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        private void goPre(Stack<TreeNode> stack) {//!!!pop().left
            TreeNode node = stack.pop().left;
            while (node != null) {
                stack.push(node);
                node = node.right;
            }
        }
    }

    /**
     * Solution 1_1, same logic as Solution 1, use generic list method
     * 中序遍历暴力解法简单易懂。队列没满(< k), 遇到一个node就塞进去；满了就把距离远的删了，距离近的塞进去。
     */
    class Solution1_1 {
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
    }

    /**
     * BST + 2 pointers
     *
     * 重点看Solution 3 and Solution 4
     */

    /**
      Solution 1 : Time : O(n), Space : O(n)
      Inorder
     */
    class Solution1 {
        public List<Integer> closestKValues1(TreeNode root, double target, int k) {
            LinkedList<Integer> res = new LinkedList<>();
            if (root == null) return res;
            helper(root, target, k, res);
            return res;
        }

        public void helper(TreeNode root, double target, int k, LinkedList<Integer> res) {
            if (root == null) return;

            /**
             Inorder property used here : inorder of BST is sorted.
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
    }


    /**
     Solution 2
     Same as Solution 4
     This is the best solution. Time :  O(log(n) + k)

     Assumption : BST is balanced

     !!!Building each of the stacks takes O(log(n)) assuming BST is balanced.
     Each call to getNextPred/Succ has an amortized cost of O(1), since every node is pushed and popped at most once.

     https://leetcode.com/problems/closest-binary-search-tree-value-ii/discuss/70503/O(logN)-Java-Solution-with-two-stacks-following-hint
     */
    class Solution2 {
        public List<Integer> closestKValues2(TreeNode root, double target, int k) {
            List<Integer> res = new ArrayList<>();
            Stack<TreeNode> pred = new Stack<>(); //!!! "<common.TreeNode>"
            Stack<TreeNode> succ = new Stack<>();
            initPred(root, target, pred);
            initSucc(root, target, succ);

            //remove duplicates between pred and succ
            if (!pred.isEmpty() && !succ.isEmpty() && pred.peek().val == succ.peek().val) {
                findNext(pred, false);
            }

            while (k-- > 0) {
                if (succ.isEmpty()) {
                    res.add(findNext(pred, false));
                } else if (pred.isEmpty()) {
                    res.add(findNext(succ, true));
                } else {
                    double predDiff = Math.abs((double) target - pred.peek().val);//!!! "peek()", NOT, "pop()"
                    double succDiff = Math.abs((double) target - succ.peek().val);//!!! "peek()", NOT, "pop()"
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
         *     8
         *    / \
         *   3   9
         *  / \
         * 2  7
         * <p>
         * target = 6
         * succ : 8 7
         * pred : 3
         * <p>
         * !!!So it does not find all pred and succ in the tree, instead, it finds the closed ones to target.
         * <p>
         * 核心的技巧： 无论是往左子树还是往右子树走，走之字型(先右后左，或者，先左后右）。因为每次都只走一个子树，所以在
         * init阶段我么没有找出所有的pred和succ. 因此在下一步中，用findNext()，弹出stack顶部的元素，然后，同样走之字型
         * 找下一个元素，然后push into stack
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

    /**
     * Solution 3 : Brute Force
     * Time and Space : O(n)

         先用 inorder traversal 求出中序遍历
         找到第一个 >= target 的位置 index
         从 index-1 和 index 出发，设置两根指针一左一右，获得最近的 k 个整数
     *
     */
    class Solution3 {
        public List<Integer> closestKValues3_JiuZhang(TreeNode root, double target, int k) {
            List<Integer> values = new ArrayList<>();

            traverse(root, values);

            int i = 0, n = values.size();
            for (; i < n; i++) {
                if (values.get(i) >= target) {
                    break;
                }
            }

            /**
             * target value is bigger than the largest element in BST
             */
            if (i >= n) {
                return values.subList(n - k, n);
            }

            /**
             * tow pointers
             */
            int left = i - 1, right = i;
            List<Integer> result = new ArrayList<>();
            for (i = 0; i < k; i++) {
                /**
                 * left is in valid range (>=0) and diff between left element and target is smaller than
                 * diff between right element and target.
                 * OR
                 * left is in valid range, right is out of range (>= n)
                 *
                 * add left and left moves one more step
                 *
                 * Otherwise
                 *
                 * add right and right moves one more step
                 */
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
    }

    /**
     * Solution 4 :
     *
     * 最优算法，时间复杂度 O(logn + k)，空间复杂度 O(logn)  (assume the BST is balanced).
     *
     * In essence, it is the same idea of solution 1, first find the element that is the closet to
     * given target , then use two pointers to find k closet elements. Instead of first traversing
     * BST inorder to output all elements into a list(which takes o(n)), we do it using BST properties
     * and don't need to traverse the whole BST.
     *
     * Here we use 2 stacks (prev and next) which are taking the roles of two pointers.
     * Building each of the stacks (prev and next) takes O(log(n)) assuming BST is balanced.
     * Each call to getNext/goPrev has an amortized cost of O(1), since every node is pushed and popped at most once.
     *
     * 思路等同于从指定节点开始分别向前和向后遍历，直到找到k个最接近target的节点。
     * 使用prev和next两个栈分别记录前驱和后继，goPrev相当于反向中序遍历，goNext相当于正向中序遍历。
     * 相当于双指针。
     */
    class Solution4 {
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
                /**
                 * current value is smaller than target, so it must be predecessor of target,
                 * add it to prev stack, then we need to find target on the right
                 */
                if (node.val < target) {
                    prev.push(node);
                    node = node.right;
                } else {
                    /**
                     * current value is bigger than target, so it must be successor of target,
                     * add it to next stack, then we need to find target on the left.
                     */
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
         *     8
         *    / \
         *   3   9
         *  / \
         * 2  7
         * <p>
         * target = 6
         * succ : 8 7
         * pred : 3
         * <p>
         * !!!So it does not find all pred and succ in the tree, instead, it finds the closed ones to target.
         * <p>
         * 核心的技巧： 无论是往左子树还是往右子树走，走之字型(先右后左，或者，先左后右）。因为每次都只走一个子树，所以在
         * init阶段我么没有找出所有的pred和succ. 因此在下一步中(goNext and goPrev)，弹出stack顶部的元素，然后，同样走之字型
         * 找下一个元素，然后压入stack
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
}

