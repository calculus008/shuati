package lintcode;

import java.util.*;
import java.util.function.Supplier;

/**
 * Created by yuank on 10/17/18.
 */
public class LI_360_Sliding_Window_Median {
    /**
         Given an array of n integer, and a moving window(size k), move the window at each iteration
         from the start of the array, find the median of the element inside the window at each moving.
         (If there are even numbers in the array, return the N/2-th number after sorting the element in the window. )

         Example
         For array [1,2,7,8,5], moving window size k = 3. return [2,7,7]

         At first the window is at the start of the array like this

         [ | 1,2,7 | ,8,5] , return the median 2;

         then the window move one step forward.

         [1, | 2,7,8 | ,5], return the median 7;

         then the window move one step forward again.

         [1,2, | 7,8,5 | ], return the median 7;

         Challenge
         O(nlog(n)) time

         Hard
     */

    /**
     * Solution 1
     * Use 2 heaps algorithm from LE_295_Find_Median_From_Data_Stream to get median for each window.
     * The extra operation for this problem is how to remove left side element when sliding window
     * moves to the right.
     *
     * Time : O(n * k), since PriorityQueue.remove() takes O(n), in order to achieve O(nlogn),
     *        need to use Hash Heap
     * Space : O(n)
     */
    public List<Integer> medianSlidingWindow(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0 || k == 0) return res;

        PriorityQueue<Integer> large = new PriorityQueue<>();//min heap
        PriorityQueue<Integer> small = new PriorityQueue<>((a, b) -> b - a);//max heap

        int l = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i >= k) {//remove left element
                /**
                 * Since large heap is maintained in the way that its size is
                 * always bigger or equal to the size of small and it can't be
                 * empty when the first window is formed, we need to check
                 * "nums[l] < large.peek()" in if logic here. small heap could be
                 * empty at this stage, for example, when k = 1
                 */
                if (nums[l] < large.peek()) {
                    small.remove(nums[l]);
                } else {
                    large.remove(nums[l]);
                }
                l++;
            }

            //maintain large and small heaps
            large.offer(nums[i]);
            small.offer(large.poll());
            if (small.size() > large.size()) {
                large.offer(small.poll());
            }

            //get median
            if (i - k + 1 >= 0) {
                res.add(k % 2 == 0 ? small.peek() : large.peek());
            }
        }

        return res;
    }

    /**
     * 使用Java的TreeSet(Java的TreeSet和TreeMap是用红黑树来实现的，
     * 实际上TreeSet是在TreeMap的基础上实现的)来模拟Heap，
     * 这样可以在删除任意元素remove(Object obj)时获得O(logN)的性能。
     * 基本思想也是用一个最大堆和最小堆来平分窗口内的元素，从而动态维护一个中位数，
     *
     * (It seems that minSet is the one that has one extra element)
     *
     * 总的时间复杂度: O(NlogN)
     */
    public class Solution2 {
        public List<Integer> medianSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length < k){
                return new ArrayList<>();
            }

            List<Integer> ans = new ArrayList<>(nums.length - k + 1);
            Comparator<Integer> cmp = (a, b) ->
                    nums[a] == nums[b] ? Integer.compare(a, b) : Integer.compare(nums[a], nums[b]);

            TreeSet<Integer> minSet = new TreeSet<>(cmp);
            TreeSet<Integer> maxSet = new TreeSet<>(cmp);

            for (int i = 0; i < nums.length; i++){
                minSet.add(i);
                swap(minSet, maxSet);

                if (minSet.size() + maxSet.size() == k){
                    ans.add(nums[minSet.last()]);
                    Integer lo = i - k + 1;

                    if (minSet.contains(lo)){
                        minSet.remove(lo);
                    } else {
                        maxSet.remove(lo);
                    }

                    if (minSet.size() < maxSet.size() || minSet.size() - maxSet.size() > 1){
                        swap(minSet, maxSet);
                    }
                }
            }
            return ans;
        }

        void swap(TreeSet<Integer> minSet, TreeSet<Integer> maxSet){
            if (minSet.size() > maxSet.size()){
                Integer tmp = minSet.last();
                maxSet.add(tmp);
                minSet.remove(tmp);
            }

            if (minSet.size() < maxSet.size()){
                Integer tmp = maxSet.first();
                minSet.add(tmp);
                maxSet.remove(tmp);
            }
        }
    }

}
