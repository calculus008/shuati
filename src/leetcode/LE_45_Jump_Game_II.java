package leetcode;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_45_Jump_Game_II {
    /**
        Given an array of non-negative integers, you are initially positioned at the first index of the array.

        Each element in the array represents your maximum jump length at that position.

        Your goal is to reach the last index in the minimum number of jumps.

        For example:
        Given array A = [2,3,1,1,4]

        The minimum number of jumps to reach the last index is 2.
        (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
     */

    /**nextMax=0, curMax=0
     * Solution 1 : Greedy, Time : O(n), Space : O(1)
     *
     * For Loop Start
     * 2 3 1 1 4      nextMax=0 lastMax=0  res=0
     *
     *
     * 2 3 1 1 4  i=0 nextMax=2, curMax=2  res=1
     * *
     *
     * 2 3 1 1 4  i=1 nextMax=4, curMax=2  res=1
     *   *
     *
     * 2 3 1 1 4  i=2 nextMax=4, curMax=4  res=2
     *     *
     *
     * 2 3 1 1 4  i=3 nextMax=4, curMax=4  res=2
     *       *
     * For Loop Stop
     *
     *

     i=0, nums[0]=2, nextMax=0, curMax=0
     update res, res=1, curMax=2
     i=0, nums[0]=2, nextMax=2, curMax=2
     ---------
     i=1, nums[1]=3, nextMax=2, curMax=2
     i=1, nums[1]=3, nextMax=4, curMax=2
     ---------
     i=2, nums[2]=1, nextMax=4, curMax=2
     update res, res=2, curMax=4
     i=2, nums[2]=1, nextMax=4, curMax=4
     ---------
     i=3, nums[3]=1, nextMax=4, curMax=4
     i=3, nums[3]=1, nextMax=4, curMax=4
     ---------

     It assumes we can always get to the end of the array.
     **/
    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int nextMax = 0;
        int curMax = 0;
        int res = 0;

        /**!!!
         * "i < nums.length - 1",
         * if get to the last element, it is possible "i == lastMax",
         * res plus 1, which is unnecessary.
         **/
        for (int i = 0; i < nums.length - 1; i++) {
            System.out.println("i=" + i + ", nums["+i+"]="+nums[i]+", nextMax=" + nextMax + ", curMax=" + curMax);

            nextMax = Math.max(nextMax, i + nums[i]);

            if (i == curMax) {
                res++;
                curMax = nextMax;
                System.out.println("update res, res=" + res + ", curMax=" + curMax);
            }
            System.out.println("i=" + i + ", nums["+i+"]="+nums[i]+", nextMax=" + nextMax + ", curMax=" + curMax);
            System.out.println("---------");
        }

        return res;
    }

    //Solution 2 : BFS
    /**
    BFS, where nodes in level i are all the nodes that can be reached in i-1th jump. for example. 2 3 1 1 4 , is
        2||
        3 1||
        1 4 ||
     */
    public static int jump1(int[] nums) {
        //!!! nums.length < 2
        if (nums == null || nums.length < 2) return 0;

        int curMax = 0;
        int nextMax = 0;
        int res = 0;
        int i = 0;

        while (i <= curMax) {//nodes count of current level>0
            res++;
            for (; i <= curMax; i++) {//traverse current level , and update the max reach of next level
                nextMax = Math.max(nextMax, i + nums[i]);

                if (nextMax >= nums.length - 1) {// if last element is in level+1,  then the min jump=level
                    return res;
                }
            }
            curMax = nextMax;
        }

        return 0;
    }

    // need to use bfs 11 ms very fast and neat
    public static int jumpBfs(int[] nums) {
        int low = 0, high = 0;
        for (int k = 0; k < nums.length; k++) {
            if (low > high) {
                break;
            }

            int farthest = 0;
            for (int i = low; i < high + 1; i++) {
                if (nums[i] + i >= nums.length - 1) return k + 1;
                farthest = Math.max(farthest, nums[i] + i);
            }
            low = high + 1;
            high = farthest;
        }
//        throw new java.lang.RuntimeException("No such path!");
        return 0;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        jump(nums);
    }
}
