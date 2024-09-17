package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Contact;
import com.example.client_schedule.helper.apptReport;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class apptReportFXAdapter {
    public apptReport apptreport;

    /**
     * Constructor for apptReportFXAdapter.
     *
     * @param apptreport An instance of apptReport. This is the original object that this class will adaptor for JavaFX data binding.
     */
    public apptReportFXAdapter(apptReport apptreport) {
        this.apptreport = apptreport;
    }

    private final IntegerProperty month = new SimpleIntegerProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final IntegerProperty count = new SimpleIntegerProperty();

    /**
     * Getter for month property.
     *
     * @return An Integer representation of month.
     */
    public Integer getMonth() {
        return month.get();
    }

    /**
     * Getter for type property.
     *
     * @return A String representation of type.
     */
    public String getType() {
        return type.get();
    }

    /**
     * Getter for count property.
     *
     * @return An Integer representation of count.
     */
    public Integer getCount() {
        return count.get();
    }

    /**
     * Setter for month property.
     *
     * @param m An Integer representing a month.
     */
    public void setMonth(Integer m) {
        this.month.set(m);
    }

    /**
     * Setter for type property.
     *
     * @param t A String representing a type.
     */
    public void setType(String t) {
        this.type.set(t);
    }

    /**
     * Setter for count property.
     *
     * @param c An Integer representing count.
     */
    public void setCount(Integer c) {
        this.count.set(c);
    }

    /**
     * Getter for monthProperty.
     * Binds the monthProperty to apptreport's month and listens for any changes to update.
     *
     * @return The IntegerProperty month.
     */
    public IntegerProperty monthProperty() {
        month.set(apptreport.getMonth());
        month.addListener((obs, old, wen) -> apptreport.setMonth((Integer) wen));
        return month;
    }

    /**
     * Getter for typeProperty.
     * Binds the typeProperty to apptreport's type and listens for any changes to update.
     *
     * @return The StringProperty type.
     */
    public StringProperty typeProperty() {
        type.set(apptreport.getType());
        type.addListener((obs, old, wen) -> apptreport.setType(wen));
        return type;
    }

    /**
     * Getter for countProperty.
     * Binds the countProperty to apptreport's count and listens for any changes to update.
     *
     * @return The IntegerProperty count.
     */
    public IntegerProperty countProperty() {
        count.set(apptreport.getCount());
        count.addListener((obs, old, wen) -> apptreport.setCount((Integer) wen));
        return count;
    }
}
