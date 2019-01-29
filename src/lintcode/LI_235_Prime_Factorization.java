package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 7/3/18.
 */
public class LI_235_Prime_Factorization {
    /**
         Prime factorize a given integer.
         将一个整数分解为若干质因数之乘积

         Example
         Given 10, return [2, 5].

         Given 660, return [2, 2, 3, 5, 11].
     */

    public List<Integer> primeFactorization(int num) {
        List<Integer> factors = new ArrayList<Integer>();

        for (int i = 2; i * i <= num; i++) {
            while (num % i == 0) {
                num = num / i;
                factors.add(i);
            }
        }

        //one extra
        if (num != 1) {
            factors.add(num);
        }

        return factors;
    }
}
