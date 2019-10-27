package io.netty.util.concurrent;



/**
 * @Author: hechenglo03
 * @Date:2019/10/24
 * @Description:
 */
public interface Promise<V> extends Future<V>{

    Promise<V> setSuccess(V result);

    boolean trySuccess(V result);

    Promise<V> setFailure(Throwable cause);

    boolean tryFailure(Throwable cause);

    boolean setUncancellable();

    Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);

    Promise<V> addListeners(GenericFutureListener< ? extends Future<? super V>>... listener);

    Promise<V> removeListener(GenericFutureListener<? extends Future< ? super V>> listener);

    Promise<V> removeListeners(GenericFutureListener< ? extends Future< ? super V>>... listeners);

    @Override
    Promise<V> await() throws InterruptedException;

    @Override
    Promise<V> awaitUinterruptibly();

    Promise<V> sync() throws InterruptedException;

    Promise<V> syncUninterruptibly();



}
