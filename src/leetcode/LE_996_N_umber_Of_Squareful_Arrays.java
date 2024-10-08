package leetcode;

import java.util.*;

public class LE_996_N_umber_Of_Squareful_Arrays {
    /**
     * An array is squareful if the sum of every pair of adjacent elements is a perfect square.
     *
     * Given an integer array nums, return the number of permutations of nums that are squareful.
     *
     * Two permutations perm1 and perm2 are different if there is some index i such that perm1[i] != perm2[i].
     *
     *
     *
     * Example 1:
     * Input: nums = [1,17,8]
     * Output: 2
     * Explanation: [1,8,17] and [17,8,1] are the valid permutations.
     *
     * Example 2:
     * Input: nums = [2,2,2]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 12
     * 0 <= nums[i] <= 109
     *
     * Hard
     *
     * https://leetcode.com/problems/number-of-squareful-arrays
     */

    /**
     * DFS, BackTracking
     * A variation of Permutation with duplicates - LE_47_Permutation_II
     */
    class Solution_practice {
        int res = 0;

        public int numSquarefulPerms(int[] nums) {
            Arrays.sort(nums); //!!!
            helper(nums, new ArrayList<>(), new boolean[nums.length], -1);
            return res;
        }

        public void helper(int[] nums, List<Integer> cur, boolean[] visited, int last) {
            if (cur.size() == nums.length) {
                res++;
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (visited[i]) continue;

                if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) continue; //!!! "!visited[i - 1]", not "!visited[i]"

                if (last != -1 && !isSquare(last, nums[i])) continue;

                visited[i] = true;
                cur.add(nums[i]);
                helper(nums, cur, visited, nums[i]);
                cur.remove(cur.size() - 1);
                visited[i] = false;
            }
        }

        public boolean isSquare(int a, int b) {
            double sqrt = Math.sqrt(a + b); //!!! a + b
            boolean res = (sqrt - Math.floor(sqrt)) == 0; //!!! (sqrt - Math.floor(sqrt)
            return res;
        }
    }

    class Solution {
        private int count = 0;

        public int numSquarefulPerms(int[] A) {
            Arrays.sort(A);
            helper(new ArrayList(), A, new boolean[A.length], -1);
            return count;
        }

        private void helper(List<Integer> temp, int[] A, boolean[] used, int lastNumber) {
            if (temp.size() == A.length) {
                count++;
                return;
            }

            for (int i = 0; i < A.length; i++) {
                if (used[i] || (i > 0 && A[i] == A[i - 1] && !used[i - 1])) continue;

                if (lastNumber != -1) {
                    // if we can't form a square we can not proceed to form a squareful array
                    if (isSquare(A[i], lastNumber) == false)
                        continue;
                }

                used[i] = true;
                temp.add(A[i]);
                helper(temp, A, used, A[i]);
                temp.remove(temp.size() - 1);
                used[i] = false;
            }
        }

        public boolean isSquare(int a, int b) {
            int sum = a + b;
            int sqrt = (int) Math.sqrt(sum);  // Compute the square root of the sum
            return (sqrt * sqrt == sum);
        }

//        private boolean isSquare(int a, int b) {
//            double sqr = Math.sqrt(a + b); //!!! a + b
//            boolean res = (sqr - Math.floor(sqr)) == 0;  //!!! (sqrt - Math.floor(sqrt)
//            return res;
//        }
    }
}
