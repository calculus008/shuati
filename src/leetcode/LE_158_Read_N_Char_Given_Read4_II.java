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

    public class Solution_Practice {
        int buffPtr = 0;
        int buffCount = 0;
        char[] buff = new char[4];

        public int read(char[] buf, int n) {
            int count = 0;

            while (count < n) {
                if (buffPtr == 0) {
                    buffCount = read4(buff);
                }

                if (buffCount == 0)  break;

                while (count < n && buffPtr < buffCount) {
                    buf[count++] = buff[buffPtr++];
                }

                if (buffPtr >= buffCount) {
                    buffPtr = 0;
                }
            }

            return count;
        }

        //dummy method
        public int read4(char[] input) {
            return 4;
        }
    }

    /**
        "The read function may be called multiple times" :
        It implies that we need to deal with the case that there are still chars left in buff from the last call
        when we start a new call.

        Therefore, we need to have global variables to track:

        1.Current pointer into buff after last call - buffPtr (we will have left-over in buff from the last call)
        2.Number chars read by calling read4() - buffCnt

        abcd efgh

        read(buf, 2) : before : buffPtr = 0, buffCnt = 0, buff=[]
                       after :  buffPtr = 2, buffCnt = 4, buff=[abcd]
        read(buf, 3) : before : buffPtr = 2, buffCnt = 4, buff=[abcd]
                       after :  buffPtr = 1, buffCnt = 4, buff=[efgh]

         Bad problem description...Took long time util I find this is a buffered Reading.(!!!)
         buf[] is a user buffer where we strore result to. Basic idea is we create a internal buffer (buff) and
         every time we read from file, we read from this internal buffer first to avoid expensive system call
         ( here you can imagine sys call is read4()).
         buffPtr point to next unread char in buff and buffcount indicate length of buff. So when we call read(),
         we read from buff starting with buffPtr. Once buffPtr == buffCount we finish reading buff and we have to call
         read4() to refill internal buff and read from buff again.
         It is important to note that read4() is a system call and every call increament file position by 4 (like C *File)
    */

    class Solution {
        /**
         * @param buf Destination buffer
         * @param n   Maximum number of characters to read
         * @return    The number of characters read
         */

        private int buffPtr = 0;
        private int buffCnt = 0;
        private char[] buff = new char[4];

        public int read(char[] buf, int n) {
            int ptr = 0;//count how many chars read in this call, local to the method

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
}
