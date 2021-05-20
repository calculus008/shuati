package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_1352_Product_Of_The_Last_KNumbers {
    /**
     * Implement the class ProductOfNumbers that supports two methods:
     *
     * 1. add(int num)
     * Adds the number num to the back of the current list of numbers.
     *
     * 2. getProduct(int k)
     * Returns the product of the last k numbers in the current list.
     * You can assume that always the current list has at least k numbers.
     *
     * At any time, the product of any contiguous sequence of numbers will
     * fit into a single 32-bit integer without overflowing.
     *
     * Example:
     * Input
     * ["ProductOfNumbers","add","add","add","add","add","getProduct","getProduct","getProduct","add","getProduct"]
     * [[],[3],[0],[2],[5],[4],[2],[3],[4],[8],[2]]
     *
     * Output
     * [null,null,null,null,null,null,20,40,0,null,32]
     *
     * Explanation
     * ProductOfNumbers productOfNumbers = new ProductOfNumbers();
     * productOfNumbers.add(3);        // [3]
     * productOfNumbers.add(0);        // [3,0]
     * productOfNumbers.add(2);        // [3,0,2]
     * productOfNumbers.add(5);        // [3,0,2,5]
     * productOfNumbers.add(4);        // [3,0,2,5,4]
     * productOfNumbers.getProduct(2); // return 20. The product of the last 2 numbers is 5 * 4 = 20
     * productOfNumbers.getProduct(3); // return 40. The product of the last 3 numbers is 2 * 5 * 4 = 40
     * productOfNumbers.getProduct(4); // return 0. The product of the last 4 numbers is 0 * 2 * 5 * 4 = 0
     * productOfNumbers.add(8);        // [3,0,2,5,4,8]
     * productOfNumbers.getProduct(2); // return 32. The product of the last 2 numbers is 4 * 8 = 32
     *
     *
     * Constraints:
     *
     * There will be at most 40000 operations considering both add and getProduct.
     * 0 <= num <= 100
     * 1 <= k <= 40000
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/product-of-the-last-k-numbers/discuss/510260/JavaC%2B%2BPython-Prefix-Product
     *
     * Prefix product, similar idea as prefix sum.
     * "0 <= num <= 100" : the key is how to deal with 0. k is like the width of a sliding window. When
     * a 0 is added, product of last k numbers becomes 0, until this 0 moves out of the sliding window.
     * So, when adding a 0, we re-init a list, add value "1" to the list. Why? the basic idea using prefix
     * product is that the result is list[last index] / list[last index - k - 1], can't have a zero here, have
     * "1" will make the division work. Also, whenever the list is initialized, 1 is added. If we have a list
     * that is inserted with x positive numbers, the length of the list is actually x + 1. This also makes
     * division work.
     *
     * Example:
     *         list
     * Add 0 : [1]
     * Add 3 : [1, 3]
     * Add 0 : [1]
     * Add 2 : [1, 2]
     * Add 5 : [1, 2, 10]
     * Add 4 : [1, 2, 10, 40]
     * Add 8 : [1, 2, 10, 40, 320]
     *
     *
     * [2, 5, 4, 8]
     * getProduct(2) : l[4] / l[5 - 2 - 1] = l[4] / l[2] = 320 /10 = 32
     * [1, 2, 10, 40, 320]
     *         ^        ^
     *
     * getProduct(3) : l[4] / l[5 - 3 - 1] = l[4] / l[1] = 320 / 2 = 160
     */
    class ProductOfNumbers {
        List<Integer> list;

        public ProductOfNumbers() {
            add(0);
        }

        public void add(int num) {
            if (num > 0) {
                list.add(list.get(list.size() - 1) * num);
            } else {
                list = new ArrayList();
                list.add(1);
            }
        }

        public int getProduct(int k) {
            int n = list.size();
            return k < n ? list.get(n - 1) / list.get(n - k - 1) : 0;
        }
    }

}

