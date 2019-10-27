package io.netty.util.concurrent;

import java.sql.Time;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hechenglo03
 * @Date:2019/10/24
 * @Description:
 */
public interface EventExecutorGroup extends ScheduledExecutorService {

    boolean isShuttingDown();

    Future<?> shutdownGracefully();

    Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit);

    Future<?> terminationFuture();

    void shutdown();

    List<Runnable> shutdownNow();

    EventExecutorGroup next();

    Iterator<EventExecutor> iterator();

    Future<?> submit(Runnable task);

    <T> Future<T> submit(Runnable task,T result);

    <T> Future<T> submit(Callable<T> task);

    ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);

    <V> ScheduledFuture<V> schedule(Callable<V> callable,long delay,TimeUnit unit);

    ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit);

    ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,long initialDelay,long period,TimeUnit unit);




}
