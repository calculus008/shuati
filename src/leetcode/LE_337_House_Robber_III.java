package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 5/10/18.
 */
public class LE_337_House_Robber_III {
    /**
         The thief has found himself a new place for his thievery again.
         There is only one entrance to this area, called the "root."
         Besides the root, each house has one and only one parent house.
         After a tour, the smart thief realized that "all houses in this place forms a binary tree".
         It will automatically contact the police if two directly-linked houses were broken into on the same night.

         Determine the maximum amount of money the thief can rob tonight without alerting the police.

         Example 1:
           3
          / \
         2   3
         \   \
         3   1
         Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.

         Example 2:
             3
            / \
           4   5
          / \   \
         1   3   1
         Maximum amount of money the thief can rob = 4 + 5 = 9.

         Medium

         https://leetcode.com/problems/house-robber-iii

         LE_198_House_Robber
         LE_213_House_Robber_II
         LE_337_House_Robber_III
     */


    /**
         DP
         Time  : O(n)
         Space : O(n)

         index      State
           1    :   Rob
           0    :   Not Rob

         For any node N:
         Rob      max val = left Child (0) + right child (0) + N.val
         Not Rob  max val = max(left child rob, left child not rob) + max(right child rob, right child not rob)

         https://leetcode.com/problems/house-robber-iii/discuss/79330/Step-by-step-tackling-of-the-problem

         Now let's take one step back and ask why we have overlapping subproblems.
         If you trace all the way back to the beginning, you'll find the answer lies
         in the way how we have defined rob(root). As I mentioned, for each tree root,
         there are two scenarios: it is robbed or is not. rob(root) does not distinguish
         between these two cases, so "information is lost as the recursion goes deeper
         and deeper", which results in repeated subproblems.

         If we were able to maintain the information about the two scenarios for each
         tree root, let's see how it plays out. Redefine rob(root) as a new function
         which will return an array of two elements:

          The first element of which denotes the maximum amount of money that can be
          robbed if root is not robbed;
          The second element signifies the maximum amount of money robbed if it is robbed.
     **/
    public int rob(TreeNode root) {
        if (root == null) return 0;
        int[] res = helper(root);
        return Math.max(res[0], res[1]);
    }

    /**
     * Postorder
     */
    private int[] helper(TreeNode root) {
        if (root == null) return new int[2];

        int[] ret = new int[2];

        int[] left = helper(root.left);
        int[] right = helper(root.right);

        /**
         *  The maximum amount of money that can be robbed if root is not robbed;
         */
        ret[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        /**
         *  The maximum amount of money robbed if root is robbed.
         */
        ret[1] = left[0] + right[0] + root.val;

        return ret;
    }
}
