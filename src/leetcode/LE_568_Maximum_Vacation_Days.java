package leetcode;

import java.util.Arrays;

public class LE_568_Maximum_Vacation_Days {
    /**
     * LeetCode wants to give one of its best employees the option to travel among N cities to
     * collect algorithm problems. But all work and no play makes Jack a dull boy, you could
     * take vacations in some particular cities and weeks. Your job is to schedule the traveling
     * to maximize the number of vacation days you could take, but there are certain rules and
     * restrictions you need to follow.
     *
     * Rules and restrictions:
     * 1.You can only travel among N cities, represented by indexes from 0 to N-1. Initially,
     *   you are in the city indexed 0 on Monday
     * 2.The cities are connected by flights. The flights are represented as a N*N matrix
     *   (not necessary symmetrical), called flights representing the airline status from the city
     *   i to the city j. If there is no flight from the city i to the city j, flights[i][j] = 0;
     *   Otherwise, flights[i][j] = 1. Also, flights[i][i] = 0 for all i.
     * 3.You totally have K weeks (each week has 7 days) to travel. You can only take flights at
     *   most once per day and can only take flights on each week's Monday morning. Since flight
     *   time is so short, we don't consider the impact of flight time
     * 4.For each city, you can only have restricted vacation days in different weeks, given an N*K
     *   matrix called days representing this relationship. For the value of days[i][j], it represents
     *   the maximum days you could take vacation in the city i in the week j.
     *
     *
     * Example 1:
     * Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[1,3,1],[6,0,3],[3,3,3]]
     * Output: 12
     * Explanation:
     * Ans = 6 + 3 + 3 = 12.
     *
     * One of the best strategies is:
     * 1st week : fly from city 0 to city 1 on Monday, and play 6 days and work 1 day.
     * (Although you start at city 0, we could also fly to and start at other cities since it is Monday.)
     * 2nd week : fly from city 1 to city 2 on Monday, and play 3 days and work 4 days.
     * 3rd week : stay at city 2, and play 3 days and work 4 days.
     * Example 2:
     * Input:flights = [[0,0,0],[0,0,0],[0,0,0]], days = [[1,1,1],[7,7,7],[7,7,7]]
     * Output: 3
     * Explanation:
     * Ans = 1 + 1 + 1 = 3.
     *
     * Since there is no flights enable you to move to another city, you have to stay at city 0 for the whole 3 weeks.
     * For each week, you only have one day to play and six days to work.
     * So the maximum number of vacation days is 3.
     * Example 3:
     * Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[7,0,0],[0,7,0],[0,0,7]]
     * Output: 21
     * Explanation:
     * Ans = 7 + 7 + 7 = 21
     *
     * One of the best strategies is:
     * 1st week : stay at city 0, and play 7 days.
     * 2nd week : fly from city 0 to city 1 on Monday, and play 7 days.
     * 3rd week : fly from city 1 to city 2 on Monday, and play 7 days.
     *
     * Note:
     * N and K are positive integers, which are in the range of [1, 100].
     * In the matrix flights, all the values are integers in the range of [0, 1].
     * In the matrix days, all the values are integers in the range [0, 7].
     * You could stay at a city beyond the number of vacation days, but you should work
     * on the extra days, which won't be counted as vacation days.
     * If you fly from the city A to the city B and take the vacation on that day, the deduction
     * towards vacation days will count towards the vacation days of city B in that week.
     * We don't consider the impact of flight hours towards the calculation of vacation days.
     *
     * Hard
     */

    /**
     * DFS (Brutal fource)
     * TLE
     *
     * Basically, try all possible travel routes and maintain the max number of vacation days in the process.
     *
     * Time complexity : O(n^k).  Depth of Recursion tree will be k and each node contains n branches in the worst case.
     *                   Here nn represents the number of cities and k is the total number of weeks.
     *
     * Space complexity : O(k). The depth of the recursion tree is k.
     */
    public class Solution_DFS {
        int max = 0, N = 0, K = 0;

        public int maxVacationDays(int[][] flights, int[][] days) {
            N = flights.length;
            K = days[0].length;
            dfs(flights, days, 0, 0, 0);

            return max;
        }

        private void dfs(int[][] f, int[][] d, int curr, int week, int sum) {
            if (week == K) {
                max = Math.max(max, sum);
                return;
            }

            for (int dest = 0; dest < N; dest++) {
                if (curr == dest || f[curr][dest] == 1) {
                    dfs(f, d, dest, week + 1, sum + d[dest][week]);
                }
            }
        }
    }

    /**
     * In the last approach, we make a number of redundant function calls, since the same function call
     * of the form dfs(flights, days, cur_city, weekno, sum) can be made multiple number of times with
     * the same cur and week. These redundant calls can be pruned off if we make use of memoization.
     *
     * In order to remove these redundant function calls, we make use of a 2-D memoization array mem.
     * In this array, mem[i][j] is used to store the number of vacation days that can be taken using the i
     * th city as the current city and the jth week as the starting week. This result is equivalent to that
     * obtained using the function call: dfs(flights, days, i, j). Thus, if the mem entry corresponding to
     * the current function call already contains a valid value, we can directly obtain the result from this
     * array instead of going deeper into recursion.
     *
     * Time complexity : O(n^2 * k). mem array of size n*k is filled and each cell filling takes O(n) time .
     *
     * Space complexity : O(n * k). mem array of size n*k is used. Here n
     *                    represents the number of cities and k is the total number of weeks.
     *
     */
    class Solution_DFS_WITH_MEM {
        int N = 0, K = 0;
        int[][] mem;

        public int maxVacationDays(int[][] flights, int[][] days) {
            N = flights.length;
            K = days[0].length;

            mem = new int[N][K];
            for (int[] m : mem) {
                Arrays.fill(m, Integer.MIN_VALUE);
            }

            return dfs(flights, days, 0, 0);
        }

        public int dfs(int[][] f, int[][] d, int cur, int week) {
            if (week == K) {
                return 0;
            }

            if (mem[cur][week] != Integer.MIN_VALUE) {
                return mem[cur][week];
            }

            int max = 0;
            for (int dest = 0; dest < N; dest++) {
                if (f[cur][dest] == 1 || dest == cur) {
                    int temp = d[dest][week] + dfs(f, d, dest, week + 1);
                    max = Math.max(max, temp);
                }
            }

            mem[cur][week] = max;

            return max;
        }
    }
}
