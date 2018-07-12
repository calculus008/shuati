package lintcode;

/**
 * Created by yuank on 7/12/18.
 */
public class LI_143_Sort_Colors_II {
    /**
         Given an array of n objects with k different colors (numbered from 1 to k),
         sort them so that objects of the same color are adjacent, with the colors in the order 1, 2, ... k.
         (Rainbow sort)

         Example
         Given colors=[3, 2, 2, 1, 4], k=4, your code should sort colors in-place to [1, 2, 2, 3, 4].

         Challenge
         A rather straight forward solution is a two-pass algorithm using counting sort.
         That will cost O(k) extra memory. Can you do it without using extra memory?
     */

    /**
     * Time : O(nlogk)!!!, Space : O(1)
     * 这个解法是QuickSort的一个变种。因为知道数组重的数是1 ～ k,所以利用这个特性可以有一些优化。
     */
    public void sortColors2(int[] colors, int k) {
        if (colors == null || colors.length == 0) {
            return;
        }

        helper(colors, 0, colors.length - 1, 1, k);
    }

    public void helper(int[] colors, int start, int end, int colStart, int colEnd) {
        if (start == end) {
            return;
        }

        //优化1， 如果开始和结尾的颜色一样，就可以停止递归。
        if (colStart == colEnd) {
            return;
        }

        int left = start;
        int right = end;
        // int pivotColor = colors[(left + right) / 2];

        //优化2 ： 直接用开始和结尾颜色值计算pivot.
        int pivotColor = (colStart + colEnd) / 2;//!!!

        while (left <= right) {
            /**
             * "colors[left] <= pivotColor" : QuickSort中用“<", 是为了尽可能百相同的值分到均匀分到
             * pivot的两边以提高性能。而此处，我们需要爆相同的值放到同一边，所以用“<="
             */
            while (left <= right && colors[left] <= pivotColor) {//!!!
                left++;
            }

            while (left <= right && colors[right] > pivotColor) {
                right--;
            }

            if (left <= right) {
                int temp = colors[left];
                colors[left] = colors[right];
                colors[right] = temp;

                left++;
                right--;
            }

            helper(colors, start, right, colStart, pivotColor);

            /**
             * 由于已经爆pivotColor放到了左边， 所以此处在往右边递归时，colStart设为"pivotColor + 1"
             */
            helper(colors, left, end, pivotColor + 1, colEnd);
        }
    }
}
