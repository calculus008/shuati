package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class LE_1146_Snapshot_Array {
    /**
     * Implement a SnapshotArray that supports the following interface:
     *
     * SnapshotArray(int length) initializes an array-like data structure with the given length.
     * Initially, each element equals 0.
     * void set(index, val) sets the element at the given index to be equal to val.
     * int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
     * int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
     *
     *
     * Example 1:
     *
     * Input: ["SnapshotArray","set","snap","set","get"]
     * [[3],[0,5],[],[0,6],[0,0]]
     * Output: [null,null,0,null,5]
     * Explanation:
     * SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
     * snapshotArr.set(0,5);  // Set array[0] = 5
     * snapshotArr.snap();  // Take a snapshot, return snap_id = 0
     * snapshotArr.set(0,6);
     * snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
     *
     *
     * Constraints:
     *
     * 1 <= length <= 50000
     * At most 50000 calls will be made to set, snap, and get.
     * 0 <= index < length
     * 0 <= snap_id < (the total number of times we call snap())
     * 0 <= val <= 10^9
     *
     * Medium
     */

    /**
     *  HashMap + Binary Search => TreeMap
     *
     *  按照题意，最直接或者"brutal force"的解法是用hashmap, snap ID -> 每次set后的array.
     *  但是，如果array size很大,set的次数也很大，很浪费空间, O(k * n), k : number of set()
     *  is called, n : size of the array.
     *
     *  如何节省空间呢？因为有snap ID, 这里肯定要用some kind of hashmap.如果set()的次数小，也就是说，每次只有
     *  一个数字被改动了，其余的数字都不变。因此，可以只存每次变动的数字。可以考虑，每个数字（对应于array中的每个
     *  index)对应一个hashmap : snap ID -> 对应每次set(idx, value), 仅仅modify hashmap[idx], 记录val, 也
     *  就是set()后的值，然后update snap ID (加一）。
     *
     *  问题是，如何get()? 对应于题中的例子，在最后的get(0,0)时，：
     *
     *  idx  -> hashmap
     *  0    -> {[0 => 5], [1 => 6]}
     *  1    -> {[0 => 0]}
     *  2    -> {[0 => 0}
     *
     *  get(0, 0), we can get "5". But how about get(1, 0), there's no snap ID "1" in hashmap for idx "1".
     *
     *  So here we can binary search => TreeMap to find the floor key of the targeted snap ID. Here we get
     *  snap ID "0", then return 0.
     *
     *
     * Array of TreeMaps
     * key : snapId
     * value : value set by "set()"
     *
     * https://leetcode.com/problems/snapshot-array/discuss/350562/JavaPython-Binary-Search
     *
     * Intuition
     * Instead of copy the whole array,
     * we can only record the changes of set.
     *
     *
     * Explanation
     * The idea is, the whole array can be large,
     * and we may take the snap tons of times.
     * (Like you may always ctrl + S twice)
     *
     * Instead of record the history of the whole array,
     * we will record the history of each cell.
     * And this is the minimum space that we need to record all information.
     *
     * For each A[i], we will record its history.
     * With a snap_id and its value.
     *
     * When we want to get the value in history, just binary search the time point.
     *
     *
     * Complexity
     * Time O(logS)
     * Space O(S)
     * where S is the number of set()  called.
     *
     * SnapshotArray(int length) is O(N) time
     * set(int index, int val) is O(1) in Python and O(log(Snap)) in Java
     * snap() is O(1)
     * get(int index, int snap_id) is O(log(Snap))
     */
    class SnapshotArray {
        TreeMap<Integer, Integer>[] A;
        int snap_id = 0;

        public SnapshotArray(int length) {
            A = new TreeMap[length];
            for (int i = 0; i < length; i++) {
                A[i] = new TreeMap<Integer, Integer>();
                A[i].put(0, 0);
            }
        }

        public void set(int index, int val) {
            A[index].put(snap_id, val);
        }

        public int snap() {
            return snap_id++;
        }

        public int get(int index, int snap_id) {
            return A[index].floorEntry(snap_id).getValue();
        }
    }

    /**
     * Same algo, using list of TreeMap, instead of array of TreeMap
     */
    class SnapshotArray2 {
        List<TreeMap<Integer, Integer>> arr;
        int currId = 0;

        public SnapshotArray2(int length) {
            arr = new ArrayList();

            for (int i = 0; i < length; i++) {
                arr.add(i, new TreeMap<Integer, Integer>());
                arr.get(i).put(0, 0);
            }
        }

        public void set(int index, int val) {
            arr.get(index).put(currId, val);
        }

        public int snap() {
            return currId++;
        }

        public int get(int index, int snap_id) {
            return arr.get(index).floorEntry(snap_id).getValue();
        }
    }
}
