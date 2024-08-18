package com.example.client_schedule.entities;

import javafx.fxml.FXML;
import jakarta.persistence.*;

import java.security.ProtectionDomain;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public abstract class baseEntity {
    @FXML
    @Column(name="Create_Date")
    protected LocalDateTime created;
    @FXML
    @Column(name="Created_By")
    protected String createdBy;
    @FXML
    @Column(name="Last_Update")
    protected ZonedDateTime updated;
    @FXML
    @Column(name="Last_Updated_By")
    protected String updatedBy;

    public LocalDateTime getcreated() {
        return created;
    }

    public void setcreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getupdated() {
        return updated;
    }

    public void setupdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public String getupdatedBy() {
        return updatedBy;
    }

    public void setupdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
