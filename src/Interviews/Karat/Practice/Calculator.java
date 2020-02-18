package Interviews.Karat.Practice;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Calculator {


    public static int calculate1(String s) {
        if (s == null || s.length() == 0) return 0;

        Deque<Character> q = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            q.offer(c);
        }

        int num = 0;
        int res = 0;
        int sign = 1;

        while (!q.isEmpty()) {
            char c = q.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '+' || c == '-') {
                res += sign * num;
                num = 0;
                sign = c == '+' ? 1 : -1;
            }
        }

        res += sign * num;

        return res;
    }

    public static int calculate2(String s) {
        if (s == null || s.length() == 0) return 0;

        Deque<Character> q = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            q.offer(c);
        }

        return getResult(q);
    }

    public static int getResult(Deque<Character> q) {
        int num = 0;
        int res = 0;
        int sign = 1;

        while (!q.isEmpty()) {
            char c = q.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '+' || c == '-') {
                res += sign * num;
                num = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                num = getResult(q);
            } else if (c == ')') {
                break;
            }
        }

        res += sign * num;

        return res;
    }

    static class Pair {
        int val;
        String str;

        public Pair(int val, String str) {
            this.val = val;
            this.str = str;
        }
    }

    public static Pair calculate3(String s, Map<String, Integer> map) {
        if (s == null || s.length() == 0) return null;

        Deque<Character> q = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            if (Character.isAlphabetic(s.charAt(i))) {
                StringBuilder sb = new StringBuilder();
                while (i < s.length() && Character.isAlphabetic(s.charAt(i))) {
                    sb.append(s.charAt(i));
                    i++;
                }
                i--;//!!!

                String key = sb.toString();
                if (map.containsKey(key)) {
                    int value = map.get(key);
                    for (char c : String.valueOf(value).toCharArray()) {
                        q.offer(c);
                    }
                } else {
                    for (char c : key.toCharArray()) {
                        q.offer(c);
                    }
                }
            } else {
                q.offer(s.charAt(i));//!!!
            }
        }

        return getResult1(q);
    }

    public static Pair getResult1(Deque<Character> q) {
        int num = 0;
        int res = 0;
        int sign = 1;
        StringBuilder sb = new StringBuilder();

        while (!q.isEmpty()) {
            char c = q.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '+' || c == '-') {
                res += sign * num;//!!!
                num = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                Pair p = getResult1(q);
                res += sign * p.val;

                if (p.str.length() != 0) {
                    if (sign == -1) {
                        char[] chs = p.str.toCharArray();
                        for (int i = 0; i < chs.length; i++) {
                            if (chs[i] == '+') {
                                chs[i] = '-';
                            } else if (chs[i] == '-') {
                                chs[i] = '+';
                            }
                        }
                        sb.append(new String(chs));
                    } else {
                        sb.append(p.str);
                    }
                }
            } else if (c == ')') {
                break;
            } else if (Character.isAlphabetic(c)) {
                sb.append(sign == 1 ? '+' : '-');//!!!
                sb.append(c);
                while (!q.isEmpty() && Character.isAlphabetic(q.peek())) {
                    sb.append(q.poll());
                }
            }
        }

        res += sign * num;//!!!
        Pair result = new Pair(res, sb.toString());

        System.out.println(result.val + result.str);

        return result;
    }

    public static void main(String[] args) {
        String input1 = "5+3-2+1-1";
        String input2 = "(4+2)-((15-10)-30)";

        System.out.println(calculate1(input1));
        System.out.println(calculate2(input2));

        Map<String, Integer> map = new HashMap<>();
        map.put("temperature", 5);
        map.put("e", 1);
        map.put("y", 2);
        String s3 = "(e+18)-(pressure+3-(temperature+8)+y)+yy";
        //Correct result - "27-pressure+yy"
        Pair result = calculate3(s3, map);

        System.out.println(result.val + result.str);
    }

}
