package leetcode;

public class LE_11_Container_With_Most_Water {
    /**
     * Given n non-negative integers a1, a2, ..., an , where each represents
     * a point at coordinate (i, ai). n vertical lines are drawn such that
     * the two endpoints of line i is at (i, ai) and (i, 0). Find two lines,
     * which together with x-axis forms a container, such that the container
     * contains the most water.
     *
     * Note: You may not slant the container and n is at least 2.
     */

    /**
     * Two Pointers
     *
     * Compare with LE_42_Trapping_Rain_Water
     */
    public class Solution {
        public int maxArea(int[] height) {
            int maxarea = 0;
            int l = 0;
            int r = height.length - 1;

            while (l < r) {
                /**
                 * "(height[l], height[r])", not "(l, r)"
                 */
                maxarea = Math.max(maxarea, Math.min(height[l], height[r]) * (r - l));
                /**
                 * "height[l] < height[r]", not "l < r"
                 */
                if (height[l] < height[r]) {
                    l++;
                } else {
                    r--;
                }
            }
            return maxarea;
        }
    }
}
