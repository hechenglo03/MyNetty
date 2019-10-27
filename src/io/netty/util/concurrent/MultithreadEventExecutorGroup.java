package io.netty.util.concurrent;

import sun.awt.image.ImageWatched;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup{

    private final EventExecutor[] children;

    private final Set<EventExecutor> readonlyChildren;

    private final AtomicInteger terminatedChildren = new AtomicInteger();

    private final EventExecutorChooserFactory.EventExecutorChooser chooser;

    private final Promise<?> terminationFuture = null;

    protected MultithreadEventExecutorGroup(int nTreads, Executor executor,
                                           EventExecutorChooserFactory chooserFactory,Object... args){
        if(nTreads < 0){
            throw new IllegalArgumentException(String.format("nthreads:%d (expected：>0)",nTreads));
        }

        if(executor == null){
            executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
        }

        children = new EventExecutor[nTreads];

        for(int i = 0 ; i < nTreads ; i++){
            boolean success = false;
            try {
                children[i] = newChild(executor,args);
                success = true;
            } catch (Exception e) {
                throw new IllegalStateException("failed to create a child event loop",e);
            } finally {
                if(!success){
                    for(int j = 0 ; j < i ; j++){
                        children[j].shutdownGracefully();
                    }
                    for(int j = 0 ; j < i ; j++){
                        EventExecutor e = children[j];
                        try {
                            while (!e.isTerminated()){
                                e.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }

        }

        chooser = chooserFactory.newChooser(children);

        final FutureListener<Object> terminationListener = new FutureListener<Object>() {
            @Override
            public void operationComplete(Future<Object> future) throws Exception {
                if(terminatedChildren.incrementAndGet() == children.length){
                    terminationFuture.setSuccess(null);
                }
            }
        };

        for(EventExecutor eventExecutor:children){
            eventExecutor.terminationFuture().addListener(terminationListener);
        }

        Set<EventExecutor> childrenSet = new LinkedHashSet<EventExecutor>(children.length);

        Collections.addAll(childrenSet,children);
        readonlyChildren = Collections.unmodifiableSet(childrenSet);
    }

    protected ThreadFactory newDefaultThreadFactory(){
        return new DefaultThreadFactory(getClass());
    }

    @Override
    public EventExecutor next(){
        return chooser.next();
    }

    @Override
    public Iterator<EventExecutor> iterator(){
        return readonlyChildren.iterator();
    }

    public final int executorCount(){
        return children.length;
    }

    @Override
    public Future<?> shutdownGracefully(long quietPeriod,long timeout,TimeUnit unit){
        for(EventExecutor eventExecutor:children){
            eventExecutor.shutdownGracefully(quietPeriod,timeout,unit);
        }
        return terminationFuture;
    }

    protected abstract EventExecutor newChild(Executor executor,Object... args) throws Exception;


    @Override
    public Future<?> terminationFuture(){
        return terminationFuture;
    }

    @Override
    public void shutdown(){
        for(EventExecutor eventExecutor:children){
            eventExecutor.shutdown();
        }
    }


    @Override
    public boolean isShuttingDown() {
        for (EventExecutor eventExecutor : children) {
            if (!eventExecutor.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isTerminated(){
        for(EventExecutor eventExecutor:children){
            if(!eventExecutor.isTerminated()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean awaitTermination(long timeout,TimeUnit unit) throws InterruptedException {
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        loop:for(EventExecutor eventExecutor:children){
            for(;;){
                long timeLeft = deadline -  System.nanoTime();
                if(timeLeft < 0){
                    break loop;
                }
                if(eventExecutor.awaitTermination(timeout,TimeUnit.SECONDS)){
                    break ;
                }
            }
        }
        return isTerminated();
    }


}
