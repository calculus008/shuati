package Interviews.Indeed.Practice;

import java.util.*;

public class Query_Word_Practice {
    class Pair {
        int line;
        int count;

        public Pair(int line, int count) {
            this.line = line;
            this.count = count;
        }
    }

    Map<String, TreeSet<Pair>> singleWordQuery = new HashMap<>();
    Map<String, List<Pair>> map = new HashMap<>();

    public void processInput(String[] lines) {
        if (lines == null || lines.length == 0) return;

        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(" ");

            Map<String, Integer> count = new HashMap<>();
            for (String s : parts) {
                count.put(s, count.getOrDefault(s, 0) + 1);
                if (!map.containsKey(s)) {
                    List<Pair> list = new ArrayList<>();
                    map.put(s, list);

                    TreeSet<Pair> set = new TreeSet<>((a, b) -> a.count == b.count ? a.line - b.line : b.count - a.count);
                    singleWordQuery.put(s, set);
                }
            }

            for (String key : count.keySet()) {
                int c = count.get(key);
                Pair p = new Pair(i + 1, c);

                map.get(key).add(p);
                singleWordQuery.get(key).add(p);
            }
        }
    }

    public void processInput1(String line, int i) {
        if (line == null || line.length() == 0) return;

        String[] parts = line.split(" ");

        Map<String, Integer> count = new HashMap<>();
        for (String s : parts) {
            count.put(s, count.getOrDefault(s, 0) + 1);
            if (!map.containsKey(s)) {
                List<Pair> list = new ArrayList<>();
                map.put(s, list);

                TreeSet<Pair> set = new TreeSet<>((a, b) -> a.count == b.count ? a.line - b.line : b.count - a.count);
                singleWordQuery.put(s, set);
            }
        }

        for (String key : count.keySet()) {
            int c = count.get(key);
            Pair p = new Pair(i + 1, c);

            map.get(key).add(p);
            singleWordQuery.get(key).add(p);
        }
    }

    public List<Integer> query(String s) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.isEmpty()) return res;

        String[] parts = s.split(" ");

        if (parts.length == 1) {
            Set<Pair> set = singleWordQuery.get(s);

            if (set == null) return res;

            Iterator<Pair> it = set.iterator();
            while (it.hasNext()) {
                res.add(it.next().line);
            }

            return res;
        }

        Set<Pair> ans = new TreeSet<>((a, b) -> a.count == b.count ? a.line - b.line : b.count - a.count);

        List<Pair> l1 = map.get(parts[0]);
        List<Pair> l2 = map.get(parts[2]);

        if (l1 == null && l2 == null) return res;

        if (l1 == null || l2 == null) {
            if (parts[1].equals("&")) {
                return res;
            } else {
                if (l1 == null) {
                    ans.addAll(l2);
                } else {
                    ans.addAll(l1);
                }
            }
        } else {
            int m = 0, n = 0;
            while (m < l1.size() && n < l2.size()) {
                if (l1.get(m).line == l2.get(n).line) {
                    Pair sum = new Pair(l1.get(m).line, l1.get(m).count + l2.get(n).count);
                    ans.add(sum);
                    m++;
                    n++;
                } else if (l1.get(m).line < l2.get(n).line) {
                    m++;
                } else {
                    n++;
                }
            }

            if (parts[1].equals("|")) {
                while (m < l1.size()) {
                    ans.add(l1.get(m++));
                }

                while (n < l2.size()) {
                    ans.add(l2.get(n++));
                }

            }
        }

        Iterator<Pair> it = ans.iterator();
        while (it.hasNext()) {
            res.add(it.next().line);
        }

        return res;
    }

    /**
     *      * 举例：
     *      * a b     //1
     *      * b a a   //2
     *      * a b b   //3
     *      * a       //4
     *      * query(a),输出就是（2，1，3, 4），
     *        query(b)输出      (3,1,2)
     *      * query(a & b) 输出 (2,3,1)
     *      * query(a | b) 输出 (2,3,1,4)
     */
    public static void main(String[] args) {
        String[] input =
                {"a b",
                "b a a",
                "a b b",
                "a"};

        Query_Word_Practice test = new Query_Word_Practice();

//        test.processInput(input);
//        List<Integer> res1 = test.query("a");
//        System.out.println(Arrays.toString(res1.toArray()));
//
//        List<Integer> res2 = test.query("b");
//        System.out.println(Arrays.toString(res2.toArray()));
//
//        List<Integer> res3 = test.query("a & b");
//        System.out.println(Arrays.toString(res3.toArray()));
//
//        List<Integer> res4 = test.query("a | b");
//        System.out.println(Arrays.toString(res4.toArray()));
//
//        List<Integer> res5 = test.query("c & b");
//        System.out.println(Arrays.toString(res5.toArray()));


        Scanner sc = new Scanner(System.in);
        int numLines = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numLines; i++) {
            test.processInput1(sc.nextLine(), i);
        }

        int queryNumber = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numLines; i++) {
            List<Integer> res6 = test.query(sc.nextLine());
            System.out.println(Arrays.toString(res6.toArray()));
        }
    }
}
