package leetcode;

import java.util.*;

public class LE_527_Word_Abbreviation {
    /**
     * Given an array of n distinct non-empty strings, you need to generate
     * minimal possible abbreviations for every word following rules below.
     *
     * 1.Begin with the first character and then the number of characters abbreviated,
     * which followed by the last character.
     *
     * 2.If there are any conflict, that is more than one words share the same abbreviation,
     * a longer prefix is used instead of only the first character until making the dist
     * from word to abbreviation become unique. In other words, a final abbreviation
     * cannot dist to more than one original words.
     *
     * 3.If the abbreviation doesn't make the word shorter, then keep it as original.
     *
     * Example:
     * Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
     * Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
     *
     * Note:
     * Both n and the length of each word will not exceed 400.
     * The length of each word is greater than 1.
     * The words consist of lowercase English letters only.
     * The return answers should be in the same order as the original array.
     *
     * Hard
     */

    class Solution_Practice {
        public List<String> wordsAbbreviation(List<String> dict) {
            List<String> res = new ArrayList<>();
            if (dict == null || dict.size() == 0) return res;

            int size = dict.size();
            int[] prefix = new int[size];
            Map<String, Integer> map = new HashMap<>();

            for (int i = 0; i < size; i++) {
                prefix[i] = 1;
                String key = getAbbr(dict.get(i), 1);
                res.add(key);
                map.put(key, map.getOrDefault(key, 0) + 1);
            }

            /**
             * !!!
             * keep going through the result set to check if each abbr is unique (count in dist equals to one),
             * if not, increase prefix value, recreate the key, then go to the next round.
             *
             * For "dear", "door", in dist:
             * d2r -> 2
             * res : {"d2r", "d2r"}
             *
             * When we get to "dear", in dist:
             * d2r  -> 2
             * de1r -> 1
             * res : {"de1r", "d2r"}
             *
             *
             * When we get to "door", in dist:
             * d2r  -> 2
             * de1r -> 1
             * do1r -> 1
             * res : {de1r, do1r}
             *
             * So no need to update the count number of existing key. If it's bigger than 1, we need to update
             * with new key. The old one will still be in the dist, only that we no longer check it since we only
             * get the key from current res list. (!!!)
             */
            while (true) {
                boolean unique = true;
                for (int i = 0; i < size; i++) {
                    if (map.get(res.get(i)) > 1) {
                        unique = false;
                        prefix[i]++;
                        String newKey = getAbbr(dict.get(i), prefix[i]);
                        res.set(i, newKey);
                        map.put(newKey, map.getOrDefault(newKey, 0) + 1);
                    }
                }

                if (unique) break;
            }

            return res;
        }

        private String getAbbr(String s , int x) {
            int n = s.length();
            if (x + 2 >= n) return s;

            return s.substring(0, x) + String.valueOf(n - (x + 1)) + s.charAt(n - 1);
        }
    }

    /**
     * "prefix[i]" records starting index for abbreviation for ith word
     */
    class Solution1 {
        public List<String> wordsAbbreviation(List<String> dict) {
            int len = dict.size();
            String[] ans = new String[len];
            int[] prefix = new int[len];

            for (int i = 0; i < len; i++) {
                prefix[i] = 1;
                ans[i] = makeAbbr(dict.get(i), 1); // make abbreviation for each string
            }

            for (int i = 0; i < len; i++) {
                while (true) {//!!!
                    /**
                     * Set saves index for words have the same abbreviation
                     */
                    HashSet<Integer> set = new HashSet<>();

                    for (int j = i + 1; j < len; j++) {
                        if (ans[j].equals(ans[i])) {
                            set.add(j); // check all strings with the same abbreviation
                        }
                    }

                    if (set.isEmpty()) {
                        break;
                    }

                    set.add(i);
                    for (int k : set) {
                        ans[k] = makeAbbr(dict.get(k), ++prefix[k]); // increase the prefix
                    }
                }
            }
            return Arrays.asList(ans);
        }

        /**
         * Make abbreviate word, abbreviation starts from index k
         */
        private String makeAbbr(String s, int k) {
            if (k + 2 >= s.length()) {
                return s;
            }

            StringBuilder builder = new StringBuilder();
            builder.append(s.substring(0, k));
            builder.append(s.length() - 1 - k);
            builder.append(s.charAt(s.length() - 1));

            return builder.toString();
        }
    }

    public class Solution2 {
        /**
         * @param dict: an array of n distinct non-empty strings
         * @return: an array of minimal possible abbreviations for every word
         */
        public String[] wordsAbbreviation(String[] dict) {
            if (null == dict) return new String[]{};

            int len = dict.length;
            int[] idx = new int[len];
            /**
             * String array that has the final result
             */
            String[] res = new String[len];

            /**
             * HashMap
             * key : abbreviated string
             * value: count of the key
             */
            Map<String, Integer> map = new HashMap<>();

            for (int i = 0; i < len; i++) {
                idx[i] = 1;
                res[i] = getAbbr(dict[i], 1);
                map.put(res[i], map.getOrDefault(res[i], 0) + 1);
            }

            while (true) {
                boolean unique = true;
                for (int i = 0; i < len; i++) {
                    if (map.get(res[i]) > 1) {
                        idx[i]++;
                        /**
                         * always pass dict[i] when calling getAbbr()
                         */
                        res[i] = getAbbr(dict[i], idx[i]);
                        map.put(res[i], map.getOrDefault(res[i], 0) + 1);
                        unique = false;
                    }
                }

                if (unique) break;
            }

            return res;
        }

        public String getAbbr(String s, int k) {
            if (k + 2 >= s.length()) return s;

            int x = s.length() - k - 1;
            return s.substring(0, k) + x + s.charAt(s.length() - 1);
        }
    }
}
