package leetcode.Concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.*;

public class LE_1114_Print_In_Order {
    /**
     * Suppose we have a class:
     *
     * public class Foo {
     *   public void first() { print("first"); }
     *   public void second() { print("second"); }
     *   public void third() { print("third"); }
     * }
     * The same instance of Foo will be passed to three different threads.
     * Thread A will call first(), thread B will call second(), and thread
     * C will call third(). Design a mechanism and modify the program to
     * ensure that second() is executed after first(), and third() is
     * executed after second().
     *
     * Example 1:
     * Input: [1,2,3]
     * Output: "firstsecondthird"
     * Explanation: There are three threads being fired asynchronously.
     * The input [1,2,3] means thread A calls first(), thread B calls second(),
     * and thread C calls third(). "firstsecondthird" is the correct output.
     *
     * Example 2:
     * Input: [1,3,2]
     * Output: "firstsecondthird"
     * Explanation: The input [1,3,2] means thread A calls first(), thread B
     * calls third(), and thread C calls second(). "firstsecondthird" is the
     * correct output.
     *
     * Note:
     * We do not know how the threads will be scheduled in the operating system,
     * even though the numbers in the input seems to imply the ordering. The input
     * format you see is mainly to ensure our tests' comprehensiveness.
     *
     * Easy
     */

    /**
     * https://leetcode.com/problems/print-in-order/solution/
     * To summarize, in order to prevent the race condition in concurrency, we need a mechanism that possess two capabilities:
     * 1). access control on critical section.
     * 2). notification to the blocking threads.
     */

    /**
     * Pair Synchronization with AtomicInteger in Java
     * In order to enforce the execution sequence of the jobs, we could create some dependencies between pairs of jobs,
     * i.e. the second job should depend on the completion of the first job and the third job should depend on the
     * completion of the second job.
     */
    class Foo {
        private AtomicInteger firstDone = new AtomicInteger(0);
        private AtomicInteger secondDone = new AtomicInteger(0);

        public Foo() {

        }

        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            firstDone.incrementAndGet();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            while (firstDone.get() != 1) {}
            printSecond.run();
            secondDone.incrementAndGet();
        }

        public void third(Runnable printThird) throws InterruptedException {
            while (secondDone.get() != 1) {}
            printThird.run();
        }
    }



    /**
     * Semaphore
     * https://www.mkyong.com/java/java-thread-mutex-and-semaphore-example/
     *
     * The argument to the Semaphore instance is the number of "permits" that are available. It can be any integer, not just 0 or 1.
     *
     * For semZero all acquire() calls will block and tryAcquire() calls will return false, until you do a release()
     *
     * For semOne the first acquire() calls will succeed and the rest will block until the first one releases.
     *
     * The class is well documented here.
     *
     * Parameters: permits - the initial number of permits available. This value may be negative,
     * in which case releases must occur before any acquires will be granted.
     *
     * When a semaphore is created with new Semaphore(0), it starts in an acquired state. This means that no permits are
     * initially available, and any thread attempting to acquire a permit will block until another thread releases one by
     * calling release(). In this state, the semaphore requires a release() before any thread can proceed.
     */
    class Foo1 {
        Semaphore run2, run3;

        public Foo1() {
            run2 = new Semaphore(0);
            run3 = new Semaphore(0);
        }

        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            run2.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            run2.acquire();
            printSecond.run();
            run3.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            run3.acquire();
            printThird.run();
        }
    }

    /**
     * CountDownLatch
     * In Java, CountDownLatch is a synchronization aid that allows one or more threads to wait until a set of operations
     * in other threads complete. It is initialized with a count, and each time a thread finishes its work, it calls countDown(),
     * reducing the count by 1. Once the count reaches zero, all waiting threads can proceed.
     */
    class Foo2 {
        private CountDownLatch latch2 = new CountDownLatch(1);
        private CountDownLatch latch3 = new CountDownLatch(1);

        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            latch2.countDown();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            latch2.await();
            printSecond.run();
            latch3.countDown();
        }

        public void third(Runnable printThird) throws InterruptedException {
            latch3.await();
            printThird.run();
        }
    }
}
