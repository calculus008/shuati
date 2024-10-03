package leetcode.Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

public class LE_1116_Print_Zero_Even_Odd {
    /**
     * You have a function printNumber that can be called with an integer parameter and prints it to the console.
     *
     * For example, calling printNumber(7) prints 7 to the console.
     * You are given an instance of the class ZeroEvenOdd that has three functions: zero, even, and odd. The same instance of ZeroEvenOdd will be passed to three different threads:
     *
     * Thread A: calls zero() that should only output 0's.
     * Thread B: calls even() that should only output even numbers.
     * Thread C: calls odd() that should only output odd numbers.
     * Modify the given class to output the series "010203040506..." where the length of the series must be 2n.
     *
     * Implement the ZeroEvenOdd class:
     *
     * ZeroEvenOdd(int n) Initializes the object with the number n that represents the numbers that should be printed.
     * void zero(printNumber) Calls printNumber to output one zero.
     * void even(printNumber) Calls printNumber to output one even number.
     * void odd(printNumber) Calls printNumber to output one odd number.
     *
     *
     * Example 1:
     *
     * Input: n = 2
     * Output: "0102"
     * Explanation: There are three threads being fired asynchronously.
     * One of them calls zero(), the other calls even(), and the last one calls odd().
     * "0102" is the correct output.
     * Example 2:
     *
     * Input: n = 5
     * Output: "0102030405"
     *
     *
     * Constraints:
     * 1 <= n <= 1000
     *
     * Medium
     *
     * https://leetcode.com/problems/print-zero-even-odd/description/
     */

    class ZeroEvenOdd_with_Semaphore {
        private int n;
        Semaphore s0, sEven, sOdd;

        public ZeroEvenOdd_with_Semaphore(int n) {
            this.n = n;
            s0 = new Semaphore(1);
            sEven = new Semaphore(0);
            sOdd = new Semaphore(0);
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= this.n; i++) {
                s0.acquire();
                printNumber.accept(0);
                if (i % 2 == 0) {
                    sEven.release();
                }else {
                    sOdd.release();
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for (int i = 2; i <= this.n; i += 2) {
                sEven.acquire();
                printNumber.accept(i);
                s0.release();
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= this.n; i += 2) {
                sOdd.acquire();
                printNumber.accept(i);
                s0.release();
            }
        }
    }

    class ZeroEvenOdd_with_ReentrantLock {
        private int n;
        private int count = 1; // To keep track of the current number
        private Lock lock = new ReentrantLock();
        private Condition zeroCondition = lock.newCondition();
        private Condition oddCondition = lock.newCondition();
        private Condition evenCondition = lock.newCondition();
        private boolean zeroTurn = true; // To control the alternation between zero and numbers

        public ZeroEvenOdd_with_ReentrantLock(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (!zeroTurn) {
                        zeroCondition.await();
                    }
                    printNumber.accept(0);  // Print 0
                    zeroTurn = false;
                    if (count % 2 == 1) {
                        oddCondition.signal();  // Signal the odd thread
                    } else {
                        evenCondition.signal(); // Signal the even thread
                    }
                } finally {
                    lock.unlock();
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for (int i = 2; i <= n; i += 2) {
                lock.lock();
                try {
                    while (zeroTurn || count % 2 == 1) { // Wait if it's not the turn of even numbers
                        evenCondition.await();
                    }
                    printNumber.accept(count);  // Print the even number
                    count++;
                    zeroTurn = true;
                    zeroCondition.signal();  // Signal the zero thread
                } finally {
                    lock.unlock();
                }
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i += 2) {
                lock.lock();
                try {
                    while (zeroTurn || count % 2 == 0) { // Wait if it's not the turn of odd numbers
                        oddCondition.await();
                    }
                    printNumber.accept(count);  // Print the odd number
                    count++;
                    zeroTurn = true;
                    zeroCondition.signal();  // Signal the zero thread
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
