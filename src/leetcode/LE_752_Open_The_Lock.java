package leetcode;

import java.util.*;

public class LE_752_Open_The_Lock {
    /**
         You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots:
         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'.

         The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'.
         Each move consists of turning one wheel one slot.

         The lock initially starts at '0000', a string representing the state of the 4 wheels.

         You are given a list of dead ends, meaning if the lock displays any of these codes,
         the wheels of the lock will stop turning and you will be unable to open it.

         Given a target representing the value of the wheels that will unlock the lock, return the
         minimum total number of turns required to open the lock, or -1 if it is impossible.

         Example 1:
         Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
         Output: 6
         Explanation:
         A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
         Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
         because the wheels of the lock become stuck after the display becomes the dead end "0102".

         Example 2:
         Input: deadends = ["8888"], target = "0009"
         Output: 1
         Explanation:
         We can turn the last wheel in reverse to move from "0000" -> "0009".

         Example 3:
         Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
         Output: -1
         Explanation:
         We can't reach the target without getting stuck.

         Example 4:
         Input: deadends = ["0000"], target = "8888"
         Output: -1

         Note:
         The length of deadends will be in the range [1, 500].
         target will not be in the list deadends.
         Every string in deadends and the string target will be a string of 4 digits from the 10,000 possibilities '0000' to '9999'.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/leetcode-752-open-the-lock/
     *
     * Level by level expansion using BFS
     *
     * 关键 ：
     * 1.Each node can have at most 8 neighbors (max branching factor is 8) :
     *   Each node has 4 digits, each digit can be changed to 2 other values, +1, -1.
     *   So total changes re 2 * 4 = 8
     *   This is done by 双重循环，外层loop on each of the 4 digit, 内层loop on 2 possible new digits current digit can
     *   be changed to.
     *
     * 2.Bypass two kinds of String - visited and dead end, we can use one set for them.
     *
     * 3.Tricky part is how to calculate the next digit, mainly, deal with 2 cases : 9 + 1 -> 0, 0 - 1 -> 9
     *
     * Number of max states : "0000" - "9999", 10000
     *
     * Time  : O(8 ^ 10000)
     * Space : O(10000 + deadend set size)
     */
    class Solution1 {
        public int openLock(String[] deadends, String target) {
            if (null == target || target.length() == 0) {
                return 0;
            }

            String start = "0000";
            if (target.equals(start)) {
                return 0;
            }

            Set<String> set = new HashSet<>();
            for (String deadend : deadends) {
                if (target.equals(deadend) || start.equals(deadend)) {
                    return -1;
                }
                set.add(deadend);
            }

            /**
             * or put visited into set (deadend set), they can share the same set.
             */
            Set<String> visited = new HashSet<>();
            visited.add(start);

            Queue<String> q = new LinkedList<>();
            q.offer(start);

            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();
                steps++;

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();

                    for (int j = 0; j < 4; j++) {
                        /**
                         * 坑1：
                         * 从当前数字走一步(转动保险柜数字开关)到下一个数字，只有两种可能：
                         * 当前数字加一，当前数字减一。
                         * 比如，3 -> 3 - 1 = 2, 3 + 1 = 4
                         * 不是从当前数字能到所有其他9个数字。
                         *
                         * So from each digit, branching factor is 2.
                         */
                        for (int k = -1; k <= 1; k += 2) {
                            /**
                             * 坑2：
                             * 还是每次变换都做一次toCharArray(),否则变换后恢复的操作不小心会出问题。
                             */
                            char[] chars = cur.toCharArray();

                            /**
                             * !!!
                             * 坑3：
                             * 模拟开关向上和向下旋转一位。
                             *
                             * 9 + 1 = 10 -> map to 0
                             * 0 - 1 = -1 -> map to 9 : "+ 10" ： 0 - 1 = -1, 所以， 0 - 1 + 10 = 9
                             *
                             * ((chars[i] - '0') + 10) % 10 + '0'
                             *
                             * '0' is offset
                             *
                             */
                            chars[j] = (char)((chars[j] - '0' + k + 10) % 10 + '0');

                            String next = String.valueOf(chars);

                            if (next.equals(target)) {
                                return steps;
                            }

                            if (set.contains(next) || visited.contains(next)) {
                                continue;
                            }

                            q.offer(next);
                            visited.add(next);
                        }
                    }
                }
            }

            return -1;
        }
    }

    /**
     * use one set
     */
    public class Solution1_Practice {
        public int openLock(String[] deadends, String target) {
            if (target == null || target.length() != 4) return -1;

            String start = "0000";
            if (target.equals(start)) return 0;

            Set<String> set = new HashSet<String>(Arrays.asList(deadends));

            /**
             * !!!
             * "set.contains(start)"
             */
            if (set.contains(target) || set.contains(start)) {
                return -1;
            }

            Queue<String> q = new LinkedList<>();

            q.offer(start);
            /**
             * !!!
             */
            set.add(start);

            int steps = 0;

            while (!q.isEmpty()) {
                steps++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();

                    for (int j = 0; j < 4; j++) {
                        for (int k = -1; k <= 1; k += 2) {
                            char[] chars = cur.toCharArray();
                            chars[j] = (char)(((chars[j] - '0' + k) + 10) % 10 + '0');
                            String next = new String(chars);

                            if (next.equals(target)) {
                                return steps;
                            }

                            if (!set.contains(next)) {
                                q.offer(next);
                                set.add(next);
                            }
                        }
                    }
                }
            }

            return -1;
        }
    }

    class Solution2 {
        public int openLock(String[] deadends, String target) {
            String start = "0000";

            Set<String> deadSet = new HashSet<>();
            for (String str : deadends) {
                if (str.equals(start)) {
                    return -1;
                }
                deadSet.add(str);
            }

            int result = 0;
            Queue<String> queue = new LinkedList<>();
            Set<String> set = new HashSet<>();

            queue.offer(start);
            set.add(start);

            while (!queue.isEmpty()) {
                int n = queue.size();

                while (n-- != 0) {
                    String cur = queue.poll();
                    if (cur.equals(target)) {
                        return result;
                    }

                    for (int i = 0; i < 4; ++i) {
                        String next = cur.substring(0, i) + (char)((cur.charAt(i) - '0' + 9) % 10 + '0') + cur.substring(i + 1);
                        if (!set.contains(next) && !deadSet.contains(next)) {
                            queue.offer(next);
                            set.add(next);
                        }

                        next = cur.substring(0, i) + (char)((cur.charAt(i) - '0' + 1) % 10 + '0') + cur.substring(i + 1);
                        if (!set.contains(next) && !deadSet.contains(next)) {
                            queue.offer(next);
                            set.add(next);
                        }
                    }
                }

                result++;
            }

            return -1;
        }
    }
}