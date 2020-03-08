package Interviews.Indeed.上机;

import java.util.*;

public class Most_Related_Query_1 {
    /**
     * Most Related Word
     *
     * 找相关词那道，就是A输⼊入java，php，B输⼊入java，python，然后C输⼊入了了 php，问你给C推荐的下⼀一个单词应该是什什么。
     * 搜词，然后按关联度推荐词 (??)
     *
     * #A
     * 上机题貌似就万年不变的query与job description去match。
     * 具体来说，给了一堆job description，每个是一个string，确保了没有标点符号、只有小写字母，
     * 比如{"we want someone good at java and spring", "are you good at cpp"}；
     * 然后给一堆query，要求对每个query找出最match的最多10个job description的id，依次输出这些id，
     * 如果一个都没有就输出-1。对于match程度的定义，是有一个叫match count的概念，就是query与某个jd匹配了多少单词。
     * 例如一条query是"good at java"，那么对于上述的两条jd的match count分别是2和1。match count越大的jd，
     * 就代表match程度越高，如果有两个jd具有相同的match count，那就认为id小的比id大的更match。
     *
     * 另外给了一些限定条件使得问题简单了很多，例如每个query都保证没有重复单词；每个jd可能有重复，
     * 但计算match count时每个单词只match一次；对于一个单词的不同形态当做不同的单词处理（get, gets）等等。
     *
     * 我是用inverted index的思路去做的，建了一个对于所有jd里每个word到出现这个word的jd的map，
     * 给了一个query就traverse这个query里的每个单词，重建每个jd的match count，最后再按规格取前10就行。
     * test case都跑通了。
     *
     * 然后后面还有两道主观题，一个是问如果给你一天时间做这题你要怎么优化，然后如果这个东西每个月会被
     * query billion次你要怎么优化；另一个是说说你的思路，以及分析时间、空间的复杂度。
     *
     * #B
     * HackerRank上做的是给定一堆document和一堆query，返回每个query的top10 documents，按match降序，
     * 如果有tie就按doc id升序。如果有个query term出现在某个doc里就算一次match，同一个term出现多次只算一个(!!!)。
     * */


    /**
     * Key points
     * 1.Must dedup before putting into map when processing each line.
     * 2.k-way merge to calculate match count, use one pq, must use class Element which includes iterator and idx value
     * 3.For final output, need another pq to get top n elements based on match count and index, be careful when wirting
     *   comparator for this pq.
     * 4.For result pq, need to have another class Pair, which has match count value and index.
     *
     *
     * 1.如果给你一天时间做这题你要怎么优化，
     * a.Improve match algorithm, not only consider word, also consider frequency and position of the key word in search
     * b.Create index for high frequency search term, saving the time spent on merge result for those terms.
     *   (position index, nextwork index, phrase index)
     * c.Inverted Index in large scale, memory on a single machine may not be big enough, must scale:
     *   1.Index term in mem, posting list on disk
     *   2.Index construction, disk-based index building (sort/external-merge based)
     * d.For big data, use mapReduce to build inverted index with mappers and reducers.
     * 4.Optimize query, use skip list
     *
     * 2.然后如果这个东西每个月会被query billion次你要怎么优化
     * Billion queries :
     * QPS = 10 ^ 9 / 24 * 60 * 60 * 30 = 385 (a few hundred)
     * a.Distributed index, load-balanced
     * b.Create index for high frequency search term, saving the time spent on merge result for those terms.
     *   (position index, nextword index, phrase index)
     * c.Cache
     *
     *
     */
    static class Element {
        Iterator<Integer> it;
        int id;

        public Element(Iterator<Integer> it, int id) {
            this.it = it;
            this.id = id;
        }

        public Element(Iterator<Integer> it) {
            this.it = it;
        }
    }

    static class Pair {
        int id;
        int count;

        public Pair(int id, int count) {
            this.id = id;
            this.count = count;
        }
    }

    static Map<String, List<Integer>> map = new HashMap<>();

    /**
     * Build inverted index
     */
    public static void processInput(String line, int id) {
        if (line == null || line.length() == 0) return;

        String[] parts = line.split(" ");

        /**
         * dedup
         */
        Set<String> set = new HashSet<>();
        for (String part : parts) {
            set.add(part);
        }

        for (String s : set) {
            if (!map.containsKey(s)) {
                List<Integer> list = new ArrayList<>();
                map.put(s, list);
            }
            map.get(s).add(id + 1);
        }

        System.out.println("------");
        for (String key : map.keySet()) {
            System.out.println(key + ":" + Arrays.toString(map.get(key).toArray()));
        }
        System.out.println("------");

    }

    /**
     * Return top n match score
     *
     * Time : mlogk, k - number of lines or docs, m is sum of the length of postings for each word
     */
    public static List<Integer> query(String s, int n) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.isEmpty()) return res;

        String[] parts = s.split(" ");

        /**
         * !!!
         * Use heap to save result, it can improve both space and time,
         *
         * Time  : O(mlogn), m is the number of unique ids among all the key words.
         *         If we use TreeSet, time is O(mlogm).
         * Space : O(n), n is the number of top elements required, for exmaple : 10.
         *
         * Notice : since we want top n largest number, this should be a min heap.
         *          so it's tricky to write the comparator, it should be the reverse
         *          of the max heap comparator (!!!)
         */
        PriorityQueue<Pair> resPq = new PriorityQueue<>((a, b) -> a.count == b.count ? b.id - a.id : a.count - b.count);

        /**
         * use k-way merge
         */
        PriorityQueue<Element> pq = new PriorityQueue<>(n, (a, b) -> a.id - b.id);
        for (String part : parts) {
            if (!map.containsKey(part)) continue;

            Iterator<Integer> it = map.get(part).iterator();
            if (it.hasNext()) {
                pq.offer(new Element(it, it.next()));
            }
        }

        if (pq.isEmpty()) return res;

        Integer pre = null;
        int count = 0;
        while (!pq.isEmpty()) {
            Element cur = pq.poll();

            if (pre == null) {
                pre = cur.id;
                count = 1;
            } else {
                if (cur.id == pre) {
                    count++;
                } else {
                    resPq.offer(new Pair(pre, count));
                    if (resPq.size() > n) {
                        resPq.poll();
                    }

                    pre = cur.id;
                    count = 1;
                }
            }

            if (cur.it.hasNext()) {
                int nextId = cur.it.next();
                cur.id = nextId;
                pq.offer(cur);
            }

            if (pq.isEmpty()) {
                resPq.offer(new Pair(pre, count));
                if (resPq.size() > n) {
                    resPq.poll();
                }
            }
        }

        while (!resPq.isEmpty()) {
            Pair p = resPq.poll();
            System.out.println(p.id + ", " + p.count);
            res.add(0, p.id);
        }

        return res;
    }

    public static void main(String[] args) {
//        String[] input = {
//                "java java java",
//                "java cpp",
//                "java and cpp and python",
//                "java and cpp cpp python",
//                "java java python"
//            };

        String[] input = {
                "java java java",
                "java cpp",
                "java and cpp",
                "java and cpp cpp",
                "java and cpp and python"
        };

        for (int i = 0; i < input.length; i++) {
            processInput(input[i], i);
        }

        List<Integer> res1 = query("java cpp", 4);
        System.out.println(Arrays.toString(res1.toArray()));

//        Most_Related_Query test = new Most_Related_Query();
//
//        Scanner sc = new Scanner(System.in);
//        int numLines = Integer.parseInt(sc.nextLine());
//        for (int i = 0; i < numLines; i++) {
//            test.processInput(sc.nextLine(), i);
//        }
//
//        int queryNumber = Integer.parseInt(sc.nextLine());
//        for (int i = 0; i < queryNumber; i++) {
//            List<Integer> res6 = test.query(sc.nextLine(), 3);
//            System.out.println(Arrays.toString(res6.toArray()));
//        }
    }
}
