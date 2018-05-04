package leetcode;

import java.util.*;

/**
 * Created by yuank on 5/2/18.
 */
public class LE_315_Count_Of_Smaller_Numbers_After_Self {
    /**
         You are given an integer array nums and you have to return a new counts array.
         The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].

         Example:

         Given nums = [5, 2, 6, 1]

         To the right of 5 there are 2 smaller elements (2 and 1).
         To the right of 2 there is only 1 smaller element (1).
         To the right of 6 there is 1 smaller element (1).
         To the right of 1 there is 0 smaller element.
         Return the array [2, 1, 1, 0].

         Medium
     */

    /**
         Solution 1 : Reverse Insertion

         Time  : O(n ^ 3)
         Space : O(n)
     */
    public List<Integer> countSmaller1(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        int n = nums.length;
        //To use Arrays.asList(), must declare the array as Integer[], not int[]
        Integer[] arr = new Integer[n];
        List<Integer> list = new ArrayList<>();

        /**
             start from back to front, try to insert each num into an ordered list (small to large).
             So each insertion index actually tells how many elements are smaller than the current one.
         */
        for (int i = n - 1; i >= 0; i--) {
            int idx = findIndex(list, nums[i]);
            list.add(idx, nums[i]);//"add" is O(n)
            //since i starts from end of the array, therefore, the sequcne we put into arr is also starting from back to front
            arr[i] = idx;
        }

        return Arrays.asList(arr);
    }

    //find the index that is ">=" given number "num"
    public int findIndex(List<Integer> list, int num) {
        if(list.isEmpty()) return 0;

        int start = 0;
        int end = list.size() - 1;

        if (num < list.get(start)) return 0;
        if (num > list.get(end)) return end + 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (list.get(mid) < num) {//!!!
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        if (list.get(start) >= num) return start;
        return end;
    }


    /**
         Solution 2 : Binary Indexed Tree
         Time  : O(nlogn)
         Space : O(k) (k is number of unique numbers in nums[])
     */
    public List<Integer> countSmaller2(int[] nums) {
        List<Integer> res = new LinkedList<>();
        if (nums == null || nums.length == 0) return res;

        //Given nums as [7, 1, 3, 2, 9, 2, 1], set will be [1, 2, 3, 7, 9]
        SortedSet<Integer> set = new TreeSet();
        for (int num : nums) {
            set.add(num);
        }

        /**
         mapping between number and its rank
         For example above, map will be :

         key   value
         1     1
         2     2
         3     3
         7     4
         9     5
         */
        Map<Integer, Integer> map = new HashMap<>();
        int rank = 1;
        // for (int i = nums.length - 1; i >= 0; i--) {
        //!!! 不是循环nums[], 而是set里的数。
        for (int num : set) {
            map.put(num, rank++);
        }

        /**
         为什么不需要先把所有值放入tree然后再query?
         因为我们是要从后往前走，看比当前元素小的元素的个数。比如 ： [7, 1, 3, 2, 9, 2, 1]，走到9, 有2个元素比9小(1,2)。
         而如果先把所有值放入tree， 则有4个元素比9小(1， 2， 2， 1), 这是错误的。
         */
        BinaryIndexedTree tree = new BinaryIndexedTree(set.size());
        for (int i = nums.length - 1; i >= 0; i--) {
            int idx = map.get(nums[i]);//!!!idx is 1 based
            res.add(0, tree.query(idx - 1));//!!!"idx - 1", query see prefix sum of the rank that 1 smaller than idx
            tree.update(idx, 1);//!!!update with idx (1 based) and delta as "1" -> meaning one more occurrence of the element with rank i
        }

        return res;
    }
}
