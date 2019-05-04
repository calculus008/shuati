package Interviews.DoorDash;

import java.util.Arrays;

public class Sorting_Array {
    /**
     * Version 1
     * Given an array of integers, assuming the integers are within a limit, let's say (0<=n<=200)
     * Return an array with sorted integers.
     *
     * 我当时给的解答是：直接create一个counter，记录integer出现的次数，按照出现次数，构建新的排序的array，返回
     * 这题我反思了一下，我觉得应该多问一clarification question：
     * 1. ascending or descending
     * 2. which one do you like to optimize in terms of complexity? Space OR Time
     *
     * 面试官问了我关于hashmap（dict）复杂度的问题，我当时简单的回答了O(1) for time, O(n) for space。
     * 这个回答过于简单，反思后我觉得应该能够针对hashtable的不同method去分析average case & worst case。
     *
     * Version 2
     * Values are non-negative and less than some number, for example:
     * 0 <= value <= 200
     * """
     * Q: What sorting algo do you know? What is time complexity?
     * A : quick sort, merge sort, insertion sort ... bla bla
     * Q: pros and cons for quick sort and merge sort
     * Q: Besdies those above sorting algo, what other ways you can sort?
     * A: (心里有点蒙了，难道要我当场发明一个全新的sorting algo? ) 我"假装"不知道counting sort , 演了一下，
     * Q: okay implemet it
     * 我觉得interviewer一定知道，很多人都能马上码出quick sort / merge sort
     * 整个问答的目的只是希望能够看你能否相互分析，并且写出你刚刚的分析
     */

    public static int[] sortArray(int[] nums) {
        int[] buckets = new int[201];//!!! 201 : 0 ~ 200

        for (int num : nums) {
            buckets[num]++;
        }

        int idx = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != 0) {
                for (int j = 0; j < buckets[i]; j++) {
                    nums[idx++] = i;
                }
            }
        }

        return nums;
    }

    public static void main(String[] args) {
        int[] input = new int[]{200, 3,  4, 4, 40, 40, 32, 21, 21, 0, 0, 0};

        int[] res = sortArray(input);
        System.out.println(Arrays.toString(res));
    }
}
