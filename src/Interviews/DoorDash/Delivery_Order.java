package Interviews.DoorDash;

import java.util.*;

public class Delivery_Order {
    /**
     * Doordash
     *
     * For each delivery, we expect a Dasher to first pickup the order
     * from a merchant and then drop off the order to a customer.
     * However, a Dasher can be batched during the delivery process,
     * meaning that they could have multiple pickup and drop off actions
     * in their route. In this case, the pickup for each delivery has to
     * occur before the respective drop off.
     *
     * Given a sequence of pickup and drop off actions, write a function
     * to ensure the sequence is valid.
     *
     * All picked up orders should be dropped off.
     *
     * Input: list of strings -- "P" + id or "D" + id, where id is an integer.
     * Output: boolean
     *
     * Example :
     * P1, D1 -> valid
     * P1, P2, D2, D1 -> valid
     *
     * Follow up:
     * Given N deliveries, write a function that prints out all the valid
     * sequences of routes without using recursion.
     *
     * Hint: Given all the valid routes for N = i, how can you determine
     * the valid routes for N = i + 1? (要求必须要用iteration去写, 并且要用到n-1的资讯)
     *
     *  N = 1 -> [p1, d1]
     *  N = 2 -> [p1, d1, p2, d2], [p1, p2, d1, d2], [p1, p2, d2, d1], [p2, d2, p1, d1], [p2, p1, d1, d2], [p2, p1, d2, d1]
     *
     */

    public static boolean isPathValid(List<String> input) {
        if (input == null || input.size() <= 1) return false; //Is "D1" valid?

        Set<Integer> set = new HashSet<>();
        boolean isPickup = false;

        for (String s : input) {
            System.out.println(s);
            isPickup = s.charAt(0) == 'p' ? true : false;
            int num = Integer.valueOf(s.trim().substring(1));

            if (isPickup) {
                if (!set.add(num)) {
                    return false;
                }
            } else {
                if (set.contains(num)) {
                    set.remove(num);
                } else {
                    return false;
                }
            }
        }

        return set.isEmpty() ? true : false;
    }

    public static List<List<String>> allPossibleRoutes(int n) {
        List<List<String>> res = new LinkedList<>();
        if (n <= 0)  return res;

        List<String> l1 = new LinkedList<>();
        l1.add("p1");
        l1.add("d1");

        res.add(l1);

        if (n == 1) return res;

        ((LinkedList<List<String>>) res).remove(0);

        List<List<String>> last = new ArrayList<>();
        last.add(l1);

        for (int i = 2; i <= n; i++) {
            System.out.println("----i=" + i + "-------");
            List<List<String>> cur = new LinkedList<>();
            int len = last.get(0).size();

            for (int j = 0; j < last.size(); j++) {

                for (int k = 0; k <= len + 1; k++) {
                    List<String> temp = new LinkedList<>(last.get(j));

//                    temp.add(k, "p" + i);


                    if (k == len + 1) {
                        temp.add("p" + i);
                    } else {
                        temp.add(k, "p" + i);
                    }

                    System.out.println("k=" + k + ", temp is " + Arrays.toString(temp.toArray()));

                    for (int h = k + 1; h <= len + 1; h++) {
                        List<String> copy = new LinkedList<>(temp);
                        copy.add(h, "d" + i);

                        System.out.println("h=" + h + ", copy is " + Arrays.toString(copy.toArray()));

                        cur.add(copy);

                        if (i == n) {
                            res.add(copy);
                        }
                    }
                }
            }

            last = cur;

        }

        return res;
    }

    public static void main(String[] args) {
        List<List<String>> validCases = new ArrayList<>();
        String[][] ss = new String[][]{{"p1", "d1", "p2", "d2"},
                {"p1", "p2", "d1", "d2"},
                {"p1", "p2", "d2", "d1"},
                {"p2", "d2", "p1", "d1"},
                {"p2", "p1", "d1", "d2"},
                {"p2", "p1", "d2", "d1"}
        };

        for (String[] s: ss) {
            validCases.add(Arrays.asList(s));
        }

        for (int i = 0; i < validCases.size(); i++) {
            boolean res = isPathValid(validCases.get(i));
            System.out.println(res);
        }

        List<List<String>> invalidCases = new ArrayList<>();
        String[][] dd = new String[][]{{"p2", "d1", "p1", "d2"},
                {"d1", "d2", "p1", "p2"},
                {"p2", "p1"},
                {"p2", "d1", "p1", "d2"},
                {"p2", "d1"},
                {"p1", "p1", "d1", "d1"},
                {"d2"},
                {"p1", "p2"},
                {""}
        };

        for (String[] d: dd) {
            invalidCases.add(Arrays.asList(d));
        }

        for (int i = 0; i < invalidCases.size(); i++) {
            boolean res = isPathValid(invalidCases.get(i));
            System.out.println(res);
        }


        List<List<String>> res = allPossibleRoutes(3);

        System.out.println("");

        for (List<String> l : res) {
            System.out.println(Arrays.toString(l.toArray()));
        }

    }


    /**
     * Variation:
     * 那个给出数字1,2,3,4,5之类的面筋题, 问一共有多少种合理的pick up 和 deliver的序列的题目, 要求用[迭代]的方式做, 不要递归的解法.
     *
     * n = 1, only 1 possible, p1 d1
     * n = 2,  6 possible
     * p1 d1 p2 d2
     * p1 p2 d1 d2
     * p1 p2 d2 d1
     * p2 p1 d1 d2
     * p2 p1 d2 d1
     * p2 d2 p1 d1
     *
     * 总之就是要对于每一个物品, pick up 一定要在 deliver之前. 具体哪个物体先哪个后无所有.
     * 我记得n = 4有84种, n = 5有2520种.
     */
}
