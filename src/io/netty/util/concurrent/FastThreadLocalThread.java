package io.netty.util.concurrent;

import io.netty.util.internal.InternalThreadLocalMap;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public class FastThreadLocalThread extends Thread {

    private final boolean cleanupFastThreadLocals;

    private InternalThreadLocalMap internalThreadLocalMap;

    public FastThreadLocalThread(){
        this.cleanupFastThreadLocals = false;
    }

    public FastThreadLocalThread(ThreadGroup threadGroup,Runnable runnable){
        super(threadGroup,FastThreadLocalRunnable.wrap(runnable));
        cleanupFastThreadLocals = true;
    }

    public FastThreadLocalThread(String name){
        super(name);
        cleanupFastThreadLocals = false;
    }

    public FastThreadLocalThread(ThreadGroup threadGroup,String name){
        super(threadGroup,name);
        cleanupFastThreadLocals = false;
    }

    public FastThreadLocalThread(Runnable target,String name){
        super(FastThreadLocalRunnable.wrap(target),name);
        cleanupFastThreadLocals = true;
    }

    public FastThreadLocalThread(ThreadGroup threadGroup,Runnable target,String name){
        super(threadGroup,FastThreadLocalRunnable.wrap(target),name);
        cleanupFastThreadLocals = true;
    }

    public FastThreadLocalThread(ThreadGroup threadGroup,Runnable target,String name,long stackSize){
        super(threadGroup,FastThreadLocalRunnable.wrap(target),name,stackSize);
        cleanupFastThreadLocals = true;
    }

    public boolean willCleanupFastThreadLocals() {
        return cleanupFastThreadLocals;
    }

    public InternalThreadLocalMap getInternalThreadLocalMap() {
        return internalThreadLocalMap;
    }

    public void setInternalThreadLocalMap(InternalThreadLocalMap internalThreadLocalMap) {
        this.internalThreadLocalMap = internalThreadLocalMap;
    }

    public static boolean willCleanupFastThreadLocals(Thread thread){
        return thread instanceof FastThreadLocalThread && ((FastThreadLocalThread)thread).willCleanupFastThreadLocals();
    }
}
