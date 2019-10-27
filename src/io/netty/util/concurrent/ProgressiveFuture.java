package io.netty.util.concurrent;

/**
 * @Author: hechenglo03
 * @Date:2019/10/24
 * @Description:
 */
public interface ProgressiveFuture<V> extends Future<V> {

    ProgressiveFuture<V> addListener(GenericFutureListener< ? extends Future<? super V>> listener);

    ProgressiveFuture<V> addListeners(GenericFutureListener< ? extends Future<? super V>>... listeners);

    ProgressiveFuture<V> removeListener(GenericFutureListener< ? extends Future< ? super V>> listener);

    ProgressiveFuture<V> removeListeners(GenericFutureListener<? extends Future<? super V>> listeners);

    ProgressiveFuture<V> sync() throws InterruptedException;

    ProgressiveFuture<V> syncUninterruptibly();

    ProgressiveFuture<V> await() throws InterruptedException;

    ProgressiveFuture<V> awaitUninterruptibly();
}
