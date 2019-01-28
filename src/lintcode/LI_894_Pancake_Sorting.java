package lintcode;

import common.FlipTool;

/**
 * Created by yuank on 7/12/18.
 */
public class LI_894_Pancake_Sorting {
    /**
         Given an an unsorted array, sort the given array. You are allowed to do only following operation on array.

         flip(arr, i): Reverse array from 0 to i
         Unlike a traditional sorting algorithm, which attempts to sort with the fewest comparisons possible,
         the goal is to sort the sequence in as few reversals as possible.

         Example
         Given array = [6, 7, 10, 11, 12, 20, 23]
         Use flip(arr, i) function to sort the array.
     */

    /**
     * https://www.geeksforgeeks.org/pancake-sorting/
     * Time : O(n ^ 2)
     * **/
    public void pancakeSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        /**
         * iterate from right to left, keep flipping to make sure the current largest number is at array[0],
         * then, flip it to the last
         **/
        int i = array.length; // track the first index of "largest" element
        while (i > 0) {
            for (int j = 0; j < i; j++) {
                if (array[0] < array[j]) {
                    // find a larger element, flip
                    FlipTool.flip(array, j); // larger element will be put in array[0]
                }
            }
            // flip the larger element to left of the current first index of large element
            FlipTool.flip(array, i - 1);
            i--;
        }
    }
}
