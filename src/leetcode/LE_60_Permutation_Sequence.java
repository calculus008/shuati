package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/3/18.
 */
public class LE_60_Permutation_Sequence {
    /**
        The set [1,2,3,…,n] contains a total of n! unique permutations.

        By listing and labeling all of the permutations in order,
        We get the following sequence (ie, for n = 3):

        "123"
        "132"
        "213"
        "231"
        "312"
        "321"
        Given n and k, return the kth permutation sequence.

        Note: Given n will be between 1 and 9 inclusive.
     */

    /**  Time and Space : O(n)
     *   n digits, total number of permutation is n!.
     *   For 4 digits, the first digit k / 3!, then k %= 3!, then keep going...
     *   Just notice we need to subtract 1 from k when calculating since list and array are zero based,
     *   therefore, we need to adjust.
     *
     *   Given: n = 4, k = 18
     *   [1,2,3,4], 18 / 3! = 3, 3rd in [1,2,3,4] is 3

         In for loop:6
         i = 4,   k = 17, index = 17 / (4-1)! = 2, k = 17 % 3! = 5 => list : 1, 2, 3, 4, at index "2", value is "3", remove "3" from list - 1, 2, 4
         i = 3,   k = 5,  index = 5 / (3-1)! = 2,  k = 5 % 2! = 1  => list : 1, 2, 4,    at index "2", value is "4", remove "4" from list = 1, 2
         i = 2    k = 1,  index = 1 / (2-1)! = 1,  k = 1 % 1! = 0  => list : 1, 2,       at index "1", value is "2", remove "2" from list = 1
         i = 1    k = 0,  index = 0 / (1-1)! = 0 , k = 0 % 1! = 1  => list : 1,          at index "0", value is "1", remove "1" from list is empty

         Answer : "3421"

    */

    public static String getPermutation(int n, int k) {
        //factoria table
        int[] fact = new int[n + 1];
        fact[0] = 1;
        for (int i = 1; i < n; i++) {
            fact[i] = fact[i - 1] * i;
        }

        //number list
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        k--; //!!!zero base calculation
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i--) {
            int index = k / fact[i - 1];
            k = k % fact[i - 1];
            sb.append(list.get(index));
            list.remove(index);
        }
        return sb.toString();
    }
}
