package leetcode;

public class LE_540_Single_Element_In_A_Sorted_Array {
    /**
         Given a sorted array consisting of only integers where every element appears
         twice except for one element which appears once. Find this single element that appears only once.

         Example 1:
         Input: [1,1,2,3,3,4,4,8,8]
         Output: 2
         Example 2:
         Input: [3,3,7,7,10,11,11]
         Output: 10

         Note: Your solution should run in O(log n) time and O(1) space.

         Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/algorithms/binary-search/leetcode-540-single-element-in-a-sorted-array/
     */
    public int singleNonDuplicate(int[] nums) {
        int l = 0;
        int r = nums.length;

        /**
         col 0 1 2 3 4 5 6 7 8 9 10
             1 1 2 2 3 3 4 5 5 6 6
         Before single number, [0,1], [2,3], [4,5], pair stars at index of even number.
         After single number, [7,8], [9, 10], pair stars at index of odd number.

         So this give indication which direction we should go.

         Basically, the single char becomes a separation point, chars before and after it will change properties :
         before it, char starting at an even number index i equals to the char to its right (i + 1);
         after it,  char starting at an odd number index j equals to the char to its right (j + 1);

         So we use this condition to do binary search. When we get mid value, it could be even or odd.
         So we need to calculate the index of its equal neighbor. Based on the rules of the "before section" :
            If it's even, its equal neighbor is at its right (i + 1);
            If it's odd, its equal neighbor is at its left (i - 1);
         **/
        while (l < r) {
            int m = l + (r - l) / 2;
            int n = m % 2 == 0 ? m + 1 : m - 1;
//            int n = m ^ 1;
            if (n < nums.length && nums[m] == nums[n]) { //!!! if m is on the left side (before) the single value
                l = m + 1;
            } else {
                r = m;
            }
        }

        return nums[l];
    }

    /**
     * Time : O(n)
     */
    public int singleNonDuplicate_exclusieve_or(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            res ^= nums[i];
        }
        return res;
    }
}