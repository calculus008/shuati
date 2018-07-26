package leetcode;

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
    */


        //!!! can't make it res = 0
        int res;

        public int maxPathSum(TreeNode root) {
            if (root == null) return 0;

            //!!! init with min value, otherwise, won't work for case [-3]
            res = Integer.MIN_VALUE;

            helper(root);
            return res;
        }

        public int helper(TreeNode root) {
            if (root == null) return 0;

            /**
             * For case :
             *    2
             *   /
             *  -1
             *
             *  Result should be 2 (root), not 1 (2 + (-1)), therefore, set threshold at 0.
             *  If path sum from one of my children will decrease total sum, ignore them!!!
             */
            int l = Math.max(0, helper(root.left));
            int r = Math.max(0, helper(root.right));

            /**
             * res是所有path的sum, 所以要l + r + root.val
             */
            res = Math.max(res, l + root.val + r);

            /**
             * !!!因为要找路径上的sum, 所以只能挑左和右中较大的值，再加上root.val
             */
            return Math.max(l, r) + root.val;
        }
}
