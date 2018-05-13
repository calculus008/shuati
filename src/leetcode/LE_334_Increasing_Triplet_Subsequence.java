package leetcode;

/**
 * Created by yuank on 5/13/18.
 */
public class LE_334_Increasing_Triplet_Subsequence {
    /**
     * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.

         Formally the function should:
         Return true if there exists i, j, k
         such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
         Your algorithm should run in O(n) time complexity and O(1) space complexity.

         Examples:
         Given [1, 2, 3, 4, 5],
         return true.

         Given [5, 4, 3, 2, 1],
         return false.

        Medium
     */

    /**
     Time  : O(n)
     Space : O(1)

     initial : [1, 2, 0, 3], small = MAX, big = MAX
     loop1 : [1, 2, 0, 3], small = 1, big = MAX
     loop2 : [1, 2, 0, 3], small = 1, big = 2
     loop3 : [1, 2, 0, 3], small = 0, big = 2 // <- Uh oh, 0 technically appeared after 2
     loop4 : return true since 3 > small && 3 > big // Isn't this a violation??

     If you observe carefully, the moment we updated big from MAX to some other value, that means that there clearly was a value less than it (which would have been assigned to small in the past). What this means is that once you find a value bigger than big, you've implicitly found an increasing triplet.
     **/
    public boolean increasingTriplet(int[] nums) {
        int big = Integer.MAX_VALUE;
        int small = Integer.MAX_VALUE;

        for (int num : nums) {
            /**
             !!! All uses "<="
             **/
            if (num <= small) {
                small = num;
            } else if (num <= big) {
                //!!! we check big in this "else if", inplicitly implies there's a number
                //smaller than big previously
                big = num;
            } else {
                //"else" here actually means num > both big and small
                return true;
            }
        }

        return false;
    }
}
