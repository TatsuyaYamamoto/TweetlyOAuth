package net.sokontokoro_factory.tweetly_oauth;

import java.lang.reflect.Field;

/**
 * privateで宣言されたフィールド変数を操作するUtilクラス
 */
public class PrivateField {
    /**
     * privateフィールド変数に対するセッター
     *
     * @param targetObject
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public static void setPrivateField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = targetObject.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);

    }

    /**
     * privateフィールド変数に対するゲッター
     *
     * @param targetObject
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getPrivateField(Object targetObject, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = targetObject.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(targetObject);
    }
}
