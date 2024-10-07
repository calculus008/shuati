package Interviews.Apple;

import java.util.PriorityQueue;

public class SimpleSchedulerWithSync {

    private final PriorityQueue<ScheduledTask> taskQueue = new PriorityQueue<>((a, b) -> Long.compare(a.scheduledTime, b.scheduledTime)); // A priority queue to store scheduled tasks (sorted by time)
    private volatile boolean isRunning = true;

    public SimpleSchedulerWithSync() {   // Constructor starts the scheduler thread
        Thread schedulerThread = new Thread(this::runScheduler);
        schedulerThread.start();
    }

    public synchronized void schedule(Long time, Runnable task) { // Method to schedule new tasks
        taskQueue.offer(new ScheduledTask(time, task));
        notify();  // Notify the scheduler thread that a new task is available
    }

    public synchronized void stop() {
        isRunning = false;
        notify();
    }

    private synchronized void runScheduler() {  // The scheduler's main loop to check and run tasks
        while (isRunning) {
            try {
                while (taskQueue.isEmpty() && isRunning) { // Wait until a new task is scheduled
                    wait();
                }

                ScheduledTask nextTask = taskQueue.peek(); // Peek at the next task in the queue
                long currentTime = System.currentTimeMillis();

                if (nextTask != null && nextTask.scheduledTime <= currentTime) {
                    taskQueue.poll(); // Remove the task from the queue
                    executeTask(nextTask); // Execute the task
                } else { // Wait for the task's scheduled time
                    if (nextTask != null) {
                        long waitTime = nextTask.scheduledTime - currentTime;
                        if (waitTime > 0) {
                            wait(waitTime);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle the interrupt
            }
        }
        System.out.println("Scheduler stopped.");
    }

    private void executeTask(ScheduledTask task) {
        new Thread(task.task).start();  // Run the task in a new thread
    }

    static class ScheduledTask {
        long scheduledTime;
        Runnable task;

        public ScheduledTask(Long time, Runnable task) {
            this.scheduledTime = time;
            this.task = task;
        }
    }

    // Test the scheduler

    /**
     * Test cases, Expected output:
     * Task 4 (late) executed
     * Task 2 executed
     * Task 1 executed
     * Task 3 executed
     */
    public static void main(String[] args) {
        SimpleSchedulerWithSync scheduler = new SimpleSchedulerWithSync();

        scheduler.schedule(System.currentTimeMillis() + 2000, () -> System.out.println("Task 1 executed"));
        scheduler.schedule(System.currentTimeMillis() + 1000, () -> System.out.println("Task 2 executed"));
        scheduler.schedule(System.currentTimeMillis() + 3000, () -> System.out.println("Task 3 executed"));
        scheduler.schedule(System.currentTimeMillis() - 1000, () -> System.out.println("Task 4 (late) executed"));

        try {
            Thread.sleep(5000); // Sleep for 5 seconds to allow tasks to execute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduler.stop();
    }
}

