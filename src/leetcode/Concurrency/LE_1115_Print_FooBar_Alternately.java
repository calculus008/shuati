package leetcode.Concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LE_1115_Print_FooBar_Alternately {
    /**
     * Suppose you are given the following code:
     *
     * class FooBar {
     *   public void foo() {
     *     for (int i = 0; i < n; i++) {
     *       print("foo");
     *     }
     *   }
     *
     *   public void bar() {
     *     for (int i = 0; i < n; i++) {
     *       print("bar");
     *     }
     *   }
     * }
     * The same instance of FooBar will be passed to two different threads.
     * Thread A will call foo() while thread B will call bar().
     * Modify the given program to output "foobar" n times.
     *
     * Example 1:
     * Input: n = 1
     * Output: "foobar"
     * Explanation: There are two threads being fired asynchronously.
     * One of them calls foo(), while the other calls bar(). "foobar"
     * is being output 1 time.
     *
     * Example 2:
     * Input: n = 2
     * Output: "foobarfoobar"
     * Explanation: "foobar" is being output 2 times.
     *
     * Medium
     */

    class FooBar_Semaphore {
        private int n;

        /**
         * When a semaphore is initialized with 0 (new Semaphore(0)), it means that no permits are initially available.
         * In this state:
         *
         * Any thread calling acquire() will block until another thread calls release() to add a permit.
         * The semaphore starts off in a "locked" state, requiring one or more releases to allow any acquiring threads
         * to proceed. This is typically used in scenarios where a thread must wait for a specific signal or event before
         * proceeding.
         */
        Semaphore foo = new Semaphore(0);
        Semaphore bar = new Semaphore(1);

        public FooBar_Semaphore(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                bar.acquire();

                printFoo.run();

                foo.release();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {

                // printBar.run() outputs "bar". Do not change or remove this line.
                foo.acquire();

                printBar.run();

                bar.release();
            }
        }
    }

    public class FooBarSynchronized {
        private int n;
        //flag 0->foo to be print  1->foo has been printed
        private int flag = 0;


        public FooBarSynchronized(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (flag == 1) {
                        this.wait();
                    }
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    flag = 1;
                    this.notifyAll();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (flag == 0) {
                        this.wait();
                    }
                    // printBar.run() outputs "bar". Do not change or remove this line.
                    printBar.run();
                    flag = 0;
                    this.notifyAll();
                }
            }
        }
    }

    class FooBar_Lock {

        private int n;
        //flag 0->foo to be print  1->foo has been printed
        private int flag=0;
        ReentrantLock reentrantLock= new ReentrantLock();
        Condition fooPrintedCondition=reentrantLock.newCondition();
        Condition barPrintedCondition=reentrantLock.newCondition();

        public FooBar_Lock(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                try {
                    reentrantLock.lock();
                    while (flag ==1){
                        barPrintedCondition.await();
                    }
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    flag=1;
                    fooPrintedCondition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                reentrantLock.lock();
                while (flag==0){
                    fooPrintedCondition.await();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                flag=0;
                barPrintedCondition.signalAll();
                reentrantLock.unlock();
            }
        }
    }

    public class FooBarCAS {
        private int n;
        ////flag 0->foo to be print  1->foo has been printed
        private AtomicInteger flag=new AtomicInteger(0);

        public FooBarCAS(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                while (!flag.compareAndSet(0,1)){
                    Thread.sleep(1);
                }
                printFoo.run();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                while (!flag.compareAndSet(1,0)){
                    Thread.sleep(1);
                }
                printBar.run();
            }
        }

    }
}
