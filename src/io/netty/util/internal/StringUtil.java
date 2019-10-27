package io.netty.util.internal;

/**
 * @Author: hechenglo03
 * @Date:2019/10/26
 * @Description:
 */
public class StringUtil {

    public static final char PACKAGE_SEPARATOR_CHAR = '.';

    public static String simpleClassName(Class<?> clazz){
        String className = ObjectUtil.checkNotNull(clazz,"clazz").getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if(lastDotIdx > -1){
            return className.substring(lastDotIdx);
        }
        return className;
    }
}
