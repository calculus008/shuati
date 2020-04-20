package leetcode;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_42_Trapping_Rain_Water {
    /**
        Given n non-negative integers representing an elevation dist where the width
        of each bar is 1, compute how much water it is able to trap after raining.

        Compare with LE_11_Container_With_Most_Water

        Hard
     */

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
    public int trap(int[] height) {
        /**
         * First pair of 2 pointers, start from begining and end of the array, move toward each other.
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

    public int trap1(int[] height) {
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
                /**
                 * !!!
                 */
                l++;
            } else {
                if (height[r] > maxR) {
                    maxR = height[r];
                }
                res += maxR - height[r];
                /**
                 * !!!
                 */
                r--;
            }
        }

        return res;
    }
}
