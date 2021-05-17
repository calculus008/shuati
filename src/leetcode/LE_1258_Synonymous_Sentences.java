package leetcode;

import java.util.*;

public class LE_1258_Synonymous_Sentences {
    /**
     * Given a list of pairs of equivalent words synonyms and a sentence text,
     * Return all possible synonymous sentences sorted lexicographically.
     *
     * Example 1:
     *
     * Input:
     * synonyms = [["happy","joy"],["sad","sorrow"],["joy","cheerful"]],
     * text = "I am happy today but was sad yesterday"
     *
     * Output:
     * ["I am cheerful today but was sad yesterday",
     * ​​​​​​​"I am cheerful today but was sorrow yesterday",
     * "I am happy today but was sad yesterday",
     * "I am happy today but was sorrow yesterday",
     * "I am joy today but was sad yesterday",
     * "I am joy today but was sorrow yesterday"]
     *
     *
     * Constraints:
     *
     * 0 <= synonyms.length <= 10
     * synonyms[i].length == 2
     * synonyms[0] != synonyms[1]
     * All words consist of at most 10 English letters only.
     * text is a single space separated sentence of at most 10 words.
     *
     * Medium
     */

    /**
     * Leetcode 1ms, 100%
     *
     * 关键 ： 设法把同义词分为一组，DFS, 当遇到同义词时，依次替换。
     *        与naive BFS 相比，不会重复产生相同的text, more efficient.
     *
     * 分组： 使用 two hashmap.
     *       wordToKey :  word -> key (unique ID for each group)
     *       keyToList :  key -> list of all 同义词。
     *
     *       每次遇到一个词，先通过 wordToKey 查找相应的同义词组key (or ID),
     *       如果找到，在用找到的key 在 wordToList 里拿到同义词组。
     *
     *       每个同义词组是经过排序的，这样保证 "Return all possible synonymous
     *       sentences sorted lexicographically"
     *
     *
     * Input:
     * [["happy","joy"],["sad","sorrow"],["joy","cheerful"]]
     * "I am happy today but was sad yesterday"
     *
     * ---wordToKey---
     * joy -> 0
     * happy -> 0
     * sad -> 1
     * cheerful -> 0
     * sorrow -> 1
     *
     * ---keyToList---
     * 0 -> [cheerful, happy, joy]
     * 1 -> [sad, sorrow]
     *
     * Time : O(k ^ n),   n is the the number of words in text, k is the average number of synonymous words group
     * Space : O(n), for two hashmaps.
     */
    class Solution_DFS {
        public List<String> generateSentences(List<List<String>> synonyms, String text) {
            Map<String, Integer> wordToKey = new HashMap<>();
            Map<Integer, List<String>> keyToList = new HashMap<>();

            int keyCount = 0;

            for (List<String> list : synonyms) {
                /**
                 * #1.Prepare a key
                 */
                int key = keyCount++;
                for (String word : list) {
                    if (wordToKey.containsKey(word)) {
                        key = wordToKey.get(word);
                    }
                }

                /**
                 * #2.Have entry ready in keytoList
                 */
                if (!keyToList.containsKey(key)) {
                    keyToList.put(key, new ArrayList<>());
                }

                /**
                 * #3.Now we have a key, insert into two hashmap if the given word has not been seen.
                 */
                for (String word : list) {
                    if (!wordToKey.containsKey(word)) {
                        wordToKey.put(word, key);
                        keyToList.get(key).add(word);
                    }
                }
            }

            /**
             * sort each 同义词组
             */
            for (Integer key : keyToList.keySet()) {
                Collections.sort(keyToList.get(key));
            }

            List<String> res = new LinkedList<>();
            String[] sentence = text.split(" ");
            helper(res, sentence, 0, wordToKey, keyToList);

            return res;
        }

        private void helper(List<String> res, String[] sentence, int index, Map<String, Integer>
                wordTokey, Map<Integer, List<String>> keyToList) {
            if (index == sentence.length) {
                StringBuilder sb = new StringBuilder();

                for (String word : sentence) {
                    sb.append(word).append(" ");
                }

                res.add(sb.deleteCharAt(sb.length() - 1).toString());
                return;

                /**
                 * Or
                 *             String newText = String.join(" ", strs);
                 *             res.add(newText);
                 *             return;
                 *
                 * Use "String.join" is simpler. Use StringBuilder is faster.
                 */
            }

            String word = sentence[index];

            //no need to replace curr word
            if (!wordTokey.containsKey(word)) {
                helper(res, sentence, index + 1, wordTokey, keyToList);
            } else {
                int key = wordTokey.get(word);
                for (String s : keyToList.get(key)) {
                    sentence[index] = s;
                    helper(res, sentence, index + 1, wordTokey, keyToList);
                }
            }
        }
    }


    /**
     * synonyms = [["happy","joy"],["strong","healthy"],["joy","cheerful"]],
     * text = "I am happy and strong"
     *
     *                                          I am happy and strong
     *                        /                /                        \
     *             I am joy and strong    I am cheerful and strong       I am happy and healthy
     *
     *  Creating the same result string multiple times and so uses a Set to eliminate duplicates.
     *  Generating the same answer more than once is inneficient.
     */
    class Solution_BFS {
        public List<String> generateSentences(List<List<String>> synonyms, String text) {
            List<String> res = new ArrayList<>();
            if (text == null || text.length() == 0 || synonyms == null || synonyms.size() == 0) return res;

            Map<String, List<String>> map = new HashMap<>();
            for (List<String> s : synonyms) {
                String s1 = s.get(0);
                String s2 = s.get(1);
                if (!map.containsKey(s1)) {
                    map.put(s1, new ArrayList<>());
                }
                map.get(s1).add(s2);

                if (!map.containsKey(s2)) {
                    map.put(s2, new ArrayList<>());
                }
                map.get(s2).add(s1);
            }

            Queue<String> q = new LinkedList<>();
            q.offer(text);

            TreeSet<String> visited = new TreeSet<>();
            visited.add(text);

            while (!q.isEmpty()) {
                String cur = q.poll();
                String[] words = cur.split(" ");

                for (int i = 0; i < words.length; i++) {
                    if (!map.containsKey(words[i])) continue;

                    for (String s : map.get(words[i])) {
                        words[i] = s;
                        String newText = String.join(" ", words);

                        if (!visited.contains(newText)) {
                            visited.add(newText);
                            q.offer(newText);
                        }
                    }
                }
            }

            // Iterator<String> it = visited.iterator();
            // while (it.hasNext()) {
            //     res.add(it.next());
            // }

            res.addAll(visited);
            return res;
        }
    }
}
