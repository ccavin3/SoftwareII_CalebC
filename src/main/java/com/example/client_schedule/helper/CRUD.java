package com.example.client_schedule.helper;

import jakarta.persistence.*;
import javafx.collections.*;
import javafx.collections.FXCollections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class for CRUD operations.
 *
 * @param <T> the Entity class type
 */
public class CRUD<T> extends TypeToken<T> {

    public ObservableList<T> trackedChanges = FXCollections.observableArrayList();
    public static EntityManagerFactory emf;
    public static EntityManager em;

    /**
     * Default constructor for the CRUD helper class.
     * Establishes connection with the persistence layer if not already established.
     */
    public CRUD() {
        super();
        if (CRUD.em == null) {
            CRUD.emf = Persistence.createEntityManagerFactory("client_schedule");
            CRUD.em = CRUD.emf.createEntityManager();
        }
        this.fetchFromDB();
    }

    private T currentRow;

    /**
     * Returns the current row in the database.
     *
     * @return T, the current row in the database.
     * @throws ClassNotFoundException if the entity class was not found.
     */
    public T getCurrentRow() throws ClassNotFoundException {
        Class<T> clz = (Class<T>) Class.forName(getType().getTypeName());
        return clz.cast(currentRow);
    }

    /**
     * Sets the current entity in the CRUD operations.
     *
     * @param curRow the entity to be set.
     */
    public void setCurrentRow(T curRow) {
        currentRow = curRow;
    }

    /**
     * Adds the current entity to the database.
     */
    public void add() {
        em.persist(currentRow);
        addTrackedRow(currentRow);
    }

    protected void addTrackedRow(T row) {
        if (!trackedChanges.contains(row)) {
            trackedChanges.add(row);
        }
    }

    protected void removeTrackedRow(T row) {
        if (trackedChanges.contains(row)) {
            trackedChanges.remove(row);
        }
    }

    /**
     * Adds a specific entity to the database.
     *
     * @param row the entity to be added.
     */
    public void add(T row) {
        em.persist(row);
        addTrackedRow(row);
    }

    /**
     * Deletes a specific entity from the database.
     *
     * @param row the entity to be deleted.
     */
    public void delete(T row) {
        em.remove(row);
        removeTrackedRow(row);
    }

    /**
     * Deletes the current entity from the database.
     */
    public void delete() {
        em.remove(currentRow);
        removeTrackedRow(currentRow);
    }

    public ObservableList<T> rows = FXCollections.observableArrayList();

    /**
     * Updates the list of rows with the current rows in the database.
     */
    public void fetchFromDB() {
        try {
            String tableName = ((Table) getGenericClass().getAnnotation(Table.class)).name();
            String fieldnames = readColumns()
                    .stream()
                    .map(f -> f.getName())
                    .collect(Collectors.joining(","));
            String sql = String.format("select t from com.example.client_schedule.entities.%s t", getGenericClass().getSimpleName());
            TypedQuery<T> query = em.createQuery(sql, getGenericClass());
            rows.clear();
            rows.addAll(query.getResultList());
        } catch (Exception e) {
            if (e.getMessage() != null) {
                // do nothing
            }
        }
    }

    /**
     * Retrieves all columns with annotation "Column" from the entity.
     *
     * @return an ObservableList of all columns in the entity.
     * @throws ClassNotFoundException if the entity class was not found.
     */
    protected ObservableList<Field> readColumns() throws ClassNotFoundException {
        List<Field> fields = readAllColumns(new LinkedList<Field>(), getGenericClass());
        List<Field> filtered = fields.stream().filter(f -> f.isAnnotationPresent(Column.class)).collect(Collectors.toList());
        return FXCollections.observableList(filtered);
    }

    /**
     * Returns all columns of the entity, including those of superclasses.
     *
     * @param fields List of fields to populate.
     * @param type   Class type of the entity.
     * @return A List of all columns of the entity.
     * @throws ClassNotFoundException if the entity class was not found.
     */
    protected List<Field> readAllColumns(List<Field> fields, final Class<?> type) throws ClassNotFoundException {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            readAllColumns(fields, type.getSuperclass());
        }
        return fields;
    }
}
