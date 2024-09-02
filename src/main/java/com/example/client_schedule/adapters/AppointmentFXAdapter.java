package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Appointment;
import com.example.client_schedule.entities.Contact;
import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentFXAdapter {
    public Appointment appointment;

    public AppointmentFXAdapter(Appointment appointment) {
        this.appointment = appointment;
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> start = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> end = new SimpleObjectProperty<>();
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final IntegerProperty contactId = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();
    private final ObjectProperty<Customer> customer = new SimpleObjectProperty<>();
    private final ObjectProperty<User> user = new SimpleObjectProperty<>();
    private final ObjectProperty<Contact> contact = new SimpleObjectProperty<>();

    public Integer getId() {
        return id.get();
    }
    public String getTitle() {
        return title.get();
    }
    public String getDescription() {
        return description.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getType() {
        return type.get();
    }

    public LocalDateTime getStart() {
        return start.get();
    }

    public LocalDateTime getEnd() {
        return end.get();
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public int getUserId() {
        return userId.get();
    }

    public int getContactId() {
        return contactId.get();
    }

    public LocalDate getStartDate() {
        return start.get().toLocalDate();
    }

    public void setStart(LocalDateTime ldt) {
        start.set(ldt);
    }

    public void setStartDate(LocalDate ld) {
        startDate.set(ld);
        start.set(ld.atTime(startTime.get()));
    }

    public LocalDate getEndDate() {
        return end.get().toLocalDate();
    }

    public void setEndDate(LocalDate ld) {
        endDate.set(ld);
        start.set(ld.atTime(endTime.get()));
    }
    public LocalTime getStartTime() {
        return start.get().toLocalTime();
    }

    public void setStartTime(LocalTime lt) {
        startTime.set(lt);
        start.set(startDate.get().atTime(startTime.get()));
    }

    public LocalTime getEndTime() {
        return start.get().toLocalTime();
    }

    public void setEndTime(LocalTime lt) {
        endTime.set(lt);
        start.set(endDate.get().atTime(endTime.get()));
    }
    public Customer getCustomer() {
        return customer.get();
    }

    public User getUser() {
        return user.get();
    }

    public Contact getContact() {
        return contact.get();
    }

    public IntegerProperty idProperty() {
        id.set(appointment.getId());
        id.addListener((obs, old, wen) -> appointment.setId((Integer) wen));
        return id;
    }

    public StringProperty titleProperty() {
        title.set(appointment.getTitle());
        title.addListener((obs, old, wen) -> appointment.setTitle(wen));
        return title;
    }

    public StringProperty descriptionProperty() {
        description.set(appointment.getDescription());
        description.addListener((obs, old, wen) -> appointment.setDescription(wen));
        return description;
    }

    public StringProperty locationProperty() {
        location.set(appointment.getLocation());
        location.addListener((obs, old, wen) -> appointment.setLocation(wen));
        return location;
    }

    public StringProperty typeProperty() {
        type.set(appointment.getType());
        type.addListener((obs, old, wen) -> appointment.setType(wen));
        return type;
    }

    public ObjectProperty<LocalDateTime> startProperty() {
        start.set(appointment.getStart());
        start.addListener((obs, old, wen) -> appointment.setStart(wen));
        return start;
    }

    public ObjectProperty<LocalDateTime> endProperty() {
        end.set(appointment.getEnd());
        end.addListener((obs, old, wen) -> appointment.setEnd(wen));
        return end;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        startDate.set(appointment.getStart().toLocalDate());
        startDate.addListener((obs, old, wen) -> appointment.setStartDate(wen));
        return startDate;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        endDate.set(appointment.getStart().toLocalDate());
        endDate.addListener((obs, old, wen) -> appointment.setEndDate(wen));
        return endDate;
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        startTime.set(appointment.getStart().toLocalTime());
        startTime.addListener((obs, old, wen) -> appointment.setStartTime(wen));
        return startTime;
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        endTime.set(appointment.getStart().toLocalTime());
        endTime.addListener((obs, old, wen) -> appointment.setEndTime(wen));
        return endTime;
    }

    public IntegerProperty customerIdProperty() {
        customerId.set(appointment.getCustomerId());
        customerId.addListener((obs, old, wen) -> appointment.setCustomerId((Integer) wen));
        return customerId;
    }

    public IntegerProperty contactIdProperty() {
        contactId.set(appointment.getContactId());
        contactId.addListener((obs, old, wen) -> appointment.setContactId((Integer) wen));
        return contactId;
    }

    public IntegerProperty userIdProperty() {
        userId.set(appointment.getUserId());
        userId.addListener((obs, old, wen) -> appointment.setUserId((Integer) wen));
        return userId;
    }

    public ObjectProperty<Customer> customerProperty() {
        customer.set(appointment.getCustomer());
        customer.addListener((obs, old, wen) -> {
            appointment.setCustomer(wen);
            appointment.setCustomerId(wen.getId());
        });
        return customer;
    }

    public ObjectProperty<User> userProperty() {
        user.set(appointment.getUser());
        user.addListener((obs, old, wen) -> {
            appointment.setUser(wen);
            appointment.setUserId(wen.getId());
        });
        return user;
    }

    public ObjectProperty<Contact> contactProperty() {
        contact.set(appointment.getContact());
        contact.addListener((obs, old, wen) -> {
            appointment.setContact(wen);
            appointment.setContactId(wen.getId());
        });
        return contact;
    }

    public void update(Appointment wen) {
        if (appointment == null || appointment.getId() != wen.getId()) {
            appointment.setId(wen.getId());
        }
        if (appointment == null || appointment.getTitle() == null || !appointment.getTitle().equals(wen.getTitle())) {
            appointment.setTitle(wen.getTitle());
        }
        if (appointment == null || appointment.getDescription() == null || !appointment.getDescription().equals(wen.getDescription())) {
            appointment.setDescription(wen.getDescription());
        }
        if (appointment == null || appointment.getLocation() == null || !appointment.getLocation().equals(wen.getLocation())) {
            appointment.setLocation(wen.getLocation());
        }
        if (appointment == null || appointment.getType() == null || !appointment.getType().equals(wen.getType())) {
            appointment.setType(wen.getType());
        }
        if (appointment == null || appointment.getStart() == null || !appointment.getStart().equals(wen.getStart())) {
            appointment.setStart(wen.getStart());
        }
        if (appointment == null || appointment.getEnd() == null || !appointment.getEnd().equals(wen.getEnd())) {
            appointment.setEnd(wen.getEnd());
        }
        if (appointment == null || appointment.getEndDate() == null || !appointment.getEndDate().equals(wen.getEndDate())) {
            appointment.setEndDate(wen.getEndDate());
        }
        if (appointment == null || appointment.getStartDate() == null || !appointment.getStartDate().equals(wen.getStartDate())) {
            appointment.setStartDate(wen.getStartDate());
        }
        if (appointment == null || appointment.getEndTime() == null || !appointment.getEndTime().equals(wen.getEndTime())) {
            appointment.setEndTime(wen.getEndTime());
        }
        if (appointment == null || appointment.getStartTime() == null || !appointment.getStartTime().equals(wen.getStartTime())) {
            appointment.setStartTime(wen.getStartTime());
        }
        if (appointment == null || appointment.getUserId() != (wen.getUserId())) {
            appointment.setUserId(wen.getUserId());
        }
        if (appointment == null || appointment.getCustomerId() != (wen.getCustomerId())) {
            appointment.setCustomerId(wen.getCustomerId());
        }
        if (appointment == null || appointment.getContactId() != (wen.getContactId())) {
            appointment.setContactId(wen.getContactId());
        }
        if (appointment == null || appointment.getUser() == null || !appointment.getUser().equals(wen.getUser())) {
            appointment.setUser(wen.getUser());
        }
        if (appointment == null || appointment.getContact() == null || !appointment.getContact().equals(wen.getContact())) {
            appointment.setContact(wen.getContact());
        }
        if (appointment == null || appointment.getCustomer() == null || !appointment.getCustomer().equals(wen.getCustomer())) {
            appointment.setCustomer(wen.getCustomer());
        }
    }
}
