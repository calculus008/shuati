package leetcode;

public class LE_702_Search_In_A_Sorted_Array_Of_Unknown_Size {
    /**
     * Given an integer array sorted in ascending order, write a function to search target in nums.
     * If target exists, then return its index, otherwise return -1. However, the array size is unknown
     * to you. You may only access the array using an ArrayReader interface, where ArrayReader.get(k)
     * returns the element of the array at index k (0-indexed).
     *
     * You may assume all integers in the array are less than 10000, and if you access the array out of
     * bounds, ArrayReader.get will return 2147483647.
     *
     * Example 1:
     * Input: array = [-1,0,3,5,9,12], target = 9
     * Output: 4
     * Explanation: 9 exists in nums and its index is 4
     *
     * Example 2:
     * Input: array = [-1,0,3,5,9,12], target = 2
     * Output: -1
     * Explanation: 2 does not exist in nums so return -1
     *
     * Note:
     *
     * You may assume that all elements in the array are unique.
     * The value of each element in the array will be in the range [-9999, 9999].
     *
     * Medium
     *
     * Same as LI_447_Search_In_A_Big_Sorted_Array
     */

    class Solution {
        //dummy class for compilation
        public class ArrayReader {
            public int get(int k) {
                return 1;
            }
        }

        public int search(ArrayReader reader, int target) {
            int idx = 1;
            while (reader.get(idx) < target) {
                idx *= 2;
            }

            int l = 0;
            int h = idx + 1;//!!!

            while (l < h) {
                int mid = l + (h - l) / 2;

                if (reader.get(mid) < target) {
                    l = mid + 1;
                } else if (reader.get(mid) > target) {
                    h = mid;
                } else {
                    return mid;
                }
            }

            return -1;
        }
    }
}
