package lintcode;

/**
 * Created by yuank on 10/9/18.
 */
public class LI_589_Connecting_Graph {
    /**
         Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.

         You need to support the following method:

         connect(a, b), add an edge to connect node a and node b`.
         query(a, b), check if two nodes are connected
         Example
         5 // n = 5
         query(1, 2) return false
         connect(1, 2)
         query(1, 3) return false
         connect(2, 4)
         query(1, 4) return true
     */

    /**
     * Union Set
     */

    public class ConnectingGraph {
        int[] nodes;

        public ConnectingGraph(int n) {
            nodes = new int[n + 1];
            //!!!
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = i;
            }
        }

        public void connect(int a, int b) {
            int ra = find(a);
            int rb = find(b);

            if (ra == rb) return;

            nodes[rb] = ra;
        }

        public boolean query(int a, int b) {
            int ra = find(a);
            int rb = find(b);

            return ra == rb;
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
