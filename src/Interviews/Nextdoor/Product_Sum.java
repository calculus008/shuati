package Interviews.Nextdoor;

public class Product_Sum {
    /**
     * 很简单，一个数字，求所有位数的乘积减去所有位数的和。
     */

    public static int product_sum (int number) {
        int prod = 1;
        int sum = 0;

        while(number != 0) {
            int a = number%10;
            number /= 10;
            prod *= a;
            sum += a;
        }

        return prod - sum;
    }
}
