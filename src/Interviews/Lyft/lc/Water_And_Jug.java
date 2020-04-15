package Interviews.Lyft.lc;

import java.util.*;

/**
 * Same as LE_365_Water_And_Jug_Problem, but requirement is to print one possible solution path.
 *
 * follow up：如果target可以等于两个bucket水量之和呢 ??
 *
 * follow up：如果你的这个function很多组都在用，每个组都有不同的要求，你觉得你能怎么扩展你的这个函数？
 * 感觉面试官想要知道如果bucket的个数不是固定的两个的话怎么扩展这个函数。改一改visited和queue里面存的状态的格式就好了，
 * 对于python选手来说直接把数组tuple()一下然后存成状态就行了。
 *
 * Note:基本题和followup面试官都要求写testcase和实际跑起来。
 * 1个小时的面试，面试官说题出完的时候还剩半个小时，应该就只有这一个题了。
 *
 * 只不过要求给出一个最佳解决方案
 * 一开始以为就是力扣原题，开始分析gcd，小哥提示说不要算，只要求出一个解决方案就行
 * 于是开始用DFS，写了一会发现DFS不对，再改用BSF磕磕碰碰总算解出来并且run通过
 *
 * 用bfs track路径？很浪费空间吧? 存个parent map就好。。相对dfs贵一点而已
 * 可能一个点对应多个parent，不过你只要一个可行解
 *
 * water 和 bucket 找达到target amount of water 的最短倒水路径。 用bfs 就可以了，
 * 但是我卡在如何maintain 每一个state 上。 大家加油！
 */
public class Water_And_Jug {
    static class State {
        int a;
        int b;

        public State(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int hashCode() {
            return 31 * a + b;
        }

        public boolean equals(Object o) {
            State s = (State)o;
            return s.a == this.a && s.b == this.b;
        }
    }

    public static List<State> canMeasureWaterWithPath(int x, int y, int z) {
        List<State> res = new ArrayList<>();
        if (x + y < z) return res;

        Queue<State> q = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<State, List<State>> parent = new HashMap<>();

        State start = new State(0, 0);
        q.offer(start);
        visited.add(start);

        while (!q.isEmpty()) {
            State cur = q.poll();
            if (cur.a + cur.b == z) return backtrace(start, cur, parent);

            Queue<State> next = new LinkedList<>();
            next.offer(new State(cur.a, 0));//empty jug2
            next.offer(new State(cur.a, y));//pour to jug2
            next.offer(new State(0, cur.b));//empty jug1
            next.offer(new State(x, cur.b));//pour to jug1
            //pour water from b to a
            next.offer(new State(Math.min(cur.a + cur.b, x), cur.a + cur.b < x ? 0 : cur.b - (x - cur.a)));
            //pour water from b to a
            next.offer(new State(cur.a + cur.b < y ? 0 : cur.a - (y - cur.b), Math.min(cur.a + cur.b, y)));

            for (State s : next) {
                if (visited.contains(s)) continue;

                if (!parent.containsKey(s)) {
                    parent.put(s, new ArrayList<>());
                }
                parent.get(s).add(cur);

                q.offer(s);
                visited.add(s);
            }
        }

        return res;
    }

    private static List<State> backtrace(State start, State end, Map<State, List<State>> parent) {
        List<State> res = new ArrayList<>();
        res.add(end);

        State last = end;
        while (!last.equals(start)) {
            last = parent.get(last).get(0);
            res.add(0, last);
        }

        return res;
    }

    private static void printResult(List<State> list) {
        if (list.size() == 0) {
            System.out.println("No Solution");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (State s : list) {
            sb.append("(" + s.a + ", " + s.b + ") -> ");
        }

        sb.setLength(sb.length() - 4);
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        printResult(canMeasureWaterWithPath(3, 5, 4));
        printResult(canMeasureWaterWithPath(2, 6, 4));
        printResult(canMeasureWaterWithPath(2, 6, 5));
        printResult(canMeasureWaterWithPath(2, 6, 6));
        printResult(canMeasureWaterWithPath(2, 6, 2));
        printResult(canMeasureWaterWithPath(2, 6, 8));
    }
}
