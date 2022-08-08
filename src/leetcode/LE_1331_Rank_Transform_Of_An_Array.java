package leetcode;

import java.util.*;

public class LE_1331_Rank_Transform_Of_An_Array {
    /**
     * Given an array of integers arr, replace each element with its rank.
     *
     * The rank represents how large the element is. The rank has the following rules:
     *
     * Rank is an integer starting from 1.
     * The larger the element, the larger the rank. If two elements are equal, their rank must be the same.
     * Rank should be as small as possible.
     *
     * Example 1:
     * Input: arr = [40,10,20,30]
     * Output: [4,1,2,3]
     * Explanation: 40 is the largest element. 10 is the smallest. 20 is the second smallest. 30 is the third smallest.
     *
     * Example 2:
     * Input: arr = [100,100,100]
     * Output: [1,1,1]
     * Explanation: Same elements share the same rank.
     *
     * Example 3:
     * Input: arr = [37,12,28,9,100,56,80,5,12]
     * Output: [5,3,4,2,8,6,7,1,3]
     *
     * Constraints:
     * 0 <= arr.length <= 105
     * -109 <= arr[i] <= 109
     *
     * Easy
     *
     * https://leetcode.com/problems/rank-transform-of-an-array/
     */

    /**
     * TreeSet + HashMap
     * 1.用TreehSet做排序
     * 2.用HashMap做一个lookup table => element -> rank
     * 3.Populate the result array.
     *
     * Time : O(nlogn)
     * Space : O(n)
     */
    class Solution1 {
        public int[] arrayRankTransform(int[] arr) {
            TreeSet<Integer> set = new TreeSet<>();
            for (int num : arr) {
                set.add(num);
            }

            int rank = 1;
            Map<Integer, Integer> map = new HashMap<>();
            for (int num : set) {
                map.put(num, rank);
                rank++;
            }

            int[] res = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                res[i] = map.get(arr[i]);
            }

            return res;
        }
    }

    /**
     * TreeMap
     * element -> list of index
     *
     * Use TreeMap, key will be in sorted order, the value is the list of indices, the element at those indices has the
     * value of the key.
     **/
    class Solution2 {
        public int[] arrayRankTransform(int[] arr) {
            TreeMap<Integer, List<Integer>> map = new TreeMap<>();

            int len = arr.length;
            for (int i = 0; i < len; i++) {
                map.putIfAbsent(arr[i], new ArrayList<>());
                map.get(arr[i]).add(i);
            }

            int rank = 1;
            int[] res = new int[len];
            for (int key : map.keySet()) {
                for (int idx : map.get(key)) {
                    res[idx] = rank;
                }
                rank++;
            }

            return res;
        }
    }

    /**
     * Use Arrays.copyOf() and Arrays.sort()
     */
    class Solution3 {
        public int[] arrayRankTransform(int[] arr) {
            int[] copy = Arrays.copyOf(arr, arr.length);
            Arrays.sort(copy);

            Map<Integer, Integer> map = new HashMap<>();
            for (int num : copy) {
                /**
                 * !!!
                 * Only add to map if element is absent to deal with duplicate elements
                 */
                map.putIfAbsent(num, map.size() + 1);
            }

            for (int i = 0; i < arr.length; i++) {
                arr[i] = map.get(arr[i]);
            }

            return arr;
        }
    }
}
