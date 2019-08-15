package leetcode;

public class LE_507_Perfect_Number {
    /**
     * We define the Perfect Number is a positive integer that is equal to the
     * sum of all its positive divisors except itself.
     *
     * Now, given an integer n, write a function that returns true when it is a
     * perfect number and false when it is not.
     *
     * Example:
     * Input: 28
     * Output: True
     * Explanation: 28 = 1 + 2 + 4 + 7 + 14
     * Note: The input number n will not exceed 100,000,000. (1e8)
     *
     * Easy
     */
    class Solution {
        public boolean checkPerfectNumber(int num) {
            /**
             * 1 + 1 != 1, therefore 1 is not a prefect number
             */
            if (num <= 1) return false;

            int sum = 0;
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    sum += i;

                    /**
                     * case for perfect square number, it can't be added twice
                     */
                    if (num / i != i) {
                        sum += num / i;
                    }
                }
            }

            sum++;

            return sum == num;
        }
    }
}
