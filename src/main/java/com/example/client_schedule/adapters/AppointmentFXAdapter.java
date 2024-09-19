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

/**
 * The AppointmentFXAdapter class is an adapter class that adapts an Appointment object to be used in a JavaFX application.
 * It provides JavaFX properties for each attribute of an Appointment, allowing for two-way binding and easy integration
 * with JavaFX UI components.
 */
public class AppointmentFXAdapter {
    public Appointment appointment;

    /**
     * Constructor requiring an Appointment object to initialize the adapter.
     *
     * @param appointment Appointment object being adapted.
     */
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

    /**
     * Applies the start property to the Appointment object if not null.
     */
    public final void applyStartProperty() {
        if (start != null) {
            appointment.setStart(start.get());
        }
    }

    /**
     * Applies the end property to the Appointment object if not null.
     */
    public final void applyEndProperty() {
        if (end != null) {
            appointment.setEnd(end.get());
        }
    }

    /**
     * Applies the startDate property to the Appointment object if not null.
     */
    public final void applyStartDateProperty() {
        if (startDate != null) {
            appointment.setStartDate(startDate.get());
        }
    }

    /**
     * Applies the endDate property to the Appointment object if not null.
     */
    public final void applyEndDateProperty() {
        if (endDate != null) {
            appointment.setEndDate(endDate.get());
        }
    }

    /**
     * Applies the startTime property to the Appointment object if not null.
     */
    public final void applyStartTimeProperty() {
        if (startTime != null) {
            appointment.setStartTime(startTime.get());
        }
    }

    /**
     * Applies the endTime property to the Appointment object if not null.
     */
    public final void applyEndTimeProperty() {
        if (endTime != null) {
            appointment.setEndTime(endTime.get());
        }
    }

    /**
     * Applies the customer to the Appointment object if not null.
     */
    public final void applyCustomer() {
        if (customer != null) {
            appointment.setCustomer(customer.get());
        }
    }

    /**
     * Applies the user to the Appointment object if not null.
     */
    public final void applyUser() {
        if (user != null) {
            appointment.setUser(user.get());
        }
    }

    /**
     * Applies the contact to the Appointment object if not null.
     */
    public final void applyContact() {
        if (contact != null) {
            appointment.setContact(contact.get());
        }
    }

    /**
     * @return Appointment's Id
     */
    public Integer getId() {
        return id.get();
    }

    /**
     * @return Appointment's Title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * @return Appointment's Description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * @return Appointment's Location
     */
    public String getLocation() {
        return location.get();
    }

    /**
     * @return Appointment's Type
     */
    public String getType() {
        return type.get();
    }

    /**
     * @return Appointment's start LocalDateTime
     */
    public LocalDateTime getStart() {
        return start.get();
    }

    /**
     * @return Appointment's end LocalDateTime
     */
    public LocalDateTime getEnd() {
        return end.get();
    }

    /**
     * Set end datetime of the Appointment object
     *
     * @param e LocalDateTime value to be set as end
     */
    public void setEnd(LocalDateTime e) {
        end.set(e);
    }

    /**
     * @return Appointment's customer Id
     */
    public int getCustomerId() {
        return customer.get().getId();
    }

    /**
     * @return Appointment's user Id
     */
    public int getUserId() {
        return user.get().getId();
    }

    /**
     * @return Appointment's Contact Id
     */
    public int getContactId() {
        return contact.get().getId();
    }

    /**
     * @return Appointment's Start Date
     */
    public LocalDate getStartDate() {
        return startDate.get();
    }

    /**
     * Set start datetime of the Appointment object
     *
     * @param ldt LocalDateTime value to be set as start
     */
    public void setStart(LocalDateTime ldt) {
        start.set(ldt);
    }

    /**
     * Set startDate of the Appointment object
     *
     * @param ld LocalDate value to be set as startDate
     */
    public void setStartDate(LocalDate ld) {
        startDate.set(ld);
        start.set(ld.atTime(startTime.get()));
    }

    /**
     * @return Appointment's End Date
     */
    public LocalDate getEndDate() {
        return endDate.get();
    }

    /**
     * Set endDate of the Appointment object
     *
     * @param ld LocalDate value to be set as endDate
     */
    public void setEndDate(LocalDate ld) {
        endDate.set(ld);
        end.set(ld.atTime(endTime.get()));
    }

    /**
     * @return Appointment's Start Time
     */
    public LocalTime getStartTime() {
        return startTime.get();
    }

    /**
     * Set startTime of the Appointment object
     *
     * @param lt LocalTime value to be set as startTime
     */
    public void setStartTime(LocalTime lt) {
        startTime.set(lt);
        start.set(startDate.get().atTime(lt));
    }

    /**
     * @return Appointment's End Time
     */
    public LocalTime getEndTime() {
        return endTime.get();
    }

    /**
     * Set endTime of the Appointment object
     *
     * @param lt LocalTime value to be set as endTime
     */
    public void setEndTime(LocalTime lt) {
        endTime.set(lt);
        end.set(endDate.get().atTime(lt));
    }

    /**
     * @return Appointment's Customer
     */
    public Customer getCustomer() {
        return customer.get();
    }

    /**
     * Set Customer for the Appointment
     *
     * @param c Customer object to be set.
     */
    public void setCustomer(Customer c) {
        customerId.set(c != null ? c.getId() : 0);
        customer.set(c);

    }

    /**
     * @return Appointment's User
     */
    public User getUser() {
        return user.get();
    }

    /**
     * Set User for the Appointment
     *
     * @param u User object to be set.
     */
    public void setUser(User u) {
        userId.set(u != null ? u.getId() : 0);
        user.set(u);
    }

    /**
     * @return Appointment's Contact
     */
    public Contact getContact() {
        return contact.get();
    }

    /**
     * Set Contact for the Appointment
     *
     * @param c Contact object to be set.
     */
    public void setContact(Contact c) {
        contactId.set(c != null ? c.getId() : 0);
        contact.set(c);
    }

    /**
     * @return IntegerProperty id for the Appointment to be used with JavaFX Binding
     */
    public IntegerProperty idProperty() {
        id.set(appointment.getId());
        id.addListener((obs, old, wen) -> appointment.setId((Integer) wen));
        return id;
    }

    /**
     * @return StringProperty for the title of the Appointment to be used with JavaFx Binding
     */
    public StringProperty titleProperty() {
        title.set(appointment.getTitle());
        title.addListener((obs, old, wen) -> appointment.setTitle(wen));
        return title;
    }

    /**
     * @return StringProperty for the description of the Appointment to be used with JavaFx Binding
     */
    public StringProperty descriptionProperty() {
        description.set(appointment.getDescription());
        description.addListener((obs, old, wen) -> appointment.setDescription(wen));
        return description;
    }

    /**
     * @return StringProperty for the location of the Appointment to be used with JavaFx Binding
     */
    public StringProperty locationProperty() {
        location.set(appointment.getLocation());
        location.addListener((obs, old, wen) -> appointment.setLocation(wen));
        return location;
    }

    /**
     * @return StringProperty for the type of the Appointment to be used with JavaFx Binding
     */
    public StringProperty typeProperty() {
        type.set(appointment.getType());
        type.addListener((obs, old, wen) -> appointment.setType(wen));
        return type;
    }

    /**
     * @return ObjectProperty for the start datetime of the Appointment to be used with JavaFx Binding
     */
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

    /**
     * @return ObjectProperty for the end datetime of the Appointment to be used with JavaFx Binding
     */
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


    /**
     * @return ObjectProperty for the start date of the Appointment to be used with JavaFx Binding
     */
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

    /**
     * @return ObjectProperty for the end date of the Appointment to be used with JavaFx Binding
     */
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

    /**
     * @return ObjectProperty for the start time of the Appointment to be used with JavaFx Binding
     */
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

    /**
     * @return ObjectProperty for the end time of the Appointment to be used with JavaFx Binding
     */
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

    /**
     * @return IntegerProperty for the customer Id of the Appointment to be used with JavaFx Binding
     */
    public IntegerProperty customerIdProperty() {
        customerId.set(appointment.getCustomer() != null ? appointment.getCustomer().getId() : 0);
        customerId.addListener((obs, old, wen) -> appointment.setCustomerId((Integer) wen));
        return customerId;
    }

    /**
     * @return IntegerProperty for the contact Id of the Appointment to be used with JavaFx Binding
     */
    public IntegerProperty contactIdProperty() {
        contactId.set(appointment.getContact() != null ? appointment.getContact().getId() : 0);
        contactId.addListener((obs, old, wen) -> appointment.setContactId((Integer) wen));
        return contactId;
    }

    /**
     * @return IntegerProperty for the user Id of the Appointment to be used with JavaFx Binding
     */
    public IntegerProperty userIdProperty() {
        userId.set(appointment.getUser() != null ? appointment.getUser().getId() : 0);
        userId.addListener((obs, old, wen) -> appointment.setUserId((Integer) wen));
        return userId;
    }

    public ObjectProperty<Customer> customerProperty() {
        if (customer == null) {
            customer = new SimpleObjectProperty<>(appointment, "customer", appointment.getCustomer()) {
                @Override
                protected void invalidated() {
                    appointment.setCustomer(get());
                    customerId.set(get() != null ? get().getId() : 0);

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
                    userId.set(get() != null ? get().getId() : 0);
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
                    contactId.set(get() != null ? get().getId() : 0);
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
