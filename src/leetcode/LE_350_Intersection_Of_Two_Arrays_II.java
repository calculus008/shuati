package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuank on 5/18/18.
 */
public class LE_350_Intersection_Of_Two_Arrays_II {
    /**
         Given two arrays, write a function to compute their intersection.

         Example:
         Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].

         Note:
         Each element in the result should appear as many times as it shows in both arrays.
         The result can be in any order.

         Follow up:
         What if the given array is already sorted? How would you optimize your algorithm?
         What if nums1's size is small compared to nums2's size? Which algorithm is better?
         What if elements of nums2 are stored on disk, and the memory is limited such that
         you cannot load all elements into the memory at once?

         Easy
     */

    /**
         Solution 1 : HashMap
         Time : O(m + n);
         Space : O(min(m, n))
     **/
    public int[] intersect1(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num : nums2) {
            if (map.containsKey(num) && map.get(num) > 0) {//!!! just check bigger than 0 and keep subtracting, avoid using "remove()"
                map.put(num, map.get(num) - 1);
                list.add(num);
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }

        return res;
    }


    /**
         Solution 2 : Sorting and two pointers
         Time  : O(nlogn + mlogm)
         Space : O(min(m, n))

         if given arrays are already sorted, it takes O(m + n) time.
         if return type is list and not considering space in return data structure, it is O(1) in space.)oˆ;l, m
     */
    public int[] intersect2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[]{};
        }

        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                /**
                 * in LE_349是往set里加（因为不要重复的数字），此处是往list里加。
                 */
                res.add(nums1[i]);
                i++;
                j++;
            }
        }

        int[] ans = new int[res.size()];
        int k = 0;
        for (int num : res) {
            ans[k++] = num;
        }

        return ans;
    }

    /**
     !!!Follow up:
     1.What if the given array is already sorted? How would you optimize your algorithm?
       Use solution 2 (without sorting), Time : O(Max(m + n)).

     2.What if nums1's size is small compared to nums2's size? Which algorithm is better?
     This one is a bit tricky. Let's say nums1 is K size. Then we should do binary search for every element in nums1.
     Each lookup is O(log N), and if we do K times, we have O(K log N).
     If K this is small enough, O(K log N) < O(max(N, M)). Otherwise, we have to use the previous two pointers method.
     let's say A = [1, 2, 2, 2, 2, 2, 2, 2, 1], B = [2, 2]. For each element in B, we start a binary search in A. To deal with duplicate entry, once you find an entry, all the duplicate element is around that that index, so you can do linear search scan afterward.

     Time complexity, O(K(logN) + N). Plus N is worst case scenario which you have to linear scan every element in A. But on average, that shouldn't be the case. so I'd say the Time complexity is O(K(logN) + c), c (constant) is number of linear scan you did.

     3.What if elements of nums2 are stored on disk, and the memory is limited such that you
       cannot load all elements into the memory at once?
         If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap,
         read chunks of array that fit into the memory, and record the intersections.

         If both nums1 and nums2 are so huge that neither fit into the memory, sort them
         individually (external sort), then read 2 elements from each array at a time in
         memory, record intersections.

         Or MapReduce
     */
}
