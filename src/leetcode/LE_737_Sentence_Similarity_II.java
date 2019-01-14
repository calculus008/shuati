package leetcode;

import common.UnionFindSetString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuank on 4/23/18.
 */
public class LE_737_Sentence_Similarity_II {
    /**
         Given two sentences words1, words2 (each represented as an array of strings),
         and a list of similar word pairs pairs, determine if two sentences are similar.

         For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] are similar,
         if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].

         Note that the similarity relation is transitive.
         For example, if "great" and "good" are similar, and "fine" and "good" are similar, then "great" and "fine" are similar.

         Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

         Also, a word is always similar with itself.
         For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

         Finally, sentences can only be similar if they have the same number of words.
         So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

         Note:

         The length of words1 and words2 will not exceed 1000.
         The length of pairs will not exceed 2000.
         The length of each pairs[i] will be 2.
         The length of each words[i] and pairs[i][j] will be in the range [1, 20].

        Medium
     */


    /**
     * Difference from LE_734 - "the similarity relation is transitive"
     */

    /**
     * http://zxi.mytechroad.com/blog/hashtable/leetcode-737-sentence-similarity-ii/
     *
     * DFS
     *
     * Time complexity: O(|Pairs| * |words1|)
     * Space complexity: O(|Pairs|)
     */

    public boolean areSentencesSimilarTwoDFS(String[] words1, String[] words2, String[][] pairs) {
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

            if (!map.containsKey(pair[1])) {
                map.put(pair[1], new HashSet<>());
            }
            map.get(pair[1]).add(pair[0]);
        }

        Set<String> visited = new HashSet<String>();
        for (int i = 0; i < words1.length; i++) {
            if (words1[i].equals(words2[i])) {
                continue;
            }

            if (!map.containsKey(words1[i])) {
                return false;
            }

            visited.clear();
            if (!dfs(words1[i], words2[i], map, visited)) {
                return false;
            }
        }

        return true;
    }

    public boolean dfs(String s1, String s2, Map<String, Set<String>> map, Set<String> visited) {
        if (s1.equals(s2)) {
            return true;
        }

        visited.add(s1);

        for (String s : map.get(s1)) {
            if (visited.contains(s)) {
                continue;
            }

            if (dfs(s, s2, map, visited)) {
                return true;
            }
        }

        return false;
    }

    /**
     *  Union Find
     *  Time complexity: O(|Pairs| + |words1|)
     *  Space complexity: O(|Pairs|)
     *
     */
    public boolean areSentencesSimilarUnionFind(String[] words1, String[] words2, String[][] pairs) {
        if (words1 == null || words2 == null || pairs == null || words1.length != words2.length) {
            return false;
        }

        UnionFindSetString ufs = new UnionFindSetString();
        for (String[] pair : pairs) {
            ufs.union(pair[0], pair[1]);
        }

        for (int i = 0; i < words1.length; i++) {
            if (words1[i].equals(words2[i])) {
                continue;
            }

            if(!ufs.find(words1[i], false).equals(ufs.find(words2[i], false))) {
                return false;
            }
        }

        return true;
    }
}

