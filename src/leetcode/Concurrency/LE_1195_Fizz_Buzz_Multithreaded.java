package leetcode.Concurrency;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class LE_1195_Fizz_Buzz_Multithreaded {
    /**
     * Write a program that outputs the string representation of numbers from 1 to n, however:
     *
     * If the number is divisible by 3, output "fizz".
     * If the number is divisible by 5, output "buzz".
     * If the number is divisible by both 3 and 5, output "fizzbuzz".
     * For example, for n = 15, we output: 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.
     *
     * Suppose you are given the following code:
     *
     * class FizzBuzz {
     *   public FizzBuzz(int n) { ... }               // constructor
     *   public void fizz(printFizz) { ... }          // only output "fizz"
     *   public void buzz(printBuzz) { ... }          // only output "buzz"
     *   public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
     *   public void number(printNumber) { ... }      // only output the numbers
     * }
     * Implement a multithreaded version of FizzBuzz with four threads.
     * The same instance of FizzBuzz will be passed to four different threads:
     *
     * Thread A will call fizz() to check for divisibility of 3 and outputs fizz.
     * Thread B will call buzz() to check for divisibility of 5 and outputs buzz.
     * Thread C will call fizzbuzz() to check for divisibility of 3 and 5 and outputs fizzbuzz.
     * Thread D will call number() which should only output the numbers.
     *
     * Medium
     */

    class FizzBuzz_Semaphore {
        private int n;
        private Semaphore fizz = new Semaphore(0);
        private Semaphore buzz = new Semaphore(0);
        private Semaphore fizzbuzz = new Semaphore(0);
        private Semaphore num = new Semaphore(1);

        public FizzBuzz_Semaphore(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int k = 1; k <= n; k++) {
                if (k % 3 == 0 && k % 5 != 0) {
                    fizz.acquire();
                    printFizz.run();
                    releaseLock(k + 1);
                }
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int k = 1; k <= n; k++) {
                if (k % 5 == 0 && k % 3 != 0) {
                    buzz.acquire();
                    printBuzz.run();
                    releaseLock(k + 1);
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int k = 1; k <= n; k++) {
                if (k % 15 == 0) {
                    fizzbuzz.acquire();
                    printFizzBuzz.run();
                    releaseLock(k + 1);
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int k = 1; k <= n; k++) {
                if (k % 3 != 0 && k % 5 != 0) {
                    num.acquire();
                    printNumber.accept(k);
                    releaseLock(k + 1);
                }
            }
        }

        public void releaseLock(int n) {
            if (n % 3 == 0 && n % 5 != 0) {
                fizz.release();
            } else if (n % 5 == 0 && n % 3 != 0) {
                buzz.release();
            } else if (n % 15 == 0) {
                fizzbuzz.release();
            } else {
                num.release();
            }
        }
    }

    /**
     * Algorithm
     *
     * We have 4 threads running simultaneously.
     * num will start at 1, and will be incremented to n.
     * Each thread checks num to see if it should take its turn
     * If thread should print, then
     * print
     * increment num
     * wake up all waiting threads
     * Else, wait() on this thread until another thread processes num.
     * Once 1 of the 4 threads processes num, that thread will call notifyAll() to wake up all 4 threads.
     *
     * The while (num <= n) is in the code to ensure each thread has the current number num in valid range
     * [1 to n] to process.
     */
    class FizzBuzz {
        private int n;
        private int num = 1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            while (num <= n) {
                if (num % 15 == 0) {
                    printFizzBuzz.run();
                    num++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }

        public synchronized void fizz(Runnable printFizz) throws InterruptedException {
            while (num <= n) {
                if (num % 3 == 0 && num % 5 != 0) {
                    printFizz.run();
                    num++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }

        public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
            while (num <= n) {
                if (num % 3 != 0 && num % 5 == 0) {
                    printBuzz.run();
                    num++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }

        public synchronized void number(IntConsumer printNumber) throws InterruptedException {
            while (num <= n) {
                if (num % 3 != 0 && num % 5 != 0) {
                    printNumber.accept(num);
                    num++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }
    }
}
