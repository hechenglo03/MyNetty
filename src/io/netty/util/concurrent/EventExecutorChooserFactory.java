package io.netty.util.concurrent;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public interface EventExecutorChooserFactory {

    EventExecutorChooser newChooser(EventExecutor[] executors);

    interface EventExecutorChooser{
        EventExecutor next();
    }
}
