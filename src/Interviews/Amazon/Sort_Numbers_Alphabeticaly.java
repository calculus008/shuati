package Interviews.Amazon;

import java.io.IOException;
import java.util.*;

public class Sort_Numbers_Alphabeticaly {
    public static void sort1(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return new Integer(o1.replaceAll("room", ""))
                        .compareTo(new Integer(o2.replaceAll("room", "")));
            }

        });

        System.out.println(Arrays.toString(list.toArray()));
    }

    /**
     * bool operator<(const string &a, const string &b){
     *     int l = min(a.size(),b.size());
     *     for(int i = 0; i < l; i++){
     *         if( a[i] > b[i]) return false; // a is greater than b
     *         if( b[i] > a[i]) return true;  // b is greater than a
     *     }
     *     if ( a.size() > l) return false;   // a is greater than b
     *     return true;                       // b is greater than a
     * }
     */

    public static int[] sort2(int n) {
        int[] nums = new int[n];

        String[] str = new String[n];
        for (int i = 1; i <= n; i++) {
            str[i - 1] = String.valueOf(i);
        }

        String temp;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (str[i].compareTo(str[j]) > 0) {
                    temp = str[i];
                    str[i] = str[j];
                    str[j] = temp;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(str[i]);
        }

        return nums;
    }

    public static int[] sort3(int n) {
        int[] nums = new int[n];

        String[] str = new String[n];
        for (int i = 1; i <= n; i++) {
            str[i - 1] = String.valueOf(i);
        }

        Arrays.sort(str);

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(str[i]);
        }

        return nums;
    }

    public static void main(String[] args) throws IOException {
        List<String> list = Arrays.asList("room1", "room100", "room2");
        sort1(list);

        int[] nums = sort3(21);
        System.out.println(Arrays.toString(nums));
    }
}
