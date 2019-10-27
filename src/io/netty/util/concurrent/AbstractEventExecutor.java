package io.netty.util.concurrent;

import com.sun.security.auth.UnixNumericGroupPrincipal;

import java.lang.management.ThreadInfo;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor {

    private final EventExecutorGroup parent;

    private final Collection<EventExecutor> selfCollection = Collections.<EventExecutor>singleton(this);

    static final Long DEFAULT_SHUTDOWN_QUIET_PERIOD = 2L;

    static final Long DEFAULT_SHUTDOWN_TIMEOUT = 15L;

    protected  AbstractEventExecutor(){
        this(null);
    }

    protected AbstractEventExecutor(EventExecutorGroup parent){
        this.parent = parent;
    }

    @Override
    public EventExecutorGroup parent(){
        return this.parent;
    }

    @Override
    public EventExecutor next(){
        return this;
    }

    @Override
    public boolean inEventLoop(){
        return inEventLoop(Thread.currentThread());
    }

    @Override
    public Iterator<EventExecutor> iterator(){
        return selfCollection.iterator();
    }

    @Override
    public Future<?> shutdownGracefully(){
        return shutdownGracefully(DEFAULT_SHUTDOWN_QUIET_PERIOD,DEFAULT_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
    }

    public List<Runnable> shutdownNow(){
        shutdown();
        return Collections.emptyList();
    }

    @Override
    public <V> Promise<V> newPromise(){
        return null;
    }

    @Override
    public <V> ProgressivePromise<V> newProgressivePromise(){
        return null;
    }

    @Override
    public <V> Future<V> newSucceededFuture(V result){
        return null;
    }

    @Override
    public <V> Future<V> newFailedFuture(Throwable cause){
        return null;
    }

    @Override
    public Future<?> submit(Runnable task){
        return (Future<?>) super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task,T result){
        return (Future<T>) super.submit(task,result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable){
        return (Future<T>) super.submit(callable);
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable,T value){
        return null;
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable){
        return null;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command,long delay,TimeUnit unit){
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable,long delay,TimeUnit unit){
        throw new UnsupportedOperationException();
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit){
        throw new UnsupportedOperationException();
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit){
        throw new UnsupportedOperationException();
    }

    protected static void safeExecute(Runnable task){
        try {
            task.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
