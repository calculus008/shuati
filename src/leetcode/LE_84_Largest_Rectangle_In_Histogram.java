package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_84_Largest_Rectangle_In_Histogram {
    /**
        Given n non-negative integers representing the histogram's bar height where the width of each bar is 1,
        find the area of largest rectangle in the histogram.

        For example,
        Given heights = [2,1,5,6,2,3],
        return 10
     */

    //Important

    //Use every bar as the min bar to calculate area, track max
    //stack, 升序，小于->计算. 注意边界处理 (-1 在前，0 在最后)
    //peek to get "start".
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) return 0;

        //存数组的下标，不是数值。
        Stack<Integer> stack = new Stack<>();
        int res = 0;

        //注意，循环用 "<= heights.length". 用于只有升序数列的case {1,2,3,4,5}. 因为一直升序，需要人为在最后加一个“0”，{1,2,3,4,5，0}
        //在能满足开始计算的判断条件。
        for (int i = 0; i <= heights.length; i++) {
            //人为在最后加一个“0”
            int h = i == heights.length ? 0 : heights[i];
            //!!!While
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int start = stack.isEmpty() ? -1 : stack.peek();
                /*
                  Why (i - start - 1):
                    int rightBoundary = i - 1;
                    int leftBoundary = stack.isEmpty() ? 0 : stack.peek() + 1;
                    int width = rightBoundary - leftBoundary + 1;
                    Therefore i - 1 - (stack.peek() + 1) + 1 = i - stack.peek() - 1 - 1 + 1 = i - stack.peek() - 1 = i - start - 1

                    This is the length of the rectangle with width as "height".
                */
                int area = height * (i - start - 1);
                res = Math.max(res, area);
            }
            //Always put i
            stack.push(i);
        }
        return res;
    }

    public int largestRectangleArea2(int[] height) {
        if (height == null || height.length == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        int n = height.length;
        int max = 0;

        for (int i = 0; i <= n; i++) {
            int h = i == n ? 0 : height[i];
            while (!stack.isEmpty() && h < height[stack.peek()]) {//!!! "height[stack.peek()]", NOT "stack.peek()"
                int x = height[stack.pop()];
                int right = i - 1;
                int left = stack.isEmpty() ? 0 : stack.peek() + 1;
                int area = x * (right - left + 1);
                max = Math.max(max, area);
            }

            stack.push(i);//!!!
        }

        return max;
    }
}
