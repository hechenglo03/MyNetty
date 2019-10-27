package io.netty.util.concurrent;

/**
 * @Author: hechenglo03
 * @Date:2019/10/24
 * @Description:
 */
public interface ProgressivePromise<V> extends Promise<V>, ProgressiveFuture<V> {

    ProgressivePromise<V> setProcess(long process,long total);

    boolean tryProcess(long process,long total);

    ProgressivePromise<V> setSuccess(V result);

    ProgressivePromise<V> setFailure(Throwable cause);

    @Override
    ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);

    @Override
    ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners);

    @Override
    ProgressivePromise<V> removeListener(GenericFutureListener< ? extends Future<? super V>> listener);

    @Override
    ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners);

    @Override
    ProgressivePromise<V> await() throws InterruptedException;

    @Override
    ProgressivePromise<V> awaitUninterruptibly();

    @Override
    ProgressivePromise<V> sync() throws InterruptedException;

    @Override
    ProgressivePromise<V> syncUninterruptibly();


}
