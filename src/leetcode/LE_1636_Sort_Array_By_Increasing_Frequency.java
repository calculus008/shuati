package leetcode;

import java.util.*;

public class LE_1636_Sort_Array_By_Increasing_Frequency {
    /**
     * Given an array of integers nums, sort the array in increasing order based on the frequency of the values.
     * If multiple values have the same frequency, sort them in decreasing order.
     *
     * Return the sorted array.
     *
     * Example 1:
     * Input: nums = [1,1,2,2,2,3]
     * Output: [3,1,1,2,2,2]
     * Explanation: '3' has a frequency of 1, '1' has a frequency of 2, and '2' has a frequency of 3.
     *
     * Example 2:
     * Input: nums = [2,3,1,3,2]
     * Output: [1,3,3,2,2]
     * Explanation: '2' and '3' both have a frequency of 2, so they are sorted in decreasing order.
     *
     * Example 3:
     * Input: nums = [-1,1,-6,4,5,-6,1,4,1]
     * Output: [5,-1,4,4,-6,-6,1,1,1]
     *
     * Constraints:
     * 1 <= nums.length <= 100
     * -100 <= nums[i] <= 100
     *
     * Easy
     *
     * https://leetcode.com/problems/sort-array-by-increasing-frequency/
     */

    /**
     * Count and Sort
     */
    class Solution {
        public int[] frequencySort(int[] nums) {
            Map<Integer, Integer> count = new HashMap<>();
            for (int num : nums) {
                count.put(num, count.getOrDefault(num, 0) + 1);
            }

            /**
             * !!!
             * Collections.sort() can't work on Set<Map.Entry<k, v>> created from map.entrySet()
             * So have to create a list and sort using key and value.
             */
            List<Integer> list = new ArrayList<>(count.keySet());
            Collections.sort(list, (a, b) -> {
                return (count.get(a) == count.get(b))? Integer.compare(b, a) : Integer.compare(count.get(a), count.get(b));
            });

            int[] res = new int[nums.length];
            int i = 0;

            /**
             * !!!
             */
            for (int num : list) {
                for (int j = 0; j < count.get(num); j++) {
                    res[i++] = num;
                }
            }
            return res;
        }
    }

    class Solution1 {
        public int[] frequencySort(int[] nums) {
            HashMap<Integer, Integer> count = new HashMap<>();

            for (int n : nums) {
                count.put(n, count.getOrDefault(n, 0) + 1);
            }

            List<int[]> list = new ArrayList<>();
            for (int key : count.keySet()) {
                list.add(new int[] {key, count.get(key)});
            }

            Collections.sort(list, (a, b) -> {
                if (a[1] != b[1]) {
                    return Integer.compare(a[1], b[1]);
                } else {
                    return Integer.compare(b[0], a[0]);
                }
            });

            int[] res = new int[nums.length];
            int i = 0;
            for (int[] a : list) {
                for (int j = 0; j < a[1]; j++) {
                    res[i++] = a[0];
                }
            }

            return res;
        }
    }

    /**
     * https://leetcode.com/problems/sort-array-by-increasing-frequency/discuss/917993/Java-Simple-Custom-Sort-with-Detailed-Explanation!
     *
     * use lambda expression on HashMap
     *
     * .stream(nums)
     * iterates through the nums array
     *
     * .boxed()
     * converts each int to Integer object, this is because .sorted() can only operate on objects
     *
     * .sorted((a,b) -> map.get(a) != map.get(b) ? map.get(a) - map.get(b) : b - a)
     * if frequency of two numbers are not the same, sort by ascending frequency. If frequencies are the same, sort by decending numeric value
     *
     * .mapToInt(n -> n)
     * converts Integer to int
     *
     * .toArray()
     * returns array
     */
    class Solution2 {
        public int[] frequencySort(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            // count frequency of each number
            Arrays.stream(nums).forEach(n -> map.put(n, map.getOrDefault(n, 0) + 1));

            // custom sort
            return Arrays.stream(nums).boxed()
                    .sorted((a,b) -> map.get(a) != map.get(b) ? map.get(a) - map.get(b) : b - a)
                    .mapToInt(n -> n)
                    .toArray();
        }
    }
}
