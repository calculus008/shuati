package leetcode;

/**
 * Created by yuank on 4/29/18.
 */

/**
 * https://www.youtube.com/watch?v=WbafSgetDDk&t=33s
 *
 * https://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
 *
 * The idea is based on the fact that all positive integers can be represented as sum of powers of 2. For example 19 can be represented as 16 + 2 + 1.
 * Every node of BI Tree stores sum of n elements where n is a power of 2. For example, in the above first diagram for getSum(),
 * sum of first 12 elements can be obtained by sum of last 4 elements (from 9 to 12) plus sum of 8 elements (from 1 to 8).
 * The number of set bits in binary representation of a number n is O(Logn). Therefore, we traverse at-most O(Logn) nodes in both
 * getSum() and update() operations. Time complexity of construction is O(nLogn) as it calls update() for all n elements.
 *
 * https://en.wikipedia.org/wiki/Fenwick_tree
 *
 * Each element whose index i is a power of 2 contains the sum of the first i elements.
 * Elements whose indices are the sum of two (distinct) powers of 2 contain the sum of the elements since the preceding power of 2.
 * In general, each element contains the sum of the values since its parent in the tree, and that parent is found by clearing the
 * least-significant bit in the index.
 *
 * To find the sum up to any given index, consider the binary expansion of the index, and add elements which correspond to each 1 bit
 * in the binary form.
 * For example, say one wishes to find the sum of the first eleven values. Eleven is 10112 in binary. This contains three 1 bits,
 * so three elements must be added: 10002, 10102, and 10112. These contain the sums of values 1–8, 9–10, and 11, respectively.
 * To modify the eleventh value, the elements which must be modified are 10112, 11002, 100002, and all higher powers of 2 up to the size of the array.
 * These contain the sums of values 11, 9–12, and 1–16, respectively.
 * The maximum number of elements which may need to be updated is limited by the number of bits in the size of the array.
 *
 * Also called Fenwick tree, solve the problem of improving time complexity of calculating range sum
 *  1.O(logn) for update and query
 *  2.Update and query forms different trees
 *  3.Root nodes is always with index of 2 ^ k
 *  4.Use trick of i & (-i) to get the low bit (lowest bit with 1, 0110, its lowbit is 0010)
 *  5.Tree has depth of log(n)
 *  6.Index for sum is always input array index plus 1
 */
class BinaryIndexedTree {
    public int[] sum;

    public BinaryIndexedTree(int n) {
        sum = new int[n + 1];
    }

    public void update(int i, int delta) {
        while (i < sum.length) {
            sum[i] += delta;
            i += lowBit(i);
        }
    }

    public int query(int i) {
        int res = 0;
        while (i > 0) {
            res += sum[i];
            i -= lowBit(i);
        }
        return res;
    }

    private int lowBit(int i) {
        return i & (-i);
    }
}
