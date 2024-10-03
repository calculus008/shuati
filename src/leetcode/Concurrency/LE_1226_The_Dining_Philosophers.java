package leetcode.Concurrency;

import java.util.concurrent.locks.*;

public class LE_1226_The_Dining_Philosophers {
    /**
     * Five silent philosophers sit at a round table with bowls of spaghetti. Forks are placed between each pair of adjacent philosophers.
     *
     * Each philosopher must alternately think and eat. However, a philosopher can only eat spaghetti when they have both left and right forks.
     * Each fork can be held by only one philosopher and so a philosopher can use the fork only if it is not being used by another philosopher.
     * After an individual philosopher finishes eating, they need to put down both forks so that the forks become available to others.
     * A philosopher can take the fork on their right or the one on their left as they become available, but cannot start eating before getting both forks.
     *
     * Eating is not limited by the remaining amounts of spaghetti or stomach space; an infinite supply and an infinite demand are assumed.
     *
     * Design a discipline of behaviour (a concurrent algorithm) such that no philosopher will starve; i.e., each can forever continue to alternate
     * between eating and thinking, assuming that no philosopher can know when others may want to eat or think.
     *
     * The philosophers' ids are numbered from 0 to 4 in a clockwise order. Implement the function void wantsToEat(philosopher, pickLeftFork, pickRightFork,
     * eat, putLeftFork, putRightFork) where:
     *
     * philosopher is the id of the philosopher who wants to eat.
     * pickLeftFork and pickRightFork are functions you can call to pick the corresponding forks of that philosopher.
     * eat is a function you can call to let the philosopher eat once he has picked both forks.
     * putLeftFork and putRightFork are functions you can call to put down the corresponding forks of that philosopher.
     * The philosophers are assumed to be thinking as long as they are not asking to eat (the function is not being called with their number).
     * Five threads, each representing a philosopher, will simultaneously use one object of your class to simulate the process.
     * The function may be called for the same philosopher more than once, even before the last call ends.
     *
     * Medium
     *
     * https://leetcode.com/problems/the-dining-philosophers/
     */

    class DiningPhilosophers_1 {
        /**
         * we can change the rules by numbering the forks 1 through 5 and insisting that the philosophers pick up the fork
         * with the lower number first. The philosopher who is sitting between fork 1 and 2 and the philosopher who is sitting
         * between forks 1 and 5 must now reach for the same fork first (fork 1) rather than picking up the one on the right.
         * Whoever gets fork 1 first is now free to take another one. Whoever doesn't get fork 1 must now wait for the first
         * philosopher to release it. Deadlock is not possible.
         */

        // Number of philosophers and forks (n = 5)
        private final int n = 5;

        // Array of locks representing the forks
        private final Lock[] forkLocks = new ReentrantLock[n];

        // Constructor initializes the fork locks
        public DiningPhilosophers_1() {
            for (int i = 0; i < n; i++) {
                forkLocks[i] = new ReentrantLock();  // Initialize each fork as a lock
            }
        }

        // Method called when a philosopher wants to eat
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {

            // Calculate the left and right fork indices
            int leftId = philosopher % n;           // Left fork
            int rightId = (philosopher + 1) % n;    // Right fork

            // Philosopher 1 picks up the right fork first, then the left fork (to avoid deadlock)
            if (philosopher % n == 1) {
                forkLocks[rightId].lock();   // Lock the right fork first
                forkLocks[leftId].lock();    // Then lock the left fork
                pickRightFork.run();         // Pick up the right fork
                pickLeftFork.run();          // Pick up the left fork
                eat.run();                   // Eat
                putLeftFork.run();           // Put down the left fork
                putRightFork.run();          // Put down the right fork
                forkLocks[leftId].unlock();  // Unlock the left fork
                forkLocks[rightId].unlock(); // Unlock the right fork
            } else {
                // Other philosophers pick up the left fork first, then the right fork
                forkLocks[leftId].lock();    // Lock the left fork first
                forkLocks[rightId].lock();   // Then lock the right fork
                pickRightFork.run();         // Pick up the right fork
                pickLeftFork.run();          // Pick up the left fork
                eat.run();                   // Eat
                putLeftFork.run();           // Put down the left fork
                putRightFork.run();          // Put down the right fork
                forkLocks[rightId].unlock(); // Unlock the right fork
                forkLocks[leftId].unlock();  // Unlock the left fork
            }
        }
    }


    class DiningPhilosophers_2 {
        private final Lock[] forks = new ReentrantLock[5];    // Array of locks representing the five forks
        private final Lock lock = new ReentrantLock();        // Global lock to ensure proper synchronization

        public DiningPhilosophers_2() {  // Constructor initializes the forks as ReentrantLocks
            for (int i = 0; i < 5; i++) {
                forks[i] = new ReentrantLock();  // Initialize each fork as a lock
            }
        }

        // This method is called when a philosopher wants to eat
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            int leftFork = philosopher;             // Left fork is the philosopher's number
            int rightFork = (philosopher + 1) % 5;  // Right fork is the next one in the circle

            // Use a global lock to ensure consistency and avoid deadlock (only one philosopher can eat at a given time)
            lock.lock();
            try {
                // Pick up both forks
                forks[leftFork].lock();    // Lock left fork
                pickLeftFork.run();        // Pick up left fork

                forks[rightFork].lock();   // Lock right fork
                pickRightFork.run();       // Pick up right fork

                // Eat after picking up both forks
                eat.run();

                // Put down both forks
                putLeftFork.run();         // Put down left fork
                forks[leftFork].unlock();  // Release left fork

                putRightFork.run();        // Put down right fork
                forks[rightFork].unlock(); // Release right fork
            } finally {
                lock.unlock();  // Release the global lock to allow other philosophers
            }
        }
    }

}
