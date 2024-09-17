package com.example.client_schedule.helper;

import com.example.client_schedule.MainApplication;
import jakarta.persistence.PrePersist;
import com.example.client_schedule.entities.*;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.hibernate.loader.ast.spi.Loadable;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class JPAListener {

    @PrePersist
    public void OnPrePersist(Object o) {
        ((baseEntity) o).setCreated(LocalDateTime.now());
        ((baseEntity) o).setCreatedBy(MainApplication.curUser);
    }

    @PreUpdate
    public void OnPreUpdate(Object o) {
        ((baseEntity) o).setUpdated(ZonedDateTime.now());
        ((baseEntity) o).setUpdatedBy(MainApplication.curUser);
    }

    @PreRemove
    public void OnPreRemove(Object o) {
    }
}
