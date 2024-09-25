package Interviews.Apple;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AvoidDeadlockExample {
    private static Lock lock = new ReentrantLock();
    private static final Object signal = new Object();

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            lock.lock();  // Acquire the lock in the new thread
            try {
                System.out.println("Thread: Lock acquired, performing work...");

                // Simulate work by sleeping for 1 second
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } finally {
                lock.unlock();  // !!! Release the lock before notifying
                System.out.println("Thread: Lock released, sending signal to main thread...");
                synchronized (signal) {
                    signal.notify();  // Notify the main thread
                }
            }
        });

        thread.start();

        // Main thread waits for the signal
        synchronized (signal) {
            try {
                signal.wait();  // Wait for the signal from the new thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Main thread: Trying to acquire lock...");
        lock.lock();  // No deadlock: main thread acquires the lock after new thread has released it
        try {
            System.out.println("Main thread: Lock acquired, proceeding...");
        } finally {
            lock.unlock();
        }
    }
}

