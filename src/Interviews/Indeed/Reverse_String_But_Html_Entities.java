package Interviews.Indeed;

public class Reverse_String_But_Html_Entities {
    /**
     * Reverse a given string, but not reverse the HTML entities in it.
     *
     * HTML entity : Some characters are reserved in HTML.
     * https://www.w3schools.com/html/html_entities.asp
     *
     * Example :
     * <  :  &lt;
     * €  :  &euro;
     * &  :  &amp;
     *
     * input -  "1234&eur;o;5677&&eu;567&""
     * output - "&765&eu;&7765;o&eur;4321"
     */
    public static void reverse(char[] chs, int l, int r) {
        while (l < r) {
            char temp = chs[l];
            chs[l] = chs[r];
            chs[r] = temp;
            l++;
            r--;
        }
    }

    public static String reverseHtml(String s) {
        if (s == null || s.length() == 0) return s;

        char[] chs = s.toCharArray();
        reverse(chs, 0, chs.length - 1);

        int start = 0;
        int end = 0;

        while (end < chs.length) {
            /**
             * find a potential reversed html entity
             */
            if (chs[end] == ';') {
                start = end;
            } else if (chs[end] == '&') {
                /**
                 * we find a reversed html entity, now reverse it
                 */
                if (chs[start] == ';') {
                    reverse(chs, start, end);
                    start = end + 1;
                }
            }

            /**
             * !!!
             */
            end++;
        }

        return new String(chs);
    }


    /**
     * If combined with LE_151_Reverse_Words_In_A_String, requires space O(1)
     *
     * Still reverse twice, use reverseHtml()
     */
    public static void reverseHtml2(char[] chs, int s, int e) {
        if (chs == null || chs.length == 0) return;

        reverse(chs, s, e);

        int start = s;
        int end = s;

        while (end <= e) {
            /**
             * find a potential reversed html entity
             */
            if (chs[end] == ';') {
                start = end;
            } else if (chs[end] == '&') {
                /**
                 * we find a reversed html entity, now reverse it
                 */
                if (chs[start] == ';') {
                    System.out.println("reverse start="+start + ", end=" + end);
                    reverse(chs, start, end);
                    start = end + 1;
                }
            }

            /**
             * !!!
             */
            end++;
        }
    }

    public static String reverseWordsWithHtml(String s) {
        if (null == s || s.length() == 0) return s;

        char[] chars = s.toCharArray();
        int n = chars.length;
        reverseHtml2(chars, 0, n - 1);

        System.out.println(new String(chars));

        int i = 0;//runner
        int j = 0;
        while (i < n) {
            /**
             * !!!
             * In inner while loop, always check if it's "< n"
             */
            while (i < j || i < n && chars[i] == ' ') i++;
            while (j < i || j < n && chars[j] != ' ') j++;
            /**
             * !!1
             * "j - 1"
             */
            System.out.println("i=" + i + ", j=" + j);
            reverseHtml2(chars, i, j - 1);
            System.out.println(new String(chars));
        }

        return cleanup(chars);
    }

    /**
     * remove extra spaces
     */
    private static String cleanup(char[] chars) {
        int n = chars.length;
        int i = 0, j = 0;

        while (j < n) {
            while (j < n && chars[j] == ' ') j++;
            while (j < n && chars[j] != ' ') chars[i++] = chars[j++];
            while (j < n && chars[j] == ' ') j++;

            if (j < n) chars[i++] = ' ';
        }

        /**
         * !!!
         * return substring
         */
        return new String(chars).substring(0, i);
    }

    public static void main(String[] args) {
        String s = "1234&eur;o;5677&&eu;567&";
        System.out.println(reverseHtml(s));

        String s1 = "1234&eur; o;5677  &&eu;567&";
        System.out.println(reverseWordsWithHtml(s1));
    }
}
