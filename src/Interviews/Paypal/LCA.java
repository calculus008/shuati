package Interviews.Paypal;

    /**
     * Given a binary tree represented as parent array, find Lowest Common Ancestor between two nodes ‘m’ and ‘n’.
     */
    // Java program to find LCA in a tree represented
// as parent array.
import java.util .*;

    class GFG {

        // Maximum value in a node
        static int MAX = 1000;

        // Function to find the Lowest common ancestor
        static int findLCA(int n1, int n2, int parent[]) {
            // Create a visited vector and mark
            // all nodes as not visited.
            boolean[] visited = new boolean[MAX];

            visited[n1] = true;

            // Moving from n1 node till root and
            // mark every accessed node as visited
            while (parent[n1] != -1) {
                visited[n1] = true;

                // Move to the parent of node n1
                n1 = parent[n1];
            }

            visited[n1] = true;

            // For second node finding the first
            // node common
            while (!visited[n2])
                n2 = parent[n2];

            return n2;
        }

        // Insert function for Binary tree
        static void insertAdj(int parent[], int i, int j) {
            parent[i] = j;
        }

        // Driver Functiom
        public static void main(String[] args) {
            // Maximum capacity of binary tree
            int[] parent = new int[MAX];

            // Root marked
            parent[20] = -1;
            insertAdj(parent, 8, 20);
            insertAdj(parent, 22, 20);
            insertAdj(parent, 4, 8);
            insertAdj(parent, 12, 8);
            insertAdj(parent, 10, 12);
            insertAdj(parent, 14, 12);

            System.out.println(findLCA(10, 14, parent));
        }
    }

