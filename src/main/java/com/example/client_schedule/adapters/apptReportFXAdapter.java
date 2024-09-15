package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Contact;
import com.example.client_schedule.helper.apptReport;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class apptReportFXAdapter {
    public apptReport apptreport;

    public apptReportFXAdapter(apptReport apptreport) {
        this.apptreport = apptreport;
    }

    private final IntegerProperty month = new SimpleIntegerProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final IntegerProperty count = new SimpleIntegerProperty();

    public Integer getMonth() {
        return month.get();
    }
    public String getType() {
        return type.get();
    }

    public Integer getCount() {
        return count.get();
    }

    public void setMonth(Integer m) {
        this.month.set(m);
    }

    public void setType(String t) {
        this.type.set(t);
    }

    public void setCount(Integer c) {
        this.count.set(c);
    }

    public IntegerProperty monthProperty() {
        month.set(apptreport.getMonth());
        month.addListener((obs, old, wen) -> apptreport.setMonth((Integer) wen));
        return month;
    }

    public StringProperty typeProperty() {
        type.set(apptreport.getType());
        type.addListener((obs, old, wen) -> apptreport.setType(wen));
        return type;
    }

    public IntegerProperty countProperty() {
        count.set(apptreport.getCount());
        count.addListener((obs, old, wen) -> apptreport.setCount((Integer)wen));
        return count;
    }
}
