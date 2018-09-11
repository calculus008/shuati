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

        The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
     */

    /**
     * Solution 1 : Greedy, Time : O(n), Space : O(1)
     *
     * For Loop Start
     * 2 3 1 1 4      curMax=0 lastMax=0  res=0
     *
     *
     * 2 3 1 1 4  i=0 curMax=2 lastMax=2  res=1
     * *
     *
     * 2 3 1 1 4  i=1 curMax=4 lastMax=2  res=1
     *   *
     *
     * 2 3 1 1 4  i=2 curMax=4 lastMax=4  res=2
     *     *
     *
     * 2 3 1 1 4  i=3 curMax=4 lastMax=4  res=2
     *       *
     * For Loop Stop
     *
     *

     i=0, nums[0]=2, curMax=0, lastMax=0
     update res, res=1, lastMax=2
     i=0, nums[0]=2, curMax=2, lastMax=2
     ---------
     i=1, nums[1]=3, curMax=2, lastMax=2
     i=1, nums[1]=3, curMax=4, lastMax=2
     ---------
     i=2, nums[2]=1, curMax=4, lastMax=2
     update res, res=2, lastMax=4
     i=2, nums[2]=1, curMax=4, lastMax=4
     ---------
     i=3, nums[3]=1, curMax=4, lastMax=4
     i=3, nums[3]=1, curMax=4, lastMax=4
     ---------
     **/
    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int curMax = 0;
        int lastMax = 0;
        int res = 0;

        //!!! "i < nums.length - 1", if get to the last element, it is possible "i == lastMax",
        //     res plus 1, which is unessesary.
        for (int i = 0; i < nums.length - 1; i++) {
            System.out.println("i=" + i + ", nums["+i+"]="+nums[i]+", curMax=" + curMax + ", lastMax=" + lastMax);

            curMax = Math.max(curMax, i + nums[i]);

            if (i == lastMax) {
                res++;
                lastMax = curMax;
                System.out.println("update res, res=" + res + ", lastMax=" + lastMax);
            }
            System.out.println("i=" + i + ", nums["+i+"]="+nums[i]+", curMax=" + curMax + ", lastMax=" + lastMax);
            System.out.println("---------");
        }

        return res;
    }

    //Solution 2 : BFS
    /*
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
            for (; i <= curMax; i++) {
                nextMax = Math.max(nextMax, i + nums[i]);
                if (nextMax >= nums.length - 1) {
                    return res;
                }
            }
            curMax = nextMax;
        }

        return 0;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        jump(nums);
    }
}
