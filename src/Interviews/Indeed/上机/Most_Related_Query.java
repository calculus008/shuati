package src.Interviews.Indeed.上机;

import java.util.*;

public class Most_Related_Query {
    /**
     * Most Related Word
     *
     * 找相关词那道，就是A输⼊入java，php，B输⼊入java，python，然后C输⼊入了了 php，问你给C推荐的下⼀一个单词应该是什什么。
     * 搜词，然后按关联度推荐词 (??)
     *
     * 上机题貌似就万年不变的query与job description去match。
     * 具体来说，给了一堆job description，每个是一个string，确保了没有标点符号、只有小写字母，
     * 比如{"we want someone good at java and spring", "are you good at cpp"}；
     * 然后给一堆query，要求对每个query找出最match的最多10个job description的id，依次输出这些id，
     * 如果一个都没有就输出-1。对于match程度的定义，是有一个叫match count的概念，就是query与某个jd匹配了多少单词。
     * 例如一条query是"good at java"，那么对于上述的两条jd的match count分别是2和1。match count越大的jd，
     * 就代表match程度越高，如果有两个jd具有相同的match count，那就认为id小的比id大的更match。
     */

    /**
     * Query_Word的变形题。唯一区别是query multiple words and the result is union,
     * so we need to use k-way merge, instead of linear merge for two lists in Query_Word
     *
     * For big data test case, use counting sort??
     */
    class Pair {
        int line;
        int count;

        public Pair(int line, int count) {
            this.line = line;
            this.count = count;
        }
    }

    class Element {
        Iterator<Pair> it;
        Pair p;

        public Element(Iterator<Pair> it, Pair p) {
            this.it = it;
            this.p = p;
        }

        public Element(Iterator<Pair> it) {
            this.it = it;
        }
    }

    Map<String, List<Pair>> map = new HashMap<>();

    /**
     * Build inverted index
     */
    public void processInput(String line, int i) {
        if (line == null || line.length() == 0) return;

        String[] parts = line.split(" ");

        Map<String, Integer> count = new HashMap<>();
        for (String s : parts) {
            count.put(s, count.getOrDefault(s, 0) + 1);
            if (!map.containsKey(s)) {
                List<Pair> list = new ArrayList<>();
                map.put(s, list);
            }
        }

        for (String key : count.keySet()) {
            int c = count.get(key);
            Pair p = new Pair(i + 1, c);
            map.get(key).add(p);
        }
    }

    /**
     * Return top n match score
     *
     * Time : mlogk, k - number of lines or docs, m is sum of the length of postings for each word
     */
    public List<Integer> query(String s, int n) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.isEmpty()) return res;

        String[] parts = s.split(" ");

        Set<Pair> ans = new TreeSet<>((a, b) -> a.count == b.count ? a.line - b.line : b.count - a.count);

        /**
         * use k-way merge
         */
        PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.p.line - b.p.line);
        for (String part : parts) {
            if (!map.containsKey(part)) continue;

            Iterator<Pair> it = map.get(part).iterator();
            if (it.hasNext()) {
                pq.offer(new Element(it, it.next()));
            }
        }

        if (pq.isEmpty()) return res;

        Integer pre = null;
        int sum = 0;
        while (!pq.isEmpty()) {
            Element e = pq.poll();

            if (pre != null && e.p.line == pre) {
                sum += e.p.count;
            } else {
                if (pre != null) {
                    ans.add(new Pair(pre, sum));
                }

                pre = e.p.line;
                sum = e.p.count;
            }

            if (e.it.hasNext()) {
                e.p = e.it.next();
                pq.offer(e);
            }

            /**
             * !!!
             * can't forget to add the last element in pq
             */
            if (pq.isEmpty()) {
                ans.add(new Pair(pre, sum));
            }
        }

        int i = n;
        Iterator<Pair> it = ans.iterator();
        while (it.hasNext() && i > 0) {
            res.add(it.next().line);
            i--;
        }

        return res;
    }

    public static void main(String[] args) {
        Most_Related_Query test = new Most_Related_Query();

        Scanner sc = new Scanner(System.in);
        int numLines = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numLines; i++) {
            test.processInput(sc.nextLine(), i);
        }

        int queryNumber = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < queryNumber; i++) {
            List<Integer> res6 = test.query(sc.nextLine(), 3);
            System.out.println(Arrays.toString(res6.toArray()));
        }
    }
}
