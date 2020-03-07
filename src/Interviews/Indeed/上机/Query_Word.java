package src.Interviews.Indeed.上机;

import java.util.*;

public class Query_Word {
    /**
     * 给出若干行的文字，再给query，输出所在的行数。行数要排序。先根据出现的频率排序，如果频率一样，
     * 就按照行数大小来排序。
     * 举例：
     * a b     //1
     * b a a   //2
     * a b b   //3
     * a       //4
     * query(a),输出就是（2，1，3,4），query(b)输出(3,1,2)
     * query(a & b) 输出(2,3,1)
     * query(a | b) 输出(2,3,1,4)
     * 因为query(a&b)(a|b)的频率计算都是单独算a和b出现次数然后求和的。
     * 只不过或的时候更宽容一点儿吧，有一个就行，与的时候要两个都在。
     *
     * 据说上机题只需要用到hashmap。
     * 然后计算频率的时候看清楚一些就行吧。
     * 还有上机题需要scanner么？
     *
     * 暂时没什么想法，除了暴力做之外，可以把每个词的query单独拎出来，然后遇到a&b的时候就比较好做了。
     * 上机只有一轮，90分钟，看命了。
     *
     */

    class Pair {
        int line;
        int count;

        public Pair(int line, int count) {
            this.line = line;
            this.count = count;
        }
    }

//    Map<String, TreeSet<Pair>> singleWordQuery = new HashMap<>();
    Map<String, List<Pair>> map = new HashMap<>();

    public void processInput(String line, int i) {
        if (line == null || line.length() == 0) return;

        String[] parts = line.split(" ");

        Map<String, Integer> count = new HashMap<>();
        for (String s : parts) {
            count.put(s, count.getOrDefault(s, 0) + 1);
            if (!map.containsKey(s)) {
                List<Pair> list = new ArrayList<>();
                map.put(s, list);

//                TreeSet<Pair> set = new TreeSet<>((a, b) -> a.count == b.count ? a.line - b.line : b.count - a.count);
//                singleWordQuery.put(s, set);
            }
        }

        for (String key : count.keySet()) {
            int c = count.get(key);
            Pair p = new Pair(i + 1, c);
            map.get(key).add(p);

//            singleWordQuery.get(key).add(p);
        }
    }

    public List<Integer> query(String s) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.isEmpty()) return res;

        String[] parts = s.split(" ");

        Set<Pair> ans = new TreeSet<>((a, b) -> a.count == b.count ? a.line - b.line : b.count - a.count);

        if (parts.length == 1) {
            List<Pair> l = map.get(s);

            if (l == null) return res;

            ans.addAll(l);
        } else {
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
        }

        Iterator<Pair> it = ans.iterator();
        while (it.hasNext()) {
            res.add(it.next().line);
        }

        return res;
    }

    /**
     * Big data test cases, use counting sort?
     */

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

        Query_Word test = new Query_Word();

        Scanner sc = new Scanner(System.in);
        int numLines = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numLines; i++) {
            test.processInput(sc.nextLine(), i);
        }

        int queryNumber = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < queryNumber; i++) {
            List<Integer> res6 = test.query(sc.nextLine());
            System.out.println(Arrays.toString(res6.toArray()));
        }
    }
}

