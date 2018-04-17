package leetcode;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_124_Binary_Tree_Max_Path_Sum {
    /*
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

            //!!! there may be negative number in tree, set the lower bound at 0
            int l = Math.max(0, helper(root.left));
            int r = Math.max(0, helper(root.right));

            res = Math.max(res, l + root.val + r);
            return Math.max(l, r) + root.val;
        }
}
