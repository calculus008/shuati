package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 4/24/18.
 */
public class UnionFindSetString {
    private Map<String, String> parents = new HashMap<>();

    public boolean union(String word1, String word2) {
        String p1 = find(word1, true);
        String p2 = find(word2, true);
        if (p1.equals(p2)) {
            return false;
        }
        parents.put(p1, p2);
        return true;
    }

    public String find(String word, boolean create) {
        if (!parents.containsKey(word)) {
            if (!create) {
                return word;
            }
            parents.put(word, word);
        }

        String w = word;
//        "while (w != parents.get(w))" { !!! It's String object, can't do "!="
        while (!w.equals(parents.get(w))) {
            parents.put(w, parents.get(parents.get(w)));
            w = parents.get(w);
        }

        return parents.get(w);
    }
}

