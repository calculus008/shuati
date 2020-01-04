package leetcode;

public class LE_552_Student_Attendance_Record_II {
    /**
     * Given a positive integer n, return the number of all possible attendance
     * records with length n, which will be regarded as rewardable. The answer
     * may be very large, return it after mod 109 + 7.
     *
     * A student attendance record is a string that only contains the following
     * three characters:
     *
     * 'A' : Absent.
     * 'L' : Late.
     * 'P' : Present.
     * A record is regarded as rewardable if it doesn't contain more than one
     * 'A' (absent) or more than two continuous 'L' (late).
     *
     * Example 1:
     * Input: n = 2
     * Output: 8
     * Explanation:
     * There are 8 records with length 2 will be regarded as rewardable:
     * "PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
     * Only "AA" won't be regarded as rewardable owing to more than one absent times.
     * Note: The value of n won't exceed 100,000.
     *
     * Hard
     */

    /**
     * First consider no A cases
     *
     * Then insert A
     */
    class Solution1 {
        static final int M = 1000000007;

        public int checkRecord(int n) {
            long[] PorL = new long[n + 1]; // ending with P or L, no A
            long[] P = new long[n + 1]; // ending with P, no A

            PorL[0] = P[0] = 1; PorL[1] = 2; P[1] = 1;

            for (int i = 2; i <= n; i++) {
                P[i] = PorL[i - 1];
                PorL[i] = (P[i] + P[i - 1] + P[i - 2]) % M;
            }

            long res = PorL[n];
            for (int i = 0; i < n; i++) { // inserting A into (n-1)-length strings
                long s = (PorL[i] * PorL[n - i - 1]) % M;
                res = (res + s) % M;
            }

            return (int) res;
        }
    }

    class Solution2 {
        static final int mod = (int) (1e9 + 7);

        public int checkRecord(int n) {
            long[] P = new long[n + 1]; //end with P w/o A
            long[] L = new long[n + 1]; //end with L w/o A

            P[0] = P[1] = L[1] = 1;

            for (int i = 2; i <= n; i++) {
                P[i] = (P[i - 1] + L[i - 1]) % mod;
                L[i] = (P[i - 1] + P[i - 2]) % mod;
            }

            long res = (P[n] + L[n]) % mod;

            /**
             * inserting A into (n-1)-length strings
             *
             * 就是说，前i个字符不含A，把A插入后面(n - 1) -i个字符的sequence.
             *
             **/
            for (int i = 0; i < n; i++) {
                long s = ((P[i] + L[i]) % mod * (P[n - i - 1] + L[n - i - 1]) % mod) % mod;
                res = (res + s) % mod;
            }

            return (int) res;
        }
    }
}
