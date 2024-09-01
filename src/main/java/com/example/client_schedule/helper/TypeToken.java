package com.example.client_schedule.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The type Type token.
 *
 * @param <T> the type parameter
 */
public abstract class TypeToken<T> {
    private Type type;

    /**
     * Instantiates a new Type token.
     */
    protected TypeToken() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets generic class.
     *
     * @return the generic class
     * @throws ClassNotFoundException the class not found exception
     */
    public Class getGenericClass() throws ClassNotFoundException {
        return Class.forName(getType().getTypeName());
    }
}
