package src.Interviews.Karat;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Basic_Calculator_Practice_Clean {
    public static int calculate1(String s) {
        if (s == null || s.length() == 0) return 0;

        int sign = 1;
        int num = 0;
        int res = 0;
        char[] chs = s.toCharArray();

        for (int i = 0; i < chs.length; i++) {
            if (Character.isDigit(chs[i])) {
                while (i < chs.length && Character.isDigit(chs[i])) {
                    num = num * 10 + chs[i] - '0';
                    i++;
                }
                i--;
            } else if (chs[i] == '+' || chs[i] == '-') {
                res += sign * num;
                sign = chs[i] == '+' ? 1 : -1;
                num = 0;
            }
        }

        res += sign * num;

        return res;
    }


    public static int calculate2(String s) {
        if (s == null || s.length() == 0) return 0;

        int sign = 1;

        char[] chs = s.toCharArray();
        int res = 0;
        int num = 0;
        for (int i = 0; i < chs.length; i++) {
            if (Character.isDigit(chs[i])) {
                while (i < chs.length && Character.isDigit(chs[i])) {
                    num = num * 10 + chs[i] - '0';
                    i++;
                }
                i--;
            } else if (chs[i] == '+' || chs[i] == '-') {
                res += sign * num;
                num = 0;
                sign = chs[i] == '+' ? 1 : -1;
            } else if (chs[i] == '(') {
                int start = i;
                int count = 1;

                i++;
                while (i < chs.length && count > 0) {
                    if (chs[i] == '(') {
                        count++;
                    } else if (chs[i] == ')') {
                        count--;
                    }
                    i++;
                }

                res += sign * calculate2(s.substring(start + 1, i - 1));
                i--;
            }
        }

        res += sign * num;

        return res;
    }

    /**
     * ==============================================
     */
    public static int calculateWithQueue1(String s) {
        if (s == null || s.length() == 0) return 0;

        Deque<Character> q = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            q.offer(c);
        }

        int num = 0;
        int sign = 1;
        int res = 0;

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

        return res += sign * num;
    }

    public static int calculateWithQueue2(String s) {
        if (s == null || s.length() == 0) return 0;

        Deque<Character> q = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            q.offer(c);
        }

        return helper1(q);
    }

    private static int helper1(Deque<Character> q) {
        int num = 0;
        int sign = 1;
        int res = 0;

        while (!q.isEmpty()) {
            char c = q.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '+' || c == '-') {
                res += sign * num;
                num = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                num = helper1(q);
            } else if (c == ')') {
                break;
            }
        }

        return res += sign * num;
    }


    static class Pair {
        int    val;
        String str;

        public Pair(int val, String str) {
            this.val = val;
            this.str = str;
        }
    }

    private static String calculateWithQueue3 (String s, Map<String, Integer> map) {
        if (s == null || s.length() == 0) return "";

        Deque<Character> q = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') continue;

            if (Character.isAlphabetic(s.charAt(i))) {
                StringBuilder sb = new StringBuilder();
                while (i < s.length() && Character.isAlphabetic(s.charAt(i))) {
                    sb.append(s.charAt(i));
                    i++;
                }
                i--;

                String key = sb.toString();

                if (map.containsKey(key)) {
                    int num = map.get(key);
                    for (char c : String.valueOf(num).toCharArray()) {
                        q.offer(c);
                    }
                } else {
                    for (char c : key.toCharArray()) {
                        q.offer(c);
                    }
                }
            } else {
                q.offer(s.charAt(i));
            }
        }

        Pair p = helper2(q, map);

        return p.val + p.str;
    }

    private static Pair helper2(Deque<Character> q,  Map<String, Integer> map) {
        int num = 0;
        int sign = 1;
        int res = 0;
        StringBuilder sb = new StringBuilder();

        while (!q.isEmpty()) {
            char c = q.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '+' || c == '-') {
                res += sign * num;
                num = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                Pair p = helper2(q, map);
                res += sign * p.val;

                if (p.str.length() != 0) {
                    if (sign == -1) {
                        char[] temp = p.str.toCharArray();
                        for (int i = 0; i < temp.length; i++) {
                            if (temp[i] == '+') {
                                temp[i] = '-';
                            } else if (temp[i] == '-') {
                                temp[i] = '+';
                            }
                        }
                        sb.append(new String(temp));
                    } else {
                        sb.append(p.str);
                    }
                }
            } else if (c == ')') {
                break;
            } else if (Character.isAlphabetic(c)) {
                sb.append(sign == 1 ? "+" : "-");
                sb.append(c);
                while (!q.isEmpty() && Character.isAlphabetic(q.peek())) {
                    sb.append(q.poll());
                }
            }
        }

        res += sign * num;

        return new Pair(res, sb.toString());
    }


    public static void main(String[] args) {
        String s1 = "10+35-11+5";
        String s2 = "1+((3+(3+11))-(3+2))-9";

        System.out.println(calculate1(s1));
        System.out.println(calculateWithQueue1(s1));

        System.out.println(calculate2(s2));
        System.out.println(calculateWithQueue2(s2));

        Map<String, Integer> map = new HashMap<>();
        map.put("temperature", 5);
        map.put("e", 1);
        map.put("y", 2);

        String s3 = "(e+8)-(pressure+3-(temperature+8)+y)+yy";
        //Correct result - "17-pressure+yy"
        System.out.println(calculateWithQueue3(s3, map));
    }
}
