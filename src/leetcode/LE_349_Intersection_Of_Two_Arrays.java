package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 5/17/18.
 */
public class LE_349_Intersection_Of_Two_Arrays {
    /**
         Given two arrays, write a function to compute their intersection.

         Example:
         Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].

         Note:
         Each element in the result must be unique.
         The result can be in any order.

         Easy

         Follow up:
         Then they ask you to solve it under these constraints:
         O(n) time and O(1) space (the resulting array of intersections is not taken into consideration).
         You are told the lists are sorted.

         See Soluion2
     */

    /**
     * Solution 1  : Two sets
     *
     * Time and Space : O(n + m)
     */
    public int[] intersection1(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> res = new HashSet<>();

        for (int num : nums1) {
            set.add(num);
        }

        for (int num : nums2) {
            /**
             * https://stackoverflow.com/questions/559839/big-o-summary-for-java-collections-framework-implementations
             *
             * set.contains() : O(1)
             */
            if (set.contains(num)) {
                res.add(num);
            }
        }

        int[] ret = new int[res.size()];
        int i = 0;
        for (int elem : res) {
            ret[i] = elem;
            i++;
        }

        return ret;
    }

    /**
         Solution 2 : sort and two pointers

         Time  : O(nlogn)
         Space : O(n)

         If given arrays are already sorted, this solution will take O(n) time.
     **/
    public int[] intersection2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        Set<Integer> intersec = new HashSet<>();

        int i = 0, j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                /**
                 * Here, it requires "Each element in the result must be unique",
                 * therefore we need to use Set to dedup.
                 *
                 * In, LE_350_Intersection_Of_Two_Arrays_II, it requires
                 * " Each element in the result should appear as many times as it shows in both arrays",
                 * we do add to a list.
                 */
                intersec.add(nums1[i]);
                i++;
                j++;
            }
        }

        int[] ret = new int[intersec.size()];
        int k = 0;
        for (int elem : intersec) {
            ret[k] = elem;
            k++;
        }

        return ret;
    }

    /**
     * Solution 3
     * Sort and Binary Search
     * Time  : O(nlogn)
     * Space : O(n)
     *
     */
    public int[] intersection3(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return null;
        }

        HashSet<Integer> set = new HashSet<>();

        Arrays.sort(nums1);
        for (int i = 0; i < nums2.length; i++) {
            if (set.contains(nums2[i])) {
                continue;
            }
            if (binarySearch(nums1, nums2[i])) {
                set.add(nums2[i]);
            }
        }

        int[] result = new int[set.size()];
        int index = 0;
        for (Integer num : set) {
            result[index++] = num;
        }

        return result;
    }

    private boolean binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (nums[start] == target) {
            return true;
        }
        if (nums[end] == target) {
            return true;
        }

        return false;
    }

}
