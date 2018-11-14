package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 9/27/18.
 */
public class LE_898_Bitwise_ORs_Of_Subarrays {
    /**
         We have an array A of non-negative integers.

         For every (contiguous) subarray B = [A[i], A[i+1], ..., A[j]] (with i <= j),
         we take the bitwise OR of all the elements in B, obtaining a result A[i] | A[i+1] | ... | A[j].

         Return the number of possible results.
         (Results that occur more than once are only counted once in the final answer.)

         Example 1:

         Input: [0]
         Output: 1
         Explanation:
         There is only one possible result: 0.

         Example 2:

         Input: [1,1,2]
         Output: 3
         Explanation:
         The possible subarrays are [1], [1], [2], [1, 1], [1, 2], [1, 1, 2].
         These yield the results 1, 1, 2, 1, 3, 3.
         There are 3 unique values, so the answer is 3.

         Example 3:

         Input: [1,2,4]
         Output: 6
         Explanation:
         The possible results are 1, 2, 3, 4, 6, and 7.

         Note:

         1 <= A.length <= 50000
         0 <= A[i] <= 10^9

         Medium
     */

    /**
         Solution 1 : Regular DP
         dp[i][j] := A[i] | A[i + 1] | … | A[j]
         dp[i][j] = dp[i][j – 1] | A[j]

         Time complexity: O(n^2)
         Space complexity: O(n^2) -> O(n)

         TLE due to data scale is large (<= 50000)
     **/
     public int subarrayBitwiseORs1(int[] A) {
         int n = A.length;
         int[][] dp = new int[n + 1][n + 1];
         Set<Integer> set = new HashSet<>();
         set.add(A[0]);
         set.add(A[n - 1]);

         for (int l = 1; l <= n; l++) {
             for (int i = 0; i + l <= n; i++) {
                 int j = i + l -1;
                 if (l == 1) {//!!!
                     dp[i][j] = A[i];//!!!
                     set.add(dp[i][j]);
                     continue;
                 }
                 dp[i][j] = dp[i][j - 1] | A[j];
                 set.add(dp[i][j]);
             }
         }

         return set.size();
     }

    /**
         Solution 2
         dp[i] := {A[i], A[i] | A[i – 1], A[i] | A[i – 1] | A[i – 2], … , A[i] | A[i – 1] | … | A[0]},
         bitwise ors of all subarrays end with A[i].

         |dp[i]| <= 32
         Proof: all the elements (in the order of above sequence) in dp[i] are monotonically increasing
         by flipping 0 bits to 1 from A[i].

         There are at most 32 0s in A[i]. Thus the size of the set is <= 32.

         证明： dp[i] = {A[i], A[i] | A[i – 1], A[i] | A[i – 1] | A[i – 2], … , A[i] | A[i – 1] | … | A[0]}，
         这个序列单调递增，通过把A[i]中的0变成1。A[i]最多有32个0。所以这个集合的大小 <= 32。

         e.g. 举例：Worst Case 最坏情况 A = [8, 4, 2, 1, 0] A[i] = 2^(n-i)。

         A[5] = 0，dp[5] = {0, 0 | 1, 0 | 1 | 2, 0 | 1 | 2 | 4, 0 | 1 | 2 | 4 | 8} = {0, 1, 3, 7, 15}.

         Time complexity: O(n*log(max(A))) < O(32n)
         Space complexity: O(n*log(max(A)) < O(32n)
     **/
    public int subarrayBitwiseORs2(int[] A) {
        Set<Integer> res = new HashSet<>();
        Set<Integer> cur = new HashSet<>();

        for (int a : A) {
            Set<Integer> next = new HashSet<>();
            //!!!
            next.add(a);

            for (int n : cur) {
                next.add(n | a);
            }
            res.addAll(next);
            cur = next;
        }

        return res.size();
    }
}
