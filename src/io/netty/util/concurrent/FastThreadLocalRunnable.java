package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public class FastThreadLocalRunnable implements Runnable {

    private final Runnable runnable;

    public FastThreadLocalRunnable(Runnable runnable){
        this.runnable = ObjectUtil.checkNotNull(runnable,"runnable");
    }

    @Override
    public void run() {

    }

    static Runnable wrap(Runnable runnable){
        return runnable instanceof FastThreadLocalRunnable ? runnable :new FastThreadLocalRunnable(runnable);
    }
}
