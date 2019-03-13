package Linkedin;

public class Kth_Missing_Element_In_Sorted_Array {
    /**
     * Given an increasing sequence a[], we need to find the K-th missing
     * contiguous element in the increasing sequence which is not present
     * in the sequence. If no k-th missing element is there output -1.
     *
     * Examples :
     *
     * Input : a[] = {2, 3, 5, 9, 10};
     *         k = 1;
     * Output : 4
     * Explanation: Missing Element in the increasing
     * sequence are {4, 6, 7, 8}. So k-th missing element
     * is 4
     *
     * Input : a[] = {2, 3, 5, 9, 10, 11, 12};
     *         k = 4;
     * Output : 8
     * Explanation: missing element in the increasing
     * sequence are {4, 6, 7, 8}  so k-th missing
     * element is 8
     */

    /**
     * Solution 1
     * Approach: Start iterating over the array elements, and for every
     * element check if next element is consecutive or not, if not, then
     * take difference between these two, and check if difference is
     * greater than or equal to given k, then calculate ans = a[i] + count,
     * else iterate for next element.
     *
     * Time  : O(n)
     * Space : O(1)
     */
    public static int missingK(int[] a, int k) {
        int numberOfMissed = 0, ans = 0, target = k;
        boolean flag = false;

        int n = a.length;

        // interating over the array
        for (int i = 0; i < n - 1; i++) {
            numberOfMissed = 0;

            // check if i-th and
            // (i + 1)-th element
            // are not consecutive
            if ((a[i] + 1) != a[i + 1]) {
                // save their numberOfMissed
                numberOfMissed += (a[i + 1] - a[i]) - 1;

                // check for numberOfMissed
                // and given k
                if (numberOfMissed >= target) {
                    ans = a[i] + target;
                    flag = true;
                    break;
                } else {
                    target -= numberOfMissed;
                }
            }
        }

        // if found
        if (flag) {
            return ans;
        } else {
            return -1;
        }
    }

    /**
     * Solution 2
     * Binary Search
     *
     * Time : log(n)
     *
     * a[] = {2, 3, 5, 9, 10}, K = 1
     *
     * l = 0, h = 4, k = 1, sum = 0
     * m = 2
     * a = a[m] - a[l] = 3  在l ~ m，如果没有missing, 应该有几个数。
     * b = m - l = 2        在l ~ m，根据下标计算，实际上有几个数。
     * a - b = 1            在l ~ m，有几个missing numbers.
     * sum = 0 + 1 >= k, go to left side, sum is still 0 (only change sum when go to right side)
     *
     * l = 0, h = 2, k = 1, sum = 0
     * m = 1
     * a = a[1] - a[0] = 1
     * b = 1 - 0 = 1
     * a - b = 0
     * 0 < k, go to right side
     *
     * l = 1, h = 2, sum = 0
     * hit base case : h - l = 1
     * return a[l] + (k - 1) = a[1] + (k - sum) = 3 + (1 - 0) = 4
     *
     */
    public static int missingK_2(int[] a, int k) {
        int n = a.length;
        if (a[n - 1] - a[0] + 1 == n || k <= 0) {
            return -1;
        }

        return find(a, 0, a.length - 1, k, 0);
     }

    private static int find(int[] a, int startIdx, int endIdx, int k, int sumOfNumberOfMissed) {
        System.out.println("l = " + startIdx + ", h = " + endIdx + ", k = " + k + ", sum = " + sumOfNumberOfMissed);

        if (endIdx - startIdx == 1) {
            int res =  a[startIdx] + (k - sumOfNumberOfMissed);
            return res >= a[a.length - 1] ? -1 : res;
        }

        int midIdx = startIdx + (endIdx - startIdx) / 2;
        int numberOfMissed = (a[midIdx] - a[startIdx]) - (midIdx - startIdx);
        if (sumOfNumberOfMissed + numberOfMissed >= k) {
            return find(a, startIdx, midIdx, k, sumOfNumberOfMissed);
        } else {
            sumOfNumberOfMissed += numberOfMissed;
            return find(a, midIdx, endIdx, k, sumOfNumberOfMissed);
        }
    }

    public static int findMissingK_Practice(int[] a, int k) {
        int n = a.length;
        if (a[n - 1] - a[0] == n - 1 || k <= 0) {
            return -1;
        }

        return find_practice(a, k, 0, n - 1, 0);
    }

    public static int find_practice(int[] a, int k, int l, int h, int sumOfNumberOfMissed) {
        if (h - l == 1) {
            int res = a[l] + (k - sumOfNumberOfMissed);
            return res >= a[a.length - 1] ? -1 : res;
        }

        int m = l + (h - l) / 2;
        int numberNoMissing = a[m] - a[l];
        int numberActual = m - l;
        int missed = numberNoMissing - numberActual;

        if (sumOfNumberOfMissed + missed >= k) {
            return find_practice(a, k, l, m, sumOfNumberOfMissed);
        } else {
            sumOfNumberOfMissed += missed;
            return find_practice(a, k, m, h, sumOfNumberOfMissed);
        }
    }

    // Driver code
    public static void main(String args[]) {
        //        int[] a = {1, 5, 11, 19};
//        int k = 11;

//        int[] a = {2, 3, 5, 9, 10};
//        int k = 1;
//
        int[] a = {1, 100};
        int k = 100;

//        int[] a = {1, 2, 4, 5};
//        int k = -1;

//        int missing = missingK(a, k);

        int missing = findMissingK_Practice(a, k);

        System.out.print(missing);
    }

}
