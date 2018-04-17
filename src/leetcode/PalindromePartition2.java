package leetcode;

/**
 * Created by yuank on 1/31/18.
 */
public class PalindromePartition2 {
    public static int minCut(String s) {
        char[] c = s.toCharArray();
        int len = c.length;
        boolean[][] isPal = new boolean[len][len];
        int[] cut = new int[len];

        for (int i = 0; i < len; i++) {
            int min = i; //number of cuts if each cut is a single char, example a b a, length is 3, 2 cuts
            System.out.println("i=" + i +", min=" + min);
            for (int j = 0; j <= i; j++) {
                //Check if substring i to j is a palindrome:
                //c='acbca' : j=0, i=4
                // i - j < 2: 0 -> aa, 1 -> aba

                System.out.println("   j=" + j + ", c["+j+"]=" + c[j] + ", c["+i+"]=" + c[i]);


                if(c[j] == c[i] && (i - j < 2 || isPal[j + 1][i - 1])) {
                    isPal[j][i] = true;
                    System.out.println("   j=" + j + ", c["+j+"]=" + c[j] + ", c["+i+"]=" + c[i] + ", isPal["+j+"+1]["+ i +"-1]");
                    System.out.println("   cut[" + j+ "-1]");
                    min = j == 0 ? 0 : Math.min(cut[j - 1] + 1, min);
                    System.out.println("   min="+min);
                }
            }
            System.out.println("cut["+i+"]="+min);
            cut[i] = min;
        }

        return cut[len - 1];
    }

    public static void main(String [] args) {
        minCut("aabaa");
    }

}
