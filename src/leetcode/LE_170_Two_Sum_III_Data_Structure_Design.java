package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/22/18.
 */
public class LE_170_Two_Sum_III_Data_Structure_Design {
    /**
        Design and implement a TwoSum class. It should support the following operations: add and find.

        add - Add the number to an internal data structure.
        find - Find if there exists any pair of numbers which sum is equal to the value.

        For example,
        add(1); add(3); add(5);
        find(4) -> true
        find(7) -> false
     */

    public class TwoSum1 {
        // Solution 1 : add O(1), find O(n)
        private Map<Integer, Integer> map;

        public TwoSum1() {
            map = new HashMap<>();
        }

        public void add(int number) {
            if (!map.containsKey(number)) {
                map.put(number, 1);
            } else {
                map.put(number, 2);
            }
        }

        public boolean find(int value) {
            for (int n1 : map.keySet()) {
                int n2 = value - n1;
                if ((n1 == n2 && map.get(n2) > 1) || n1 != n2 && map.containsKey(n2)) {
                    return true;
                }
            }
            return false;
        }

    }

    public class TwoSum2 {
        /**
            Solution 2 : add O(n), find O(1), Better for find
            用两个set， 一个存所有可能的sum，一个存数本身。
            TLE
         **/
        private Set<Integer> num = new HashSet<>();
        private Set<Integer> sum = new HashSet<>();

        public TwoSum2() {
            num = new HashSet<>();
            sum = new HashSet<>();
        }

        public void add(int number) {
            if (num.contains(number)) {
                sum.add(number * 2);
            } else {
                /**
                 * 1.注意Iterator的用法 ： "Iterator<Inreger>"
                 *
                 * 2.set用的是"contains", 不是“containsKey"
                 */
                Iterator<Integer> it = num.iterator();
                while (it.hasNext()) {
                    sum.add(it.next() + number);
                }
                num.add(number);
            }

        }

        public boolean find(int value) {
            return sum.contains(value);
        }
    }

    public class TwoSum3 {
        /**
         * 双指针。add操作时间复杂度一般情况下为O(1)。find操作时间复杂度O(nlogn)。n为已加入的元素个数。
         */

        ArrayList<Integer> nums = new ArrayList<>();

        public void add(int number) {
            // write your code here
            nums.add(number);
        }

        /*
         * @param value: An integer
         * @return: Find if there exists any pair of numbers which sum is equal to the value.
         */
        public boolean find(int value) {
            Collections.sort(nums);

            /**
             * Or
                 int start = 0;
                 int end = nums.size() - 1;

                 while (start < end) {
                 ...
                 }
             */
            for (int i = 0, j = nums.size() - 1; i < j;) {
                if (nums.get(i) + nums.get(j) == value) {
                    return true;
                } else if (nums.get(i) + nums.get(j) < value) {
                    i++;
                } else {
                    j--;
                }
            }
            return false;
        }
    }
}
