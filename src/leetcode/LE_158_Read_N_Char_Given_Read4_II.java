package leetcode;

/**
 * Created by yuank on 3/19/18.
 */
public class LE_158_Read_N_Char_Given_Read4_II {
    /**
        The API: int read4(char *buf) reads 4 characters at a time from a file.

        The return value is the actual number of characters read. For example,
        it returns 3 if there is only 3 characters left in the file.

        By using the read4 API, implement the function int read(char *buf, int n)
        that reads n characters from the file.

        Note:
        The read function may be called multiple times.
     */

    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */

    /**
        "The read function may be called multiple times" :
        It implies that we need to deal the case that there are still chars left in buff from the last call
        when we start a new call.

        Therefore, we need to have global variables to track:

        1.Current pointer into buff after last call - buffPtr
        2.Number chars read by calling read4() - buffCnt

        abcd efgh

        read(buf, 2) : before : buffPtr = 0, buffCnt = 0, buff=[]
                       after :  buffPtr = 2, buffCnt = 4, buff=[abcd]
        read(buf, 3) : before : buffPtr = 2, buffCnt = 4, buff=[abcd]
                       after :  buffPtr = 1, buffCnt = 4, buff=[efgh]
    */

    private int buffPtr = 0;
    private int buffCnt = 0;
    private char[] buff = new char[4];

    public int read(char[] buf, int n) {
        int ptr = 0;//how many chars read in this call, local to the method

        while (ptr < n) {
            if (buffPtr == 0) {//nothing in buff to read, call read4 to get more chars from stream
                buffCnt = read4(buff);
            }

            //if buffPtr is not 0, then there are chars left from last call.

            if (buffCnt == 0) {
                break;//nothing read from stream
            }

            //when there's more in buff to be read from and we haven't read all n chars for this call, read.
            while (ptr < n && buffPtr < buffCnt) {
                buf[ptr++] = buff[buffPtr++];
            }

            //no more chars in buff, reset buffPtr
            if (buffPtr >= buffCnt) {
                buffPtr = 0;
            }
        }
        return ptr;
    }

    //dummy method
    public int read4(char[] input) {
        return 4;
    }
}
