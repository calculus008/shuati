package leetcode;

public class LE_1010_Pairs_Of_Songs_With_Total_Durations_Divisible_By_60 {
    /**
     * In a list of songs, the i-th song has a duration of time[i] seconds.
     *
     * Return the number of pairs of songs for which their total duration in
     * seconds is divisible by 60.  Formally, we want the number of indices
     * i < j with (time[i] + time[j]) % 60 == 0.
     *
     * Example 1:
     * Input: [30,20,150,100,40]
     * Output: 3
     * Explanation: Three pairs have a total duration divisible by 60:
     * (time[0] = 30, time[2] = 150): total duration 180
     * (time[1] = 20, time[3] = 100): total duration 120
     * (time[1] = 20, time[4] = 40): total duration 60
     *
     * Example 2:
     * Input: [60,60,60]
     * Output: 3
     * Explanation: All three pairs have a total duration of 120, which is divisible by 60.
     *
     * Easy
     */

    /**
     * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/discuss/256738/JavaC%2B%2BPython-Two-Sum-with-K-60
     *
     * Intuition
     * Calculate the time % 60 then it will be exactly same as two sum problem.
     *
     * t % 60 gets the remainder from 0 to 59.
     * We count the occurrence of each remainders in a array/hashmap c.
     *
     * we want to know that, for each t,
     * how many x satisfy (t + x) % 60 = 0.
     *       60 - n % 60
     * 30,     30
     * 20,     40 +1
     * 150,    10
     * 100,    40 +1
     * 40      20 +1
     *
     *        n % 60     60 - n % 60
     * 30      30           30          30:1
     * 20      20           40          20:1
     * 150     30           30          +1   30:2
     * 100     40           20          +1   40:1
     * 40      40           20          +1   40:2
     */
    class Solution {
        public int numPairsDivisibleBy60(int[] time) {
            if (time == null || time.length < 2) return 0;

            int[] c = new int[60];
            int res = 0;

            for (int t : time) {
                res += c[(60 - t % 60) % 60];
                c[t % 60]++;
            }

            return res;
        }
    }
}
