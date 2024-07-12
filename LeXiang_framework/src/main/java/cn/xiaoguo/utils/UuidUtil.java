package cn.xiaoguo.utils;

/**
 * @ClassName UuidUtil
 * @Description
 * @Author 小果
 * @Date 2024/6/8 22:40
 * @Version 1.0
 */
public class UuidUtil {
    public static String getUUID(){

        return java.util.UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}