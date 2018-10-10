package lintcode;

/**
 * Created by yuank on 10/9/18.
 */
public class LI_591_Connecting_Graph_III {
    /**
         Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.

         You need to support the following method:

         connect(a, b), an edge to connect node a and node b
         query(), Returns the number of connected component in the graph
         Example
         5 // n = 5
         query() return 5
         connect(1, 2)
         query() return 4
         connect(2, 4)
         query() return 3
         connect(1, 4)
         query() return 3
     */

    public class ConnectingGraph3 {
        int[] nodes;
        /****/
        int count;

        public ConnectingGraph3(int n) {
            nodes = new int[n + 1];
            //!!!
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = i;
            }
            /****/
            count = n;
        }

        public void connect(int a, int b) {
            int ra = find(a);
            int rb = find(b);

            if (ra == rb) return;

            nodes[rb] = ra;
            /****/
            count--;
        }

        public int query() {
            return count;
        }

        private int find(int x) {
            //Recursive version
            if (nodes[x] == x) return x;

            return nodes[x] = find(nodes[x]);

            // while(nodes[x] != x) {
            //     nodes[x] = nodes[nodes[x]];
            //     x = nodes[x];
            // }

            // return x;
        }
    }
}
