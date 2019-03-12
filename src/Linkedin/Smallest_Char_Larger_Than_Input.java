package Linkedin;

public class Smallest_Char_Larger_Than_Input {
    /***
     * Return the smallest character that is strictly larger than the search character,
     *
     * If no such character exists, return the smallest character in the array
     *
     * @param sortedStr : sorted list of letters, sorted in ascending order.
     * @param c : character for which we are searching.
     *
     * Given the following inputs we expect the corresponding output:
     * ['c', 'f', 'j', 'p', 'v'], 'a' => 'c'
     * ['c', 'f', 'j', 'p', 'v'], 'c' => 'f'
     * ['c', 'f', 'j', 'p', 'v'], 'k' => 'p'
     * ['c', 'f', 'j', 'p', 'v'], 'z' => 'c' // The wrap around case
     * ['c', 'f', 'k'], 'f' => 'k'
     * ['c', 'f', 'k'], 'c' => 'f'
     * ['c', 'f', 'k'], 'd' => 'f'
     */

    public static char findNextChar(char[] list, char c) {
        int l = 0;
        int r = list.length;

        while (l < r) {
            int m = l + (r - l) / 2;
            if (list[m] > c) {
                r = m;
            } else {
                l = m + 1;
            }
        }

        /**
         * check the case of wrap-up
         */
        return l >= list.length ? list[0] : list[l];
    }

    public static void main(String[] args) {
        char[][] tests = {{'c', 'f', 'j', 'p', 'v'}, {'c', 'f', 'k'}};

        char[][] target = {{'a', 'c', 'k', 'z'}, {'f', 'c', 'd'}};
        for (char c : target[0]) {
            char res = findNextChar(tests[0], c);
            System.out.println(res);
        }

        for (char c : target[1]) {
            char res = findNextChar(tests[1], c);
            System.out.println(res);
        }
    }
}
