package Linkedin;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer_Consumer {
    final static ReentrantLock lock = new ReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static AtomicInteger capacity = new AtomicInteger();

    public static void main(String[] args) {
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                try {
                    while (queue.size() == capacity.intValue()) {
                        notEmpty.await();
                    }

                    queue.add("elem");
                    notFull.signalAll();
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }

            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                try {
                    while (queue.size() == 0) {
                        notFull.await();
                    }

                    queue.add("elem");
                    notEmpty.signalAll();
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }

            }
        });

        producer.start();
        consumer.start();
    }


}
