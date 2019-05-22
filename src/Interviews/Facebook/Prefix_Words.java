package Interviews.Facebook;

import java.util.*;

public class Prefix_Words {
    /**
     * Input = [word1, word2, word3...wordn]，就是一串words，这里简写了。
     * 找到所有不同的prefix words，words必须是input list里面有的。
     * 举个例子，[app,apple,banana,ban,bar]，这里的output -> [app, ban, bar]
     *
     * 我的解法O(n^2)，面试官说OK，下一个。
     */

    /**
     * Brutal Force
     * Time  : O(n ^ 2)
     * Space : O(n)
     */
    public static List<String> prefixWords(String[] words) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;

        List<String> list = Arrays.asList(words);
        Set<String> set = new HashSet<>(list);

        for (int i = 0; i < list.size(); i++) {
            String cur = list.get(i);
            for (int j = 0; j <list.size(); j++) {
                if (i == j) continue;

                if (cur.startsWith(list.get(j))) {
                    set.remove(cur);
                }
            }
        }

        res.addAll(set);

        return res;
    }

    public static List<String> prefixWords1(String[] words) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;

        List<String> list = Arrays.asList(words);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });

        Set<String> set = new HashSet<>(list);

        for (int i = 0; i < list.size(); i++) {
            String cur = list.get(i);
            for (int j = i + 1; j <list.size(); j++) {
                if (cur.length() == list.get(j).length()) continue;

                if (cur.startsWith(list.get(j))) {
                    set.remove(cur);
                }
            }
        }

        res.addAll(set);

        return res;
    }

    public static void main(String[] args) {
        String[] words = {"app", "apple", "banana", "ban", "bar", "ba"};

        System.out.println(Arrays.toString(prefixWords1(words).toArray()));
    }
}
