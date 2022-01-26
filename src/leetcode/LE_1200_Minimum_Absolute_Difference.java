package leetcode;

import java.util.*;
import java.util.stream.*;

public class LE_1200_Minimum_Absolute_Difference {
    /**
     * Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements.
     *
     * Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows
     *
     * a, b are from arr
     * a < b
     * b - a equals to the minimum absolute difference of any two elements in arr
     *
     *
     * Example 1:
     * Input: arr = [4,2,1,3]
     * Output: [[1,2],[2,3],[3,4]]
     * Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.
     *
     * Example 2:
     * Input: arr = [1,3,6,10,15]
     * Output: [[1,3]]
     *
     * Example 3:
     * Input: arr = [3,8,-10,23,19,-4,-14,27]
     * Output: [[-14,-10],[19,23],[23,27]]
     *
     * Constraints:
     * 2 <= arr.length <= 105
     * -106 <= arr[i] <= 106
     *
     * Easy
     *
     * https://leetcode.com/problems/minimum-absolute-difference/
     */

    /**
     * Sorting + 2 Traversals
     * Time  : O(nlogn)
     * Space : O(1)
     */
    class Solution1 {
        public List<List<Integer>> minimumAbsDifference(int[] arr) {
            List<List<Integer>> res = new ArrayList<>();
            int n = arr.length;

            Arrays.sort(arr);
            int min_diff = Integer.MAX_VALUE;

            /**
             * We can do this because we know that the numbers in arr are DISTINCT
             */
            for (int i = 1; i < n; i++) {
                min_diff = Math.min(min_diff, arr[i] - arr[i - 1]);
            }

            for (int i = 1; i < n; i++) {
                if (arr[i] - arr[i - 1] == min_diff) {
                    res.add(Arrays.asList(arr[i - 1], arr[i]));

                    /**
                     * NOTE:
                     * This two steps code does not work:
                     *
                     *  int[] pair = {arr[i - 1], arr[i]};
                     *  res.add(Arrays.asList(pair));
                     *
                     * It returns error "Incompatible type". It seems to suggest that arr length has some problem.
                     * Instead, you can use stream function like:
                     *
                     *   res.add(Arrays.stream(pair).boxed().collect(Collectors.toList()));
                     */
                }
            }

            return res;
        }
    }

    /**
     * Sort + 1 Traversal
     *
     * Time  : O(nlogn)
     * Space : O(1)
     */
    class Solution2 {
        class Solution {
            public List<List<Integer>> minimumAbsDifference(int[] arr) {
                List<List<Integer>> res = new ArrayList<>();
                int n = arr.length;

                Arrays.sort(arr);
                int min_diff = Integer.MAX_VALUE;

                for (int i = 1; i < n; i++) {
                    int diff = arr[i] - arr[i - 1];

                    /**
                     * Improve from Solution1: Whenever we find a smaller diff, clear res, add current one and
                     * set current diff as min_diff
                     */
                    if (diff == min_diff) {
                        res.add(Arrays.asList(arr[i - 1], arr[i]));
                    } else if (diff < min_diff) {
                        min_diff = diff;
                        res.clear();
                        res.add(Arrays.asList(arr[i - 1], arr[i]));
                    }
                }

                return res;
            }
        }
    }


    /**
     * Counting Sort + 2 Traversal
     *
     * Time  : O(n)
     * Space : O(range)
     */
    class Solution3 {
        public List<List<Integer>> minimumAbsDifference(int[] arr) {
            List<List<Integer>> res = new ArrayList<>();
            int n = arr.length;

            int shift = 1000000;
            int m = 2 * shift + 1;
            boolean[] bucket = new boolean[m];

            for (int x : arr) {
                bucket[x + shift] = true;
            }

            int min_diff = Integer.MAX_VALUE;
            int last = -1;
            for (int i = 0; i < m; i++) {
                if (bucket[i]) {
                    if (last >= 0) {
                        int diff = i - last;
                        if (diff == min_diff) {
                            res.add(Arrays.asList(last - shift, i - shift));
                        } else if (diff < min_diff) {
                            res.clear();
                            res.add(Arrays.asList(last - shift, i - shift));
                            min_diff = diff;
                        }
                    }
                    last = i;
                }
            }

            return res;
        }
    }

    /**
     * Counting Sort
     *
     *
     */
    class Solution4{
        public List<List<Integer>> minimumAbsDifference(int[] arr) {
            /**
             * !!!
             * First loop to find the max and min value of arr, so we can know the exact range
             * of the numbers, therefore save time and space when doing counting sort.
             */
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i : arr) {
                if (i < min) min = i;
                if (i > max) max = i;
            }

            /**
             * !!!
             */
            int n = max - min + 1;
            var line = new boolean[n];
            for (int i : arr) {
                /**
                 * !!!
                 * index mapping, only associated with min value.
                 */
                line[i - min] = true;
            }

            int diff = Integer.MAX_VALUE;
            var res = new ArrayList<List<Integer>>();

            for (int i = 1, prev = 0; i < n; i++) {
                /**
                 * This seems to accelerate execution a lot
                 */
                while (!line[i]) i++;

                if (i - prev < diff) {
                    diff = i - prev;
                    res.clear();
                    /**
                     * !!!
                     * value mapping by "+ min"
                     */
                    res.add(Arrays.asList(prev + min, i + min));
                } else if (i - prev == diff) {
                    res.add(Arrays.asList(prev + min, i + min));
                }
                prev = i;
            }

            return res;
        }
    }
}
