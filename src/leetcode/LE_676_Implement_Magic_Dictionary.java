package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 10/9/18.
 */
public class LE_676_Implement_Magic_Dictionary {
    /**
         Implement a magic directory with buildDict, and search methods.

         For the method buildDict, you'll be given a list of non-repetitive words to build a dictionary.

         For the method search, you'll be given a word, and judge whether if you modify exactly one character
         into another character in this word, the modified word is in the dictionary you just built.

         Example 1:
         Input: buildDict(["hello", "leetcode"]), Output: Null
         Input: search("hello"), Output: False
         Input: search("hhllo"), Output: True
         Input: search("hell"), Output: False
         Input: search("leetcoded"), Output: False

         Note:
         You may assume that all the inputs are consist of lowercase letters a-z.
         For contest purpose, the test data is rather small by now. You could think about highly efficient
         algorithm after the contest.
         Please remember to RESET your class variables declared in class MagicDictionary, as static/class variables
         are persisted across multiple test cases. Please see here for more details.

         Medium
     */
    /**
     * https://zxi.mytechroad.com/blog/hashtable/leetcode-676-implement-magic-dictionary/
     *
     * Solution 1
     * HashMap
     * Time and Space : O(n * m)
     *
     * 脑筋急转弯
     * 1.Search()要找的，是word中任意以为做了变换，比如，如果字典中有"hello",search "hello" returns FALSE,
     *   since it can not be the searched word itself.
     * 2.但是，如果字典输入有"hello"和“hallo", search "hello" returns TRUE, because "hello" can be deemed as
     *   "hallo" replacing "a" with "e".
     * 3.Therefore, use HashMap, for a given input word, the keys are formed by replacing each char in word with '*':
     *   a.HashMap<String, Set<Character>>
     *      A Set that contains the character of the original char in word. The logic in search():
     *     1.If Set does not contain char, returns TRUE since we know for sure that the searched word can be formed
     *       by changing char at current index and the searched word is NOT the one that in input which forms the current key
     *     2.If Set contains char, then check size of the Set, if the size is bigger than 1, returns TRUE,
     *       because, even the searched word is used to create current key, but it is NOT the only one, other word(s) is also
     *       used to create current key, so the searched word can be deemed as valid. See "hello" and "hallo" example in #2.
     *
     *       if (map.containsKey(key) && (!map.get(key).contains(c) || map.get(key).size() > 1)) {
     *          return true;
     *       }
     *
     *   b.HashMap<String, Character>
     *     Use a trick based on conclusion from 'a', if there are multiple words in input can be used to create a key,
     *     then we don't care what original chars are at the index, so we simply use Character as value in HashMap,
     *     for this scenario, we just use '*' as value.
     *
     *     For example : if there's only hello in dictionary, for key "h*llow", HashMap entry is :
     *                   ["h*llow", 'e']
     *
     *                   if both "hello" and "hallo" are in dictionary, same key entry will be :
     *                   ["h*llow", '*']
     *
     *                   Therefore "h*llo" will return TRUE.
     */
    class MagicDictionary {
        Map<String, Character> map;

        public MagicDictionary() {
            map = new HashMap<>();
        }

        /** Build a dictionary through a list of words */
        public void buildDict(String[] dict) {
            for (String word : dict) {
                for (int i = 0; i < word.length(); i++) {
                    String key = word.substring(0, i)  + '*' + word.substring(i + 1);
                    map.put(key, map.containsKey(key) ? '*' : word.charAt(i));
                }
            }
        }

        /** Returns if there is any word in the trie that equals to the given word after modifying exactly one character */
        public boolean search(String word) {
            if (word == null || word.length() == 0) return false;

            for (int i = 0; i < word.length(); i++) {
                String key = word.substring(0, i)  + '*' + word.substring(i + 1);
                if (map.containsKey(key) && word.charAt(i) != map.get(key)) {
                    return true;
                }
            }

            return false;
        }
    }
}
