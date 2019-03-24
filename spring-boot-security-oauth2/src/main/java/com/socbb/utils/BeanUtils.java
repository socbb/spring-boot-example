package com.socbb.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 对象拷贝
 * create by socbb on 2019/3/16 11:40.
 */
public final class BeanUtils {

    /**
     * 对象属性拷贝(不使用getter, setter方法))
     *
     * @param source     来源对象
     * @param target     目标对象
     * @param ignoreNull 是否忽略null属性
     */
    public static void copyProp(Object source, Object target, boolean ignoreNull) throws RuntimeException {

        Class<?> sourceClass = source.getClass();
        Field[] declaredFields = sourceClass.getDeclaredFields();

        Class<?> targetClass = target.getClass();

        for (Field field : declaredFields) {
            String name = field.getName();
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(source);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无访问权限的属性: " + sourceClass.getName() + "." + name);
            }
            if (ignoreNull && value == null) {
                continue;
            }
            Field declaredField = null;
            try {
                declaredField = targetClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                continue;
            }
            try {
                declaredField.setAccessible(true);
                declaredField.set(target, value);// 不会调用对象的set方法
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无访问权限的属性: " + sourceClass.getName() + "." + name);
            }
        }
    }

    /**
     * 对象属性拷贝(使用getter, setter方法)
     *
     * @param source     来源对象
     * @param target     目标对象
     * @param ignoreNull 是否忽略null属性
     */
    public static void copyPropGetSet(Object source, Object target, boolean ignoreNull) throws RuntimeException {
        Class<?> sourceClass = source.getClass();
        Field[] declaredFields = sourceClass.getDeclaredFields();
        Class<?> targetClass = target.getClass();

        for (Field field : declaredFields) {
            String name = field.getName();
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(source);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无访问权限的属性: " + sourceClass.getName() + "." + name);
            }
            if (ignoreNull && value == null) {
                continue;
            }
            String setMondthName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = null;
            try {
                method = targetClass.getMethod(setMondthName, field.getType());
            } catch (NoSuchMethodException e) {
                continue;
            }
            try {
                method.invoke(target, value);
            } catch (Exception e) {
                throw new RuntimeException("赋值失败: " + e.getMessage());
            }
        }
    }
}
