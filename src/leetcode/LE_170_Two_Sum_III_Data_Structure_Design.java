package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/22/18.
 */
public class LE_170_Two_Sum_III_Data_Structure_Design {
    /*
        Design and implement a TwoSum class. It should support the following operations: add and find.

        add - Add the number to an internal data structure.
        find - Find if there exists any pair of numbers which sum is equal to the value.

        For example,
        add(1); add(3); add(5);
        find(4) -> true
        find(7) -> false
     */

    public class TwoSum1 {
        // Solution 1 : add O(1), find )(n)
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
        //Solution 2 : add O(n), find O(1), Better for find
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
}
