package com.example.client_schedule.entities;

import javafx.fxml.FXML;
import jakarta.persistence.*;

import java.security.ProtectionDomain;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * The type Base entity.
 */
public abstract class baseEntity {
    /**
     * The Created.
     */
    @FXML
    @Column(name="Create_Date")
    protected LocalDateTime created;
    /**
     * The Created by.
     */
    @FXML
    @Column(name="Created_By")
    protected String createdBy;
    /**
     * The Updated.
     */
    @FXML
    @Column(name="Last_Update")
    protected ZonedDateTime updated;
    /**
     * The Updated by.
     */
    @FXML
    @Column(name="Last_Updated_By")
    protected String updatedBy;

    /**
     * Gets .
     *
     * @return the
     */
    public LocalDateTime getcreated() {
        return created;
    }

    /**
     * Sets .
     *
     * @param created the created
     */
    public void setcreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public ZonedDateTime getupdated() {
        return updated;
    }

    /**
     * Sets .
     *
     * @param updated the updated
     */
    public void setupdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    /**
     * Gets by.
     *
     * @return the by
     */
    public String getupdatedBy() {
        return updatedBy;
    }

    /**
     * Sets by.
     *
     * @param updatedBy the updated by
     */
    public void setupdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
