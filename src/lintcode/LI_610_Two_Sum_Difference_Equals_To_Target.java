package lintcode;

import jdk.internal.util.xml.impl.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 7/11/18.
 */
public class LI_610_Two_Sum_Difference_Equals_To_Target {
    /**
         Given an array of integers, find two numbers that their difference equals to a target value.
         where index1 must be less than index2. Please note that your returned answers
         (both index1 and index2) are NOT zero-based.

         Medium
     */

    //HashMap solution, Time and Space : O(n)
    public int[] twoSum7(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            //case 1 : current num - one appeared previously = target
            int k = nums[i] - target;
            if (map.containsKey(k)) {
                int[] res1 = new int[2];
                res1[0] = map.get(k) + 1;
                res1[1] = i + 1;
                return res1;
            }
            //case 2 : one appeared previously - num current = target
            int j = nums[i] + target;
            if (map.containsKey(j)) {
                int[] res2 = new int[2];
                res2[0] = map.get(j) + 1;
                res2[1] = i + 1;
                return res2;
            }

            map.put(nums[i], i);
        }

        return new int[]{};
    }

    //Same HashMap solution, concise version
    public int[] twoSum7_1(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[]{};
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            if (map.containsKey(nums[i] + target)) {
                return new int[]{map.get(nums[i] + target) + 1, i + 1};
            } else if (map.containsKey(nums[i] - target)) {
                return new int[]{map.get(nums[i] - target) + 1, i + 1};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[]{};
    }

    /**
     * HashMap Solution
     *
     * [2,7,15,24], target = 5
     *
     * map:
     * 2  0
     * 7  0
     * -5 0
     *
     * [7,2,15,24], target = 5
     *
     * map:
     * 7 0
     * 2 0
     * 9 0
     *
     */

    public int[] twoSum7_2(int[] nums, int target) {
        int[] res = new int[2];
        if (nums == null || nums.length < 2) {
            return res;
        }
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                res[0] = map.get(nums[i]) + 1;
                res[1] = i + 1;
                break;
            }

            map.put(nums[i] + target, i);
            map.put(nums[i] - target, i);
        }

        return res;
    }

    /**
     *   Two Pointers solution : Time : O(nlogn), Space : O(n)

         作为两数之和的一个 Follow up 问题，在两数之和被问烂了以后，两数之差是经常出现的一个面试问题。
         我们可以先尝试一下两数之和的方法，发现并不奏效，因为即便在数组已经排好序的前提下，nums[i] - nums[j] 与 target 之间的关系并不能决定我们淘汰掉 nums[i] 或者 nums[j]。

         那么我们尝试一下将两根指针同向前进而不是相向而行，在 i 指针指向 nums[i] 的时候，j 指针指向第一个使得 nums[j] - nums[i] >= |target| 的下标 j：

         如果 nums[j] - nums[i] == |target|，那么就找到答案
         否则的话，我们就尝试挪动 i，让 i 向右挪动一位 => i++
         此时我们也同时将 j 向右挪动，直到 nums[j] - nums[i] >= |target|
         可以知道，由于 j 的挪动不会从头开始，而是一直递增的往下挪动，那么这个时候，i 和 j 之间的两个循环的就不是累乘关系而是叠加关系。
     */

    /**
     * Save the value and its original index info
     */
    class Pair {
        public int idx, num;
        public Pair(int i, int n) {
            this.idx = i;
            this.num = n;
        }
    }

    public int[] twoSum7_nlogn(int[] nums, int target) {
        int[] indexs = new int[2];
        if (nums == null || nums.length < 2)
            return indexs;

        /**
         * !!! 实际上是取target的绝对值
         */
        if (target < 0)
            target = -target;

        /**
         * Save mapping between value to its original index, so we can do sort and still be able to track the original index
         */
        int n = nums.length;
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; ++i)
            pairs[i] = new Pair(i, nums[i]);

        Arrays.sort(pairs, new Comparator<Pair>() {
            public int compare(Pair p1, Pair p2) {
                return p1.num - p2.num;
            }
        });

        int j = 0;
        for (int i = 0; i < n; ++i) {
            //!!!
            if (i == j) {
                j++;
            }
            /**
             * 两个指针同相而行，先移动j, 指向第一个使得 nums[j] - nums[i] >= |target| 的下标 j
             */
            while (j < n && pairs[j].num - pairs[i].num < target) {
                j++;
            }

            if (j < n && pairs[j].num - pairs[i].num == target) {
                indexs[0] = pairs[i].idx + 1;
                indexs[1] = pairs[j].idx + 1;
                if (indexs[0] > indexs[1]) {
                    int temp = indexs[0];
                    indexs[0] = indexs[1];
                    indexs[1] = temp;
                }
                return indexs;
            }

            /**
             * 如果没有找到答案，继续for loop, 增加i,
             */
        }
        return indexs;
    }
}
