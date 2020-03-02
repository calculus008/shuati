package Interviews.Indeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Quintiles {
    /**
     * quintiles的个数，比如2就是求中位数，3就是把数组分成3等分的那两个数的值。
     * 数值是⽤用Pair给出的，(value count)值和个数的Pair, ⽐比如(1, 3)表示数组中有3个1.
     * Pair的顺序不不是sorted.
     *
     * 还有就是Pair的个数。
     *
     * 题⽬的示例:
     * 3 三等分
     * 3 三个Pair
     * 7 2 数组中有2个7
     * 6 2 数组中有2个6
     * 5 2 数组中有2个5
     * 输出应该是
     *
     * 5 6
     *
     * k-th quintiles的index是N*k/Q.
     * (N是数组⻓度，在第⼀步时对所有Pair中count求和就是了),
     * Q是quintiles的个数 预处理理求和 然后二分即可
     *
     * q : 粉尘q等分
     * k : kth等分
     *
     * For example:
     * q = 3, k = 1
     * 要把数组分成3等分，求3等分中第一个等分处的数值。
     */

    public double getQuantile(List<Pair> pairs, int q, int k) {
        Collections.sort(pairs, (a, b) -> a.val - b.val);

        if (k == 0) return pairs.get(0).val;
        if (k == q) return pairs.get(pairs.size() - 1).val;

        int[] sum = new int[pairs.size()];
        sum[0] = pairs.get(0).count;

        for (int i = 1; i < pairs.size(); i++) {
            sum[i] = sum[i - 1] + pairs.get(i).count;
        }

        int total = sum[pairs.size() - 1];

        //k-th quintiles的index是N*k/Q.
        double temp = Math.ceil(total * k * 1.0 / q);
        int idx = (int) temp;

        System.out.println("total="+total +", idx=" + idx);
        System.out.println(Arrays.binarySearch(sum, idx));

        int pos = Arrays.binarySearch(sum, idx);
        int pos1 = Arrays.binarySearch(sum, idx + 1);


        if (pos < 0) {
            pos = -(pos + 1);
        }

        if (pos1 < 0) {
            pos1 = -(pos1 + 1);
        }

        System.out.println("pos="+pos + ", pos1=" + pos1);

        if (total * k % q != 0) {
            return pairs.get(pos).val;
        } else {
            return (pairs.get(pos).val + pairs.get(pos1).val) / 2.0;
        }
    }

    public static void main(String[] args) {
        Pair p1 = new Pair(7, 2);
        Pair p2 = new Pair(6, 2);
        Pair p3 = new Pair(5, 2);

        List<Pair> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        Quintiles q = new Quintiles();

        System.out.println(q.getQuantile(list, 2, 1));

        /**
         * ??
         * Have problem with q = 2, k = 1
         *
         */
    }
}
