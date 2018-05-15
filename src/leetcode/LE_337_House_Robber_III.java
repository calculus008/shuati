package leetcode;

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
     */


    /**
         DP
         Time  : O(n)
         Space : O(n)

         1 : Rob
         0 : Not Rob

         For any node N:
         Rob      max val = left Child (0) + right child (1) + N.val
         Not Rob  max val = max(left child rob, left child not rob) + max(right child rob, right child not rob)
     **/
    public int rob(TreeNode root) {
        if (root == null) return 0;
        int[] res = helper(root);
        return Math.max(res[0], res[1]);
    }

    private int[] helper(TreeNode root) {
        if (root == null) return new int[2];

        int[] ret = new int[2];

        int[] left = helper(root.left);
        int[] right = helper(root.right);

        ret[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        ret[1] = left[0] + right[0] + root.val;

        return ret;
    }
}
