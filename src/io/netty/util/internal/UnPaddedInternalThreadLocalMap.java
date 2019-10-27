package io.netty.util.internal;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public class UnPaddedInternalThreadLocalMap {

    static final ThreadLocal<InternalThreadLocalMap> slowThreadLocalMap =
            new ThreadLocal<InternalThreadLocalMap>();
}
