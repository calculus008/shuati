package Interviews.Nextdoor;

public class Max_Ribbon {
    /**
     * Given a list representing the length of ribbon, and the target number "k" parts of ribbon.
     * we want to cut ribbon into k parts with the same size, at the same time we want the maximum size.
     *
     * Ex.
     * Input: A = [1, 2, 3, 4, 9], k = 5
     * Output: 3
     * Explanation:​​​​​​​​​​​​​​​​​​​
     * if size = 1, then we have 19 parts
     * if size = 2, then we have 8 parts
     * if size = 3, then we have 5 parts
     * if size = 4, then we have 3 parts, which is not enough. So return the max size = 3.
     */

    /**
     * Binary Search
     */
    public static int maxRibbon(int[] A, int k) {
        int hi = 0;
        for (int i = 0; i < A.length; i++) {
            hi += A[i];
        }

        int lo = 0;
        int res = 0;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int part = 0;

            for (int i = 0; i < A.length; i++) {
                part += A[i] / mid;
            }

            if (part >= k) {
                res = Math.max(res, mid);
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return res;
    }

    public static int max_ribbon(int[] A, int k) {
        // we want max cut --> right most search
        int low = 0;
        int high = 0;
        for (int num : A) {
            high += num;
        }

        int res = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int part = 0;

            for (int num : A) {
                part += num / mid;
            }

            if (part >= k) {
                res = Math.max(res, mid);
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int result = max_ribbon(new int[]{1, 2, 3, 4, 9}, 6);
        System.out.println(result);
    }
}
