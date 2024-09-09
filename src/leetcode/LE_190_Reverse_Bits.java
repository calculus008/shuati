package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_190_Reverse_Bits {
    /**
     * Reverse bits of a given 32 bits unsigned integer.
     *
     * Note:
     *
     * Note that in some languages, such as Java, there is no unsigned integer type. In this case, both input and output
     * will be given as a signed integer type. They should not affect your implementation, as the integer's internal
     * binary representation is the same, whether it is signed or unsigned.
     * In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 2 above,
     * the input represents the signed integer -3 and the output represents the signed integer -1073741825.
     *
     *
     * Example 1:
     * Input: n = 00000010100101000001111010011100
     * Output:    964176192 (00111001011110000010100101000000)
     * Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596,
     * so return 964176192 which its binary representation is 00111001011110000010100101000000.
     * 00000010100101000001111010011100
     * 00111001011110000010100101000000
     *
     * Example 2:
     * Input: n = 11111111111111111111111111111101
     * Output:   3221225471 (10111111111111111111111111111111)
     * Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293,
     * so return 3221225471 which its binary representation is 10111111111111111111111111111111.
     *
     *
     * Constraints:
     *
     * The input must be a binary string of length 32
     *
     * Follow up: If this function is called many times, how would you optimize it?
     *
     * Easy
     *
     * https://leetcode.com/problems/reverse-bits
     */

    public class Solution_reverse_bit_by_bit {
        // you need treat n as an unsigned value
        // Space and Time O(1)
        public int reverseBits(int n) {
            int ret = 0, power = 31;
            while (n != 0) {
                ret += (n & 1) << power;
                n = n >>> 1;
                power -= 1;
            }
            return ret;
        }
    }

    public class Solution_without_loop {
        /**
         * 1). First, we break the original 32-bit into 2 blocks of 16 bits, and switch them.
         * 2). We then break the 16-bits block into 2 blocks of 8 bits. Similarly, we switch the position of the 8-bits blocks
         * 3). We then continue to break the blocks into smaller blocks, until we reach the level with the block of 1 bit.
         * 4). At each of the above steps, we merge the intermediate results into a single integer which serves as the input
         *    for the next step.
         */
        public int reverseBits(int n) {
            n = (n >>> 16) | (n << 16);
            n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
            n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
            n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
            n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
            return n;
        }
    }


    /**
     * *******************************************
     */
    // you need treat n as an unsigned value
    public int reverseBits1(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result += n & 1;
            n >>= 1;
        }
        return result;
    }

    public int reverseBits2(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result += n & 1;
            n >>>= 1;
            if (i < 31) {
                result <<= 1;
            }
        }
        return result;
    }

    public int reverseBits3(int n) {
        return Integer.reverse(n);
    }

    /*
        How to optimize if this function is called multiple times?

        We can divide an int into 4 bytes, and reverse each byte then combine into an int.
        For each byte, we can use cache to improve performance.
     */

    // cache
    private final Map<Byte, Integer> cache = new HashMap<Byte, Integer>();
    public int reverseBits4(int n) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) // convert int into 4 bytes
            bytes[i] = (byte)((n >>> 8*i) & 0xFF);
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result += reverseByte(bytes[i]); // reverse per byte
            if (i < 3)
                result <<= 8;
        }
        return result;
    }

    private int reverseByte(byte b) {
        Integer value = cache.get(b); // first look up from cache
        if (value != null)
            return value;
        value = 0;
        // reverse by bit
        for (int i = 0; i < 8; i++) {
            value += ((b >>> i) & 1);
            if (i < 7)
                value <<= 1;
        }
        cache.put(b, value);
        return value;
    }
}
