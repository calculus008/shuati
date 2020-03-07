package src.Interviews.Indeed.上机;

import java.util.*;

public class Trireviews {
    /**
     *      * 跟之前的上机题不一样，让求 trireviews，就是给你一堆 log，包含公司名和 reviews，让你
     *      * 求出现频率最高的 triangrams， 比如 indeed is a good company, triangrams 就是 indeed is a, is a
     *      * good, a good company, 会给很多句 review 然后对一个公司返回 n 个出现频率最高的 triangrams，如
     *      * 果出现频率一样就按字母顺序排.
     */

    class Pair {
        String val;
        int freq;

        public Pair(String val, int freq) {
            this.val = val;
            this.freq = freq;
        }
    }

    Map<String, TreeSet<Pair>> map = new HashMap<>();

    public void process(String s) {
        if (s == null || s.isEmpty()) return;

        String[] parts = s.split(" ");
        if (parts.length < 3) return;

        String company = parts[0];

        if (!map.containsKey(company)) {
            map.put(company, new TreeSet<Pair>((a, b) -> a.freq == b.freq ? a.val.compareTo(b.val): b.freq - a.freq));
        }

        Map<String, Integer> count = new HashMap<>();
        for (int i = 1; i < parts.length - 2; i++) {
            String review = parts[i] + " " + parts[i + 1] + " " + parts[i + 2];
            count.put(review, count.getOrDefault(review, 0) + 1);
        }


        for (String key : count.keySet()) {
            System.out.println(key+ "->" + count.get(key));
            Pair p = new Pair(key, count.get(key));
            map.get(company).add(p);
        }
    }

    public List<String> getTriReview(String company) {
        List<String> res = new ArrayList<>();
        if (company == null || company.isEmpty()) return res;

        if (!map.containsKey(company)) return res;

        Set<Pair> set = map.get(company);

        Iterator<Pair> it = set.iterator();

        while (it.hasNext()) {
            res.add(it.next().val);
        }

        return res;
    }

    public static void main(String[] args) {
        Trireviews test = new Trireviews();
        String input = "indeed a a a a a b c c c d c c d";

        test.process(input);

        List<String> res = test.getTriReview("indeed");
        System.out.println(Arrays.toString(res.toArray()));
    }
}
