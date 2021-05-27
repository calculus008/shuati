package leetcode;

public class LE_1423_Maximum_Points_You_Can_Obtain_From_Cards {
    /**
     * There are several cards arranged in a row, and each card has an associated number of points. The points are given
     * in the integer array cardPoints.
     *
     * In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
     *
     * Your score is the sum of the points of the cards you have taken.
     *
     * Given the integer array cardPoints and the integer k, return the maximum score you can obtain.
     *
     * Example 1:
     * Input: cardPoints = [1,2,3,4,5,6,1], k = 3
     * Output: 12
     * Explanation: After the first step, your score will always be 1. However, choosing the rightmost card first will
     * maximize your total score. The optimal strategy is to take the three cards on the right, giving a final score
     * of 1 + 6 + 5 = 12.
     *
     * Example 2:
     * Input: cardPoints = [2,2,2], k = 2
     * Output: 4
     * Explanation: Regardless of which two cards you take, your score will always be 4.
     *
     * Example 3:
     * Input: cardPoints = [9,7,7,9,7,7,9], k = 7
     * Output: 55
     * Explanation: You have to take all the cards. Your score is the sum of points of all cards.
     *
     * Example 4:
     * Input: cardPoints = [1,1000,1], k = 1
     * Output: 1
     * Explanation: You cannot take the card in the middle. Your best score is 1.
     *
     * Example 5:
     * Input: cardPoints = [1,79,80,1,1,1,200,1], k = 3
     * Output: 202
     *
     * Constraints:
     * 1 <= cardPoints.length <= 105
     * 1 <= cardPoints[i] <= 104
     * 1 <= k <= cardPoints.length
     *
     * Medium
     */

    /**
     * Prefix Sum
     * https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/discuss/597825/Simple-Clean-Intuitive-Explanation-with-Visualization
     *
     * Key idea: You can’t choose 2nd element from the beginning unless you have chosen the first one. Similarly,
     * you can’t choose 2nd element from last unless you have chosen the last one.
     *
     * So now just try all possible combinations. Choose 0 from the beginning and K from the last, 1 from front and
     * K-1 from last and so on until K from beginning and 0 from behind. Maximum out of all those combinations is the answer.
     *
     * To make it faster to find sum of first i cards, store the cumulative sum from the beginning to current index i
     * in an array. In the similar way, store cumulative sums from the back in separate array.
     *
     * How to solve exactly?
     *
     * 1.Find cumulative sum from beginning to the current index.
     * 2.Find cumulative sum from behind till the current index.
     * 3.If you choose i elements from front, you will need to choose k-i elements from behind.
     *   Sum of first i elements = cumulativeSumFromFront[i],
     *   Sum of last (k-i) elements = cumulativeSumFromBehind[K-i].
     *   So points obtained when choosing i elements from the front = cumulativeSumFromFront[i] + cumulativeSumFromBehind[K-i]
     * 4.Repeat Step 3 for all i ranging from 0 to K.
     * 5.Return the maximum value of points reached.
     */
    class Solution {
        public int maxScore(int[] cardPoints, int k) {
            int n = cardPoints.length;
            int[] presum1 = new int[n];
            int[] presum2 = new int[n];

            presum1[0] = cardPoints[0];
            for (int i = 1; i < n; i++) {
                presum1[i] = presum1[i - 1] + cardPoints[i];
            }

            presum2[0] = cardPoints[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                presum2[n - i - 1] = presum2[n - i - 2] + cardPoints[i];
            }

            if (k == n) return presum1[n - 1];

            int res = Integer.MIN_VALUE;
            for (int i = 0; i <= k; i++) {
                int sum1 = i == 0 ? 0 : presum1[i - 1];
                int sum2 = k - i == 0 ? 0 : presum2[k - i - 1];
                res = Math.max(res, sum1 + sum2);
            }

            return res;
        }
    }
}
