package leetcode.Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class LE_1117_Building_H2O {
    /**
     * There are two kinds of threads: oxygen and hydrogen. Your goal is to group these threads to form water molecules.
     *
     * There is a barrier where each thread has to wait until a complete molecule can be formed. Hydrogen and oxygen threads
     * will be given releaseHydrogen and releaseOxygen methods respectively, which will allow them to pass the barrier.
     * These threads should pass the barrier in groups of three, and they must immediately bond with each other to form a
     * water molecule. You must guarantee that all the threads from one molecule bond before any other threads from the next molecule do.
     *
     * In other words:
     *
     * If an oxygen thread arrives at the barrier when no hydrogen threads are present, it must wait for two hydrogen threads.
     * If a hydrogen thread arrives at the barrier when no other threads are present, it must wait for an oxygen thread and another hydrogen thread.
     * We do not have to worry about matching the threads up explicitly; the threads do not necessarily know which other threads they are paired up with.
     * The key is that threads pass the barriers in complete sets; thus, if we examine the sequence of threads that bind and divide them into groups of three,
     * each group should contain one oxygen and two hydrogen threads.
     *
     * Write synchronization code for oxygen and hydrogen molecules that enforces these constraints.
     *
     *
     *
     * Example 1:
     * Input: water = "HOH"
     * Output: "HHO"
     * Explanation: "HOH" and "OHH" are also valid answers.
     *
     * Example 2:
     * Input: water = "OOHHHH"
     * Output: "HHOHHO"
     * Explanation: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" and "OHHOHH" are also valid answers.
     *
     *
     * Constraints:
     * 3 * n == water.length
     * 1 <= n <= 20
     * water[i] is either 'H' or 'O'.
     * There will be exactly 2 * n 'H' in water.
     * There will be exactly n 'O' in water.
     *
     * Medium
     *
     * https://leetcode.com/problems/building-h2o/description/
     */

    class H2O_with_Semaphore_CyclicBarrier {
        private final CyclicBarrier barrier = new CyclicBarrier(3);
        private final Semaphore hSem = new Semaphore(2);
        private final Semaphore oSem = new Semaphore(1);

        public H2O_with_Semaphore_CyclicBarrier() {}

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            try {
                hSem.acquire();
                barrier.await();
                // releaseHydrogen.run() outputs "H". Do not change or remove this line.
                releaseHydrogen.run();
            } catch(Exception ignore) {

            } finally {
                hSem.release();
            }
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            try {
                oSem.acquire();
                barrier.await();
                // releaseOxygen.run() outputs "O". Do not change or remove this line.
                releaseOxygen.run();
            } catch(Exception ignore) {

            } finally {
                oSem.release();
            }
        }
    }


    class H2O_with_ReentrantLock {
        private int hydrogenCount = 0;  // Counter for hydrogen threads
        private final Lock lock = new ReentrantLock();  // Lock for synchronization
        private final Condition canProceed = lock.newCondition();  // Condition to control thread execution

        public H2O_with_ReentrantLock() {
            // No initialization needed beyond lock and counters
        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            lock.lock();  // Acquire the lock to enter the critical section
            try {
                // Wait if there are already 2 hydrogen threads present (we need an oxygen) -> no more increase for H
                while (hydrogenCount == 2) {
                    canProceed.await();
                }

                // Increment hydrogen count and release hydrogen
                hydrogenCount++;
                releaseHydrogen.run();  // Print "H"

                // If there are 2 hydrogen threads now, signal the condition to proceed
                if (hydrogenCount == 2) {
                    canProceed.signalAll();  // Wake up oxygen and hydrogen if ready
                }
            } finally {
                lock.unlock();  // Always release the lock
            }
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            lock.lock();  // Acquire the lock to enter the critical section
            try {
                // Wait until exactly 2 hydrogen threads are present
                while (hydrogenCount < 2) {
                    canProceed.await();
                }

                // Release the oxygen and reset the hydrogen count (forming a water molecule)
                releaseOxygen.run();  // Print "O"
                hydrogenCount = 0;  // Reset hydrogen count after forming one water molecule

                // Signal any waiting hydrogen threads to proceed
                canProceed.signalAll();  // Signal hydrogen threads to proceed
            } finally {
                lock.unlock();  // Always release the lock
            }
        }
    }

}
