package lintcode;

import common.TreeNode;

import java.util.Stack;

/**
 * Created by yuank on 10/17/18.
 */
public class LI_126_Max_Tree {
    /**
         Given an integer array with no duplicates. A max tree building on this array is defined as follow:

         The root is the maximum number in the array
         The left subtree and right subtree are the max trees of the subarray divided by the root number.
         Construct the max tree by the given array.

         Example
         Given [2, 5, 6, 0, 3, 1], the max tree constructed by this array is:

             6
            / \
           5   3
          /   / \
         2   0   1

         Challenge
         O(n) time and memory.

         Hard
     */

    /**
     * Solution from JiuZhang
     *
     * 总体思想是强化班中讲的构造单调递减栈，
     * 有一个小技巧是首先把正无穷大的Node加入到栈中。然后遍历数组，for循环中当前元素是A[i]
     * 1.如果 A[i] 小于栈顶的元素，则无脑 push
     * 2.如果 A[i] 大于栈顶的元素，那么把栈顶元素 pop 出来成为 son 节点，同时比较 A[i] 和 新的栈顶元素，分情况讨论
     *  a.如果 A[i] 较小，那么让 A[i] 成为 son 的 father 节点，同时 push A[i] 节点进栈
     *  b.如果 A[i] 较大，那么 pop 出栈顶元素成为 son 的 father 节点。同时把 father push 回栈。
     *    注意这个时候 A[i] 实际上并没有参与树的构建，所以我用到了 i--，在下一层 for 循环再来判断这个 A[i]
     *
     * for 循环结束后，栈为单调递减栈，这个时候把栈里所有非正无穷大的元素 pop 出来最后 return root
     *
     * [2,5,3,6,3,0,1]
     *
     * stack [max]
     *
     * i = 0
     * stack [max, 2]
     *
     * i = 1
     * stack [max, 5]
     *            /
     *           2
     *
     * i = 2
     * stack [max, 5, 3]
     *            /
     *           2
     *
     * i = 3
     * stack [max, 5, ]  6
     *            / \
     *           2   3
     *
     * i = 3
     * stack [max, 6]
     *            /
     *           5
     *          / \
     *         2  3
     *
     * i = 4
     * stack [max, 6, 3]
     *            /
     *           5
     *          / \
     *         2  3
     *
     * i = 5
     * stack [max, 6, 3, 0]
     *            /
     *           5
     *          / \
     *         2  3
     *
     * i = 6
     * stack [max, 6, 3, 1]
     *            /     /
     *           5     0
     *          / \
     *         2  3
     *
     *           6
     *         /   \
     *        5     3
     *       / \     \
     *      2  3      1
     *               /
     *              0
     * */
    public class Solution {
        public TreeNode maxTree(int[] A) {
            if(A == null || A.length == 0) {
                return null;
            }

            Stack<TreeNode> stack = new Stack<TreeNode>();
            stack.push(new TreeNode(Integer.MAX_VALUE));
            TreeNode root = null;

            for(int i = 0; i < A.length; i++) {
                TreeNode node = new TreeNode(A[i]);

                if(node.val < stack.peek().val) {
                    //save nodes for constructing right links for right substree
                    stack.push(node);
                } else {
                    TreeNode son = stack.pop();
                    if(node.val < stack.peek().val) {
                        //setup left child link
                        node.left = son;
                        stack.push(node);
                    } else {
                        //setup right child link in left subtree
                        TreeNode father = stack.pop();
                        father.right = son;
                        stack.push(father);
                        i--;
                    }
                }
            }

            /**
             * construct right child link from bottom up
             */
            root = stack.pop();
            while(stack.size() > 1) {
                TreeNode newRoot = stack.pop();
                newRoot.right = root;
                root = newRoot;
            }

            return root;
        }
    }

    /**
     * Recursion
     */
    public class Solution2 {
        public TreeNode maxTree(int[] A) {
            TreeNode root = null;
            for(int x : A) {
                root = insert(root, x);
            }
            return root;
        }

        private TreeNode insert(TreeNode node, int x) {
            TreeNode xnode = new TreeNode(x);
            if (node == null)
                return xnode;

            if (xnode.val > node.val) {
                xnode.left = node;
                return xnode;
            } else {
                node.right = insert(node.right, x);
                return node;
            }
        }
    }
}
