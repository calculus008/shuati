package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuank on 4/23/18.
 */
public class LE_734_Sentence_Similarity {
    /**
         Given two sentences words1, words2 (each represented as an array of strings),
         and a list of similar word pairs pairs, determine if two sentences are similar.

         For example, "great acting skills" and "fine drama talent" are similar,
         if the similar word pairs are pairs = [["great", "fine"], ["acting","drama"], ["skills","talent"]].

         Note that the similarity relation is not transitive. For example, if "great" and "fine" are similar,
         and "fine" and "good" are similar, "great" and "good" are not necessarily similar.

         However, similarity is symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

         Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"],
         pairs = [] are similar, even though there are no specified similar word pairs.

         Finally, sentences can only be similar if they have the same number of words.
         So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

         Note:

         The length of words1 and words2 will not exceed 1000.
         The length of pairs will not exceed 2000.
         The length of each pairs[i] will be 2.
         The length of each words[i] and pairs[i][j] will be in the range [1, 20].
     */

    /**
     * Time and Space : O(n)
     */
    public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length) return false;

        /**
         * Build undirected graph
         */
        Map<String, Set<String>> map = new HashMap<>();
        for (String[] pair : pairs) {
            if (!map.containsKey(pair[0])) {
                map.put(pair[0], new HashSet<>());
            }
            map.get(pair[0]).add(pair[1]);

            /**
             * !!! similarity is symmetric, so we also need to add pair[1] to dict
             */
            if (!map.containsKey(pair[1])) {
                map.put(pair[1], new HashSet<>());
            }
            map.get(pair[1]).add(pair[0]);

            //It is wrong to use getOrDefault for object - https://stackoverflow.com/questions/43737014/how-does-java-util-maps-getordefault-works
            // map.getOrDefault(pair[0], new HashSet<String>()).add(pair[1]);
            // map.getOrDefault(pair[1], new HashSet<String>()).add(pair[0]);
        }

        for (int i = 0; i < words1.length; i++) {
            if (words1[i].equals(words2[i])) continue;

            Set set = map.get(words1[i]);
            if (set == null || !set.contains(words2[i])) return false;
        }

        return true;
    }
}
