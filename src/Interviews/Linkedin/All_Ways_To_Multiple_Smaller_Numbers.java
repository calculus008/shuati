package Interviews.Linkedin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class All_Ways_To_Multiple_Smaller_Numbers {
    /**
     * Write a program that takes an integer and prints out all ways
     * to multiply smaller integers that equal the original number,
     * without repeating sets of factors. In other words, if your
     * output contains 4 * 3, you should not print out 3 * 4 again
     * as that would be a repeating set. Note that this is not asking
     * for prime factorization only.
     *
     * Also, you can assume that the input integers are reasonable in size;
     * correctness is more important than efficiency.
     *
     * Eg: PrintFactors(12) 12 * 1, 6 * 2, 4 * 3, 3 * 2 * 2
     */

    private static void printFactor(int n) {
        if (n <= 0) {
            System.out.print("Wrong input!");
            return;
        }
        if (n == 1) {
            System.out.print("1");
            return;
        }
        long[] a = new long[n/2];
        findFactor(n/2, n, a, 0);

    }

    private static void findFactor(long i, long n, long[] arr, int index) {
        if (i == 1) {
            if (n == 1) {
                for (int k = 0; k < index - 1; k++) {
                    System.out.print(arr[k]);
                    if (k < index - 2) {
                        System.out.print("*");
                    }
                }
                System.out.println();

            }
            return;
        }

        for (long k = i; k >= 1; k--) {
            if (n % k == 0) {
                arr[index] = k;
                findFactor(k, n / k, arr, index + 1);
            }
        }
    }

    public static void getFactors(int n) {
        if (n == 1) {
            System.out.println("1");
        }
        //!!! 1不是因子，所以必须从2开始
        helper(new ArrayList<>(), 2, n);
    }

    public static void helper(List<Integer> list, int start, int n) {
        /**
         * !!!
         *  "> 1".排除因子中只有n本身，如，n=12, 如果用"> 0",则[12]会出现在res中。
         *
         *  Because we start from 2, so 1 is not in the list, "list.size() == 1" means
         *  we only have number itself in the current list, therefore, we need to put
         *  1 in it.
         *  **/
        if (n == 1) {
            if (list.size() == 1) {
                list.add(1);
            }

            System.out.println(Arrays.toString(list.toArray()));
            return;
        }

        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                list.add(i);
                //!!! 同一因子可以重复出现，所以继续用i作为start.
                //!!! "n / i"
                helper(list, i, n / i);
                list.remove(list.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        printFactor(12);

        getFactors(12);
    }
}
