package leetcode;

import java.util.*;

public class LE_710_Random_Pick_With_Blacklist {
    /**
     * You are given an integer n and an array of unique integers blacklist. Design an algorithm to pick a random integer
     * in the range [0, n - 1] that is not in blacklist. Any integer that is in the mentioned range and not in blacklist
     * should be equally likely to be returned.
     *
     * Optimize your algorithm such that it minimizes the number of calls to the built-in random function of your language.
     *
     * Implement the Solution class:
     * Solution(int n, int[] blacklist) Initializes the object with the integer n and the blacklisted integers blacklist.
     * int pick() Returns a random integer in the range [0, n - 1] and not in blacklist.
     *
     * Example 1:
     * Input
     * ["Solution", "pick", "pick", "pick", "pick", "pick", "pick", "pick"]
     * [[7, [2, 3, 5]], [], [], [], [], [], [], []]
     * Output
     * [null, 0, 4, 1, 6, 1, 0, 4]
     *
     * Explanation
     * Solution solution = new Solution(7, [2, 3, 5]);
     * solution.pick(); // return 0, any integer from [0,1,4,6] should be ok. Note that for every call of pick,
     *                  // 0, 1, 4, and 6 must be equally likely to be returned (i.e., with probability 1/4).
     * solution.pick(); // return 4
     * solution.pick(); // return 1
     * solution.pick(); // return 6
     * solution.pick(); // return 1
     * solution.pick(); // return 0
     * solution.pick(); // return 4
     *
     *
     * Constraints:
     * 1 <= n <= 10 ^ 9
     * 0 <= blacklist.length <- min(10 ^ 5, n - 1)
     * 0 <= blacklist[i] < n
     * All the values of blacklist are unique.
     * At most 2 * 10 ^ 4 calls will be made to pick.
     *
     * Hard
     *
     * https://leetcode.com/problems/random-pick-with-blacklist/
     */

    /**
     * HashMap serves as a Virtual List
     *
     * if m = blacklist.length, then the total number of valid numbers : size = n - m. We use a HashMap to map numbers that are
     * smaller than size to the valid numbers (not in blacklist) that are bigger than size.
     *
     * Example:
     * N=10, blacklist=[3, 5, 8, 9], re-map 3 and 5 to 7 and 6.
     * size = 10 - 4 = 6
     *           ||
     *       X   X     X X
     * 0 1 2 3 4 5 6 7 8 9
     *       |   |_| |
     *       |_______|
     *
     * So we get a random within range [0, 6), if the number is 3, it is mapped to 7, we return 7, if it is 5, it is mapped to 6
     * we return 6.
     *
     * Time and Space : O(B), B is the length of blacklist
     *
     * !!!
     * With given conditions:
     * 1 <= n <= 10 ^ 9
     * 0 <= blacklist.length <- min(10 ^ 5, n - 1)
     *
     * It suggests that the length of blacklist is much smaller than n. So make the time complexity as O(B) makes it run much
     * faster than naive solution, which takes O(n).
     */
    class Solution {
        Random random;
        Map<Integer, Integer> map;
        int size;

        public Solution(int n, int[] blacklist) {
            random = new Random();
            map = new HashMap<>();

            /**
             * After this loop, map will serve as a set to tell if a number is in blacklist
             */
            for (int b : blacklist) {//O(n)
                map.put(b, -1);
            }

            size = n - blacklist.length;

            int max = n - 1;
            for (int b : blacklist) {//O(n)
                if (b < size) {
                    /**
                     * If a number is in map, it means it is not a valid number (in blacklist), we need to skip it
                     * and move the next number.
                     * Because of the given condition "All the values of blacklist are unique", we don't need to check if
                     * b is in map.
                     */
                    while (map.containsKey(max)) {
                        max--;
                    }
                    map.put(b, max);
                    max--;//!!!
                }
            }
        }

        public int pick() {
            int x = random.nextInt(size);
            if (map.containsKey(x))  return map.get(x);
            return x;
        }
    }

    /**
     * Naive Solution
     * Make a whitelist, then get randome index and retrieve the value.
     *
     * Time and Space : O(n)
     * TLE
     */
    class Solution_WhiteList {
        List<Integer> list;
        Random random;

        public Solution_WhiteList(int n, int[] blacklist) {
            random = new Random();
            Set<Integer> set = new HashSet<>();

            for (int i = 0; i < n; i++) {// O(n)
                set.add(i);
            }
            for (int i = 0; i < blacklist.length; i++) {//O(n)
                if (set.contains(blacklist[i])) {
                    set.remove(blacklist[i]);
                }
            }

            list = new ArrayList<>();
            for (int i : set) {//O(n)
                list.add(i);
            }
        }

        public int pick() {
            return list.get(random.nextInt(list.size()));
        }
    }
}
