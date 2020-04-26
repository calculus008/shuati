package Interviews.Amazon;

import java.util.Arrays;

public class Min_In_Sliding_Window_For_Unsorted_Array {
    /**
     * 给一个fixed的unsorted array，找一个window里面的最小值，(!!!)不能iterate但是可以preprocessing这个array
     *
     * 面试官当时给我hint之后我把这个array变成个tree，每个节点预先存好start，end和这个window的min
     * 然后binary search start，start match之后binary search end直到两个都match返回存的min
     *
     * The purpose of the question is to use Segment Tree
     *
     * https://www.geeksforgeeks.org/segment-tree-set-1-range-minimum-query/
     *
     * https://algs4.cs.princeton.edu/99misc/SegmentTree.java.html
     *
     * Segment tree can be used to do preprocessing and query in moderate time. With segment tree,
     * pre-processing time is O(n) and time to for range minimum query is O(Logn). The extra space
     * required is O(n) to store the segment tree.
     */


    int st[]; //array to store segment tree

    /**
     * A utility function to get minimum of two numbers
     **/
    int minVal(int x, int y) {
        return (x < y) ? x : y;
    }

    /**
     * A utility function to get the middle index from corner indexes.
     **/
    int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

    /**  A recursive function to get the minimum value in a given
        range of array indexes. The following are parameters for
        this function.

        st    --> Pointer to segment tree
        index --> Index of current node in the segment tree. Initially
                   0 is passed as root is always at index 0
        ss & se  --> Starting and ending indexes of the segment
                     represented by current node, i.e., st[index]
        qs & qe  --> Starting and ending indexes of query range
     **/
    int RMQUtil(int start, int end, int qs, int qe, int index) {
        /**
         * If segment of this node is a part of given range,
         *  then return the min of the segment
         ***/
        if (qs <= start && qe >= end) {
            return st[index];
        }

        /**
         * If segment of this node is outside the given range
         **/
        if (end < qs || start > qe) {
            return Integer.MAX_VALUE;
        }

        /**
         * If a part of this segment overlaps with the given range
         **/
       int mid = getMid(start, end);

       return minVal(RMQUtil(start, mid, qs, qe, 2 * index + 1),
                RMQUtil(mid + 1, end, qs, qe, 2 * index + 2));
    }

    /**
     * Return minimum of elements in range from index qs (query start)
     *  to qe (query end).  It mainly uses RMQUtil()
     *  **/
    int RMQ(int n, int qs, int qe) {
        // Check for erroneous input values
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }

        return RMQUtil(0, n - 1, qs, qe, 0);
    }

    /**
     * A recursive function that constructs Segment Tree for array[ss..se].
     * si is index of current node in segment tree st
     **/
    int constructSTUtil(int arr[], int start, int end, int idx) {
        /**
         * If there is one element in array, store it in current node of segment tree and return
         **/
        if (start == end) {
            st[idx] = arr[start];
            return arr[start];
        }

        /**
         * If there are more than one elements, then recur for left and right subtrees and store
         * the minimum of two values in this node
         **/
        int mid = getMid(start, end);
        st[idx] = minVal(constructSTUtil(arr, start, mid, idx * 2 + 1),
                constructSTUtil(arr, mid + 1, end, idx * 2 + 2));

        return st[idx];
    }

    /**
     * Function to construct segment tree from given array. This function allocates memory for
     * segment tree and calls constructSTUtil() to fill the allocated memory
     *
     * Construction of Segment Tree from given array
     * We start with a segment arr[0 . . . n-1]. and every time we divide the current segment into
     * two halves(if it has not yet become a segment of length 1), and then call the same procedure
     * on both halves, and for each such segment, we store the minimum value in a segment tree node.
     *
     * All levels of the constructed segment tree will be completely filled except the last level.
     * Also, the tree will be a Full Binary Tree because we always divide segments in two halves at
     * every level. Since the constructed tree is always full binary tree with n leaves, there will
     * be n-1 internal nodes. So total number of nodes will be 2*n – 1.
     *
     * Height of the segment tree will be log(n). Since the tree is represented using array and
     * relation between parent and child indexes must be maintained, size of memory allocated for
     * segment ree will be 2 * 2 ^ h - 1.
     **/
    void constructST(int arr[], int n) {
        /**
         * Allocate memory for segment tree by calculating Height of segment tree
         **/
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));

        /** Maximum size of segment tree **/
        int max_size = 2 * (int) Math.pow(2, x) - 1;
        st = new int[max_size];

        /** Fill the allocated memory st **/
        constructSTUtil(arr, 0, n - 1, 0);
    }

    // Driver program to test above functions
    public static void main(String args[]) {
        int arr[] = {1, 3, 2, 7, 9, 11};
        int n = arr.length;
        Min_In_Sliding_Window_For_Unsorted_Array tree = new Min_In_Sliding_Window_For_Unsorted_Array();

        // Build segment tree from given array
        tree.constructST(arr, n);

        /**
         * Test single query query
         */
        int qs = 1;  // Starting index of query range
        int qe = 5;  // Ending index of query range

        // Print minimum value in arr[qs..qe]
        System.out.println("Minimum of values in range [" + qs + ", "
                + qe + "] is = " + tree.RMQ(n, qs, qe));

        /**
         * Test sliding window min
         */
        int windowSize = 3;
        int[] res = new int[arr.length - windowSize + 1];
        for (int i = 0; i < arr.length - windowSize + 1; i++) {
            int end = i + windowSize - 1;
            res[i] = tree.RMQ(arr.length, i, end);
            System.out.println("Range : " + i + ", " + end + ", res : " + res[i]);
        }

        System.out.println(Arrays.toString(res));
    }
}
