package Interviews.Apple;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {
    /**
     *  the deadlock in this example won't resolve after the Thread.sleep(1000) finishes.
     *  The issue is that the main thread is waiting indefinitely for the lock that the new thread
     *  is holding. Even after the sleep period, the new thread never releases the lock because
     *  it's stuck waiting for the main thread to proceed.
     *
     * Both threads are waiting on each other: the new thread holds the lock and the main thread
     * is waiting to acquire the same lock, causing a deadlock. Neither thread can proceed, and
     * the deadlock won't be resolved automatically
     *
     * The new thread never reaches the lock.unlock() in the finally block because the main thread
     * is holding the signal.wait() indefinitely. The new thread sends the signal using signal.notify(),
     * but since the main thread is waiting to acquire the lock afterward, it creates a deadlock.
     * The main thread tries to acquire the lock while the new thread holds it, and the new thread
     * can't finish and release the lock because the main thread is waiting to acquire the same lock,
     * causing the deadlock.
     *
     * The signal mechanism using notify() and wait() allows inter-thread communication between the new
     * thread and the main thread. Here's how it works:
     *
     * New thread: It acquires the lock, performs some work, and calls signal.notify() to inform the main
     * thread that it can proceed.
     *
     * Main thread: It calls signal.wait() and pauses until it receives the notification (notify()) from the
     * new thread. After being notified, the main thread continues and tries to acquire the lock.
     * The deadlock happens when the main thread tries to acquire the lock while the new thread is still
     * holding it. The new thread is stuck in the finally block, unable to release the lock because the main
     * thread is trying to acquire it, creating the deadlock situation.
     *
     * Key points:
     * notify() wakes up a waiting thread.
     * wait() pauses execution until notified by another thread.
     * Deadlock occurs when two threads are waiting for each other to release resources (like locks).
     */
    private static Lock lock = new ReentrantLock();
    private static Object signal = new Object();

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            lock.lock();  // Acquire the lock in the new thread
            try {
                System.out.println("Thread: Lock acquired, sending signal to main thread...");
                synchronized (signal) {
                    signal.notify();  // Notify the main thread
                }
                // Simulate some work while holding the lock
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } finally {
                lock.unlock();  // This will never be reached, causing a deadlock
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
        lock.lock();  // Deadlock occurs here as the new thread holds the lock
        try {
            System.out.println("Main thread: Lock acquired.");
        } finally {
            lock.unlock();
        }
    }
}

