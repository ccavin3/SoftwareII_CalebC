package com.example.client_schedule.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Class to represent the actual type arguments for the parameters of the generic superclass.
 *
 * @param <T> Type of the object to represent.
 */
public abstract class TypeToken<T> {
    private Type type;

    /**
     * Constructor to initialize the type of the generic superclass.
     */
    protected TypeToken() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    /**
     * Method to get the type of the generic superclass.
     *
     * @return type of the generic superclass.
     */
    public Type getType() {
        return type;
    }

    /**
     * Method to get the generic class of the type token.
     *
     * @return Class representation of the type token.
     * @throws ClassNotFoundException if the class cannot be located
     */
    public Class getGenericClass() throws ClassNotFoundException {
        return Class.forName(getType().getTypeName());
    }
}
