package lintcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 6/11/18.
 */
public class LI_841_String_Replace {
    /**
         Given two identical-sized string array A, B and a string S.
         All substrings A appearing in S are replaced by B.(Notice: From left to right,
         it must be replaced if it can be replaced. If there are multiple alternatives, replace longer priorities.
         After the replacement of the characters can't be replaced again.)

         The size of each string array does not exceed 100, the total string length does not exceed 50000.
         The lengths of A [i] and B [i] are equal.
         The length of S does not exceed 50000.
         All characters are lowercase letters.
         We guarantee that the A array does not have the same string

         Example
         Given A = ["ab","aba"], B = ["cc","ccc"], S = "ababa", return "cccba".

         In accordance with the rules, the substring that can be replaced is "ab" or "aba". Since "aba" is longer,
         we replace "aba" with "ccc".


         Given A = ["ab","aba"], B = ["cc","ccc"] ,S = "aaaaa" ,return "aaaaa".

         S does not contain strings in A, so no replacement is done.

         Given A = ["cd","dab","ab"], B = ["cc","aaa","dd"], S = "cdab", return "ccdd".

         From left to right, you can find the "cd" can be replaced at first,
         so after the replacement becomes "ccab", then you can find "ab" can be replaced,
         so the string after the replacement is "ccdd".

         Hard
     */


    /**
         首先用indexof计算出string里每个位置可能的替换选择
         然后遍历整个string，每次用最长的选择进行替换
         复杂度1. O(a.len*s.len); 2. O(s.len*a.len)，所以整体上平均复杂度是O(mn) - m:a.len; n:s.len
         在第一步里面，可以用heap存储替换选择，进行优化，但复杂度不变


         Given A = ["ab","aba"], B = ["cc","ccc"], S = "ababa", return "cccba".

         list [
                [0, 1],
                [],
                [0, 1],
                [],
                [],
              ]

        Recommended Solution:
        https://www.jiuzhang.com/solution/string-replace/
     */

    class Solution1 {
        public String stringReplace(String[] a, String[] b, String s) {
            // Write your code here
            if (a == null || b == null) {
                return s;
            }
            if (a.length == 0 || b.length == 0) {
                return s;
            }


            List<List<Integer>> list = new ArrayList<List<Integer>>();
            for (int i = 0; i < s.length(); i++) {
                list.add(new ArrayList<Integer>());
            }

            // Calculate all the possible replacements on each string position
            for (int i = 0; i < a.length; i++) {
                int index = 0;
                while (s.indexOf(a[i], index) >= 0) {
                    int tmp = s.indexOf(a[i], index);
                    List<Integer> entry = list.get(tmp);
                    entry.add(i);
                    index = tmp + 1;
                }
            }


            StringBuilder sb = new StringBuilder();
            int index = 0;
            // Check on each string position and replace with the longest one
            while (index < s.length()) {
                List<Integer> candidates = list.get(index);
                if (candidates.size() == 0) {
                    sb.append(s.charAt(index));
                    index++;
                } else {
                    int target = findMaxLen(candidates, a);
                    sb.append(b[target]);
                    index = index + a[target].length();
                }
            }
            return sb.toString();
        }

        /**
         * return value is the index (for a and b) which has the max length among elements in list
         */
        public int findMaxLen(List<Integer> list, String[] a) {
            int target = -1;
            int len = 0;
            for (int i : list) {
                if (a[i].length() > len) {
                    len = a[i].length();
                    target = i;
                }
            }
            return target;
        }
    }

    public class Solution2 {
        public String stringReplace(String[] a, String[] b, String s) {
            if(a.length == 0 || s.isEmpty()){
                return s;
            }

            StringBuilder builder = new StringBuilder(s);
            int m = a.length;
            int n = builder.length();
            Map<Integer, Integer> map = findReplaceable(a, s);

            for(int i = 0; i < n; i++){
                if(map.containsKey(i)){
                    int index = map.get(i);
                    int len = b[index].length();
                    builder.replace(i, i + len, b[index]);
                    i = i + len - 1;
                }
            }

            return builder.toString();
        }

        //map of i->index
        //s[i] is replaceable by max(a[index].length())
        private Map<Integer, Integer> findReplaceable(String[] a, String s){
            Map<Integer, Integer> rpMap = new HashMap<>();
            for(int i = 0; i < a.length; i++){
                for(int j = 0; j < s.length(); j++){
                    int index = s.indexOf(a[i], j);
                    if(index != -1){
                        if(!rpMap.containsKey(index) || rpMap.containsKey(index) && a[rpMap.get(index)].length() < a[i].length()){
                            rpMap.put(index, i);
                        }
                    }
                    j = j + a[i].length() - 1;
                }

            }
            return rpMap;
        }
    }
}
