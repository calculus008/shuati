package leetcode;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_204_Count_Primes {
    /**
        Count the number of prime numbers less than a non-negative number, n.
     */
    public int countPrimes(int n) {
        boolean notPrime[] = new boolean[n]; //!!!notPreim, NOT isPrime
        int res = 0;

        //" less than a non-negative number, n."
        for (int i = 2; i < n; i++) {
            if(notPrime[i] == false) {
                res++;
                for (int j = 2; i * j < n; j++) {
                    notPrime[i * j] = true;
                }
            }
        }

        return res;
    }

    /**
     * scan only odd number
     * avoid i^2 overflow by comparing with upper bound = Math.sqrt(n)
     * initialize j = i * i instead of j = 2 * i
     * E.g when i = 2, 4,6,8 will be crossed out;
     * when i = 3; 9,15,21 need to be checked, instead of 6,9,12,15,18,21
     * increase j by 2*i to keep j as an odd number E.g. when i = 3 : 9,15,21 need to be checked, instead of 6,9,12,15,18,21

     */
    public int countPrimes2(int n) {
        if(n <= 2) return 0;
        int ans = 1;// don't forget to record 2. :-)
        boolean[] isCompositeArr = new boolean[n];
        int upper = (int) Math.sqrt(n);
        for(int i = 3;i < n;i=i+2){//1.scan only odd number
            if(isCompositeArr[i]) continue;
            ans++;
            if(i > upper) continue;//2. avoid i^2 overflow.
            for(int j = i*i; j < n;j = j + 2*i){//3. initialize j to i^2
                //4. increase 2i to keep j as an odd number

                isCompositeArr[j] = true;
            }
        }
        return ans;
    }
}

