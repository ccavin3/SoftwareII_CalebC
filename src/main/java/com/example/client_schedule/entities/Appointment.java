package com.example.client_schedule.entities;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.SortedMap;

import jakarta.persistence.*;

/**
 * The type Appointment.
 */
@Entity
@Table(name="appointments")

public class Appointment {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Appointment_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int id;
    
    @Transient
    private IntegerProperty idProperty = new SimpleIntegerProperty();

    @FXML
    @Column(name="Title")
    public String title;

    @Transient
    private StringProperty titleProperty = new SimpleStringProperty();
    
    @FXML
    @Column(name="Description")
    public String description;

    @Transient
    private StringProperty descriptionProperty = new SimpleStringProperty();
    
    @FXML
    @Column(name="Location")
    public String location;

    @Transient
    private StringProperty locationProperty = new SimpleStringProperty();
    
    @FXML
    @Column(name="Type")
    public String type;

    @Transient
    private StringProperty typeProperty = new SimpleStringProperty();
    
    @FXML
    @Column(name="Start")
    public LocalDateTime start;

    @Transient
    private ObjectProperty<LocalDateTime> startProperty = new SimpleObjectProperty<>();
    
    @FXML
    @Column(name="End")
    public LocalDateTime end;

    @Transient
    private ObjectProperty<LocalDateTime> endProperty = new SimpleObjectProperty<>();
    
    /**
     * The Created.
     */
    @FXML
    @Column(name="Create_Date")
    public LocalDateTime created;
    
    /**
     * The Created by.
     */
    @FXML
    @Column(name="Created_By")
    public String createdBy;

    /**
     * The Updated.
     */
    @FXML
    @Column(name="Last_Update")
    public ZonedDateTime updated;

    /**
     * The Updated by.
     */
    @FXML
    @Column(name="Last_Updated_By")
    public String updatedBy;

    @FXML
    @Column(name="Customer_ID", insertable=false, updatable=false)
    public int customerId;
    
    @Transient
    private IntegerProperty customerIdProperty = new SimpleIntegerProperty();
    
    @FXML
    @Column(name="User_ID", insertable=false, updatable=false)
    public int userId;

    @Transient
    private IntegerProperty userIdProperty = new SimpleIntegerProperty();
    
    @FXML
    @Column(name="Contact_ID", insertable=false, updatable=false)
    public int contactId;

    @Transient
    private final IntegerProperty contactIdProperty = new SimpleIntegerProperty();

    @Transient
    private StringProperty startTimeProperty = new SimpleStringProperty();
    
    @Transient
    private StringProperty startDateProperty = new SimpleStringProperty();

    @Transient
    private StringProperty endTimeProperty = new SimpleStringProperty();

    @Transient
    private StringProperty endDateProperty = new SimpleStringProperty();

    @Transient
    private DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Transient
    private DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("hh:mm[:ss] a");
    @Transient
    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm[:ss] a");

//endregion

//region ORM
    @ManyToOne()
    @JoinColumn(name="Customer_ID")
    public Customer customer;

    @Transient
    private ObjectProperty<Customer> customerProperty = new SimpleObjectProperty<>();
    
    @ManyToOne()
    @JoinColumn(name="User_ID")
    public User user;

    @Transient
    private ObjectProperty<User> userProperty = new SimpleObjectProperty<>();
    
    @ManyToOne()
    @JoinColumn(name="Contact_ID")
    public Contact contact;
    
    @Transient
    private ObjectProperty<Contact> contactProperty = new SimpleObjectProperty<>();

//endregion

    private void setListeners() {
        this.getStartProperty().addListener(new ChangeListener<LocalDateTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalDateTime> obs, LocalDateTime old, LocalDateTime wen) {
                if (old == null || old.toLocalDate() != wen.toLocalDate()) {
                    setStartDate(wen.toLocalDate());
                }
                if (old == null || old.toLocalTime() != wen.toLocalTime()) {
                    setStartTime(wen.toLocalTime());
                }
            }
        });
        this.getEndProperty().addListener(new ChangeListener<LocalDateTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalDateTime> obs, LocalDateTime old, LocalDateTime wen) {
                if (old == null || old.toLocalDate() != wen.toLocalDate()) {
                    setEndDate(wen.toLocalDate());
                }
                if (old == null || old.toLocalTime() != wen.toLocalTime()) {
                    setEndTime(wen.toLocalTime());
                }
            }
        });
        this.getUserProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> obs, User old, User wen) {
                if (old == null || old.getId() != wen.getId()) {
                    setUserId(wen.getId());
                }
            }
        });
        this.getCustomerProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> obs, Customer old, Customer wen) {
                if (old == null || old.getId() != wen.getId()) {
                    setCustomerId(wen.getId());
                }
            }
        });
        this.getContactProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> obs, Contact old, Contact wen) {
                if (old == null || old.getId() != wen.getId()) {
                    setContactId(wen.getId());
                }
            }
        });
    }

//region Constructors
    /**
     * Instantiates a new Appointment.
     */
    public Appointment() {
        this.setListeners();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title the title
     */
    public Appointment(String title) {
        this.setListeners();
        this.setTitle(title);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     */
    public Appointment(String title, String description) {
        this.setListeners();
        this.setTitle(title);
        this.setDescription(description);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id    the id
     * @param title the title
     */
    public Appointment(int id, String title) {
        this.setListeners();
        this.setId(id);
        this.setTitle(title);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     */
    public Appointment(String title, String description, String location) {
        this.setListeners();
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id          the id
     * @param title       the title
     * @param description the description
     * @param location    the location
     */
    public Appointment(int id, String title, String description, String location) {
        this.setListeners();
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     */
    public Appointment(String title, String description, String location, String type) {
        this.setListeners();
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id          the id
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     */
    public Appointment(int id, String title, String description, String location, String type) {
        this.setListeners();
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location     the location
     * @param type        the type
     * @param start       the start
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start) {
        this.setListeners();
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setStartDate(start.toLocalDate());
        this.setStartTime(start.toLocalTime());
        this.setEnd(start);
        this.setEndDate(start.toLocalDate());
        this.setEndTime(start.toLocalTime());
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location     the location
     * @param type        the type
     * @param start       the start
     * @param end         the end
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end) {
        this.setListeners();
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setStartDate(start.toLocalDate());
        this.setStartTime(start.toLocalTime());
        this.setEnd(end);
        this.setEndDate(end.toLocalDate());
        this.setEndTime(end.toLocalTime());
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id          the id
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param start       the start
     * @param end         the end
     * @param created     the created
     * @param createdBy   the created by
     * @param updated     the updated
     * @param updatedBy   the updated by
     * @param customerId  the customer id
     * @param userId      the user id
     * @param contactId   the contact id
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy, int customerId, int userId, int contactId) {
        this.setListeners();
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setStartDate(start.toLocalDate());
        this.setStartTime(start.toLocalTime());
        this.setEnd(end);
        this.setEndDate(end.toLocalDate());
        this.setEndTime(end.toLocalTime());
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.setCustomerId(customerId);
        this.setUserId(userId);
        this.setContactId(contactId);
    }

    //endregion

//region getters-setters

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    public IntegerProperty getIdProperty() {
        return this.idProperty;
    }
    
    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
        this.idProperty.set(id);
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
        this.titleProperty.set(title);
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }
    
    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
        this.descriptionProperty.set(description);
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    public StringProperty getLocationProperty() {
        return locationProperty;
    }
    
    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
        this.locationProperty.set(location);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    public StringProperty getTypeProperty() {
        return typeProperty;
    }
    
    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
        this.typeProperty.set(type);
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    public ObjectProperty<LocalDateTime> getStartProperty() {
        return startProperty;
    }
    
    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    public ObjectProperty<LocalDateTime> getEndProperty() {
        return endProperty;
    }
    
     /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }
    
    public IntegerProperty getCustomerIdProperty() {
        return customerIdProperty;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        this.customerIdProperty.set(customerId);
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    public IntegerProperty getUserIdProperty() {
        return userIdProperty;
    }
    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
        this.userIdProperty.set(userId);
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }
    
    public IntegerProperty getContactIdProperty() {
        return contactIdProperty;
    }
    
    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
        this.contactIdProperty.set(contactId);
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }
    public ObjectProperty<Customer> getCustomerProperty() {
        return customerProperty;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerProperty.set(customer);
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    public ObjectProperty<User> getUserProperty() {
        return userProperty;
    }
    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
        this.userProperty.set(user);
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    public ObjectProperty<Contact> getContactProperty() {
        return contactProperty;
    }
    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
        this.contactProperty.set(contact);
    }

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

    public LocalTime getStartTime() {
        return LocalTime.parse(this.startTimeProperty.get(), tformatter);
    }

    public StringProperty getStartTimeProperty() {
        return startTimeProperty;
    }
    
    public void setStartTime(LocalTime startTime) {
        if (startTime == null) {
            this.startTimeProperty.set(null);
            this.setStart(null);
        } else if (this.startTimeProperty.get() == null || startTime != LocalTime.parse(this.startTimeProperty.get(), tformatter)) {
            this.startTimeProperty.set(startTime.format(tformatter));
            if (this.startDateProperty.get() != null ) {
                this.setStart(LocalDate.parse(this.startDateProperty.get(), dformatter).atTime(LocalTime.parse(this.startTimeProperty.get(), tformatter)));
            }
        }
    }

    public LocalDate getStartDate() {
        return LocalDate.parse(this.startDateProperty.get(), dformatter);
    }

    public StringProperty getStartDateProperty() {
        return startDateProperty;
    }
    
    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            this.startDateProperty.set(null);
            this.setStart(null);
        } else if (this.startDateProperty.get() == null || startDate != LocalDate.parse(this.startDateProperty.get(), dformatter)) {
            this.startDateProperty.set(startDate.format(dformatter));
            if (this.startTimeProperty.get() != null) {
                this.setStart(LocalDate.parse(this.startDateProperty.get(), dformatter).atTime(LocalTime.parse(this.startTimeProperty.get(), tformatter)));
            }
        }
    }

    public LocalTime getEndTime() {
        return LocalTime.parse(this.endTimeProperty.get(), tformatter);
    }

    public StringProperty getEndTimeProperty() {
        return endTimeProperty;
    }
    
    public void setEndTime(LocalTime endTime) {
        if (endTime == null) {
            this.endTimeProperty.set(null);
            this.setEnd(null);
        } else if (this.endTimeProperty.get() == null || endTime != LocalTime.parse(this.endTimeProperty.get(), tformatter)) {
            this.endTimeProperty.set(endTime.format(tformatter));
            if (this.endDateProperty.get() != null ) {
                this.setEnd(LocalDate.parse(this.endDateProperty.get(), dformatter).atTime(LocalTime.parse(this.endTimeProperty.get(), tformatter)));
            }
        }
    }

    public LocalDate getEndDate() {
        return LocalDate.parse(this.endDateProperty.get(), dformatter);
    }

    public StringProperty getEndDateProperty() {
        return endDateProperty;
    }
    
    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            this.endDateProperty.set(null);
            this.setEnd(null);
        } else if (this.endDateProperty.get() == null || endDate != LocalDate.parse(this.endDateProperty.get(), dformatter)) {
            this.endDateProperty.set(endDate.format(dformatter));
            if (this.endTimeProperty.get() != null) {
                this.setEnd(LocalDate.parse(this.endDateProperty.get(), dformatter).atTime(LocalTime.parse(this.endTimeProperty.get(), tformatter)));
            }
        }
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
        this.startProperty.set(start);
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
        this.endProperty.set(end);
    }


    //endregion
}
