package leetcode;

import java.util.*;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_42_Trapping_Rain_Water {
    /**
        Given n non-negative integers representing an elevation map where the width
        of each bar is 1, compute how much water it is able to trap after raining.

        Compare with LE_11_Container_With_Most_Water

        Hard

        https://leetcode.com/problems/trapping-rain-water
     */


    public int trap(int[] height) {
        /**
         * First pair of pointers, start from beginning and end of the array, move toward each other.
         */
        int left = 0;
        int right = height.length - 1;
        /**
         * Second pair of 2 pointers, record max value from left and right.
         * 在小的一遍找最大的，计算差值
         */
        int leftMax = 0;
        int rightMax = 0;

        int res = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                leftMax = Math.max(leftMax, height[left]);
                res += leftMax - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                res += rightMax - height[right];
                right--;
            }
        }

        return res;
    }


    // 3 passes DP solution from editorial, run with the same time as the one pass DP solution above.
    class Solution {
        public int trap(int[] height) {
            if (height.length == 0) return 0;
            int ans = 0;
            int size = height.length;
            int[] left_max = new int[size];
            int[] right_max = new int[size];

            left_max[0] = height[0];
            for (int i = 1; i < size; i++) {
                left_max[i] = Math.max(height[i], left_max[i - 1]);
            }

            right_max[size - 1] = height[size - 1];
            for (int i = size - 2; i >= 0; i--) {
                // update right max with current max
                right_max[i] = Math.max(height[i], right_max[i + 1]);
            }

            for (int i = 1; i < size - 1; i++) {
                ans += Math.min(left_max[i], right_max[i]) - height[i];
            }
            return ans;
        }
    }

    public int trap_practice(int[] height) {
        if (height == null || height.length == 0) return 0;

        int l = 0, r = height.length - 1;
        int maxL = 0, maxR = 0;
        int res = 0;

        while (l < r) {
            if (height[l] < height[r]) {
                if (height[l] > maxL) {
                    maxL = height[l];
                }
                res += maxL - height[l];
                l++; //!!!
            } else {
                if (height[r] > maxR) {
                    maxR = height[r];
                }
                res += maxR - height[r];
                r--; //!!!
            }
        }

        return res;
    }

    public class Solution_mono_stack {
        public int trap(int[] height) {
            int ans = 0, current = 0;
            Deque<Integer> stack = new LinkedList<Integer>();

            while (current < height.length) {
                while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
                    int top = stack.peek();
                    stack.pop();
                    if (stack.isEmpty()) break;
                    int distance = current - stack.peek() - 1;
                    int bounded_height =
                            Math.min(height[current], height[stack.peek()]) - height[top];
                    ans += distance * bounded_height;
                }
                stack.push(current++);
            }
            return ans;
        }
    }



    /**
     Tow pointer solution : O(n)

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l                     r
     lMax = 0              rMax = 1
     sum = 0 - 0 = 0

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l                   r
     lMax = 1            rMax = 1
     sum = 0 + (1 - 1) = 0

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l                 r
     lMax = 1          rMax = 2
     sum = 0 + (1 - 1) = 0

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l               r
     lMax = 1        rMax = 2
     sum = 0 + (1 - 0) = 1

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l             r
     lMax = 2      rMax = 2
     sum = 1 + (2 - 2) = 1

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l           r
     lMax = 2    rMax = 2
     sum = 1 + (2 - 1) = 2

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l         r
     lMax = 2  rMax = 2
     sum = 2 + (2 - 2) = 2

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l       r
     lMax = 2
     rMax = 3
     sum = 2 + (2 - 2) = 2

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l     r
     lMax = 2
     rMax = 3
     sum = 2 + (2 - 1) = 3


     [0,1,0,2,1,0,1,3,2,1,2,1]
     l   r
     lMax = 2
     rMax = 3
     sum = 3 + (2 - 0) = 5

     [0,1,0,2,1,0,1,3,2,1,2,1]
     l r
     lMax = 2
     rMax = 3
     sum = 5 + (2 - 1) = 6

     */

    /**
     * *****************************************
     */

    /**
     * Variation
     * 比如525225，不是返回总的雨量=9，而是返回最大雨量的[left boundary, right boundary]，所以就是[2,5]，
     */
    public static int trap_variation(int[] height) {
        if (height == null || height.length == 0) return 0;

        int l = 0, r = height.length - 1;
        int maxL = 0, maxR = 0;
        int res = 0;

        int sum = 0;
        int maxSum = 0;

        int left = -1;
        int right = -1;

        int maxLeft = -1;
        int maxRight = -1;

        int[] temp = new int[height.length];

        while (l < r) {
            if (height[l] < height[r]) {
                if (height[l] > maxL) {
                    maxL = height[l];
                    System.out.println("maxl at " + l);
                }

                res += maxL - height[l];
                temp[l] = maxL - height[l];
                l++;
            } else {
                if (height[r] > maxR) {
                    maxR = height[r];
                    System.out.println("maxr at " + r);
                }

                res += maxR - height[r];
                temp[r] = maxR - height[r];
                r--;
            }
        }

        System.out.println(Arrays.toString(temp));
        maxSubArray(temp);
        return res;
    }

    public static int maxSubArray(int[] nums) {
        int res = 0;
        int sum = 0;
        int max = 0;
        int maxL = -1;
        int maxR = -1;

        int start = -1;
        int end = -1;

        for (int i = 0; i < nums.length ; i++) {
            if (nums[i] == 0) {
                sum = 0;
                start = i;
                continue;
            }

            sum += nums[i];
            res= Math.max(res, sum);

            if (sum > max) {
                max = sum;
                maxL = start;
                maxR = i;
                end = i;
            }

            System.out.println("start="+start+",end="+end);
        }

        maxR++;
        System.out.println(max + ",maxL=" + maxL + ",maxR="+maxR);
        return res;
    }

    public static void main(String[] args) {
//        int[] input = {5, 2, 5, 2, 2, 5};
        int[] input = {5, 2, 7, 2, 2, 2, 5};

        System.out.println(trap_variation(input));
    }

}
