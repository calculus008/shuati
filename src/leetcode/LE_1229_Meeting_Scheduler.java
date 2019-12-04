package leetcode;

import java.util.*;

public class LE_1229_Meeting_Scheduler {
    /**
     * Given the availability time slots arrays slots1 and slots2 of two people
     * and a meeting duration duration, return the earliest time slot that works
     * for both of them and is of duration duration.
     *
     * If there is no common time slot that satisfies the requirements, return
     * an empty array.
     *
     * The format of a time slot is an array of two elements [start, end]
     * representing an inclusive time range from start to end.
     *
     * It is guaranteed that no two availability slots of the same person intersect
     * with each other. That is, for any two time slots [start1, end1] and [start2, end2]
     * of the same person, either start1 > end2 or start2 > end1.
     *
     * Example 1:
     * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 8
     * Output: [60,68]
     *
     * Example 2:
     * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 12
     * Output: []
     *
     * Constraints:
     *
     * 1 <= slots1.length, slots2.length <= 10^4
     * slots1[i].length, slots2[i].length == 2
     * slots1[i][0] < slots1[i][1]
     * slots2[i][0] < slots2[i][1]
     * 0 <= slots1[i][j], slots2[i][j] <= 10^9
     * 1 <= duration <= 10^6
     *
     * Medium
     */

    /**
     * Swipe Line, similar to LE_253_Meeting_Rooms_II,
     * Time : O(nlogn), n = l1 + l2
     */
    class Solution1 {
        class Pair {
            int x;
            int type;

            public Pair(int x, int type) {
                this.x = x;
                this.type = type;
            }
        }

        public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
            List<Integer> res = new ArrayList<>();
            if (slots1 == null || slots2 == null || slots1.length == 0 || slots2.length == 0) return res;

            List<Pair> list = new ArrayList<>();

            for (int[] slot1 : slots1) {
                /**
                 * !!!
                 * Important optimization : only consider those intervals that are longer than input duration
                 */
                if (slot1[1] - slot1[0] >= duration) {
                    list.add(new Pair(slot1[0], 1));
                    list.add(new Pair(slot1[1], 0));
                }
            }

            for (int[] slot2 : slots2) {
                if (slot2[1] - slot2[0] >= duration) {
                    list.add(new Pair(slot2[0], 1));
                    list.add(new Pair(slot2[1], 0));
                }
            }

            Collections.sort(list, (a, b) -> a.x != b.x ? a.x - b.x : a.type - b.type);

            int count = 0;
            int overlapstart = -1;
            for (Pair p : list) {
                if (p.type == 1) {
                    count++;
                    if (count == 2) {
                        overlapstart = p.x;
                    }
                } else {
                    count--;
                    if (count == 1) {
                        if (p.x - overlapstart >= duration) {
                            res.add(overlapstart);
                            /**
                             * !!!
                             * Tne answer is [overlapstart, overlapstart + duration],
                             * NOT [overlapstart, p.x]
                             */
                            res.add(overlapstart + duration);
                            break;
                        }
                    }
                }
            }

            return res;
        }
    }

    /**
     * PriorityQueue solution
     */
    class Solution2 {
        public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
            /**
             * pq, sort by starting time
             */
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparing(a -> a[0]));
            for (int[] s : slots1) {
                if (s[1] - s[0] >= duration) {
                    pq.offer(s);
                }
            }

            for (int[] s : slots2) {
                if (s[1] - s[0] >= duration) {
                    pq.offer(s);
                }
            }

            while (pq.size() > 1) {
                /**
                 * if "pq.poll()[1] >= pq.peek()[0] + duration" is true,
                 * then current interval and peeked interval must be from two persons.
                 */
                if (pq.poll()[1] >= pq.peek()[0] + duration) {
                    return Arrays.asList(pq.peek()[0], pq.peek()[0] + duration);
                }
            }

            return Arrays.asList();
        }
    }
}
