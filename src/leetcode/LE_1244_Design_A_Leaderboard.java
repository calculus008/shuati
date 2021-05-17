package leetcode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LE_1244_Design_A_Leaderboard {
    /**
     * Design a Leaderboard class, which has 3 functions:
     *
     * addScore(playerId, score): Update the leaderboard by adding score to the given player's score. If there is no
     * player with such id in the leaderboard, add him to the leaderboard with the given score.
     *
     * top(K): Return the score sum of the top K players.
     *
     * reset(playerId): Reset the score of the player with the given id to 0 (in other words erase it from the
     * leaderboard). It is guaranteed that the player was added to the leaderboard before calling this function.
     *
     * Initially, the leaderboard is empty.
     *
     * Example 1:
     * Input:
     * ["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
     * [[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]
     * Output:
     * [null,null,null,null,null,null,73,null,null,null,141]
     *
     * Explanation:
     * Leaderboard leaderboard = new Leaderboard ();
     * leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
     * leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
     * leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
     * leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
     * leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
     * leaderboard.top(1);           // returns 73;
     * leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
     * leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
     * leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
     * leaderboard.top(3);           // returns 141 = 51 + 51 + 39;
     *
     *
     * Constraints:
     * 1 <= playerId, K <= 10000
     * It's guaranteed that K is less than or equal to the current number of players.
     * 1 <= score <= 100
     * There will be at most 1000 function calls.
     *
     * Medium
     */

    /**
     * Keeping id and score mapping => HashMap
     * Top K calculation => need a way to save scores in sorted order, we also need to save how many players have a given score,
     * therefore => TreeMap, key is score, value is count of the players that have the same score. Here we don't care about
     * the ids of the players, only the count of number of the players.
     */
    class Leaderboard_Mine {
        Map<Integer, Integer> ids;
        TreeMap<Integer, Integer> scores;

        public Leaderboard_Mine() {
            ids = new HashMap<>();
            scores = new TreeMap<>(Collections.reverseOrder());//!!!Collections.reverseOrder()
        }

        public void addScore(int playerId, int score) {
            if (!ids.containsKey(playerId)) {
                ids.put(playerId, score);
                scores.put(score, scores.getOrDefault(score, 0) + 1);
            } else {
                int cur = ids.get(playerId);

                /**
                 * it's "add score", not update!!!
                 */
                int newScore = cur + score;
                ids.put(playerId, newScore);
                adjustCount(cur);
                scores.put(newScore, scores.getOrDefault(newScore, 0) + 1);
            }
        }

        public int top(int K) {
            int ans = 0;
            for (Map.Entry<Integer, Integer> e : scores.entrySet()) {
                int score = e.getKey();
                int cnt = e.getValue();
                /**
                 * nice trick to use Math.min()
                 */
                int n = Math.min(cnt, K);

                ans += n * score;
                K -= n;

                if (K == 0) break;
            }

            return ans;
        }

         //first version for top()
//        public int top(int K) {
//            int sum = 0;
//            int count = 0;
//
//            for (int key : scores.keySet()) {
//                int num = scores.get(key);
//
//                for (int i = 1; i <= num; i++) {
//                    sum += key;
//                    count++;
//                    if (count == K) break;
//                }
//
//                if (count == K) break;
//            }
//
//            return sum;
//        }

        public void reset(int playerId) {
            int cur = ids.get(playerId);
            adjustCount(cur);
            ids.remove(playerId);
        }

        /**
         * Given a scores n, adjust value in scores, if updated value is 0, remove n from scores.
         */
        private void adjustCount(int n) {
            int count = scores.get(n);
            scores.put(n, count - 1);
            /**
             * From JDK doc:
             * HashMap.remove(Object key, Object value)
             * Removes the entry for the specified key only if it is currently mapped to the specified value.
             */
            scores.remove(n, 0);
        }
    }
}
