package leetcode;

import java.util.*;

public class LE_1366_Rank_Teams_By_Votes {
    /**
     * In a special ranking system, each voter gives a rank from highest to lowest to all teams participated in the competition.
     *
     * The ordering of teams is decided by who received the most position-one votes. If two or more teams tie in the first position,
     * we consider the second position to resolve the conflict, if they tie again, we continue this process until the ties are resolved.
     * If two or more teams are still tied after considering all positions, we rank them alphabetically based on their team letter.
     *
     * Given an array of strings votes which is the votes of all voters in the ranking systems. Sort all teams according to the ranking
     * system described above.
     *
     * Return a string of all teams sorted by the ranking system.
     *
     * Example 1:
     * Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
     * Output: "ACB"
     * Explanation: Team A was ranked first place by 5 voters. No other team was voted as first place so team A is the first team.
     * Team B was ranked second by 2 voters and was ranked third by 3 voters.
     * Team C was ranked second by 3 voters and was ranked third by 2 voters.
     * As most of the voters ranked C second, team C is the second team and team B is the third.
     *
     * Example 2:
     * Input: votes = ["WXYZ","XYZW"]
     * Output: "XWYZ"
     * Explanation: X is the winner due to tie-breaking rule. X has same votes as W for the first position but X has one vote
     * as second position while W doesn't have any votes as second position.
     *
     * Example 3:
     * Input: votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
     * Output: "ZMNAGUEDSJYLBOPHRQICWFXTVK"
     * Explanation: Only one voter so his votes are used for the ranking.
     *
     * Constraints:
     * 1 <= votes.length <= 1000
     * 1 <= votes[i].length <= 26
     * votes[i].length == votes[j].length for 0 <= i, j < votes.length.
     * votes[i][j] is an English uppercase letter.
     * All characters of votes[i] are unique.
     * All the characters that occur in votes[0] also occur in votes[j] where 1 <= j < votes.length.
     *
     * Medium
     *
     * https://leetcode.com/problems/rank-teams-by-votes/
     */

    /**
     * Sorting with PriorityQueue + HashMap
     */
    class Solution1 {
        public String rankTeams(String[] votes) {
            int n = votes[0].length();
            Map<Character, int[]> map = new HashMap<>();
            StringBuilder sb = new StringBuilder();

            for (String v : votes) {
                char[] chs = v.toCharArray();

                for (int i = 0; i < n; i++) {
                    char c = chs[i];
                    if (!map.containsKey(c)) {
                        map.put(c, new int[n]);
                    }

                    int[] count = map.get(c);
                    count[i]++;
                }
            }

            PriorityQueue<Map.Entry<Character, int[]>> pq = new PriorityQueue<>((a, b) -> {
                int[] count1 = a.getValue();
                int[] count2 = b.getValue();

                for (int i = 0; i < count1.length; i++) {
                    if (Integer.compare(count2[i], count1[i]) != 0) {
                        return Integer.compare(count2[i], count1[i]);
                    }
                }

                return Character.compare(a.getKey(), b.getKey());
            });

            for (Map.Entry<Character, int[]> entry : map.entrySet()) {
                pq.offer(entry);
            }

            while (!pq.isEmpty()) {
                sb.append(pq.poll().getKey());
            }

            return sb.toString();
        }
    }

    /**
     * Simplified version of Solution1
     */
    class Solution2 {
        public String rankTeams(String[] votes) {
            int n = votes[0].length();
            Map<Character, int[]> map = new HashMap<>();
            StringBuilder sb = new StringBuilder();

            for (String v : votes) {
                for (int i = 0; i < n; i++) {
                    char c = v.charAt(i);
                    map.putIfAbsent(c, new int[n]);
                    map.get(c)[i]++;
                }
            }

            PriorityQueue<Character> pq = new PriorityQueue<>((a, b) -> {
                int[] c1 = map.get(a);
                int[] c2 = map.get(b);

                for (int i = 0; i < c1.length; i++) {
                    if (Integer.compare(c2[i], c1[i]) != 0) {
                        return Integer.compare(c2[i], c1[i]);
                    }
                }

                return Character.compare(a, b);
            });

            for (Character c : map.keySet()) {
                pq.offer(c);
            }

            while (!pq.isEmpty()) {
                sb.append(pq.poll());
            }

            return sb.toString();
        }
    }
    /**
     * Use Collections.sort
     *
     * The elements that the sort is based on does not have to be in the list.
     */
    class Solution3 {
        public String rankTeams(String[] votes) {
            Map<Character, int[]> map = new HashMap<>();
            int n = votes[0].length();
            for (String vote : votes) {
                for (int i = 0; i < n; i++) {
                    char c = vote.charAt(i);
                    map.putIfAbsent(c, new int[n]);
                    map.get(c)[i]++;
                }
            }

            List<Character> list = new ArrayList<>(map.keySet());
            /**
             * !!!
             */
            Collections.sort(list, (a, b) -> {
                for (int i = 0; i < n; i++) {
                    if (map.get(a)[i] != map.get(b)[i]) {
                        return map.get(b)[i] - map.get(a)[i];
                    }
                }
                return a - b;
            });

            StringBuilder sb = new StringBuilder();
            for (char c : list) {
                sb.append(c);
            }
            return sb.toString();
        }
    }

}
