package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/22/18.
 */
public class LE_166_Fraction_To_Recurring_Decimal {
    /*
        Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.

        If the fractional part is repeating, enclose the repeating part in parentheses.

        For example,

        Given numerator = 1, denominator = 2, return "0.5".
        Given numerator = 2, denominator = 1, return "2".
        Given numerator = 2, denominator = 3, return "0.(6)".
     */

    //Important

    //Time and Space : O(n)
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        sb.append((numerator > 0) ^ (denominator > 0) ? "-" : "");

        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        sb.append( num / den);

        if ( num % den == 0) {
            return sb.toString();
        }

        sb.append(".");
        Map<Long, Integer> map = new HashMap<>();
        num = num % den;
        map.put(num, sb.length());

        while (num > 0) {
            num *= 10;
            sb.append(num / den);
            num %= den;
            if(map.containsKey(num)) {
                int index = map.get(num);
                sb.insert(index, "(");
                sb.append(")");
                break;
            } else {
                map.put(num, sb.length());
            }
        }

        return sb.toString();
    }
}
