package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 11/24/18.
 */
public class LE_911_Online_Election {
    /**
         In an election, the i-th vote was cast for persons[i] at time times[i].

         Now, we would like to implement the following query function:
         TopVotedCandidate.q(int t) will return the number of the person that was leading the election at time t.

         Votes cast at time t will count towards our query.
         In the case of a tie, the most recent vote (among tied candidates) wins.



         Example 1:

         Input: ["TopVotedCandidate","q","q","q","q","q","q"], [[[0,1,1,0,0,1,0],[0,5,10,15,20,25,30]],[3],[12],[25],[15],[24],[8]]
         Output: [null,0,1,1,0,0,1]
         Explanation:
         At time 3, the votes are [0], and 0 is leading.
         At time 12, the votes are [0,1,1], and 1 is leading.
         At time 25, the votes are [0,1,1,0,0,1], and 1 is leading (as ties go to the most recent vote.)
         This continues for 3 more queries at time 15, 24, and 8.


         Note:

         1 <= persons.length = times.length <= 5000
         0 <= persons[i] <= persons.length
         times is a strictly increasing array with all elements in [0, 10^9].
         TopVotedCandidate.q is called at most 10000 times per test case.
         TopVotedCandidate.q(int t) is always called with t >= times[0].

         Medium
     */

    /**
     * 2 HashMap + Binary Search
     *
     *
     */
    class TopVotedCandidate {
        /**
         map time to the lead person id at that time
         **/
        Map<Integer, Integer> map;
        int[] time;

        /**
         * Time  : O(n)
         * Space : O(n)
         */
        public TopVotedCandidate(int[] persons, int[] times) {
            time = times;
            int n = persons.length;
            map = new HashMap<>();

            /**
             map to count votes for each person
             **/
            Map<Integer, Integer> votes = new HashMap<>();
            int cur = -1;
            for (int i = 0; i < n; i++) {
                int count = votes.getOrDefault(persons[i], 0) + 1;
                votes.put(persons[i], count);

                if (i == 0 || count >= votes.getOrDefault(cur, 0)) {
                    cur = persons[i];
                }
                map.put(times[i], cur);
            }
        }

        /**
         * Time : O(logn)
         */
        public int q(int t) {
            int i = Arrays.binarySearch(time, t);

            /**
             * !!!
             * Arrays.binarySearch return :
             * Index of the specified key if found within the specified range in the specified array,
             * Otherwise (-(insertion point) – 1).
             * The insertion point is defined as a point at which the specified key would be inserted:
             *    the index of the first element in the range greater than the key,
             *    or a.length if all elements in the range are less than the specified key.
             *
             * - insertion - 1 = i,  - insertion = i + 1, insertion = - i - 1,
             * we need is : insertion - 1 = - i - 2
             **/
            return i < 0 ? map.get(time[- i - 2]) : map.get(time[i]);
        }
    }
}
