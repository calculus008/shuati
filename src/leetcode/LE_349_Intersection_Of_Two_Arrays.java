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
     */

    /**
     * Solution 1  : Two sets
     *
     * Time and Space : O(n)
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

}
