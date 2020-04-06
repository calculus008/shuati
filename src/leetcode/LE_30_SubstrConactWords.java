package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_30_SubstrConactWords {
    /**
        You are given a string, s, and a list of words, words, that are all of
        the same length. Find all starting indices of substring(s) in s that
        is a concatenation of each word in words exactly once and without
        any intervening characters.

        For example, given:
        s: "barfoothefoobarman"
        words: ["foo", "bar"]

        You should return the indices: [0,9].
        (order does not matter).

        Hard
     */

    /**
     * Solution 1 : Time : O(n ^ 2), brutal force
     **/
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.length() == 0) return res;

        int n = words.length;
        int m = words[0].length(); //!!! words[0] is a String, so "length()"!!!
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        //!!! "<= s.length() - n * m"
        for (int i = 0; i <= s.length() - n * m; i++) {
            Map<String, Integer> copy = new HashMap<>(map);//make copy of dist
            int j = i;
            int k = n;
            while (k > 0) {
                String cur = s.substring(j, j + m);
                //!!! faster in execution
                if (!copy.containsKey(cur) || copy.get(cur) < 1) break;

                k--;
                j += m;
                copy.put(cur, copy.get(cur) - 1);
            }

            if (k == 0) {
                res.add(i);
            }
        }

        return res;
    }

    /**
     * Solution 2 : Two dist solution, Time : O(n ^ 2)
     * */
    public ArrayList<Integer> findSubstring2(String S, String[] L) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        HashMap<String, Integer> toFind = new HashMap<String, Integer>();
        HashMap<String, Integer> found = new HashMap<String, Integer>();
        int m = L.length, n = L[0].length();

        for (int i = 0; i < m; i ++){
            if (!toFind.containsKey(L[i])){
                toFind.put(L[i], 1);
            } else{
                toFind.put(L[i], toFind.get(L[i]) + 1);
            }
        }

        for (int i = 0; i <= S.length() - n * m; i ++){
            found.clear();
            int j;

            for (j = 0; j < m; j ++){
                int k = i + j * n;
                String stub = S.substring(k, k + n);
                if (!toFind.containsKey(stub)) break;

                if(!found.containsKey(stub)){
                    found.put(stub, 1);
                } else{
                    found.put(stub, found.get(stub) + 1);
                }

                if (found.get(stub) > toFind.get(stub)) break;
            }

            if (j == m) {
                result.add(i);
            }
        }
        return result;
    }

    //Solution 3
    //Sliding Window, Time : O(KN), K - length of words[0], N - length of s
    public List<Integer> findSubstring3(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (words == null || words.length == 0 || s == null || s.length() == 0) return ans;

        final Map<String, Integer> need = new HashMap<>();
        for (final String word : words) {
            need.put(word, need.getOrDefault(word, 0) + 1);
        }
        final int n = s.length();
        final int num = words.length;
        final int len = words[0].length();

        for (int i = 0; i < len; i++) {
            int l = i, count = 0;
            final Map<String, Integer> seen = new HashMap<>();

            for (int j = i; j <= n - len; j += len) {
                final String word = s.substring(j, j + len);
                if (need.containsKey(word)) {
                    seen.put(word, seen.getOrDefault(word, 0) + 1);

                    if (seen.get(word) <= need.get(word)) {
                        count++;
                    } else {
                        /**
                         * move sliding window, until the number that word appears matches
                         * the number in "need"
                         */
                        while (seen.get(word) > need.get(word)) {
                            final String first = s.substring(l, l += len);//move sliding window starting point
                            seen.put(first, seen.getOrDefault(first, 0) - 1);

                            /**
                             能满足这个条件的，一定是overflow的word, 因为他出现的次数大于应有的次数，
                             当在seen里减一后，只有它有可能和need里的值相等。此时，word出现的次数已经
                             合法，不用再减count了。
                             **/
                            if (seen.get(first) < need.get(first)) {
                                count--;
                            }
                        }
                    }

                    if (count == num) {
                        ans.add(l);

                        //move sliding window starting point
                        count--;
                        final String first = s.substring(l, l += len);
                        seen.put(first, seen.get(first) - 1);
                    }
                } else {
                    seen.clear();
                    count = 0;
                    l = j + len;//l records start index of current sliding window
                }
            }
        }
        return ans;
    }

    /**
        Solution 4
        Best Solution. Same sliding window algorithm as Solution 3. Always updating seen and count++ at the same time,
        so avoid confusing statement like "if (seen.get(first) < need.get(first))"

        It also run faster, 21 ms. Solution 3 is 206 ms
     */
    public List<Integer> findSubstring4(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (words == null || words.length == 0 || s == null || s.length() == 0) return ans;

        final Map<String, Integer> need = new HashMap<>();
        for (final String word : words) {
            need.put(word, need.getOrDefault(word, 0) + 1);
        }

        final int n = s.length();
        final int num = words.length;
        final int len = words[0].length();

        /**
         * !!!
         * 外循环的次数是word的长度
         */
        for (int i = 0; i < len; i++) {
            int l = i, count = 0;
            final Map<String, Integer> seen = new HashMap<>();

            //!!! "j <= n - len", last col : n - 1, therefore (n - 1) - len + 1 = n - len
            for (int j = i; j + len <= n; j += len) {
                final String word = s.substring(j, j + len);
                if (need.containsKey(word)) {
                    /**
                     * 是要找的word，并且其frequency符合要求
                     */
                    if (seen.getOrDefault(word, 0) < need.get(word)) {
                        seen.put(word, seen.getOrDefault(word, 0) + 1);
                        count++;
                    } else {
                        /**
                         * 是要找的word，但并且其frequency不符合要求。
                         * 移动sliding window 的左边界，直到其frequency
                         * 符合要求。
                         */
                        while (seen.get(word) >= need.get(word)) {
                            String first = s.substring(l, l += len);
                            seen.put(first, seen.get(first) - 1);
                            count--;
                        }

                        seen.put(word, seen.getOrDefault(word, 0) + 1);
                        count++;
                    }

                    /**
                     * now current window is an answer, add starting index to final result
                     */
                    if (count == num) {
                        ans.add(l);

                        /**
                         * move sliding window starting point :
                         * 1.decrease count
                         * 2.l = l + len
                         * 3.decrease frequency count for the first word in window
                         **/
                        count--;
                        String first = s.substring(l, l += len);
                        seen.put(first, seen.get(first) - 1);
                    }
                } else {
                    /**
                     * 每次发现不在词典里的词，重新开始找，清空seen和count,
                     * 把sliding window的起点移到下一个词。
                     */
                    seen.clear();
                    count = 0;
                    l = j + len;//l records start (or left) index of current sliding window
                }
            }
        }
        return ans;
    }
}
