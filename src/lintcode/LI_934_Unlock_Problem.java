package lintcode;

import java.util.Arrays;

public class LI_934_Unlock_Problem {
    /**
     * Given n keys(numbered from 1 to n) and m locks(numbered from 1 to m).
     * When the number of the lock can be divisible by the number of the key,
     * the lock can be opened/closed. Initially, all locks are locked, then u
     * se all keys to unlock all locks, find the number of locks which is
     * opened in the end.
     *
     * Medium
     */

    public int unlock(int n, int m) {
        int[] lock = new int[m + 1];
        for (int i = 1; i <= n; i++) {
            int j = i;
            while (j < lock.length) {
                /**
                 * 如果被开了，就锁起来，如果锁了就打开， 0^1 = 1, 1^ 1= 0
                 */
                lock[j] ^= 1;
                j += i;
            }
        }
        return Arrays.stream(lock).sum();
    }
}
