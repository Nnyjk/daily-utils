package com.daily.excel.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtil {

    /**
     * 获取类的所有字段，包括父类的字段
     *
     * @param clazz 类的Class对象
     * @return 字段列表
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        // 获取类的所有字段，包括父类的字段
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        Class<?> superclass = clazz.getSuperclass();

        while (superclass != null && superclass != Object.class) {
            fields.addAll(Arrays.stream(superclass.getDeclaredFields()).collect(Collectors.toList()));
            superclass = superclass.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取类的所有方法，包括父类的方法
     *
     * @param clazz 类的Class对象
     * @return 方法列表
     */
    public static List<Method> getAllMethods(Class<?> clazz) {
        // 获取类的所有方法，包括父类的方法
        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList());
        Class<?> superclass = clazz.getSuperclass();

        while (superclass != null && superclass != Object.class) {
            methods.addAll(Arrays.stream(superclass.getDeclaredMethods()).collect(Collectors.toList()));
            superclass = superclass.getSuperclass();
        }
        return methods;
    }

    /**
     * 设置字段值
     *
     * @param object    要设置字段的对象实例
     * @param fieldName 字段名
     * @param value     要设置的值
     * @throws NoSuchFieldException   当找不到字段时抛出
     * @throws IllegalAccessException 当字段不可访问时抛出
     */
    public static void setFieldValue(Object object, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field field = getField(clazz, fieldName);
        if (field != null) {
            field.setAccessible(true); // 设置可访问
            field.set(object, value);
        }
    }

    /**
     * 获取字段值
     *
     * @param object    要获取字段的对象实例
     * @param fieldName 字段名
     * @return 字段值
     * @throws NoSuchFieldException   当找不到字段时抛出
     * @throws IllegalAccessException 当字段不可访问时抛出
     */
    public static Object getFieldValue(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field field = getField(clazz, fieldName);
        if (field != null) {
            field.setAccessible(true); // 设置可访问
            return field.get(object);
        }
        return null;
    }

    /**
     * 获取类的特定字段，包括父类的字段
     *
     * @param clazz     类的Class对象
     * @param fieldName 字段名
     * @return 字段对象
     * @throws NoSuchFieldException 当找不到字段时抛出
     */
    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null && superclass != Object.class) {
                return getField(superclass, fieldName);
            }
            throw e;
        }
    }
}
