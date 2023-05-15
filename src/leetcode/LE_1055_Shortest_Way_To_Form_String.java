package leetcode;

import java.util.*;

public class LE_1055_Shortest_Way_To_Form_String {
    /**
     * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none)
     * of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence
     * of "abcde" while "aec" is not).
     *
     * Given two strings source and target, return the minimum number of subsequences of source such that their concatenation
     * equals target. If the task is impossible, return -1.
     *
     * Example 1:
     * Input: source = "abc", target = "abcbc"
     * Output: 2
     * Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".
     *
     * Example 2:
     * Input: source = "abc", target = "acdbc"
     * Output: -1
     * Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.
     *
     * Example 3:
     * Input: source = "xyz", target = "xzyxz"
     * Output: 3
     * Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
     *
     *
     * Constraints:
     * 1 <= source.length, target.length <= 1000
     * source and target consist of lowercase English letters.
     *
     * Medium
     *
     * https://leetcode.com/problems/shortest-way-to-form-string/description/
     */

    /**
     *  Insights:
     *  1。The only reason that there's no solution is that target string has a character that does not exist in
     *     source string. Otherwise, it always will find a subsequence in source, for example, a single character.
     *  2。Using two pointers to iterate both strings, if target pointer reaches the end without returning -1, then we
     *     have at least one subsequence
     *  3.Use set to store all chars in source so that we can check if a char in target exists in source.
     *
     * Time : O(m * n)
     * Space : O(m) (size of the set)
     */

    /**
     * Tow pointers move in interlace way
     */
    class Solution1_test {
        public int shortestWay(String source, String target) {
            Set<Character> set = new HashSet<>();
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();
            for (char c : src) {
                set.add(c);
            }

            int res = 0;
            int j = 0;

            while (j < tar.length) {
                if (!set.contains(tar[j])) return -1;

                for (int i = 0; i < src.length; i++) {
                    if (j < tar.length && src[i] == tar[j]) {
                        j++;
                    }
                }

                res++;
            }

            return res;
        }
    }

    /**
     * Follow up 1: can you do it without using set?
     *
     * Modify from Solution1_test, remove set usage and add two lines to check if target index has moved
     */
    class Solution_without_set_best {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();

            int res = 0;
            int j = 0;

            while (j < tar.length) {
                int last_j = j;//**

                for (int i = 0; i < src.length; i++) {
                    if (j < tar.length && src[i] == tar[j]) {
                        j++;
                    }
                }

                if (j == last_j) return -1;//**

                res++;
            }

            return res;
        }
    }

    /**
     * Follow up 2: what's the time complexity for above solutions. O(MN). could u make it better?
     *
     * the time complexity is better than O (MN), should be O(logM * N) or O (N)
     * to find a logM way, it is easy to think of binary search. For each char in tar, we need loop the src from j to end to find a char same as tar[i].
     * We can build a map which key is from 'a' -> 'z', the value is a list of indices for this char in src. Because idx is added from small to big,
     * when we iterate tar[i], we can easily find the tar[i]'s idx list. Then search if there's an idx is larger or equal than j+1.it is logM.
     * We have N chars in tar, so the time complexity is N * logM, the time is to build the map is O(M);
     */
    class Solution_binary_search {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();

            Map<Character, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < src.length; i++) {
                map.putIfAbsent(src[i], new ArrayList<>());
                map.get(src[i]).add(i);
            }

            /**
             * !!!
             */
            int res = 1;
            int i = 0, j = 0;

            while (j < tar.length) {
                if (!map.containsKey(tar[j])) return -1;

                List<Integer> list = map.get(tar[j]);

                /**
                 * Binary search a value that is bigger than i, return its index in list
                 */
                int k = Collections.binarySearch(list, i);
                if (k < 0) {
                    k = -k - 1;
                }

                /**
                 * this means the char does not exist and we reach the end of str, reset i
                 */
                if (k == list.size()) {
                    res++;
                    i = 0;
                } else {
                    /**
                     * move i to the next index of the one that found.
                     * Note: k is index in list, so we need to use list.get(k) to get the index in src
                     */
                    i = list.get(k) + 1;
                    j++;
                }
            }

            return res;
        }
    }


    /**
     * Follow up 3: great. could u improve it more?
     *
     * So we have to think a solution which is O(N), how should we use O(1) to know the next J pos?
     * In binary search solution we will have a map like a -> {1,3,7,16} (total src length is 20), so we need binary search.
     * if we can flatten them, i mean for each pos in 20 length, we just save the next idx, we can use O(1) to find the next J.
     * a -> {1,1,3,3,7,7,7,7,16,16,16,16,16,16,16,16,16,0,0,0}
     * for example if now j is 4, we can just check map[4] = 7; we know 7 pos have an 'a', so next j will be 7 + 1.
     * if now j is 17, we get map[17] = 0, we know there is no more j after. so j = 0, res++;
     *
     * the time complexity is O (N) , and build the map cost O(26 * M)
     *
     * src:
     * 0 1 2
     * a b c
     *
     * a -> [1, 0, 0]   # For 'a', at idx '0', the next index of 'a' is 1, at idx '1', the next index of 'a' is 0 (does not exist)
     * b -> [2, 2, 0]   # For 'b', at idx '0', the next index of 'b' is 2, at idx '1', the next index of 'a' is 2, at idx '2', the next index of 'a' is 0 (does not exist)
     * c -> [3, 3, 3]
     * d -> [0, 0, 0]
     * ...
     *
     *
     */
    class Solution {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();

            int[][] idx = new int[26][src.length];
            for (int i = 0; i < src.length; i++) {
                idx[src[i] - 'a'][i] = i + 1;
            }

            for (int i = 0; i < 26; i++) {
                for (int j = src.length - 1, pre = 0; j >= 0; j--) {
                    if (idx[i][j] == 0) {
                        idx[i][j] = pre;
                    } else {
                        pre = idx[i][j];
                    }
                }
            }

            int res = 1;
            int i = 0, j = 0;

            while (j < tar.length) {
                if (i == src.length) {
                    res++;
                    i = 0;
                }

                if (idx[tar[j] - 'a'][0] == 0) return -1;

                i = idx[tar[j] - 'a'][i];

                if (i == 0) {
                    res++;
                    j--;//!!!
                }

                j++; //!!!
            }

            return res;
        }
    }

    /**
     * follow up 4: cool, if we assume which can copy an array to another array with 26 length in constant time. could u implement it with O(M + N)?
     *
     * It sounds like we need switch the map from [26][src.length] to [src.length][26].
     * and we also need to use O(1) time to know what's next j position.
     * now we are in the 2rd idx (j = 1), so tar[i] = 'a' we should save the map[1]['a'] the next position of j.
     * if we are in the last idx, i think the map should be all 0, except the last tar char. for example the char is 'a'
     * so the map for it is [last+1,0,0,...,0]
     * how about last -1 idx, if the tar[last - 1] is same as tar[last], we just update it , [last - 1 + 1, 0....0]
     * if is not same. we can update a 0 with last - 1 + 1
     */
    class Solution_followup_4 {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();

            int[][] idx = new int[src.length][26];
            idx[src.length - 1][src[src.length - 1] - 'a'] = src.length;

            for (int i = src.length - 2; i >= 0; i--) {
                idx[i] = Arrays.copyOf(idx[i + 1], 26);
                idx[i][src[i] - 'a'] = i + 1;
            }

            int i = 0, res = 1;
            for (int j = 0; j < tar.length; j++) {
                if (i == src.length) {
                    i = 0;
                    res++;
                }

                i = idx[i][tar[j] - 'a'];

                if (idx[0][tar[j] - 'a'] == 0) return -1;

                if (i == 0) {
                    res++;
                    j--;
                }
            }

            return res;
        }
    }




    /**
     * ================================
     */

    class Solution1_mine {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();
            Set<Character> set = new HashSet<>();
            for (char c : src) {
                set.add(c);
            }

            int res = 0;
            int i = 0, j = 0;

            while (j < tar.length) {
                if (src[i] == tar[j]) {
                    i++;
                    j++;
                } else {
                    if (!set.contains(tar[j])) {
                        return -1;
                    }
                    i++;
                }

                if (i == src.length) {
                    i = 0;
                    res++;
                }
            }

            /**
             * !!!
             * Without this line, it can't deal with this case like:
             * aaaaa
             * aaaaa aaaaa aaa
             *
             * Expected result is 3. But it returns 2
             * How to check if the last section of tar is a subsequence of src (i does not have to each end of src)?
             *
             * When tar pointer reaches the end, we set i to 0, so we check if i is 0:
             *   if NO, meaning we are still in the process of moving i while j reaches the end, so the last part must be
             *   a subsequence of src, so we need to increase result by 1.
             *   if Yes, the only possibility is that, by the end of target pointer, source pointer also reaches end and
             *   reset to 0. So no need to increase res by one, since it is already taken care of in "if (i == src.length)"
             *
             *
             * Example 1:
             * src: abcde,  tar: abc
             *
             * Example 2:
             * src: abcdefg tar: abc bf
             */
            if (i != 0) res++;

            return res;
        }
    }

    /**
     * Visualize the process:
     * 1.Move both pointers in source and target for each loop (!!!)
     * 2.If current char in target does not exist in source string, return -1.
     * 3.If current chars not equal, move source pointer forward, hoping to find a matched char
     * 4.If source pointer reaches end, then we have matched subsequence:
     *   a.result increases by one
     *   b.set both pointers to the correct index so that we can start the next round a of match-checking
     *     source pointer should go back to index "0"
     *     target pointer should point to the char that is next to the matched subsequence
     *
     *  This solution is better than mine:
     *  1.Moving both pointers in each for loop and advances source pointer if chars are not equal
     *  2.In this way, we can init res as 1, if nothing happens in loop, we will have one subsequence, not need to deal with corner case
     */
    class Solution1 {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();
            Set<Character> set = new HashSet<>();
            for (char c : src) {
                set.add(c);
            }

            int res = 1;//!!!

            /**
             * i++, j++: moving both i, j in each loop, so must init res as 1!!!
             */
            for (int i = 0, j = 0; j < tar.length; i++, j++) {
                if (!set.contains(tar[j])) return -1;

                /**
                 * only move src index if not equal
                 */
                while (i < src.length && src[i] != tar[j]) {
                    i++;
                }

                if (i == src.length) {
                    res++;
                    /**
                     * !!!
                     * since we increase automatically in for loop, set i and j one less so it will return to correct
                     * index after i++, j++.
                     *
                     * src: abc
                     * tar: abcbc
                     *
                     * When first "abc" match found, both i and j advance to index "3". "i < src.length" condition in while
                     * loop prevents we refer to src[i] which will incur index out of bound error. When we see "i == src.length"
                     * we know one match found, res increases. Then we need to get ready for the next round. When next loop
                     * starts, we want i go back to "0" and j stays at index "3". Since we do "i++, j++" automatically in
                     * each for loop, hence we need to set i and j one less (so after i++ j++ it will be at the right place)
                     */
                    i = -1;
                    j--;
                }
            }

            return res;
        }
    }

    /**
     * Without using Set
     */
    class Solution2_mine {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();
            // Set<Character> set = new HashSet<>();
            // for (char c : src) {
            //     set.add(c);
            // }

            int res = 0;
            int i = 0, j = 0;
            int last_j = j;//remember last j position

            while (j < tar.length) {
                if (src[i] == tar[j]) {
                    i++;
                    j++;
                } else {
                    // if (!set.contains(tar[j])) {
                    //     return -1;
                    // }
                    i++;
                }

                if (i == src.length) {
                    /**
                     * If source pointer reaches end and target point does not move, we know the current char in target
                     * does not exist in source string
                     */
                    if (last_j == j) return -1;

                    last_j = j;//remember last j posistion
                    i = 0;
                    res++;
                }
            }

            if (i != 0) res++;

            return res;
        }
    }

    class Solution2 {
        public int shortestWay(String source, String target) {
            char[] src = source.toCharArray();
            char[] tar = target.toCharArray();

            int res = 0;
            int j = 0;

            while (j < tar.length) {
                int last_j = j;

                for (int i = 0; i < src.length; i++) {
                    if (j < tar.length && src[i] == tar[j]) {
                        j++;
                    }
                }

                if (j == last_j) return -1;

                res++;
            }

            return res;
        }
    }
}
