package leetcode;

/**
 * Created by yuank on 3/19/18.
 */
public class LE_157_Read_N_Char_Given_Read4 {
    /*
        The API: int read4(char *buf) reads 4 characters at a time from a file.

        The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.

        By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.

        Note:
        The read function will only be called once for each test case.
     */

    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    /*
       Very Important

       Key issue is to deal with the number of chars read, 'n' is the number we want to read, but we don't know
       how many chars coming from the stream (m), so :
       1.n < m : we stop reading when we have n chars, ignore the rest.This is simple, we just count how many chars we have got and stop when we have all.
       2.n == m :number matched.
       3.n > m : we want to read n chars but in the end there is not enough chars from stream, so only get n chars.

       Time : O(n), Space : O(1)
    */
    public int read1(char[] buf, int n) {
        char[] temp = new char[4];
        int index = 0;

        while (true) {
            int count = read4(temp);
            //!!! this is the key to deal with case #3."count" is what we actually read from stream, "n - index" is what we
            //    want to read. For case #3, we just read the min of those two values, which will be "count".
            count = Math.min(count, n - index);
            for (int i = 0; i < count; i++) {
                buf[index++] = temp[i];
            }

            //Only return for the 2 possible outcome:
            //"index == n" : for case #1 and #2
            //"count < 4"  : for case #4 (want to read more but not enough chars left in stream)
            if (index == n || count < 4) {
                return index;
            }
        }
    }

    //dummy method
    public int read4(char[] input) {
        return 4;
    }

    //This version is easier to understand.
    public int read2(char[] buf, int n) {
        char[] temp = new char[4];
        int index = 0; //count how many chars have been read
        boolean eof = false;

        while(!eof && n > index) {
            int count = read4(temp);
            if (count < 4) {
                eof = true;
            }

            count = Math.min(count, n - index);
            for(int i=0; i < count; i++) {
                buf[index++] = temp[i];
            }
        }

        return index;
    }
}
