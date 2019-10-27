package io.netty.util.concurrent;

import java.util.EventListener;

/**
 * @Author: hechenglo03
 * @Date:2019/10/24
 * @Description:
 */;
public interface GenericFutureListener<F extends Future<?>> extends EventListener {

    void operationComplete(F future) throws Exception;
}
