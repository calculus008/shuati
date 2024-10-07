package Interviews.Apple;

import java.util.PriorityQueue;

public class SimpleSchedulerWithLock  {
    private final PriorityQueue<ScheduledTask> taskQueue = new PriorityQueue<>((a, b) -> Long.compare(a.scheduledTime, b.scheduledTime));
    private final Object lock = new Object();    // Lock object for synchronization

    public SimpleSchedulerWithLock() {
        Thread schedulerThread = new Thread(this::runScheduler);
        schedulerThread.start();
    }

    public void schedule(Long time, Runnable task) {
        synchronized (lock) {//!!!
            taskQueue.offer(new ScheduledTask(time, task));
            lock.notify(); // Notify the scheduler thread that a new task is added
        }
    }

    private void runScheduler() {
        while (true) {
            synchronized (lock) {//!!!
                try {
                    while (taskQueue.isEmpty()) {
                        lock.wait();
                    }

                    ScheduledTask nextTask = taskQueue.peek();
                    long currentTime = System.currentTimeMillis();

                    if (nextTask != null && nextTask.scheduledTime <= currentTime) {
                        taskQueue.poll(); // Remove the task from the queue
                        executeTask(nextTask); // Execute the task
                    } else {
                        if (nextTask != null) {
                            lock.wait(nextTask.scheduledTime - currentTime);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // Method to execute the task
    private void executeTask(ScheduledTask task) {
        new Thread(task.task).start(); // Run the task in a new thread
    }

    static class ScheduledTask {
        long scheduledTime;
        Runnable task;

        public ScheduledTask(Long time, Runnable task) {
            this.scheduledTime = time;
            this.task = task;
        }
    }

    public static void main(String[] args) {
        SimpleSchedulerWithLock scheduler = new SimpleSchedulerWithLock();

        System.out.println("Test with lock Objcet");
        // Schedule tasks with delays
        scheduler.schedule(System.currentTimeMillis() + 2000, () -> System.out.println("Task 1 executed"));
        scheduler.schedule(System.currentTimeMillis() + 1000, () -> System.out.println("Task 2 executed"));
        scheduler.schedule(System.currentTimeMillis() + 3000, () -> System.out.println("Task 3 executed"));
        scheduler.schedule(System.currentTimeMillis() - 1000, () -> System.out.println("Task 4 (late) executed"));

        // Sleep to give time for tasks to run
        try {
            Thread.sleep(5000); // Sleep for 5 seconds to allow tasks to execute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

