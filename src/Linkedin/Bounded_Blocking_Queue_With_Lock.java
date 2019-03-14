package Linkedin;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Modified from JDK LinkedBlockingQueue
 *
 * https://github.com/ZenOfAutumn/jdk8/blob/master/java/util/concurrent/LinkedBlockingQueue.java
 */
public class Bounded_Blocking_Queue_With_Lock<E> {
    private final int capacity;
    private Queue<E> queue;
    private final AtomicInteger count = new AtomicInteger();

    private final ReentrantLock takeLock = new ReentrantLock();

    /** Wait queue for waiting takes */
    private final Condition takeCondition = takeLock.newCondition();

    /** Lock held by put, offer, etc */
    private final ReentrantLock putLock = new ReentrantLock();

    /** Wait queue for waiting puts */
    private final Condition putCondition = putLock.newCondition();

    public Bounded_Blocking_Queue_With_Lock(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.capacity = capacity;
        queue = new LinkedList();
    }


    /**
     * Inserts the specified element at the tail of this queue, waiting if
     * necessary for space to become available.
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();
        // Note: convention in all put/take/etc is to preset local var
        // holding count negative to indicate failure unless set.
        int c = -1;
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;

        putLock.lockInterruptibly();

        try {
            /*
             * Note that count is used in wait guard even though it is
             * not protected by lock. This works because count can
             * only decrease at this point (all other puts are shut
             * out by lock), and we (or some other waiting put) are
             * signalled if it ever changes from capacity. Similarly
             * for all other uses of count in other wait guards.
             */
            while (count.get() == capacity) {
                putCondition.await();
            }

            queue.offer(e);

            c = count.getAndIncrement();
            if (c + 1 < capacity) {
                putCondition.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalTake();
    }

    public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                takeCondition.await();
            }
            x = queue.poll();

            c = count.getAndDecrement();
            if (c > 1) {
                takeCondition.signal();
            }
        } finally {
            takeLock.unlock();
        }

        if (c == capacity) {
            signalPut();
        }

        return x;
    }

    public int size() {
        return count.get();
    }

    /**
     * Signals a waiting take. Called only from put/offer (which do not
     * otherwise ordinarily lock takeLock.)
     */
    private void signalTake() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            takeCondition.signal();
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * Signals a waiting put. Called only from take/poll.
     */
    private void signalPut() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            putCondition.signal();
        } finally {
            putLock.unlock();
        }
    }

    /**
     * Removes a single instance of the specified element from this queue,
     * if it is present.  More formally, removes an element {@code e} such
     * that {@code o.equals(e)}, if this queue contains one or more such
     * elements.
     * Returns {@code true} if this queue contained the specified element
     * (or equivalently, if this queue changed as a result of the call).
     *
     * @param o element to be removed from this queue, if present
     * @return {@code true} if this queue changed as a result of the call
     */
    public boolean remove(Object o) {
        if (o == null) return false;
        fullyLock();
        try {
            return queue.remove(o);
        } finally {
            fullyUnlock();
        }
    }

    /**
     * Locks to prevent both puts and takes.
     */
    void fullyLock() {
        putLock.lock();
        takeLock.lock();
    }

    /**
     * Unlocks to allow both puts and takes.
     */
    void fullyUnlock() {
        takeLock.unlock();
        putLock.unlock();
    }


}
