package leetcode;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class LE_846_Hand_Of_Straights {
    /**
     * Alice has a hand of cards, given as an array of integers.
     *
     * Now she wants to rearrange the cards into groups so that
     * each group is size W, and consists of W consecutive cards.
     *
     * Return true if and only if she can.
     *
     * Example 1:
     *
     * Input: hand = [1,2,3,6,2,3,4,7,8], W = 3
     * Output: true
     * Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8].
     * Example 2:
     *
     * Input: hand = [1,2,3,4,5], W = 4
     * Output: false
     * Explanation: Alice's hand can't be rearranged into groups of 4.
     *
     *
     * Note:
     *
     * 1 <= hand.length <= 10000
     * 0 <= hand[i] <= 10^9
     * 1 <= W <= hand.length
     *
     * Medium
     */

    /**
     * Best Solution
     *
     * https://leetcode.com/problems/hand-of-straights/solution/
     *
     * We will repeatedly try to form a group (of size W) starting with the lowest card.
     * This works because the lowest card still in our hand must be the bottom end of
     * a size W straight.
     *
     * Let's keep a count {card: number of copies of card} as a TreeMap (or dict).
     *
     * Then, repeatedly we will do the following steps: find the lowest value card
     * that has 1 or more copies (say with value x), and try to remove x, x+1, x+2, ..., x+W-1
     * from our count. If we can't, then the task is impossible.
     *
     * Example:
     * Input: hand = [1,2,3,6,2,3,4,7,8], W = 3
     *
     * After TreepMap sort :
     * [1,2,2,3,3,4,6,7,8]
     *
     * count:
     * 1 -> 1
     * 2 -> 2
     * 3 -> 2
     * 4 -> 1
     * 6 -> 1
     * 7 -> 1
     * 8 -> 1
     *
     * First key = 1, first group [1, 2, 3]:
     * 2 -> 1
     * 3 -> 1
     * 4 -> 1
     * 6 -> 1
     * 7 -> 1
     * 8 -> 1
     *
     * First key = 2, 2nd group [2,3,4]
     * 6 -> 1
     * 7 -> 1
     * 8 -> 1
     *
     * First key = 6, 3rd group [6,7,8]
     */
    class Solution1 {
        public boolean isNStraightHand(int[] hand, int W) {
            TreeMap<Integer, Integer> count = new TreeMap();
            for (int card: hand) {
                count.put(card, count.getOrDefault(card, 0) + 1);
            }

            while (count.size() > 0) {
                int first = count.firstKey();

                /**
                 * 连续数字的条件是包含在循环的条件里的:
                 *
                 * "card < first + W" -> first, first + 1, first + 2,..., first + W
                 */
                for (int i = first; i < first + W; ++i) {
                    if (!count.containsKey(i)) return false;

                    int c = count.get(i);
                    if (c == 1) {
                        count.remove(i);
                    } else {
                        count.put(i, c - 1);
                    }
                }
            }

            return true;
        }
    }

    /**
     * https://leetcode.com/problems/hand-of-straights/discuss/135598/C%2B%2BJavaPython-O(MlogM)-Complexity
     *
     * Very confusing, Solution1 is much easier to understand
     *
     * Time  : O(NlogN)
     * Space : O(N)
     */
    class Solution2 {
        public boolean isNStraightHand(int[] hand, int W) {
            /**
             * !!!
             * Use TreeMap, basically sort the hand[] and record number of the cards
             * of each val.
             */
            Map<Integer, Integer> freq = new TreeMap<>();
            for (int n : hand) {
                freq.put(n, freq.getOrDefault(n, 0) + 1);
            }

            /**
             * For each key, how many new groups are opened
             */
            Queue<Integer> delta = new LinkedList<>();
            int opened = 0;
            int pre = -1;

            for (int key : freq.keySet()) {
                int val = freq.get(key);

                /**
                 * "key != pre + 1" : not consecutive
                 * "val < opened" : number of current val can't fill all opened groups
                 */
                if (opened > 0 && (key != pre + 1 || val < opened)) return false;

                /**
                 * record number of newly opened group
                 */
                delta.add(val - opened);
                /**
                 * if we come here, opened <= val
                 */
                opened = val;
                pre = key;

                if (delta.size() == W) {
                    opened -= delta.remove();
                }

            }

            return opened == 0;
        }
    }
}
