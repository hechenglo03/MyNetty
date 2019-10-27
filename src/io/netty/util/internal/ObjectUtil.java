package io.netty.util.internal;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public class ObjectUtil {

    public static <T> T checkNotNull(T arg,String text){
        if(arg == null){
            throw new NullPointerException(text);
        }
        return arg;
    }
}
