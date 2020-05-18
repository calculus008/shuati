package Interviews.Nextdoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cool_Feature {
    /**
     * coolFeature
     * Give three array a, b and query. This one is hard to explain. Just read the example. Input:
     * a = [1, 2, 3]
     * b = [3, 4]
     * query = [[1, 5], [1, 1 , 1], [1, 5]] Output:
     * [2, 1]
     *
     * Explain:
     * Just ignore every first element in sub array in query.
     * So we will get a new query like this query = [[5], [1, 1], [5]]
     * Only record the result when meet the single number in new query array.
     * And the rule of record is find the sum of the single number.
     * The example above is 5 = 1 + 4 and 5 = 2 + 3, there are two result.
     * So currently the output is [2]
     * When we meet the array length is larger than 1, such as [1, 1]. That means we will replace
     * the b[x] = y, x is the first element, y is second element. So in this example, the b will be
     * modify like this b = [1, 4]
     *
     * And finally, we meet the [5] again. So we will find sum again. This time the result is 5 = 1 + 4.
     * So currently the output is [2, 1]
     *
     * Note: Don't have to modify the query array, just ignore the first element.
     *
     * 输入a，b两个array， 一个query array。query有两种type，
     * 一种是[target]查从a中取一个数， b中取一个数，求加起来等于target的情况有多少种。
     * 第二种query是[index, num], 把b中在 in​​​​​​​​​​​​​​​​​​​dex位置的数字改成num，
     * 这种query不需要输出。最后输出所有第一种query的result。
     */

    /**
     * A variation that uses two sum algorithm
     */
    public static int[] coolFeature(int[] a, int[] b, int[][] querys) {
        List<Integer> ans = new ArrayList<Integer>();

        /**
         * map : frequency of each number in a
         */
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < a.length; i++) {
            map.put(a[i], map.getOrDefault(a[i], 0) + 1);
        }

        for (int[] query : querys) {
            if (query.length == 2) {
                int temp = findSum(map, b, query[1]);
                ans.add(temp);
            } else if (query.length == 3) {
                changeArray(a, b, query[1], query[2]);
            }
        }

        int[] ansArray = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            ansArray[i] = ans.get(i);
        }
        return ansArray;
    }

    /**
     * two sum, find number of combination that sums to target number, using hashmap
     */
    public static int findSum(Map<Integer, Integer> map, int[] b, int target) {
        int res = 0;
        for (int i = 0; i < b.length; i++) {
            if (map.containsKey(target - b[i])) {
                res += map.get(target - b[i]);
            }
        }
        return res;
    }

    public static void changeArray(int[] a, int[] b, int loc, int num) {
        b[loc] = num;
    }
}
