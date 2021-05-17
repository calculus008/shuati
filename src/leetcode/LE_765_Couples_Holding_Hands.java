package leetcode;

public class LE_765_Couples_Holding_Hands {
    /**
     * N couples sit in 2N seats arranged in a row and want to hold hands.
     * We want to know the minimum number of swaps so that every couple is
     * sitting side by side. A swap consists of choosing any two people,
     * then they stand up and switch seats.
     *
     * The people and seats are represented by an integer from 0 to 2N-1,
     * the couples are numbered in order, the first couple being (0, 1),
     * the second couple being (2, 3), and so on with the last couple being (2N-2, 2N-1).
     *
     * The couples' initial seating is given by row[i] being the value of the person
     * who is initially sitting in the i-th seat.
     *
     * Example 1:
     *
     * Input: row = [0, 2, 1, 3]
     * Output: 1
     * Explanation: We only need to swap the second (row[1]) and third (row[2]) person.
     *
     * Example 2:
     *
     * Input: row = [3, 2, 0, 1]
     * Output: 0
     * Explanation: All couples are already seated side by side.
     * Note:
     *
     * len(row) is even and in the range of [4, 60].
     * row is guaranteed to be a permutation of 0...len(row)-1.
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/couples-holding-hands/discuss/113362/JavaC%2B%2B-O(N)-solution-using-cyclic-swapping
     *
     * partner[i] denotes the partner of label i (i can be either a seat or a person) - -
     * ptn[i] = i + 1 if i is even; ptn[i] = i - 1 if i is odd.
     *
     * pos[i] denotes the index of the person with label i in the row array - - row[pos[i]] == i.
     *
     * The meaning of i == partner[pos[partner[row[i]]]] is as follows:
     *
     * 1.The person sitting at seat i has a label row[i], and we want to place him/her next to his/her partner.
     * 2.So we first find the label of his/her partner, which is given by partner[row[i]].
     * 3.We then find the seat of his/her partner, which is given by pos[partner[row[i]]].
     * 4.Lastly we find the seat next to his/her partner's seat, which is given by partner[pos[partner[row[i]]]].
     *
     * Time and Space : O(n)
     */
    class Solution {
        public int minSwapsCouples(int[] row) {
            int res = 0, N = row.length;

            int[] partner = new int[N];
            int[] pos = new int[N];

            for (int i = 0; i < N; i++) {
                partner[i] = (i % 2 == 0 ? i + 1 : i - 1);
                pos[row[i]] = i;
            }

            for (int i = 0; i < N; i++) {
                for (int j = partner[pos[partner[row[i]]]]; i != j; j = partner[pos[partner[row[i]]]]) {
                    swap(row, i, j);
                    swap(pos, row[i], row[j]);
                    res++;
                }
            }

            return res;
        }

        private void swap(int[] arr, int i, int j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
    }
}
