package lintcode;

import leetcode.TreeNode;

/**
 * Created by yuank on 7/24/18.
 */

//For Solution 3
public class LI_578_Lowest_Common_Ancestor_III {
    /**
         Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.
         The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.

         Return null if LCA does not exist.(!!!)

         Example
         For the following binary tree:

             4
            / \
           3   7
          / \
         5   6

         LCA(3, 5) = 4

         LCA(5, 6) = 7

         LCA(6, 7) = 7

         Medium
     */

    /**
     *   Solution 1
         Divide & Conquer + Global Variable（不需要 ResultType）

         这题和 LCA 原题(LE_236_Lowest_Common_Ancestor_Of_BT) 的区别主要是要找的 A 和 B 可能并不存在树里。所以我们要做出这两个改变

         用全局变量把 A 和 B 是否找到保存起来。最后在 main function 里面要查看是否都找到
         当 root 等于 A 或者 B 时不能直接返回root了。原题可以直接返回是因为两个 node 是保证存在的所以这情况下 LCA 一定是 root。
         现在 root 等于 A 或者 B 我们还是要继续往下找是否存在另外的一个
         不用 ResultType 的一个好处是：如果面试的时候出了一个原题，然后问这题做 follow up。如果从头开始写 result type 代码改动会比较大
         。一是比较容易写错，二是时间可能会不够。

         这个方法只需要增加两个全局变量并且改动 LCA 原题的代码两行即可。
     */

    boolean foundA = false;
    boolean foundB = false;

    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode A, TreeNode B) {
        TreeNode res = helper(root, A, B);
        if (foundA && foundB) {
            return res;
        }
        return null;
    }

    public TreeNode helper(TreeNode root, TreeNode A, TreeNode B) {
        if (root == null) {
            return root;
        }

        /**
         * 如果 root 是要找的，更新全局变量
         **/
        TreeNode left = helper(root.left, A, B);
        TreeNode right = helper(root.right, A, B);

        /**
         * 如果 root 是要找的，更新全局变量
         **/
        if (root == A || root == B) {
            foundA = foundA || (root == A);
            foundB = foundB || (root == B);
            return root;
        }

        /**
         注意这种情况返回的时候是不保证两个都有找到的。
         可以是只找到一个或者两个都找到
         所以在最后上面要查看是不是两个都找到了
         **/
        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        } else {
            return null;
        }
    }

    /**
     * Solution 2
     *
     * This solution doesn't need a ResultType class.
     * A member variable is used to store the LCA.
     *
     * The helper function return true if and only if the subtree contains one of the target node.
     * If we have found both, we can return without executing extra logic.
     *
     */
    private TreeNode result;

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode A, TreeNode B) {
        helper(root, A, B);
        return result;
    }

    private boolean helper2(TreeNode root, TreeNode A, TreeNode B) {
        if (result != null) {
            return false;
        }
        if (root == null) {
            return false;
        }

        boolean left = helper2(root.left, A, B);
        boolean right = helper2(root.right, A, B);

        if (left && right) {
            result = root;
            return false;
        }

        if (left || right) {
            if (root == A || root == B) {
                result = root;
                return false;
            }
            return true;
        }
        if (root == A && root == B) {
            result = root;
            return false;
        }
        if (root == A || root == B) {
            return true;
        }
        return false;
    }

    /**
     * Solution 3
     * With ResultType
     */

    class ResultType {
        public boolean a_exist, b_exist;
        public TreeNode node;
        ResultType(boolean a, boolean b, TreeNode n) {
            a_exist = a;
            b_exist = b;
            node = n;
        }
    }

    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode A, TreeNode B) {
        // write your code here
        ResultType rt = helper3(root, A, B);
        if (rt.a_exist && rt.b_exist)
            return rt.node;
        else
            return null;
    }

    public ResultType helper3(TreeNode root, TreeNode A, TreeNode B) {
        if (root == null)
            return new ResultType(false, false, null);

        ResultType left_rt = helper3(root.left, A, B);
        ResultType right_rt = helper3(root.right, A, B);

        boolean a_exist = left_rt.a_exist || right_rt.a_exist || root == A;
        boolean b_exist = left_rt.b_exist || right_rt.b_exist || root == B;

        if (root == A || root == B)
            return new ResultType(a_exist, b_exist, root);

        if (left_rt.node != null && right_rt.node != null)
            return new ResultType(a_exist, b_exist, root);
        if (left_rt.node != null)
            return new ResultType(a_exist, b_exist, left_rt.node);
        if (right_rt.node != null)
            return new ResultType(a_exist, b_exist, right_rt.node);

        return new ResultType(a_exist, b_exist, null);
    }
}
