package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/21/18.
 */
public class LE_163_Missing_Range {
    /*
        Given a sorted integer array where the range of elements are in the inclusive range [lower, upper], return its missing ranges.

        For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return ["2", "4->49", "51->74", "76->99"].
     */

    /*  Case 1 :
    [0, 1, 3, 50, 75]   lower = 0, upper = 99

    l = 0, r = 99

     l                     l              l = 2  getRange(2, 3-1) => "2", l = cur + 1 = 3 + 1 = 4
    [0, 1, 3, 50, 75]  [0, 1, 3, 50, 75]  [0, 1, 3, 50, 75]
    cur                   cur                   cur

    l = 4, getRange(4, 50-1) => "4->49", l = cur + 1 = 50 + 1 = 51
    [0, 1, 3, 50, 75]
              cur
    l = 51, getRange(51, 75-1) => "51->74", l = cur + 1 = 75 + 1 = 76
    [0, 1, 3, 50, 75]
                  cur

    for loop ends

    l = 76, r = 99, l < r, getRange(76, 99) => "76->99"

    Case 2 :
    [0, 1, 3, 50, 75]   lower = 7, upper = 55, because "the range of elements are in the inclusive range [lower, upper]", therefore, this is not a valid case. "upper" should include the last element of the array.

    Special case, [], 1-99,3 then for loop does not execute, "1->99", or range "1", only gap is "1".
*/
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        //!!! nums.length == 0 is valid case : [], should allow it to run
        if (nums == null) return res;

        //!!! need to use long type to prevent overflow
        long left = (long)lower;
        long right = (long)upper;

        for (int num : nums) {
            long cur = (long)num;
            if (cur < left) {
                continue;
            }

            if (cur == left) {
                left++;
                continue;
            }

            res.add(getRange(left, cur - 1));
            //!!! move cursor
            left = cur + 1;
        }

        if (left <= right) {
            res.add(getRange(left, right));
        }

        return res;
    }

    private String getRange(long x, long y) {
        if (x == y) {
            return x + "";
        }
        return x + "->" + y;
    }
}
