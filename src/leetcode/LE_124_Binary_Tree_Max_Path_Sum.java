package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_124_Binary_Tree_Max_Path_Sum {
    /**
         Given a non-empty binary tree, find the maximum path sum.

         For this problem, a path is defined as any sequence of nodes from some starting
         node to any node in the tree along the parent-child connections. The path must
         contain at least one node and does not need to go through the root.

         Example 1:
         Input: [1,2,3]

           1
          / \
         2   3

         Output: 6

         Example 2:
         Input: [-10,9,20,null,null,15,7]

             -10
             / \
            9  20
          /  \
         15   7

         Output: 42

         Hard
     */

    class solution_clean {
        int res;

        public int maxPathSum(TreeNode root) {
            if (root == null) return 0;

            res = Integer.MIN_VALUE;

            helper(root);
            return res;
        }

        public int helper(TreeNode root) {
            if (root == null) return 0;

            int l = Math.max(0, helper(root.left));//!!!
            int r = Math.max(0, helper(root.right));

            res = Math.max(res, l + root.val + r);

            return Math.max(l, r) + root.val;
        }
    }



    /**
        Postorder
                 3
                / \
               9   20
              / \
             15  7

             Recurse to 9 :  res = 15 + 9 + 7 = 31
                             return : 15 + 9
             Recurse to 20 : res = 31
                             return : 20
             Recurse to 3 :  res = (15 + 9) + 3 + 20 = 47
                             return : (15 + 9) + 3 = 27

            Return value in each recursion has nothing to do with "res"

            Compare with LI_475_Binary_Tree_Maximum_Path_Sum_II (path only start from root)

            Similar problems:
            LE_543_Diameter_Of_Binary_Tree
            LE_687_Longest_Univalue_Path
    */

        //!!! can't make it res = 0
        int res;

        public int maxPathSum(TreeNode root) {
            if (root == null) return 0;

            /**
             * !!!
             * init with min value, otherwise, won't work for case [-3]
             */
            res = Integer.MIN_VALUE;

            helper(root);
            return res;
        }

        /**
         * For return value of this helper, it is the length of the path that
         * goes through the input root. It is NOT the final answer since the path
         * does not have to go through any node and can go from right branch to left branch
         */
        public int helper(TreeNode root) {
            if (root == null) return 0;

            /**
             * !!!
             * !!!
             * " Math.max(0, helper(root.left))"
             *
             * For case :
             *    2
             *   /
             *  -1
             *
             *  Result should be 2 (root), not 1 (2 + (-1)), therefore, set threshold at 0.
             *  If path sum from one of my children will decrease total sum, ignore them!!!
             */
            int l = Math.max(0, helper(root.left));//!!!
            int r = Math.max(0, helper(root.right));

            /**
             * res是所有path的sum, 所以要l + r + root.val
             */
            res = Math.max(res, l + root.val + r);

            /**
             * !!!因为要找路径上的sum, 所以只能挑左和右中较大的值，再加上root.val
             * In other words, the returned value here is just part of the
             * path sum calculated later.
             */
            return Math.max(l, r) + root.val;
        }
}
