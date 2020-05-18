package Interviews.Nextdoor;

import java.util.PriorityQueue;

public class Remove_Exact_One_Digit {
    /**
     * remove exact one digit char from string s or t, so that s < t;
     * input: String s1,s2 (lower case letters and digits)
     * output: number of ways to remove the digit char.
     */
    public static int remove_exact_one_digit(String s, String t) {
        // remove 1 digits from s1 or s2  to make s1 < s2
        PriorityQueue<String> pqT = new PriorityQueue();
        PriorityQueue<String> pqS = new PriorityQueue();
        pqT.offer(t);
        pqS.offer(s);

        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                System.out.println(s.substring(0, i) + s.substring(i + 1));
                pqT.offer(s.substring(0, i) + s.substring(i + 1));
            }
        }

        while (!pqT.peek().equals(t)) {
            result++;
            pqT.poll();
        }

        for (int i = 0; i < t.length(); i++) {
            if (Character.isDigit(t.charAt(i))) {
                System.out.println(t.substring(0, i) + t.substring(i + 1));
                pqS.offer(t.substring(0, i) + t.substring(i + 1));
            }
        }

        while (!pqS.peek().equals(s)) {
            pqS.poll();
        }

        pqS.poll();
        result += pqS.size();

        return result;
    }

    public static void main(String[] args) {
        int result = remove_exact_one_digit("123ab", "423cd");
        System.out.println(result);
    }
}
