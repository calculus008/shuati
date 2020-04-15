package Interviews.Lyft.lc;

public class MinSlidingWindow {
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0) return "";

        int[] count = new int[256];
        for (char c : t.toCharArray()) {
            count[c]++;
        }
        int total = t.length();
        int start = -1;
        int len = Integer.MAX_VALUE;

        for (int i = 0, j = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (count[c] > 0) {
                total--;
            }
            count[c]--;

            while (total == 0) {
                if (i - j + 1 < len) {
                    start = j;
                    len = i - j + 1;
                }

                count[s.charAt(j)]++;
                if (count[s.charAt(j)] > 0) {
                    total++;
                }
                j++;
            }
        }

        return start == -1? "" : s.substring(start, start + len);
    }

    public static void main(String[] args) {
        String s = "ADAOBECCODEBAANCC";
        String t = "AABCC";

        System.out.println(minWindow(s, t));
    }
}
