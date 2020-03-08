package Interviews.Indeed.上机;

import java.util.*;

public class Most_Related_Query_2 {
    class Element {
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

    class Pair {
        int id;
        int count;

        public Pair(int id, int count) {
            this.id = id;
            this.count = count;
        }
    }

    Map<String, List<Integer>> map = new HashMap<>();

    /**
     * Build inverted index
     */
    public void processInput(String line, int id) {
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

//        System.out.println("------");
//        for (String key : map.keySet()) {
//            System.out.println(key + ":" + Arrays.toString(map.get(key).toArray()));
//        }
//        System.out.println("------");
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
//            System.out.println(p.id + ", " + p.count);
            res.add(0, p.id);
        }

        return res;
    }

    public static void main(String[] args) {
        Most_Related_Query_2 test = new Most_Related_Query_2();

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
