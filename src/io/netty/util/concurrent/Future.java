package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @Author: hechenglo03
 * @Date:2019/10/24
 * @Description:
 */

public interface Future<V> extends java.util.concurrent.Future<V> {

    boolean isSuccess();

    boolean isCancellable();

    Throwable cause();

    Future<V> await() throws InterruptedException;

    Future<V> awaitUinterruptibly();

    boolean await(long timeout, TimeUnit unit) throws InterruptedException;

    boolean await(long timeoutMillis) throws InterruptedException;

    boolean awaitUinterruptibly(long timeout,TimeUnit unit);

    boolean awaitUinterruptibly(long timeoutMills);

    Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);

    Future<V> addListeners(GenericFutureListener<? extends Future< ? super V>>... listeners);

    Future<V> removeListener(GenericFutureListener<? extends Future< ? super V>> listener);

    Future<V> removeListeners(GenericFutureListener< ? extends Future<? super V>>... listeners);

    Future<V> sync() throws InterruptedException;

    Future<V> syncUninterruptibly();

    V getNow();

    @Override
    boolean cancel(boolean mayInterruptIfRunning);

}
