package Interviews.C3.AI;

import java.util.Stack;

public class RunningTemperature {
    /**
     * Given a timeseries that keeps information about temperature readings for a city,
     * return a timeseries that tells you, for a given day, how long has its value been the largest running value.
     * For example, for temperature readings [3,5,6,2,1,4,6,9], the transformed timeseries would be [1,2,3,1,1,3,7,8]
     *
     * Similar LE_239_Sliding_Window_Maximum
     *         LE_739_Daily_Temperatures
     *
     * Here it is not a window, so logic is easier.
     *
     * Key point :
     *
     * 1.For "previous bigger element" - for a given element, find the element that is bigger than it in elements come before.
     * 2.Calculate distance.
     *
     * Algorithm:
     * 1.Use mono-decreasing stack
     * 2.Stack saves index, not element itself, because if we save elements (values), after we pop it, we lose it
     *   and the result won't be correct.
     * 3.Stack里存的是上一个比当前元素大的元素的坐标，所以当前答案是 i - stack.peek()
     * 4.处理stack 为空的情况。如果为空，也就是说，前面没有元素比当前的大，所以答案是i + 1, 因为i是下标值。
     *
     */

    class MyCode {
        public int[] getRunningValue(int[] temperature) {
            if (null == temperature || temperature.length == 0) return new int[]{};

            int n = temperature.length;
            int[] res = new int[n];

            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < n; i++) {
                while (!stack.isEmpty() && temperature[i] >= temperature[stack.peek()]) {
                    stack.pop();
                }

                if (stack.isEmpty()) {
                    res[i] = i + 1;
                } else {
                    res[i] = i - stack.peek();
                }

                stack.push(i);
            }

            return res;
        }
    }
}
