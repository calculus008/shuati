package leetcode;

import java.util.Arrays;

public class LE_564_Find_The_Closest_Palindrome {
    /**
     * Given an integer n, find the closest integer (not including itself), which is a palindrome.
     *
     * The 'closest' is defined as absolute difference minimized between two integers.
     *
     * Example 1:
     * Input: "123"
     * Output: "121"
     * Note:
     * The input n is a positive integer represented by string, whose length will not exceed 18.
     * If there is a tie, return the smaller one as answer.
     *
     * Hard
     */

    class Solution {
        public String nearestPalindromic(String n) {
            long num = Long.parseLong(n);
            long big = findHigher(String.valueOf(num+1));
            long small = findLower(String.valueOf(num-1));

            return Math.abs(num-big) >= Math.abs(num-small)?String.valueOf(small):String.valueOf(big);
        }

        private long findHigher(String n){
            char[] s = n.toCharArray();
            int m = s.length;
            //!!!
            char[] t = Arrays.copyOf(s, m);

            for(int i=0; i<m/2; i++) {
                t[m-1-i] = s[i];
            }

            for(int j=0; j<m; j++) {
                if(s[j] < t[j]) {
                    return Long.parseLong(String.valueOf(t));
                } else if(s[j] > t[j]) {
                    //!!! k>=0
                    for(int k=(m-1)/2; k>=0; k--) {
                        if(++t[k] > '9') {
                            t[k] = '0';
                        } else {
                            break;
                        }
                    }

                    for(int l=0; l<m/2; l++) {
                        t[m-1-l] = t[l];
                    }

                    return Long.parseLong(String.valueOf(t));
                }
            }
            //!!!!
            return Long.parseLong(String.valueOf(t));
        }

        private long findLower(String n){
            char[] s = n.toCharArray();
            int m = s.length;
            //!!!
            char[] t = Arrays.copyOf(s, m);

            for(int i=0; i<m/2; i++) {
                t[m-1-i] = s[i];
            }

            for(int j=0; j<m; j++) {
                if(s[j] > t[j]) {
                    return Long.parseLong(String.valueOf(t));
                } else if(s[j] < t[j]) {
                    //!!!
                    for(int k=(m-1)/2; k>=0; k--) {
                        //!!! char , it should be '0'
                        if(--t[k] < '0') {
                            t[k] = '9';
                        } else {
                            break;
                        }
                    }

                    //For example 10000
                    if(t[0]=='0') {
                        char[] x = new char[m-1];
                        //!!!Arrays.fill
                        Arrays.fill(x, '9');
                        return Long.parseLong(String.valueOf(x));
                    }

                    for(int l=0; l<m/2; l++) {
                        //!!!it's swap inside t, not from s
                        t[m-1-l] = t[l];
                    }

                    return Long.parseLong(String.valueOf(t));
                }
            }
            //!!!!
            return Long.parseLong(String.valueOf(t));
        }
    }


}