package leetcode;

public class LE_849_Maximize_Distance_To_Closest_Person {
    /**
     * In a row of seats, 1 represents a person sitting in that seat,
     * and 0 represents that the seat is empty.
     *
     * There is at least one empty seat, and at least one person sitting.
     *
     * Alex wants to sit in the seat such that the distance between him
     * and the closest person to him is maximized.
     *
     * Return that maximum distance to closest person.
     *
     * Example 1:
     *
     * Input: [1,0,0,0,1,0,1]
     * Output: 2
     * Explanation:
     * If Alex sits in the second open seat (seats[2]), then the closest
     * person has distance 2.
     * If Alex sits in any other open seat, the closest person has distance 1.
     * Thus, the maximum distance to the closest person is 2.
     * Example 2:
     *
     * Input: [1,0,0,0]
     * Output: 3
     * Explanation:
     * If Alex sits in the last seat, the closest person is 3 seats away.
     * This is the maximum distance possible, so the answer is 3.
     * Note:
     *
     * 1 <= seats.length <= 20000
     * seats contains only 0s or 1s, at least one 0, and at least one 1.
     *
     * Easy
     */

    /**
     * Same solution as LE_821_Shortest_Distance_To_A_Character
     *
     * Two passes, left->right, then right->left, get min, then get max in dist[]
     */
    class Solution1 {
        public int maxDistToClosest(int[] seats) {
            int n = seats.length;
            int[] dist = new int[n];

            int pos = -n;

            for (int i = 0; i < n; i++) {
                if (seats[i] == 0) {
                    dist[i] = i - pos;
                } else {
                    pos = i;
                }
            }

            pos = -n;
            for (int i = n - 1; i >= 0; i--) {
                if (seats[i] == 0) {
                    dist[i] = Math.min(dist[i], Math.abs(i - pos));
                } else {
                    pos = i;
                }
            }

            int res = 0;
            for (int num : dist) {
                res = Math.max(num, res);
            }

            return res;
        }
    }

    /**
     * One pass solution
     * https://leetcode.com/problems/maximize-distance-to-closest-person/solution/
     *
     * Keep track of prev, the filled seat at or to the left of i, and future,
     * the filled seat at or to the right of i.
     *
     * Then at seat i, the closest person is min(i - prev, future - i), with one
     * exception. i - prev should be considered infinite if there is no person to
     * the left of seat i, and similarly future - i is infinite if there is no one
     * to the right of seat i.
     */
    class Solution2 {
        public int maxDistToClosest(int[] seats) {
            int N = seats.length;
            int prev = -1, future = 0;
            int ans = 0;

            for (int i = 0; i < N; ++i) {
                if (seats[i] == 1) {
                    prev = i;
                } else {
                    while (future < N && seats[future] == 0 || future < i)
                        future++;

                    int left = prev == -1 ? N : i - prev;
                    int right = future == N ? N : future - i;
                    ans = Math.max(ans, Math.min(left, right));
                }
            }

            return ans;
        }
    }
}
