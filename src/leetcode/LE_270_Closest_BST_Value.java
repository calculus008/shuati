package leetcode;

/**
 * Created by yuank on 4/15/18.
 */
public class LE_270_Closest_BST_Value {
    /**
     * Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.

     Note:
     Given target value is a floating point.
     You are guaranteed to have only one unique value in the BST that is closest to the target.
     */

    //Time : O(n), Space : O(1)
    public int closestValue(TreeNode root, double target) {
        int res = root.val;
        while (root != null) {
            if (Math.abs(target - res) > Math.abs(target - root.val)) {
                res = root.val;
            }
            root = root.val > target ? root.left : root.right;
        }
        return res;
    }
}
