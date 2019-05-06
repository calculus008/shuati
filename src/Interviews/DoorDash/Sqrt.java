package Interviews.DoorDash;

public class Sqrt {
    /**
     * DoorDash
     *
     * sqrt()，要求precision到0.001
     *
     * 返回int， 但是跟leetcode 不同的是要返回平方后大于等于输入值的结果 比如输入15的话要返回4, 而不是3
     *
     * follow-up:  输入 输出都是double/float 型， 用牛顿法解决
     *
     * LE_69_Sqrt
     * LE_367_Valid_Perfect_Square
     *
     *
     * 用二分写 写的倒是挺快的但是就在check mid >= n / mid时忘记了那个除是向下round的…于是有时就off by 1...
     * 然后改了改最后对了但是感觉有点不好……
     */
    public static int sqrt_JiuZhang(int x) {
        if (x <= 1) return x;

        System.out.println("start");
        int start = 1;
        int end = x;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (mid == x / mid) {
                return mid;
            } else if (mid > x / mid) {
                end = mid;
            } else {
                start = mid;
            }
        }

        System.out.println(start);
        return start;
    }

    public static int sqrt_1(int x) {
        if (x <= 1) return x;

        int start = 1;
        int end = x;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (mid == x / mid) {
                return mid * mid == x ? mid : mid + 1;
            } else if (mid < x / mid) {
                start = mid;
            } else {
                end = mid;
            }
        }

        return start + 1;
    }

    public static int mySqrt_Newton(int a) {
        long x = a;
        while (x * x > a) {
            x = (x + a / x) / 2;
        }
        return (int)x;
    }

    public static float squareRoot(int number, int precision) {
        int start = 0, end = number;
        int mid;

        // variable to store the answer
        double ans = 0.0;

        // for computing integral part
        // of square root of number
        while (start <= end) {
            mid = (start + end) / 2;

            if (mid * mid == number) {
                ans = mid;
                break;
            }

            // incrementing start if integral
            // part lies on right side of the mid
            if (mid * mid < number) {
                start = mid + 1;
                ans = mid;
            } else {
                // decrementing end if integral part
                // lies on the left side of the mid
                end = mid - 1;
            }
        }

        // For computing the fractional part
        // of square root up to given precision
        double increment = 0.1;
        for (int i = 0; i < precision; i++) {
            while (ans * ans <= number) {
                ans += increment;
            }

            // loop terminates when ans * ans > number
            ans = ans - increment;
            increment = increment / 10;
        }
        return (float)ans;
    }

    public static float myPrecisionSqrt(int num, int precision) {
        int res = sqrt_JiuZhang(num);

        double ans = res;
        double increment = 0.1;

        for (int i = 0; i < precision; i++) {
            while (ans * ans <= num) {
                ans += increment;
            }

            // loop terminates when ans * ans > number
            ans = ans - increment;
            increment = increment / 10;

            System.out.println("ans=" + (float)ans + ", incremenet=" + increment);

        }

        return (float)ans;
    }

    public static void main(String[] args) {
//        float res = myPrecisionSqrt(30, 4);
//        System.out.println(res);

        int res1 = sqrt_1(50);
        System.out.println(res1);
    }

}
