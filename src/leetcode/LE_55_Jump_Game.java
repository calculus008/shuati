package leetcode;

/**
 * Created by yuank on 3/2/18.
 */
public class LE_55_Jump_Game {
    /**
        Given an array of non-negative integers, you are initially positioned at the first index of the array.

        Each element in the array represents your maximum jump length at that position.

        Determine if you are able to reach the last index.

        For example:
        A = [2,3,1,1,4], return true.

        A = [3,2,1,0,4], return false.
     */

    /**
     * https://zxi.mytechroad.com/blog/greedy/leetcode-55-jump-game/
     *
     * Greedy
     * If you can jump to i, then you can jump to at least i + nums[i].
     *
     * Always jump as far as you can.
     *
     * Keep tracking the farthest index you can jump to.
     *
     * Init far = nums[0].
     *
     * far = max(far, i + nums[i])
     *
     * check far >= n – 1
     */
    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) return false;

        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max < i) {
                return false;
            }
            max = Math.max(max, i + nums[i]); //!!! i + mums[i]
        }

        return max >= nums.length - 1;
//        return true;
    }
}
