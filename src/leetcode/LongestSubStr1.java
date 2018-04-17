package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 3/30/16.
 */


public class LongestSubStr1 {
    public static int lengthOfLongestSubstringTwoDistinct(String s) {
        if(null == s || "".equals(s))
            return 0;

        int start = 0;
        int end = 0;
        int len = s.length();
        HashMap<Character, Integer> h = new HashMap<Character, Integer>();
        int count = 0;
        int maxCount = Integer.MIN_VALUE;;
        int uniquekey = 0;

        while (start <= end && end <len){
            if(!h.containsKey(s.charAt(end))){
                if(uniquekey < 2){
                    uniquekey++;
                }else{
                    Character[] keys = h.keySet().toArray(new Character[2]);
                    char b;
                    if(keys[0] == s.charAt(end - 1))
                        b=keys[1];
                    else
                        b=keys[0];
                    start = h.get(b)+1;
                    System.out.println("To be removed key is " + b );

                    h.remove(b);
                    uniquekey=2;
                }
            }

            if(end - start + 1 > maxCount)
                maxCount = end - start + 1;
            System.out.println("start="+start + ", end="+end + ", maxCount=" + maxCount);

            h.put(s.charAt(end), end);
            end++;
        }

        return maxCount;
    }

    public static int lengthOfLongestSubstringTwoDistinct1(String s) {
        if(null == s || "".equals(s))
            return 0;

        int start = 0;
        int end = 0;
        int maxCount = 0;
        HashMap<Character, Integer> h = new HashMap<Character, Integer>();

        for(int i=0; i<s.length(); i++){
            if(h.size() <= 2){
                h.put(s.charAt(end), end);
                end++;
            }else{
                int leftmost = s.length();
                for(int j:h.values()){
                    leftmost = Math.min(j, leftmost);
                }
                start = leftmost + 1;
                h.remove(s.charAt(leftmost));
            }

            maxCount = Math.max(maxCount, end - start);
            System.out.println("start="+start + ", end="+end + ", maxCount=" + maxCount);

        }

        return maxCount;
    }


        public static void main(String [] args)
    {
        lengthOfLongestSubstringTwoDistinct("bacc");
        System.out.println("====");

        lengthOfLongestSubstringTwoDistinct1("bacc");
    }
}
