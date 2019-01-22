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
     * Use Binary Index Tree
     *
     * Time : O(nlog)
     * Space : O(n)
     *
     * Similar problem : LE_315_Count_Of_Smaller_Numbers_After_Self
     *                   LE_307_Range_Sum_Query_Mutable
     *                   LI_532_Reverse_Pairs (跟这道题几乎完全一样，只是换了种问法)
     *
     * The tricky part is how to switch from a number array to use BIT. The keys :
     *
     * 1.First we need to get the rank of each element, when we go to the next step, the value of each element
     *   is no longer important, rank is what we are interested in. For example:
     *
     *  nums before transform to rank  :  [7, 1, 3, 2, 9, 2, 1]
     *  nums after transform to rank   :  [4, 1, 3, 2, 5, 2, 1]
     *
     *  The process of the transformation is "discretization", 2 approaches:
     *  a.Time O(nlogn), space O(n) (Solution 4)
     *    int[] B = A.clone();
     *    Arrays.sort(B);
     *    Arrays.BinarySearch(B, A[i]);
     *
     *  b.Time O(n), space O(n) (Solution 3)
     *    Use TreeSet and HashMap
     *
     * 2.Once we have rank, info, we will have an count array, it records number of occurrences for each rank.
     *   Notice :
     *   1.This count array is what BIT will operate on.
     *   2.To get answer for the problem, the update and query using BIT is like an "online" process - processing
     *     while moving backward. Using example above (use TreeSet and HashMap in discretization()):
     *
     *  i   0  1  2  3  4  5  6
     *     [4, 1, 3, 2, 5, 2, 1]
     *     <-------------------
     *          0  1  2  3  4  5
     *  count  [0, 0. 0, 0, 0, 0]
     *
     *  i = 6, nums[6] = 1, query(1 - 1) = 0, update(1) :
     *          0  1  2  3  4  5
     *  count  [0, 1. 0, 0, 0, 0]
     *
     *  i = 5, nums[5] = 2, query(2 - 1) = 1, update(2)
     *          0  1  2  3  4  5
     *  count  [0, 1, 1, 0, 0, 0]
     *
     *  i = 4, nums[4] = 5, query[4 - 1] = 2, update(5)
     *          0  1  2  3  4  5
     *  count  [0, 1, 1, 0, 0, 1]
     *
     *  i = 3, nums[3] = 2, query[2 - 1] = 1, update(2)
     *          0  1  2  3  4  5
     *  count  [0, 1, 2, 0, 0, 1]
     *
     *  i = 2, nums[2] = 3, query[3 - 1] = 3, update(3)
     *          0  1  2  3  4  5
     *  count  [0, 1, 2, 1, 0, 1]
     *
     *  i = 1, nums[1] = 1, query[1 - 1] = 0, update(1)
     *          0  1  2  3  4  5
     *  count  [0, 2, 2, 1, 0, 1]
     *
     *  i = 0, nums[0] = 4, query[4 - 1] = 5, update(4)
     *          0  1  2  3  4  5
     *  count  [0, 2, 2, 1, 1, 1]
     *
     *
     *  res = (5, 0, 3, 1, 2, 1, 0)
     */

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
         nums :  [7, 1, 3, 2, 9, 2, 1]
         rank:   [4, 1, 3, 2, 5, 2, 1]
                 <--------------------

         为什么不需要先把所有值放入tree然后再query?
         因为我们是要从后往前走，看比当前元素小的元素的个数。比如 ： [7, 1, 3, 2, 9, 2, 1]，走到9, 有2个元素比9小(1,2)。
         而如果先把所有值放入tree， 则有4个元素比9小(1， 2， 2， 1), 这是错误的。
         */
        BinaryIndexedTree tree = new BinaryIndexedTree(set.size());
        for (int i = nums.length - 1; i >= 0; i--) {
            int idx = map.get(nums[i]);//!!! get rank for nums[i], col is 1 based
            res.add(0, tree.query(idx - 1));//!!!"col - 1", query see prefix sum of the rank that 1 smaller than col
            tree.update(idx, 1);//!!!update with col (1 based) and delta as "1" -> meaning one more occurrence of the element with rank i
        }

        return res;
    }

    /**
     * Solution 3
     *
     * Use Binary Indexed Tree, just not using BinaryIndexedTree class, embed logic in code.
     */

    int[] bit;

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        //Get the rank of each unique element in nums.
        SortedSet<Integer> set = new TreeSet<>();
        for (int num : nums) {
            set.add(num);
        }

        Map<Integer, Integer> map = new HashMap<>();
        int rank = 1;
        for (int num : set) {
            map.put(num, rank++);
        }

        bit = new int[set.size() + 1];

        for (int i = nums.length - 1; i >= 0; i--) {
            int idx = map.get(nums[i]);
            res.add(0, query(idx - 1));
            update(idx, 1);
        }

        return res;
    }

    private void update(int idx, int delta) {
        // for (int i = col + 1; i < bit.length; i = i + lowbit(i)) {
        //     bit[i] += delta;
        // }
        while (idx < bit.length) {
            bit[idx] += delta;
            idx += lowbit(idx);
        }
    }

    private int query(int idx) {
        int sum = 0;
        // for (int i = col + 1; i > 0; i = i - lowbit(i)) {
        //     sum += bit[i];
        // }

        while (idx > 0) {
            sum += bit[idx];
            idx -= lowbit(idx);
        }
        return sum;
    }

    private int lowbit(int x) {
        return x & (-x);
    }

    /**
     *
     * Solution 4
     *
     * JiuZhang
     *
     * 真正的 nlogn 的算法
     * 首先使用离散化将原来的数组变为对应的 order 数组。这样就不会有负数，也不会有特别大的数。
     * 如：[1, 1000, -100, 10, 100]，将每个数替换为在整个数组中对应的排序。如，1是从小到大第2个，那么就替换为 2。
     * 替换之后得到数组 [2, 5, 1, 3, 4]。
     *
     * 接着在用 Binary Indexed Tree 来统计每个数右边有多少个数比他小，只需要从右到左遍历这个数组，一边把数丢到 BIT 里一边计算就行了。
     */

    public List<Integer> countSmaller4(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<Integer>();
        }

        discretization(nums);

        // build bit array
        int[] bit = new int[nums.length + 1];
        int[] count = new int[nums.length];

        List<Integer> result = new ArrayList<Integer>();


        for (int i = nums.length - 1; i >= 0; i--) {
            /**
             * For example, current nums[i] is 3, meaning current value ranks 3rd,
             * so we need to find out the sum for ranks at 2.
             */
//            count[i] = getSum(bit, nums[i] - 1);

            result.add(0, getSum(bit, nums[i] - 1) );

            /**
             * 经过离散化后，已经不用考虑下标加一的问题了，这里"nums[i]"已经是rank (1 based).
             */
            update(bit, nums[i]);
        }

//        List<Integer> result = new ArrayList<Integer>();
//        for (int i = 0; i < count.length; i++) {
//            result.add(count[i]);
//        }

        return result;
    }

    // this is nlogn
    // sort the orignal array and mapping the number to
    // the order in the sorted array;
    private void discretization(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        for (int i = 0; i < nums.length; i++) {
            nums[i] = Arrays.binarySearch(sorted, nums[i]) + 1;
        }
    }

    private void update(int[] bit, int index) {
        for (int i = index; i < bit.length; i = i + lowbit(i)) {
            bit[i]++;
        }
    }

    private int getSum(int[] bit, int index) {
        int sum = 0;
        for (int i = index; i > 0; i = i - lowbit(i)) {
            sum += bit[i];
        }
        return sum;
    }

}
