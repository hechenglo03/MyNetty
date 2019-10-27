package io.netty.util.internal;

import io.netty.util.concurrent.FastThreadLocalThread;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public class InternalThreadLocalMap extends UnPaddedInternalThreadLocalMap {

    public static InternalThreadLocalMap getIfSet(){
        Thread thread = Thread.currentThread();
        if(thread instanceof FastThreadLocalThread){
            return ((FastThreadLocalThread) thread).getInternalThreadLocalMap();
        }
        return slowThreadLocalMap.get();
    }

    public static InternalThreadLocalMap get(){
        Thread thread = Thread.currentThread();
        if(thread instanceof FastThreadLocalThread){
            return fastGet((FastThreadLocalThread) thread);
        }
        return slowGet();
    }

    private static InternalThreadLocalMap fastGet(FastThreadLocalThread threadLocalThread){
        InternalThreadLocalMap internalThreadLocalMap = threadLocalThread.getInternalThreadLocalMap();
        if(internalThreadLocalMap == null){
            threadLocalThread.setInternalThreadLocalMap( internalThreadLocalMap = new InternalThreadLocalMap());
        }
        return internalThreadLocalMap;
    }


    private static InternalThreadLocalMap slowGet(){
        ThreadLocal<InternalThreadLocalMap> slowThreadLocalMap = UnPaddedInternalThreadLocalMap.slowThreadLocalMap;
        InternalThreadLocalMap internalThreadLocalMap = slowThreadLocalMap.get();
        if(internalThreadLocalMap == null){
            internalThreadLocalMap = new InternalThreadLocalMap();
            slowThreadLocalMap.set(internalThreadLocalMap);
        }
        return internalThreadLocalMap;
    }


    public static void remove(){
        Thread thread = Thread.currentThread();
        if(thread instanceof FastThreadLocalThread){
            ((FastThreadLocalThread) thread).setInternalThreadLocalMap(null);
        }
        slowThreadLocalMap.remove();
    }

    public static void destroy(){
        slowThreadLocalMap.remove();
    }




}
