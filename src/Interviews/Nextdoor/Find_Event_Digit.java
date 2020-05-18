package Interviews.Nextdoor;

public class Find_Event_Digit {
    /**
     * Find how many numbers have even digit in a list.
     * Ex.Input: A = [12, 3, 5, 3456]
     * Output: 2
     */
    public static int find_even_digits(int[] a) {
        int result = 0;
        for (int n : a) {
            String s = Integer.toString(n);
            if (s.length() % 2 == 0) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int result = find_even_digits(new int[]{124, 124125, 1124124, 124125});
        System.out.println(result);
    }
}
