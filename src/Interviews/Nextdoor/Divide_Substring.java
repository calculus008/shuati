package Interviews.Nextdoor;

import java.util.HashSet;
import java.util.Set;

public class Divide_Substring {
    /**
     * Give a number ​n​ and digit number ​k​, find all serial substring that is able to divide n.
     *
     * Input: n = 120, k = 2 Output: 2
     * Explain:
     * 120 -> 12 and 20 120 % 12 == 0 (O)
     *
     * 120 % 20 == 0 (O) Input: n = 555, k = 1; Output: 1
     * Explain:
     * 555 -> 5, 5 and 5 (Duplicate so only count one 5) 555 % 5 == 0 (O)
     * Input: n = 2345, k = 2
     * Output: 0
     * Explain:
     * 2345 -> 23, 34, 45 2345 % 23 != 0 (X) 2345 % 34 != 0 (X) 2345 % 45 != 0 (X)
     */
    public static int divideSubString(String s, int k) {
        int res = 0;
        int total = Integer.parseInt(s);
        Set<Integer> set = new HashSet<Integer>();

        for (int i = 0; i < s.length() - k + 1; i++) {
            String temp = s.substring(i, i + k);
            int num = Integer.parseInt(temp);

            System.out.println(num);
            System.out.println(total);

            if (!set.contains(num) && num != 0) {
                if (total % num == 0) {
                    res++;
                }
            }
            set.add(num);
        }
        return res;
    }

    public static int divide_substring(int num, int k) {
        HashSet<String> set = new HashSet();
        String string = String.valueOf(num);

        int result = 0;
        if (k > num) {
            return result;
        }

        for (int i = 0; i + k <= string.length(); i++) {
            set.add(string.substring(i, i + k));
        }

        for (String s : set) {
            int n = Integer.valueOf(s);
            //System.out.println(n);
            if (n == 0) {
                continue;
            }
            if (num % n == 0) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int result = divideSubString("2345", 2);
        System.out.println(result);
    }

}
