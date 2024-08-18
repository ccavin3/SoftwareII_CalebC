package com.example.client_schedule.helper;

import jakarta.persistence.*;
import javafx.collections.*;
import javafx.collections.FXCollections;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CRUD<T> extends TypeToken<T>{

    public static EntityManager em;

    public CRUD() {
        super();
    }

    private T currentRow;

    public <T> T getCurrentRow() throws ClassNotFoundException {
        Class<T> clz = (Class<T>) Class.forName(getType().getTypeName());
        return clz.cast(currentRow);
    }

    public void setCurrentRow(T curRow) {
        currentRow = curRow;
    }

    public void add() {
        em.persist(currentRow);
    }

    public <T> void add(T row) {
        em.persist(row);
    }
    public <T> void delete(T row) {

        em.remove(row);
    }

    public void delete() {
        em.remove(currentRow);
    }

    public ObservableList<T> rows() {
        try {
            String tableName = ((Table) getGenericClass().getAnnotation(Table.class)).name();
            String fieldnames = readColumns().stream().map(f -> f.getName()).collect(Collectors.joining(","));
            String sql = String.format("select t from com.example.client_schedule.entities.%s t", getGenericClass().getSimpleName());
//            String sql = String.format("select %s from com.example.client_schedule.entities.%s", fieldnames, getGenericClass().getSimpleName());
//            String sql = String.format("select t from %s t", tableName);
            TypedQuery<T> query = em.createQuery(sql, getGenericClass());

//            Query query = em.createQuery(String.format("select new com.example.client_schedule.entities.%s ( %s )", getType().getClass().getSimpleName(), fieldnames));
            return FXCollections.observableList(query.getResultList());
        } catch(Exception e) {
            return null;
        }
    }

    protected ObservableList<Field> readColumns() throws ClassNotFoundException {
        List<Field> fields = readAllColumns(new LinkedList<Field>(), getGenericClass());
        List<Field> filtered = fields.stream().filter(f -> f.isAnnotationPresent(Column.class)).toList();
        return FXCollections.observableList(filtered);
    }
    protected List<Field> readAllColumns(List<Field> fields, final Class<?> type) throws ClassNotFoundException {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            readAllColumns(fields, type.getSuperclass());
        }
        return fields;
    }
}
