package com.example.client_schedule.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeToken<T> {
    private Type type;

    protected TypeToken() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
    public Class getGenericClass() throws ClassNotFoundException {
        return Class.forName(getType().getTypeName());
    }
}
