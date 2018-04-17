package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/9/18.
 */
public class LE_218_The_Sky_Line_Problem {
    /*
        A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance.
        Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A),
        write a program to output the skyline formed by these buildings collectively (Figure B).

         Buildings  Skyline Contour
        The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left
        and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX,
        and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.

        For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .

        The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline.
        A key point is the left endpoint of a horizontal line segment. Note that the last key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.

        For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].

        Notes:

        The number of buildings in any input list is guaranteed to be in the range [0, 10000].
        The input list is already sorted in ascending order by the left x position Li.
        The output list must be sorted by the x position.
        There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...]
        is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
     */

    /*
        Very Important.

        扫描线(Line Sweep)
     */

    /*
    扫描线(Line Sweep)
    关键的规律 ：
    按x坐标扫描，在x1, 碰到正方形的起始边，如果该边的高度h是到现在遇到的边中最高的，那它是一个key point,加入 k[x1, h].
               在x2, 碰到正方形的终结边，要看第二高的边。
    https://www.youtube.com/watch?v=7AE-VCGEhtI

*/
    //Solution 1 : Heap, Time : O(n ^ 2), Space : O(n)
    public List<int[]> getSkyline1(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();

        //Based on the question conditions, b[0] - start, b[1] - end, b[2] - H
        for (int[] b : buildings) {
            //!!! NOT "new int[2]{b[0], -b[2]}"
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1],  b[2]});
        }

        /*
        Sort height by x, if x is equal, by height. Since we make the entry height negative,
        if the exit line of rectangle A and the start line of rectangle B are at the same x, exit line of A will be in front of the entry line of B.
        This is the trick that helps to deal with special cases that entry/exit lines of different rectangles overlap.
        */
        Collections.sort(heights, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b -a);
        heap.add(0);

        int pre = 0; //height of the last added key point
        for(int[] h : heights) {
            if (h[1] < 0) {//Entry line
                heap.add(-h[1]);
            } else {//Exit line
                //Take time O(n)
                heap.remove(h[1]);
            }

            int cur = heap.peek(); //!!! peek(), NOT peak()
            if (pre != cur) {
                res.add(new int[]{h[0], cur});
                pre = cur;
            }
        }

        return res;
    }

    //Solution 2 : Use TreeMap (B-Tree), Time : O(nlogn), Space : O(n)
    public List<int[]> getSkyline2(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();

        for (int[] b : buildings) {
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1],  b[2]});
        }

        Collections.sort(heights, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        //!!! "Collections.reverseOrder()",. "<Integer, Integer>"
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        map.put(0, 1);
        int pre = 0;

        for (int[] h : heights) {
            if (h[1] < 0) {
                map.put(-h[1], map.getOrDefault(-h[1], 0) + 1);
            } else {
                int val = map.get(h[1]);
                if (val == 1) {
                    //!!! 注意，必须删除count为0的元素。否则treemap不能对key重新排序。
                    //"remove" takes time O(logn), 这是这个解法比用heap快的地方。
                    map.remove(h[1]);
                } else {
                    map.put(h[1], val - 1);
                }

            }

            //!!! TreeMap "firstKey()"
            int cur = map.firstKey();
            if (pre != cur) {
                res.add(new int[]{h[0], cur});
                pre = cur;
            }
        }

        return res;
    }

}
