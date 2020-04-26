package leetcode;

public class LE_774_Minimize_Max_Distance_To_Gas_Station {
    /**
     * On a horizontal number line, we have gas stations at positions
     * stations[0], stations[1], ..., stations[N-1], where N = stations.length.
     *
     * Now, we add K more gas stations so that D, the maximum dirs
     * between adjacent gas stations, is minimized.
     *
     * Return the smallest possible value of D.
     *
     * Example:
     *
     * Input: stations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], K = 9
     * Output: 0.500000
     *
     * Note:
     * stations.length will be an integer in range [10, 2000].
     * stations[i] will be an integer in range [0, 10^8].
     * K will be an integer in range [1, 10^6].
     * Answers within 10^-6 of the true value will be accepted as correct.
     */

    /**
     * 这题的特殊之处在于binary search on range with double type.
     * Not sure why huahua's template does not work here
     *
     * Maybe for double, 左闭右开 does not work ??
     */
    class Solution1 {
        public double minmaxGasDist1(int[] st, int K) {
            int count;
            int N = st.length;

            double left = 0;
            double right = st[N - 1] - st[0];
            double mid = 0;

            while (left + 1e-6 < right) {
                mid = (left + right) / 2;
                count = 0;

                for (int i = 0; i < N - 1; ++i) {
                    count += Math.ceil((st[i + 1] - st[i]) / mid) - 1;
                }

                if (count > K) {
                    left = mid;
                } else {
                    right = mid;
                }
            }

            return right;
        }

        public double minmaxGasDist2(int[] st, int K) {
            int count, N = st.length;
            double left = 0;
            double right = st[N - 1] - st[0];
            double mid = 0;

            while (left + 1e-6 < right) {
                mid = (left + right) / 2;
                count = 0;

                for (int i = 0; i < N - 1; ++i) {
                    count += Math.ceil((st[i + 1] - st[i]) / mid) - 1;
                }

                if (count <= K) {
                    right = mid;
                } else {
                    left = mid;
                }
            }

            return left;
        }
    }
}
