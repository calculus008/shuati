package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 4/13/18.
 */
public class LE_267_Palindrome_Permutation_II {
    /**
     *  Given a string s, return all the palindromic permutations (without duplicates) of it.
     *  Return an empty list if no palindromic permutation could be form.

         For example:

         Given s = "aabb", return ["abba", "baab"].

         Given s = "abc", return [].

         Medium
     */

    class Solution {
        public List<String> generatePalindromes(String s) {
            String mid = "";
            List<String> res = new ArrayList<>();
            List<Character> list = new ArrayList<>();
            HashMap<Character, Integer> map = new HashMap<>();
            int odd = 0;

            for (char c : s.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
                //!!! a good trick
                odd += map.get(c) % 2 == 0 ? -1 : 1;
            }

            /**
             * !!!
             * there can be only 1 char that has odd number of frequency,
             * if there's more than one, we can't make palindrome out of it.
             */

            if (odd > 1) return res;

            //!!! HashMap.Entry<>
            for (HashMap.Entry<Character, Integer> entry : map.entrySet()) {
                char key = entry.getKey();
                int value = entry.getValue();
                if (value % 2 != 0) {
                    mid = key + "";
                }
                for (int i = 0; i < value / 2; i++) {
                    list.add(key);
                }
            }

            helper(res, list, new StringBuilder(), new boolean[list.size()], mid);

            return res;
        }

        //See LE_47 : Permutation II
        public void helper(List<String> res, List<Character> list, StringBuilder sb, boolean[] used, String mid) {
            if (sb.length() == list.size()) {
                res.add(sb.toString() + mid + sb.reverse().toString());
                sb.reverse();//!!!
                return;
            }

            for (int i = 0; i < list.size(); i++) {
                /**
                 Remove duplication: Given [b, a, a, a, c, c, d], when we have tried the permutation begin with "ba",
                 we don’t want to try another "ba", so just skip the following 2 a, and build the permutation from bc.
                 The same thing happens when we meet the 2nd c.

                 Each recursion level actually represents a position in the result string, we will try each of the unique char in list.
                 "i>0": prevent overflow with annotation "get(i-1)".
                 "!used[i-1]", it proves that the current one and the previous one are currently
                 tried in the same recursion level (each element is set to unused after recursion comes back from next level (position)).
                 If "used[i-1]==true", then the last element is used for other position, then we can try this element for the current position)
                 */
                if (i > 0 && list.get(i) == list.get(i - 1) && !used[i - 1]) continue;

                if (!used[i]) {
                    sb.append(list.get(i));
                    used[i] = true;
                    helper(res, list, sb, used, mid);
                    used[i] = false;
                    //!!!deleteCharAt!!!
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
    }

    class Solution_Practice {
        public List<String> generatePalindromes(String s) {
            List<String> res = new ArrayList<>();
            if (s == null || s.length() == 0)  return res;

            Map<Character, Integer> map = new HashMap<>();
            List<Character> list = new ArrayList<>();
            int odd = 0;

            for (char c : s.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
                odd += map.get(c) % 2 == 0 ? -1 : 1;
            }

            if (odd > 1) return res;

            /**
             * !!!
             * can't init char with empty '', so we make it an empty string
             */
            String mid = "";

            for (Map.Entry<Character, Integer> entry: map.entrySet()) {
                char key = entry.getKey();
                int  val = entry.getValue();

                if (val % 2 == 1) {
                    mid = "" + key;
                }

                for (int i = 0; i < val/2; i++) {
                    list.add(key);
                }
            }

            helper(res, list, new boolean[list.size()], new StringBuilder(), mid);

            return res;
        }

        private void helper(List<String> res, List<Character> list, boolean[] visited, StringBuilder sb, String mid) {
            if (sb.length() == list.size()) {
                res.add(sb.toString() + mid + sb.reverse().toString());
                /**
                 * !!!
                 * Must reverse() to get the order back, since it is reversed one time in the last line.
                 */
                sb.reverse();
                return;
            }

            int len = sb.length();

            for (int i = 0; i < list.size(); i++) {
                /**
                 * !!!
                 */
                if (i > 0 && list.get(i) == list.get(i - 1) && !visited[i - 1]) {
                    continue;
                }

                if (visited[i]) continue;

                visited[i] = true;
                sb.append(list.get(i));
                helper(res, list, visited, sb, mid);
                sb.setLength(len);
                visited[i] = false;
            }

        }
    }
}
