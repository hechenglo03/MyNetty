package io.netty.util.concurrent;

import io.netty.util.internal.StringUtil;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolId = new AtomicInteger();

    private final AtomicInteger nextId = new AtomicInteger();
    private final String prefix;
    private final boolean daemon;
    private final int priority;
    protected final ThreadGroup threadGroup;


    public DefaultThreadFactory(Class<?> poolType){
        this(poolType,false,Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(Class<?> poolType,boolean daemon){
        this(poolType,daemon,Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String poolName,int priority){
        this(poolName,false,priority);
    }

    public DefaultThreadFactory(Class<?> poolType,boolean daemon,int priority){
        this(toPoolName(poolType),daemon,priority);
    }

    public static String toPoolName(Class<?> poolType){
        if(poolType == null){
            throw new IllegalArgumentException("poolType");
        }
        String poolName = StringUtil.simpleClassName(poolType);
        switch (poolName.length()){
            case 0:
                return "unknown";
            case 1:
                return poolName.toLowerCase(Locale.US);
            default:
               if(Character.isUpperCase(poolName.charAt(0)) && Character.isLowerCase(poolName.charAt(1))){
                   return Character.isLowerCase(poolName.charAt(0))+poolName.substring(1);
               }
               return poolName;

        }
    }


    public DefaultThreadFactory(String poolName,boolean daemon,int priority){
        this(poolName,daemon,priority,Thread.currentThread().getThreadGroup());
    }

    public DefaultThreadFactory(String poolName,boolean daemon,int priority,ThreadGroup threadGroup){
        if(poolName == null){
            throw new NullPointerException("poolName");
        }
        if(priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY){
            throw new IllegalArgumentException("priority : "+priority+
                    " (expected: Thread.MIN_PRIORITY <= priorpty <= Thread.MAX_PRIORITY");
        }

        prefix = poolName + "_"+poolId.incrementAndGet()+"_";
        this.daemon = daemon;
        this.priority = priority;
        this.threadGroup = threadGroup;
    }



    @Override
    public Thread newThread(Runnable r) {
        Thread thread =
                newThread(FastThreadLocalRunnable.wrap(r),prefix+nextId.incrementAndGet());
        try {
            if(thread.isDaemon() != daemon){
                thread.setDaemon(daemon);
            }

            if(thread.getPriority() != priority){
                thread.setPriority(priority);
            }
        } catch (Exception e) {

        }
        return thread;
    }

    protected Thread newThread(Runnable r,String name){
        return new FastThreadLocalThread(threadGroup,r,name);
    }
}
