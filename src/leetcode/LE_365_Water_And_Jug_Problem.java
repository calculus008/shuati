package leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LE_365_Water_And_Jug_Problem {
    /**
     * You are given two jugs with capacities x and y litres. There is an infinite amount
     * of water supply available. You need to determine whether it is possible to measure
     * exactly z litres using these two jugs.
     *
     * If z liters of water is measurable, you must have z liters of water contained within
     * one or both buckets by the end.
     *
     * Operations allowed:
     *
     * Fill any of the jugs completely with water.
     * Empty any of the jugs.
     * Pour water from one jug into another till the other jug is completely full or the
     * first jug itself is empty.
     *
     * Example 1: (From the famous "Die Hard" example)
     * Input: x = 3, y = 5, z = 4
     * Output: True
     *
     * Example 2:
     * Input: x = 2, y = 6, z = 5
     * Output: False
     *
     * medium
     */

    /**
     * BFS
     */
    class Solution_BFS{
        public boolean canMeasureWater(int x, int y, int z) {
            if (x + y < z) return false;
            if (x == z || y == z || x + y == z) return true;

            Queue<Integer> q = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();

            q.offer(0);

            int[] options = {x, y, -x, -y};

            while (!q.isEmpty()) {
                int cur = q.poll();
                if (cur == z) return true;

                for (int o : options) {
                    int next = cur + o;

                    if (next < 0 || next > x + y || visited.contains(next)) continue;

                    if (next == z) return true;

                    q.offer(next);
                    visited.add(next);
                }
            }

            return false;
        }
    }

    class Solution_BFS_1 {
        class Solution {
            class State {
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

            public boolean canMeasureWater(int x, int y, int z) {
                if (x + y < z) return false;
                if (x == z || y == z || x + y == z) return true;

                Queue<State> q = new LinkedList<>();
                Set<State> visited = new HashSet<>();

                State init = new State(0, 0);
                q.offer(init);
                visited.add(init);

                while (!q.isEmpty()) {
                    State cur = q.poll();
                    if (cur.a + cur.b == z) return true;

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
                        q.offer(s);
                        visited.add(s);
                    }
                }

                return false;
            }
        }
    }

    /**
     * Math solution
     * https://leetcode.com/problems/water-and-jug-problem/discuss/83715/Math-solution-Java-solution
     */
    class Solution_GCD {
        public boolean canMeasureWater(int x, int y, int z) {
            if (x + y < z) return false;
            if (x == z || y == z || x + y == z) return true;

            int k = gcd(x, y);
            return z % k == 0;
        }

        private int gcd(int a, int b) {
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }

            return a;
        }
    }
}
