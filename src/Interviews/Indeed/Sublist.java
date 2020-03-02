package Interviews.Indeed;

import java.util.*;

public class Sublist {
    /**
     * 给⼀一个list， 如何把⾥里里⾯面的字符分配到尽量量少的⼦子list⾥里里，
     * 并且每个⼦子list没有重复元素 ['a','b','c','a','a','b']，
     * 可以分成['a', 'b', 'c'], ['a', 'b'], ['a']
     * ['a', 'a', 'a', 'b', 'b', 'b']，可以分成['a', 'b'], ['a', 'b'], ['a', 'b']
     */

    /**
     * 可以先数⼀一遍字符个数，找到出现最多的，⽐比如a出现3 次，就建3个⼦子list，然后把每种字符
     * round robin那样放进各个list就⾏行行了了， 这样是O(n)
     */
    public static List<List<Character>> getList(char[] str) {
        List<List<Character>> res = new ArrayList<>();
        if (str == null || str.length == 0) return res;

        Map<Character, Integer> map = new HashMap<>();

        for (char c : str) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int max = 0;
        Character maxChar = null;

        for (char c : map.keySet()) {
            if (map.get(c) > max) {
                maxChar = c;
                max = map.get(c);
            }
        }

        int count = map.get(maxChar);
        for (int i = 0; i < count; i++) {
            List<Character> list = new ArrayList<>();
            list.add(maxChar);
            res.add(list);
        }

        map.remove(maxChar);

        for (char c : map.keySet()) {
            int n = map.get(c);
            for (int i = 0; i < n; i++) {
                res.get(i).add(c);
            }
        }

        return res;
    }

    private static void printRes(List<List<Character>> lists) {
        for (List<Character> list : lists) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

    public static void main(String[] args) {
        char[] input = {'a','b','c','a','a','b'};
        List<List<Character>> res = getList(input);
        printRes(res);
    }
}
