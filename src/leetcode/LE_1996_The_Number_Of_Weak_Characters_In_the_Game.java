package leetcode;

import java.util.*;

public class LE_1996_The_Number_Of_Weak_Characters_In_the_Game {
    /**
     * You are playing a game that contains multiple characters, and each of the characters has two main properties:
     * attack and defense. You are given a 2D integer array properties where properties[i] = [attack(i), defense(i)] represents
     * the properties of the ith character in the game.
     *
     * A character is said to be weak if any other character has both attack and defense levels strictly greater than this
     * character's attack and defense levels. More formally, a character i is said to be weak if there exists another character
     * j where attackj > attacki and defensej > defensei.
     *
     * Return the number of weak characters.
     *
     * Example 1:
     * Input: properties = [[5,5],[6,3],[3,6]]
     * Output: 0
     * Explanation: No character has strictly greater attack and defense than the other.
     *
     * Example 2:
     * Input: properties = [[2,2],[3,3]]
     * Output: 1
     * Explanation: The first character is weak because the second character has a strictly greater attack and defense.
     *
     * Example 3:
     * Input: properties = [[1,5],[10,4],[4,3]]
     * Output: 1
     * Explanation: The third character is weak because the second character has a strictly greater attack and defense.
     *
     * Constraints:
     * 2 <= properties.length <= 10 ^ 5
     * properties[i].length == 2
     * 1 <= attacki, defensei <= 10 ^ 5
     *
     * Medium
     *
     * https://leetcode.com/problems/the-number-of-weak-characters-in-the-game/
     */

    /**
     * Sorting + Mono Stack
     *
     * 根据题意，要比较大小，很清楚，我们需要要用某种sorting。每个单元有两个元素，很自然，我们想到，既然不能同时sort两个元素，那我们就
     * 先搞定一个 - 先把以p[i][0](attach[i])的大小sort，然后再来处理p[0][1](defence[i])。比如：
     *
     * 1.Sort
     * After sort:
     *   idx      0  1  2  3  4  5
     * attack  : [1, 2, 3, 4, 5, 6]  already sorted
     * defence : [5, 4, 3, 7, 2, 1]  not sorted
     *
     * 2.Now we know p[i][0] is none-decreasing. So the problem turns into:
     * 给定一个乱序的数组，找到符合以下条件的元素个数：在该元素后面，存在着比这个元素大的元素。
     *
     * 马上联想到了 LE_739_Daily_Temperatures (or RunningTemperature), 都是在一个乱序数组找大小相关的元素或元素个数，所以，可以用
     * Mono Stack. So is it mono increasing stack or mono decreasing stack? Since we look for count of elements that is
     * smaller than elements that come after, we can count it this way : compare top element in the stack, if current number
     * is bigger than the top element, we pop the top element and increase count by one. Therefore, we have a mono-decreasing
     * stack. Use example above:
     *
     * [5, 4, 3, 7, 2, 1]
     *
     * Stack:
     * [5]         count = 0
     * [5, 4]      count = 0
     * [5, 4, 3]   count = 0
     *
     * Now we comes to "7", keep popping out element that is smaller than 7, increase count, then push "7" to stack:
     * [7]        count = 3
     * [7, 2]     count = 3
     * [7, 2, 1]  count = 3
     *
     * So the final answer is "3".
     *
     * 3.Now we have to deal the trick part of the question - how to deal with the case that the values of the first element
     *   are the same? For example:
     *
     *   idx      0  1  2  3  4  5
     * attack  : [1, 2, 3, 3, 3, 6]
     * defense : [5, 4, 3, 7, 2, 1]
     *                  *  *
     * So p[2] = {3, 3}, p[3] = {3, 7}, we should not count p[2] since its first element is the same as the first element of p[3],
     * which, based on definition of "weak"("both attack and defense levels strictly greater than.."), is not qualified as a valid
     * one.
     *
     * A brilliant trick : when doing sorting in step 1, add one more logic, when first elements are equal, put the one which less value in
     * the 2nd element in front, so we will have:
     *
     *    idx     0  1  2  3  4  5
     * attack  : [1, 2, 3, 3, 3, 6]
     * defense : [5, 4, 7, 3, 2, 1]
     *
     * Then we do mono-stack in step 2, "7" will come before "3", then we will have count value as "2" after popping out
     * "5" and "4", then push in "7". Next, "3" is smaller than "7", therefore we eliminate "3" in the popping and counting
     * action.
     *
     * It has to come to mind - the sorting logic should not be so simple by comparing the first element, the 2nd element
     * has to do something...
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution1 {
        public int numberOfWeakCharacters(int[][] properties) {
            Arrays.sort(properties, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(b[1] , a[1]));

            int res = 0;
            Deque<Integer> stack = new ArrayDeque();

            for (int[] p : properties) {
                if (stack.isEmpty() || stack.peek() >= p[1]) {
                    stack.push(p[1]);
                } else {
                    while (!stack.isEmpty() && stack.peek() < p[1]) {
                        stack.pop();
                        res++;
                    }
                    stack.push(p[1]);
                }
            }

            return res;
        }
    }

    /**
     * Sorting + max mark
     *
     * Unlike LE_739_Daily_Temperatures, we don't need to find element positions for calculating distance, we only find
     * if there exists a bigger element, therefore, it is a simple issue of keeping a current max value.
     *
     * Without using stack, only iterate from end to start, keep a max mark, compare and update. Faster than Solution1
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution2 {
        public int numberOfWeakCharacters(int[][] properties) {
            Arrays.sort(properties, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(b[1] , a[1]));

            int res = 0;
            int max = Integer.MIN_VALUE;

            int n = properties.length;

            for (int i = n - 1; i >= 0; i--) {
                if (properties[i][1] < max) {
                    res++;
                } else {
                    max = properties[i][1];
                }
            }

            return res;
        }
    }

    /**
     * O(n), without sorting, kind of bucket algorithm
     *
     * https://leetcode.com/problems/the-number-of-weak-characters-in-the-game/discuss/1452696/O(n)
     *
     * We can track maximum defence(p[1]) for each attack(p[0]) value in the array. To do this efficiently,
     * we first assign the best defence for each attack value, and then sweep from highest to lowest attack,
     * tracking and assigning the maximum defence.
     *
     * attack  : [0, 1, 1, 1, 2, 2, 2]
     * defence : [5, 3, 6, 1, 4, 7, 3]
     *
     *  idx     0, 1, 2
     * maxH :  [5, 6, 7]
     *
     *  idx     0, 1, 2, 3, 4
     * maxH :  [7, 7, 7, 0, 0]
     *
     * [0, 5] : 5 < maxH[0 + 1], res++
     * [1, 3] : 3 < maxH[1 + 1], res++
     * ....
     * [2, 4] : 4 > maxH[2 + 1], res is not creased.
     *
     * 1.Use maxH array, we put the same attack values "i" in the same bucket (maxH[i]), therefore we don't need to worry
     *   about the same attack value case.
     * 2.Since i is increasing, it has equivalent effect of sorting by attack value. But we do it in O(n) instead of O(nlogn).
     *
     * Time : O(n)
     * Space : O(1)
     */
    class Solution3 {
        public int numberOfWeakCharacters(int[][] properties) {
            /**
             * "1 <= attacki, defensei <= 10 ^ 5"
             */
            int[] maxH = new int[100002];
            int res = 0;

            /**
             * 对应于每一个attack的值，找到最大的defence值。
             */
            for (int[] p : properties) {
                maxH[p[0]] = Math.max(p[1], maxH[p[0]]);
            }

            /**
             * For an attack value i, what is the max defense value from i to the max attack value.
             */
            for (int i = 100000; i >= 0; i--) {
                maxH[i] = Math.max(maxH[i + 1], maxH[i]);
            }

            for (int[] p : properties) {
                /**
                 * Is current defense value is smaller than defense values of elements that have bigger attack value?
                 */
                if (p[1] < maxH[p[0] + 1]) {
                    res++;
                }
            }

            return res;
        }
    }
}
