package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/30/18.
 */
public class LE_229_Majority_Element_II {
    /**
        Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
        The algorithm should run in linear time and in O(1) space.
     */

    /**
    Time : O(n), Space : O(1)

    Key points
    1.在数组中出现次数大于三分之一的数最多有2个。
    2.用 LE_169的算法先找到出现次数最多的两个数 (num1 and num2).
    3.再遍历数组一遍统计num1和num2的出现次数，如果大于数组长度的三分之一，加入res.
    4.注意，第一遍遍历之后，count1和count2里的数不是num1和num2真正的出现次数，所以要再遍历一遍。

   [1,2,1,1,2,3]
   num  num1  num2   count1  count2
    -    0     0       0       0
    1    1     0       1       0
    2    1     2       1       1
    1    1     2       2       1
    1    1     2       3       1
    2    1     2       3       2
    3    1     2       2       2

    num1 = 1, num2 = 2
    res = {1}

   [1,1,1,3,3,2,2,2]
   num  num1  num2   count1  count2
    -    0     0       0       0
    1    1     0       1       0
    1    1     0       2       0
    1    1     0       3       0
    3    1     3       3       1
    3    1     3       3       2
    2    1     3       2       1
    2    1     3       1       0
    2    1     2      .1       1

    num1 = 1, num2 = 2
    res = {1, 2}
*/
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        int num1 = 0, num2 = 0;
        int count1 = 0, count2 = 0;
        for (int num : nums) {
            if (num == num1) {
                count1++;
            } else if (num == num2) {
                count2++;
            } else if (count1 == 0) {
                num1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                num2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }

            //!!! Wrong logic !!!
            // } else if (num != num1) {
            //     count1--;
            // } else if (num != num2) {
            //     count2--;
            // }
        }


        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == num1) {
                count1++;
            } else if (num == num2) {
                count2++;
            }
        }

        if (count1 > nums.length / 3) {
            res.add(num1);
        }
        if (count2 > nums.length / 3) {
            res.add(num2);
        }

        return res;
    }
}

