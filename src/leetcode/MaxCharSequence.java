package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 3/24/16.
 */
public class MaxCharSequence {

        public static int lengthOfLongestSubstring(String s) {
            if(s==null || ("").equals(s))
                return 0;

            int maxLen = 0;
            int maxLeft = 0;
            int maxRight = 0;
            int len = s.length();

            if(len==1)
                return 1;

            HashMap<Character, Integer> hm = new HashMap<Character, Integer>();

            int left = 0, right =0;

            while(left < len && right < len){
                if(hm.containsKey(s.charAt(right))){
                    int temp = hm.get(s.charAt(right));
                    System.out.println("temp=" + temp + ", right=" + right);
                    if(left <= temp)
                        left = temp + 1;
                }

                hm.put(s.charAt(right), right);


                System.out.println("left="+left+" , right="+right);

                if(right - left + 1 > maxLen) {
                    maxLen = right - left + 1;
                    maxLeft = left;
                    maxRight = right;
                }

                right++;


//                System.out.println("maxlen=" + maxLen + " window:" + left + "-" + right);
            }

            System.out.println("maxlen=" + maxLen + " window:" + left + "-" + right);

            return maxLen;
        }

    public static int lengthOfLongestSubstring1(String s) {
        if(null == s)
            return 0;
        int len = s.length();
        if(len < 2)
            return len;

        int l = 0, r = 0, max = 0;
        HashMap<Character, Integer> map = new HashMap<>();

        while(r < len){
            if(map.containsKey(s.charAt(r))){
                char c = s.charAt(r);
                //last konw location of the current char (repeated)
                int k = map.get(c);
                //move left boundary to the left of this location and remove this char from dist
                l = k + 1;
                map.remove(c);
            }

            map.put(s.charAt(r), r);

            System.out.println("left="+l+" , right="+r);

            max = Math.max(max, r -l + 1);

            r++;
        }

        System.out.println("maxlen=" + max + " window:" + l + "-" + r);

        return max;
    }

    public static void main(String [] args)
    {
//        String s="ktdmfeqppdgdlysrrdxgermuqogyjmithlhmsychdkkpleicjfinyxkrlqpue";
//        lengthOfLongestSubstring(s);

        String t="abba";
        lengthOfLongestSubstring1(t);
    }
}
