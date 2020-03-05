package src.Interviews.Indeed;

import java.util.*;

public class Normalize_Title {
/**
 * Given a rawTitle, and a list(or array) of clean titles. For each clean title,
 * the raw title can get a "match point". For example, if raw title is "senior software engineer"
 * and clean titles are "software engineer" and "mechanical engineer", the "match point" will be
 * 2 and 1. In this case we return "software engineer" because it has higher "match point”.
 *
 * 我是用Map存，key: document ID, value: word。这种解法只能过14/20。想要全部都过的话需要用key: word, value: document ID。
 * 据说Indeed上机题是一阵一阵的，可能今年下半年的上机题全是这个。
 *
 * 这题我的思路路就是建倒排链
 *
 * 而且它说没有重复的词，也是暗示用map
 */

    public String getNormalizeTitle(String t, String[] strs) {
        Map<String, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            String[] parts = strs[i].split(" ");

            for (String s : parts) {
                if (!map.containsKey(s)) {
                    map.put(s, new ArrayList<>());
                }
                map.get(s).add(i);
            }
        }

        Map<Integer, Integer> count = new HashMap<>();

        int max = 0;
        int maxIdx = 0;

        String[] temp = t.split(" ");
        for (String s : temp) {
            if (!map.containsKey(s)) continue;

            for (int idx : map.get(s)) {
                int val = count.getOrDefault(idx, 0) + 1;
                count.put(idx, val);

                if (val > max) {
                    max = val;
                    maxIdx = idx;
                }
            }
        }

        return strs[maxIdx];
    }

    class Pair {
        int idx;
        int count;

        public Pair(int idx, int count) {
            this.idx = idx;
            this.count = count;
        }
    }


    public String getNormalizeTitleWithDup(String t, String[] strs) {
        Map<String, List<Pair>> map = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            String[] parts = strs[i].split(" ");

            /**
             * first count word frequency
             */
            Map<String, Integer> freq = new HashMap<>();
            Set<String> set = new HashSet<>();

            for (String s : parts) {
                freq.put(s, freq.getOrDefault(s, 0) + 1);
                set.add(s);
            }

            for (String s : set) {
                if (!map.containsKey(s)) {
                    map.put(s, new ArrayList<>());
                }
                map.get(s).add(new Pair(i, freq.get(s)));
            }
        }

        Map<Integer, Integer> count = new HashMap<>();

        int max = 0;
        int maxIdx = 0;

        String[] temp = t.split(" ");
        Map<String, Integer> freq1 = new HashMap<>();
        Set<String> set1 = new HashSet<>();

        for (String s : temp) {
            freq1.put(s, freq1.getOrDefault(s, 0) + 1);
            set1.add(s);
        }


        for (String s : set1) {
            if (!map.containsKey(s)) continue;

            for (Pair p : map.get(s)) {
                int idx = p.idx;
                int c = p.count;

                int score = Math.min(freq1.get(s), c);

                int val = count.getOrDefault(idx, 0) + score;
                count.put(idx, val);

                System.out.println(s + " , idx=" + idx + ", score=" + val);
                if (val > max) {
                    max = val;
                    maxIdx = idx;
                }
            }
        }

        System.out.println("max=" + max);
        return strs[maxIdx];
    }

    public static void main(String[] args) {
        Normalize_Title test = new Normalize_Title();
        String rawTitle = "senior software engineer";
        String[] cleanTitles = {
                "software engineer",
                "mechanical engineer",
                "senior software engineer"};

        String result = test.getNormalizeTitle(rawTitle, cleanTitles);
        System.out.println(result);

        String rawTitle1 = "senior software software engineer";
        String[] cleanTitles1 = {
                "software engineer",
                "mechanical engineer",
                "senior software engineer",
                "senior software software engineer"};
        System.out.println("===with dup===");
        System.out.println(test.getNormalizeTitleWithDup(rawTitle1, cleanTitles1));
    }
}
