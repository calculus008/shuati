package leetcode;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by yuank on 4/12/16.
 */
public class MinWindowSubStr {

    public static String minWindow(String s, String t) {
        if(null == s || "".equals(s) || null == t || "".equals(t))
            return "";

        int counter = t.length();
        int len = s.length();

        int start = 0;
        int end = 0;
        int min_start = 0;
        int min_end = 0;
        int minLen = Integer.MAX_VALUE;

        Map<Character, Integer> m = new HashMap<>();

        for(int i=0; i<counter; i++){
            char c = t.charAt(i);
            if(m.containsKey(c)){
                m.put(c, m.get(c) + 1);
            }else{
                m.put(c, 1);
            }
        }

        while(end < len){
            // Character n = new Character(s.charAt(end));
            System.out.println("outerloop: " + start + " , " + end);

            char n = s.charAt(end);
            if(m.containsKey(n)){
                if(m.get(n) > 0) {
                    counter--;
                }
                m.put(n, m.get(n) - 1);
            }
            end++;

            while(counter == 0){//"while" is the key
                System.out.println("inner loop: " + start + " , " + end);

                if(end - start < minLen){
                    min_start = start;
                    min_end = end;
                    minLen = end - start;
                    System.out.println("min calculation: " + min_start + " , " + min_end);
                }

                char cc = s.charAt(start);
                if(m.containsKey(cc)) {//containsKey!!!
                    m.put(cc, m.get(cc) + 1);
                }

                if(m.containsKey(cc) && m.get(cc) > 0){
                    counter++;
                }

                start++;

            }
        }


        if(minLen!=Integer.MAX_VALUE){
            return s.substring(min_start, min_end);//substring!!!
        }else{
            return "";
        }
    }

    public static String minWindow1(String s, String t) {
        HashMap<Character,Integer> map = new HashMap();
        for(char c : s.toCharArray())
            map.put(c,0);

        for(char c : t.toCharArray())
        {
            if(map.containsKey(c))
                map.put(c,map.get(c)+1);
            else
                return ""; /* important! for the case the s doesn't have all the character in t!!!*/
        }

        int start =0, end=0, minStart=0,minLen = Integer.MAX_VALUE, counter = t.length();
        while(end < s.length())
        {
            char c1 = s.charAt(end);
            if(map.get(c1) > 0)
                counter--;
            map.put(c1,map.get(c1)-1);

            end++;

            while(counter == 0)
            {
                if(minLen > end-start)
                {
                    minLen = end-start;
                    minStart = start;
                }

                char c2 = s.charAt(start);
                System.out.println( map.get(c2));
                map.put(c2, map.get(c2)+1);

                System.out.println(c2 + ", " + map.get(c2));
                if(map.get(c2) > 0)
                    counter++;

                start++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart,minStart+minLen);
    }


    public static String formatIntegerInStr(String value) {
        if (null == value) {
            return "0";
        }

        double val = Double.parseDouble(value);
        if (val > 1000000) {
            val /= 1000000.0;
            System.out.println(val);
            return MessageFormat.format("{0,number,#.##}", val) + "M";
        } else {
            if (val == (long) val) {
                System.out.println("1");
                return String.format("%,d", (long) val);
            } else {
                System.out.println("2");
                DecimalFormat df = new DecimalFormat("#,###.00");
                return df.format(val);
            }
        }
    }

    //input "123456", output "123,456"
    public static String formatNumberOutput(String value) {
        if (null == value || "".equals(value)) {
            return "0";
        }

        if(value.contains(".")) {
            value = value.substring(0, value.indexOf("."));
        }

        return String.format("%,d", Long.parseLong(value));
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private static void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        System.out.println("===========adding new set : " + Arrays.toString(tempList.toArray()));
        for(int i = start; i < nums.length; i++){
            tempList.add(nums[i]);
            System.out.println("adding new element : " + Arrays.toString(tempList.toArray()));
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
            System.out.println("after removing the last element : " + Arrays.toString(tempList.toArray()));
        }

        System.out.println("===========return to last level");
        System.out.println("");
    }

    public static void main(String [] args)
    {
//        String result = minWindow1("cabwefgewcwaefgcf", "cae");
//        String result = minWindow("cabwefgewcwaefgcf", "caez");

//        System.out.println("result=" + result);


//        result = minWindow1("bba", "ab");
//        System.out.println("result=" + result);

//        String r1 = formatIntegerInStr("11123456.0");
//        System.out.println(r1);

        int[] set = {1, 2, 3};
        List<List<Integer>> result = subsets(set);

    }
}
