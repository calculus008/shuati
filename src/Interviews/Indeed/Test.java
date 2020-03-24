package Interviews.Indeed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Test {


        static class Pair{
            int id;
            long count;

            public Pair(int id, long count) {
                this.id = id;
                this.count = count;
            }
        }

        static class Element {
            Iterator<Pair> it;
            Pair p;

            public Element(Iterator<Pair> it, Pair p) {
                this.it = it;
                this.p = p;
            }
        }

        static Map<String, List<Pair>> map = new HashMap<>();

        public static void storeDocument(final String document, final int documentNumber) {
            if (document == null || document.length() == 0) return;

            String[] parts = document.split(" ");
            Map<String, Long> count = new HashMap<>();

            for (String s : parts) {
                long num = 0L;
                if (count.containsKey(s)) {
                    num = count.get(s) + 1;
                }
                count.put(s, num);
                if (!map.containsKey(s)) {
                    List<Pair> list = new ArrayList<>();
                    map.put(s, list);
                }
            }

            for (String key : count.keySet()) {
                System.out.println(key + ", "+ count.get(key));
                long val = count.get(key);
                Pair p = new Pair(documentNumber, val);
                map.get(key).add(p);
            }
        }

        public static String performSearch(final String search) {
            if (search == null || search.length() == 0) return "-1";

            String[] parts = search.split(" ");

            PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.p.id - b.p.id);

            for (String part : parts) {
                if (!map.containsKey(part)) {
                    continue;
                }

                Iterator<Pair> it = map.get(part).iterator();
                if (it.hasNext()) {
                    pq.offer(new Element(it, it.next()));
                }
            }

            if (pq.isEmpty()) return "-1";

            Integer pre = null;
            long sum = 0;

            TreeSet<Pair> ans = new TreeSet<>((a, b) -> a.count == b.count ? a.id - b.id : Long.compare(b.count, a.count));

            while (!pq.isEmpty()) {
                Element e = pq.poll();

                if (pre != null && e.p.id == pre) {
                    sum += e.p.count;
                } else {
                    if (pre != null) {
                        ans.add(new Pair(pre, sum));
                    }

                    pre = e.p.id;
                    sum = e.p.count;
                }

                if (e.it.hasNext()) {
                    e.p = e.it.next();
                    pq.offer(e);
                }

                if (pq.isEmpty()) {
                    ans.add(new Pair(pre, sum));
                }
            }

            // if (ans.isEmpty()) return "-1";

            StringBuilder sb = new StringBuilder();
            Iterator<Pair> it = ans.iterator();
            int i = 10;
            while (it.hasNext() && i > 0) {
                Pair p = it.next();
                System.out.println("id="+p.id + ", count=" + p.count);
                sb.append(p.id).append(" ");
            }

            return sb.toString();
        }



        public static void main(String args[] ) throws Exception {
            /* Enter your code here. Read input from STDIN. Print output to STDOUT */
            final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            final int N = Integer.parseInt(br.readLine());
            // Read documents
            for (int i = 0; i < N; i++) {
                storeDocument(br.readLine(), i);
            }

            final int M = Integer.parseInt(br.readLine());
            // Read searches
            for (int j = 0; j < M; j++) {
                System.out.println(performSearch(br.readLine()));
            }
        }

}
