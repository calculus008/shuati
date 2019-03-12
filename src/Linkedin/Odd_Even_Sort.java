package Linkedin;

import java.util.Arrays;

public class Odd_Even_Sort {
    /**
     * Given array, half odd, half even, 调整数组顺序实现
     * 问题一，需要重新排列数组，使得数组的奇数在前面，偶数在后面；
     *
     * 问题二，使得“奇偶奇偶”相间，时间复杂度为O(n)。空间复杂度是O(1)。
     */

    public static boolean isEven(int number) {
        return (number & 0x01) == 0x01 ? false : true;
    }

    /*
     * 实现奇数和偶数分开，奇数在前，偶数在后
     */
    public static int ajust(int a[], int len) {
        if (a == null || len <= 0) {
            return -1;
        }

        int start = 0;
        int end = len - 1;

        while (start <= end) {
            while (start <= end && isEven(a[end])) {
                end--;
            }
            while (start <= end && !isEven(a[start])) {
                start++;
            }

            if (start <= end) {
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
                start++;
                end--;
            }
        }

        return 0;
    }

    /**
     * 实现奇数和偶数相间
     *
     * 利用算法导论快排的数组划分思想，
     * 让i（确切的说是i+1）指向需要交换的位置，j从i后一个位置开始遍历，
     * 找到第一个符合要求的数与i位置的数进行交换。
     *
     * [1,3,6,4,9,10,13]
     *
     * i = -1, j = 0
     * right place, no swap
     *
     * i = 0, j = 1, i + 1 = 1, a[j] = a[1] = 3, isEven(1) = false, isEven(a[1]) = false;
     * wrong place, do nothing
     *
     * i = 0, j = 2, i + 1 = 1, a[j] = a[2] = 6, isEven(1) = false, isEven(a[2]) = true;
     * wrong place
     * swap a[1] and a[2]
     * after swap : [1, 6, 3, 4, 9, 10, 13], i = 1, j = 1
     *
     * i = 1, j = 2
     * right place, no swap
     * i = 2, j = 3
     * right place, no swap
     * i = 3, j = 4
     * right place, no swap
     * i = 4, j = 5
     * right place, no swap
     * i = 5, j = 6
     * right place, no swap
     *
     *
     * [1,3,5,4,9,10,12]
     *
     * i = -1, j = 0
     * right place, no swap
     * i = 0, j = 1
     * wrong place
     * i = 0, j = 2
     * right place, no swap
     * i = 0, j = 3
     * right place, no swap
     * i = 0, j = 4
     * right place, no swap
     * i = 0, j = 5
     * right place, no swap
     * i = 0, j = 6
     * wrong place
     *
     * swap a[1] and a[6]
     * after swap : [1, 12, 5, 4, 9, 10, 3]
     *
     * i = 1, j = 2
     * right place, no swap
     * i = 2, j = 3
     * right place, no swap
     * i = 3, j = 4
     * right place, no swap
     * i = 4, j = 5
     * right place, no swap
     * i = 5, j = 6
     * right place, no swap
     */
    public static int odd_even(int a[]) {
        int i = -1;
        int j = 0;
        boolean changed = true;    //这个标志位非常重要，可以理解为，i和j之间相差不能超过1

        while (j < a.length) {
            System.out.println("i = " + i + ", j = " + j);
            if (isEven(j) == !isEven(a[j])) {//如果所在位置正确，不进行交换，直接判断i是否自增
                System.out.println("right place, no swap");

                /**
                 * 当changed为false时，意味着i+1指向错位的元素，j正在移动，寻找可以
                 * 交换的元素，所以此时i应该不动，直到找到合适的元素并完成交换。
                 */
                if (changed) {
                    i++;
                }
            } else {
                System.out.println("wrong place");

                /**
                 * 所在位置不正确, 看i+1所形成的下标的值，
                 * 和a[j]的值是否符合交换的要求 - 两个值不能同为偶数或奇数。
                 */
                changed = false;
                if (!isEven(i + 1) == isEven(a[j])) {
                    i++;

                    System.out.println("swap a[" + i + "] and a[" + j + "]");

                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;

                    j = i;        //将j重新赋值，但是这会导致时间复杂度超过O(n)
                    changed = true;

                    System.out.println("after swap : " + Arrays.toString(a));
                }
            }
            j++;
        }

        return 0;
    }

    public static void main(String[] args) {
//        int[] a = {1,3,6,4,9,10,13};
        int[] a = {1,3,5,4,9,10,12};
        odd_even(a);
    }

}
