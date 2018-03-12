package org.minecord.minecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {

    private static AtomicInteger counter = new AtomicInteger(0);
    private static ExecutorService POOL = Executors.newFixedThreadPool(8, r -> new Thread(r, String.format("Thread %s", counter.incrementAndGet())));
    private static ScheduledExecutorService RUNNABLE_POOL = Executors.newScheduledThreadPool(2, r -> new Thread(r, "Thread " + counter.incrementAndGet()));

    public static void schedule(Runnable r, long initDelay, long delay, TimeUnit unit){
        RUNNABLE_POOL.scheduleAtFixedRate(r, initDelay, delay, unit);
    }

    public static void runAsync(Runnable r){
        POOL.execute(r);
    }
}
