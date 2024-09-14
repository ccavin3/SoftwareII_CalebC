package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Appointment;
import com.example.client_schedule.entities.Contact;
import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import org.hibernate.loader.ast.spi.Loadable;

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
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final IntegerProperty contactId = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> endDate;
    private ObjectProperty<LocalTime> startTime;
    private ObjectProperty<LocalTime> endTime;
    private ObjectProperty<Customer> customer;
    private ObjectProperty<User> user;
    private ObjectProperty<Contact> contact;

    public final void applyStartProperty() {
        if (start != null) {
            appointment.setStart(start.get());
        }
    }

    public final void applyEndProperty() {
        if (end != null) {
            appointment.setEnd(end.get());
        }
    }

    public final void applyStartDateProperty() {
        if (startDate != null) {
            appointment.setStartDate(startDate.get());
        }
    }

    public final void applyEndDateProperty() {
        if (endDate != null) {
            appointment.setEndDate(endDate.get());
        }
    }

    public final void applyStartTimeProperty() {
        if (startTime != null) {
            appointment.setStartTime(startTime.get());
        }
    }

    public final void applyEndTimeProperty() {
        if (endTime != null) {
            appointment.setEndTime(endTime.get());
        }
    }

    public final void applyCustomer() {
        if (customer != null) {
            appointment.setCustomer(customer.get());
        }
    }

    public final void applyUser() {
        if (user != null) {
            appointment.setUser(user.get());
        }
    }

    public final void applyContact() {
        if (contact != null) {
            appointment.setContact(contact.get());
        }
    }

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

    public void setEnd(LocalDateTime e) {
        end.set(e);
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
        return startDate.get();
    }

    public void setStart(LocalDateTime ldt) {
        start.set(ldt);
    }

    public void setStartDate(LocalDate ld) {
        startDate.set(ld);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public void setEndDate(LocalDate ld) {
        endDate.set(ld);
    }
    public LocalTime getStartTime() {
        return startTime.get();
    }

    public void setStartTime(LocalTime lt) {
        startTime.set(lt);
    }

    public LocalTime getEndTime() {
        return endTime.get();
    }

    public void setEndTime(LocalTime lt) {
        endTime.set(lt);
    }
    public Customer getCustomer() {
        return customer.get();
    }

    public void setCustomer(Customer c) {
        customer.set(c);
    }

    public User getUser() {
        return user.get();
    }

    public void setUser(User u) {
        user.set(u);
    }

    public Contact getContact() {
        return contact.get();
    }

    public void setContact(Contact c) {
        contact.set(c);
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
        if (start == null) {
            start = new SimpleObjectProperty<>(appointment, "start", appointment.getStart()) {
                @Override
                protected void invalidated() {
                    appointment.setStart(get());
                }
            };
        }
            return start;
    }

    public ObjectProperty<LocalDateTime> endProperty() {
        if (end == null) {
            end = new SimpleObjectProperty<>(appointment, "end", appointment.getEnd()) {
                @Override
                protected void invalidated() {
                    appointment.setEnd(get());
                }
            };
        }
            return end;
    }


    public ObjectProperty<LocalDate> startDateProperty() {
        if (startDate == null) {
            startDate = new SimpleObjectProperty<>(appointment, "startDate", appointment.getStart().toLocalDate()) {
                @Override
                protected void invalidated() {
                    appointment.setStartDate(get());
                }
            };
        }
            return startDate;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        if (endDate == null) {
            endDate = new SimpleObjectProperty<>(appointment, "endDate", appointment.getEnd().toLocalDate()) {
                @Override
                protected void invalidated() {
                    appointment.setEndDate(get());
                }
            };
        }
        return endDate;
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        if (startTime == null) {
            startTime = new SimpleObjectProperty<>(appointment, "startTime", appointment.getStart().toLocalTime()) {
                @Override
                protected void invalidated() {
                    appointment.setStartTime(get());
                }
            };
        }
        return startTime;
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        if (endTime == null) {
            endTime = new SimpleObjectProperty<>(appointment, "endTime", appointment.getEnd().toLocalTime()) {
                @Override
                protected void invalidated() {
                    appointment.setEndTime(get());
                }
            };
        }
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
        if (customer == null) {
            customer = new SimpleObjectProperty<>(appointment, "customer", appointment.getCustomer()) {
                @Override
                protected void invalidated() {
                    appointment.setCustomer(get());
                }
            };
        }
            return customer;
    }

    public ObjectProperty<User> userProperty() {
        if (user == null) {
            user =  new SimpleObjectProperty<>(appointment, "user", appointment.getUser()) {
                @Override
                protected void invalidated() {
                    appointment.setUser(get());
                }
            };
        }
            return user;
    }

    public ObjectProperty<Contact> contactProperty() {
        if (contact == null) {
            contact = new SimpleObjectProperty<>(appointment, "contact", appointment.getContact()) {
                @Override
                protected void invalidated() {
                    appointment.setContact(get());
                }
            };
        }
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
