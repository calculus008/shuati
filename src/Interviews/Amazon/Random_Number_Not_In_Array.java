package Interviews.Amazon;

import java.util.*;

public class Random_Number_Not_In_Array {
    /**
     * 要求写一个special Random function 输入N 和 一个array, 要求随机返回一个值， 但值不能是array 里有的。
     * n = 5, arr = [1, 2, 6, 7, 9], 是不是说random的范围是[0, 5], 但不能是arr里有的1， 2
     * 这个意思呢？
     * 是的 不能返回array里的数字
     * 那是不是先random 出来个n的数， 然后用set check在不在array里就行了？
     * 要考虑一些edge case. 比如说 array 里面包含了从0到N所有数， 就infinite loop了 怎么处理？
     * follow-up: 如果只可以call 一次 rand(), 怎么做？
     */

    public int getRandom(int[] nums, int n) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            if (!set.contains(i)) {
                list.add(i);
            }
        }

        Random rand = new Random();
        int idx = rand.nextInt(list.size());

        return list.get(idx);
    }
}
