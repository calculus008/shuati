package leetcode;

public class LE_668_Kth_Smallest_Number_In_Multiplication_Table {
    /**
         Nearly every one have used the Multiplication Table.
         But could you find out the k-th smallest number quickly from the multiplication table?

         Given the height m and the length n of a m * n Multiplication Table,
         and a positive integer k, you need to return the k-th smallest number in this table.

         Example 1:
         Input: m = 3, n = 3, k = 5
         Output:
         Explanation:
         The Multiplication Table:
         1	2	3
         2	4	6
         3	6	9

         The 5-th smallest number is 3 (1, 2, 2, 3, 3).

         Example 2:
         Input: m = 2, n = 3, k = 6
         Output:
         Explanation:
         The Multiplication Table:
         1	2	3
         2	4	6

         The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).

         Note:
         The m and n will be in the range [1, 30000].
         The k will be in the range [1, m * n]
     */

    /**
     * https://zxi.mytechroad.com/blog/algorithms/binary-search/leetcode-668-kth-smallest-number-in-multiplication-table/
     *
     * Binary Search
     *
     * Find first x such that there are k elements less or equal to x in the table.
     *
     * Time  : O(m * log(m*n))
     * Space : O(1)
     *
     * 1 2 3
     * 2 4 6
     * 3 6 9
     *
     * 1 2 2 3 3 4 6 6 9
     *
     * m = 3, n = 3, k = 7
     *
     * l = 1, r = 10, mid = 5, len(5) = 6, l = mid + 1 = 6
     * l = 6, r = 10, mid = 8, len(8) = 8, r = mid = 8
     * l = 6, r = 8,  mid = 7, len(7) = 8, r = mid = 7
     * l = 6, r = 7,  mid = 6, len(7) = 8, r = mid = 6
     *
     * l = 6, r = 6, exit
     *
     * return l = 6
     *
     */
    public static int findKthNumber(int m, int n, int k) {
        int l = 1;
        int r = m * n + 1;

        while (l < r) {
            int mid = l + (r - l) / 2;
            /**
             * ">=", 是要找比mid小的数的个数是 k - 1 个
             * 而mid是第k个
             */
//            System.out.println("l = " + l + ", r = " + r + ", mid = " + mid);

            if (len(m, n, mid) >= k) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    private static int len(int m, int n, int x) {
        int res = 0;
        for (int i = 1; i <= m; i++) {
            res += Math.min(x / i, n);
        }
//        System.out.println(" len(" + x + ") = " + res);
        return res;
    }

    public static void main(String [] args) {
        findKthNumber(3, 3, 7);
    }

}