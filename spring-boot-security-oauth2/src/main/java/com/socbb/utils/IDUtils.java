package com.socbb.utils;

/**
 * ID 生成策略
 * create by socbb on 2019/3/14 16:56.
 */
public class IDUtils {

    private final static Snowflake snowflake = new Snowflake(1, 1);

    public static long get() {
        return snowflake.nextId();
    }
}
