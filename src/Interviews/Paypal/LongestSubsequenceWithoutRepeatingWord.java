package Interviews.Paypal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestSubsequenceWithoutRepeatingWord {
    /**
     * 找到包含不重复单词的最长sequence
     * 举个例子 List<String> words: cats, turtles, cats, rats, dogs, rabitts,
     * 要求返回 List<String> 例子要返回turtles, cats, rats, dogs, rabitts,...
     * 因为第一个单词和第三个单词都是cats，所以要把第一个cats丢掉
     * 写的代码用的for loop里套while loop，双指针加set。
     *
     * Same as LE_03_Longest_Substring_Without_Repeating_Characters
     * Just using word instead of character
     */

    class Solution_Practice {
        public int lengthOfLongestSubstring(List<String> words) {
            if (words == null || words.size() == 0) return 0;

            Map<String, Integer> map = new HashMap<>();
            int res = 0;

            for (int i = 0, j = 0; i < words.size(); i++) {
                String word = words.get(i);
                map.put(word, map.getOrDefault(word, 0) + 1);

                while (map.get(word) > 1) {
                    String s = words.get(j);
                    map.put(s, map.get(s) - 1);
                    j++;
                }

                res = Math.max(res, i - j + 1);
            }

            return res;
        }
    }
}
