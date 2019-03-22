package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_412_Fizz_Buzz {
    /**
     * Write a program that outputs the string representation of numbers from 1 to n.
     *
     * But for multiples of three it should output “Fizz” instead of the number and for
     * the multiples of five output “Buzz”. For numbers which are multiples of both three
     * and five output “FizzBuzz”.
     */
    class Solution {
        public List<String> fizzBuzz(int n) {

            // ans list
            List<String> ans = new ArrayList<String>();

            for (int num = 1; num <= n; num++) {

                boolean divisibleBy3 = (num % 3 == 0);
                boolean divisibleBy5 = (num % 5 == 0);

                if (divisibleBy3 && divisibleBy5) {
                    // Divides by both 3 and 5, add FizzBuzz
                    ans.add("FizzBuzz");
                } else if (divisibleBy3) {
                    // Divides by 3, add Fizz
                    ans.add("Fizz");
                } else if (divisibleBy5) {
                    // Divides by 5, add Buzz
                    ans.add("Buzz");
                } else {
                    // Not divisible by 3 or 5, add the number
                    ans.add(Integer.toString(num));
                }
            }

            return ans;
        }
    }
}
