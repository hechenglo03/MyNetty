package io.netty.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: hechenglo03
 * @Date:2019/10/24
 * @Description:
 */
public abstract class AbstractFuture<V> implements Future<V> {

    public V get() throws InterruptedException, ExecutionException{
        await();
        Throwable cause = cause();
        if(cause == null){
            getNow();
        }
        if(cause instanceof CancellationException){
            throw (CancellationException) cause;
        }

        throw new ExecutionException(cause);

    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException,ExecutionException, TimeoutException{
        if(await(timeout,unit)){
            Throwable cause = cause();
            if(cause == null){
                return getNow();
            }
            if(cause instanceof CancellationException){
                throw (CancellationException) cause;
            }
            throw new ExecutionException(cause);
        }
        throw new TimeoutException();

    }
}
