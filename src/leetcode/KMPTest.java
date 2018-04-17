package leetcode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * Created by yuank on 9/20/17.
 */
public class KMPTest {
    private static int[] getTable(String s) {
        int[] table = new int[s.length()];

        //k - prefix, j - postfix
        int j=1, k=0;
        while(j<s.length()) {
            if(s.charAt(j) == s.charAt(k)) {
                k++;
                table[j] = k;
                System.out.println("table[" +j+"]="+ table[j]+", j="+j+", k=" +k);
                j++;
            } else {
                if(k>0) {
                    //!!!
                    int n = k-1;
                    System.out.println("table[k-1] is table[" + n +"], k=" + table[n]);
                    k=table[k-1];
                } else {
                    k=0;
                    j++;
                    System.out.println("k=0, j="+j);
                }
            }
        }

        return table;
    }

    public static String processCVRValue(String value) {
        BigDecimal bigDecimal = new BigDecimal((String) value);
        Double percent = Double.valueOf(bigDecimal.doubleValue() );

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(6);

        return numberFormat.format(percent);
    }

    public static void main(String [] args) {
        String s = "aacecaaa#aaacecaa";
//        int[] table = getTable(s);
        System.out.println(processCVRValue("1.6796888836190066E-5"));
    }
}
