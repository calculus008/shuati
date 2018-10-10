package lintcode;

/**
 * Created by yuank on 10/9/18.
 */
public class LI_590_Connecting_Graph_II {
    /**
         Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.

         You need to support the following method:

         connect(a, b), an edge to connect node a and node b
         query(a), Returns the number of connected component nodes which include node a.
         Example
         5 // n = 5
         query(1) return 1
         connect(1, 2)
         query(1) return 2
         connect(2, 4)
         query(1) return 3
         connect(1, 4)
         query(1) return 3
     */

    public class ConnectingGraph2 {
        int[] nodes;
        /****/
        int[] size;

        public ConnectingGraph2(int n) {
            nodes = new int[n + 1];
            /****/
            size = new int[n + 1];

            //!!!
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = i;
                /****/
                size[i] = 1;
            }
        }

        public void connect(int a, int b) {
            int ra = find(a);
            int rb = find(b);

            if (ra == rb) return;

            /****/
            size[ra] += size[rb];
            nodes[rb] = ra;
        }

        public int query(int a) {
            int ra = find(a);

            /***/
            return size[ra];
        }

        private int find(int x) {
            while(nodes[x] != x) {
                nodes[x] = nodes[nodes[x]];
                x = nodes[x];
            }

            return x;
        }
    }
}
