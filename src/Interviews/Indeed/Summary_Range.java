package Interviews.Indeed;

import java.util.*;

public class Summary_Range {
    /**
     * 给⼀一个排序(!!!)的Integer的array， [1,2,3,5,6,8,9,12] 输出⼀一个string: “1->3,5->6,8->9,12′′
     * Followup，如果输⼊入有deplicate numbers，怎么办? [1,2,2,3] ==>”1->3′′
     * [1,2,2,5] ==>”1->2,5′′
     *
     * LE_228_Summary_Ranges
     */

    /**
     * No duplicate
     * O(n)
     */
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            while (i < nums.length - 1 && nums[i] + 1 == nums[i + 1]) {
                i++;
            }

            if (cur == nums[i]) {
                res.add("" + cur);
            } else {
                res.add(cur + "->" + nums[i]);
            }
        }

        return res;
    }

    /**
     * O(n)
     */
    public static List<String> summaryRangesWithDuplicates(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            while (i < nums.length - 1 && (nums[i] == nums[i + 1] || nums[i] + 1 == nums[i + 1])) {
                i++;
            }

            if (cur == nums[i]) {
                res.add("" + cur);
            } else {
                res.add(cur + "->" + nums[i]);
            }
        }

        return res;
    }

    /**
     * Unsorted array
     *
     * Key:
     * 1.use map to track left and right boundary of an interval, when add interval, add both ends:
     *  map.put(left, right);
     *  map.put(right, left);
     * 2.When there's new boundary remove the old one (!!!)
     *
     * Example :
     *
     * {4, 3, 5}
     * i = 0:
     * map
     * 4 -> 4
     *
     * i = 1;
     * map
     * 3 -> 4
     * 4 -> 3
     *
     * i = 2
     * map
     * 5 -> 3
     * 3 -> 5
     *
     *
     * Time  : O(n + mlogm), n is number of numbers in nums[], m is number intervals in map
     * Space : O(n)
     */
    public static List<String> summaryRangesUnsorted(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set  = new HashSet<>();

        for (int num : nums) {
            /**
             * use set to filter duplicates, check range when a new value is added to set
             */
            if (set.add(num)) {
                int left = num;
                int right = num;

                if (set.contains(num - 1)) {
                    left = map.get(num - 1);
                    /**
                     * !!!
                     */
                    map.remove(num - 1);
                }

                if (set.contains(num + 1)) {
                    right = map.get(num + 1);
                    /**
                     * !!!
                     */
                    map.remove(num + 1);
                }

                map.put(left, right);
                map.put(right, left);
            }
        }

        List<int[]> temp = new ArrayList<>();
        for (int key : map.keySet()) {
            int val = map.get(key);

//            System.out.println(key + "->" + val);


            if (key < val) {
                temp.add(new int[]{key, val});
            } else if (key == val) {
                temp.add(new int[]{key});
            }
        }

        Collections.sort(temp, (a, b) -> a[0] - b[0]);

        for (int[] elem : temp) {
            if (elem.length == 1) {
                res.add("" + elem[0]);
            } else {
                res.add(elem[0] + "->" + elem[1]);
            }
        }

        return res;
    }

        private static void printRes(List<String> res) {
        System.out.println(Arrays.toString(res.toArray()));
    }

    public static void main(String[] args) {
        int[] input1 = {1, 2, 2, 3};
        int[] input2 = {2,2,2,2,2,2};
        int[] input3 = {1,1,1,2,2,2,3,3,3, 50, 59, 59, 60, 61};

        int[] input4 = {60, 1, 50, 2, 61, 2, 3, 59, 3, 3, 50, 1, 1, 2, 59, 61};

        int[] input5 = {10, 1, 3, 11, 12, 3, 14, 3, 13, 2, 7};

        int[] input6 = {100, 5, 1, 6, 7, 1, 8};

        printRes(summaryRangesWithDuplicates(input1));
        printRes(summaryRangesWithDuplicates(input2));
        printRes(summaryRangesWithDuplicates(input3));

        printRes(summaryRangesUnsorted(input4));
        printRes(summaryRangesUnsorted(input5));
        printRes(summaryRangesUnsorted(input6));

    }
}

