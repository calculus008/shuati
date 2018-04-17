package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 2/8/18.
 */
public class Histogram {
    //lc84
    //Largest Rectangle

    public static int largestRectangleArea(int[] heights) {
        Stack<Integer> positions = new Stack<>();
        int maxArea = 0;

        for(int i = 0; i <= heights.length; i++) {
            int h = i == heights.length?0:heights[i];
            System.out.println("i="+i+", h="+h);
            while(!positions.isEmpty() && h <= heights[positions.peek()]) {

                int start = positions.pop();

                System.out.println("pop " + start + " out of stack");
                int temp = positions.isEmpty()?0:positions.peek()+1;
                int wd = i - (positions.isEmpty()?0:(positions.peek()+1));
                System.out.println("width = "+i+"-"+ temp + "=" + wd + ", height="+heights[start] + ", current max=" + maxArea);

                maxArea = Math.max(maxArea, heights[start] * (i - (positions.isEmpty()?0:positions.peek()+1)));
                System.out.println("maxArea=" + maxArea);
            }
            System.out.println("push i="+i+" into stack");
            positions.push(i);
            System.out.println("---------");
        }

        return maxArea;
    }

    public static void main(String [] args) {
//        int[] arr = {6,7,5,2,4,5,9,3};
        int[] arr = {2,1,5,6,2,3};
        largestRectangleArea(arr);
    }

}
