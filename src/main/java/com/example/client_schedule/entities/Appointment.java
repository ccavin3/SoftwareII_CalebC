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
    /**
     * The Id.
     */
    private void setPropertyRefs() {
        this.idProperty = new SimpleIntegerProperty(this, "id", this.id);
        this.titleProperty = new SimpleStringProperty(this, "title", this.title);
        this.descriptionProperty = new SimpleStringProperty(this, "description", this.description);
        this.locationProperty = new SimpleStringProperty(this, "location", this.location);
        this.typeProperty  = new SimpleStringProperty(this, "type", this.type);
        this.startProperty = new SimpleObjectProperty<>(this, "start", this.start);
        this.endProperty = new SimpleObjectProperty<>(this, "end", this.end);
        this.customerIdProperty = new SimpleIntegerProperty(this, "customerId", this.customerId);
        this.userIdProperty = new SimpleIntegerProperty(this, "userId", this.userId);
        this.contactIdProperty = new SimpleIntegerProperty(this, "contactId", this.contactId);
        this.startTimeProperty = new SimpleObjectProperty<>(this, "startTime", this.startTime);
        this.startDateProperty = new SimpleObjectProperty<>(this, "startDate", this.startDate);
        this.endTimeProperty = new SimpleObjectProperty<>(this, "endTime", this.endTime);
        this.endDateProperty = new SimpleObjectProperty<>(this, "endDate", this.endDate);
        this.customerProperty = new SimpleObjectProperty<>(this, "customer", this.customer);
        this.userProperty = new SimpleObjectProperty<>(this, "user", this.user);
        this.contactProperty = new SimpleObjectProperty<>(this, "contact", this.contact);
    }
//region Entity Columns
    @Id
    @FXML
    @Column(name="Appointment_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    @Transient
    private IntegerProperty idProperty;

    /**
     * The Title.
     */
    @FXML
    @Column(name="Title")
    public String title;

    @Transient
    private StringProperty titleProperty;
    
    @FXML
    @Column(name="Description")
    public String description;

    @Transient
    private StringProperty descriptionProperty;
    
    @FXML
    @Column(name="Location")
    public String location;

    @Transient
    private StringProperty locationProperty;
    
    @FXML
    @Column(name="Type")
    public String type;

    @Transient
    private StringProperty typeProperty;
    
    @FXML
    @Column(name="Start")
    public LocalDateTime start;

    @Transient
    private ObjectProperty<LocalDateTime> startProperty;
    
    @FXML
    @Column(name="End")
    public LocalDateTime end;

    @Transient
    private ObjectProperty<LocalDateTime> endProperty;
    
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

    /**
     * The Customer id.
     */
    @FXML
    @Column(name="Customer_ID", nullable=false)
    public int customerId;
    
    @Transient
    private IntegerProperty customerIdProperty;
    
    @FXML
    @Column(name="User_ID", nullable=false)
    public int userId;

    @Transient
    private IntegerProperty userIdProperty;
    
    @FXML
    @Column(name="Contact_ID", nullable=false)
    public int contactId;

    @Transient
    private IntegerProperty contactIdProperty;

    @Transient
    private ObjectProperty<LocalTime> startTimeProperty;

    @Transient
    private LocalTime startTime;

    @Transient
    private ObjectProperty<LocalDate> startDateProperty;

    @Transient
    private LocalDate startDate;

    @Transient
    private ObjectProperty<LocalTime> endTimeProperty;

    @Transient
    private LocalTime endTime;

    @Transient
    private ObjectProperty<LocalDate> endDateProperty;

    @Transient
    private LocalDate endDate;

    @Transient
    private DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Transient
    private DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("hh:mm[:ss] a");
    @Transient
    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm[:ss] a");

//endregion

    /**
     * The Customer.
     */
//region ORM
    @ManyToOne()
    @JoinColumn(name="Customer_ID", insertable = false, updatable = false)
    public Customer customer;

    @Transient
    private ObjectProperty<Customer> customerProperty;
    
    @ManyToOne()
    @JoinColumn(name="User_ID", insertable = false, updatable = false)
    public User user;

    @Transient
    private ObjectProperty<User> userProperty;
    
    @ManyToOne()
    @JoinColumn(name="Contact_ID", insertable = false, updatable = false)
    public Contact contact;
    
    @Transient
    private ObjectProperty<Contact> contactProperty;

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
        this.setPropertyRefs();
        this.setListeners();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title the title
     */
    public Appointment(String title) {
        this.setTitle(title);
        this.setPropertyRefs();
        this.setListeners();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     */
    public Appointment(String title, String description) {
        this.setTitle(title);
        this.setDescription(description);
        this.setPropertyRefs();
        this.setListeners();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id    the id
     * @param title the title
     */
    public Appointment(int id, String title) {
        this.setId(id);
        this.setTitle(title);
        this.setPropertyRefs();
        this.setListeners();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     */
    public Appointment(String title, String description, String location) {
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setPropertyRefs();
        this.setListeners();
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
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setPropertyRefs();
        this.setListeners();
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
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setPropertyRefs();
        this.setListeners();
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
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setPropertyRefs();
        this.setListeners();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param start       the start
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start) {
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
        this.setPropertyRefs();
        this.setListeners();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param start       the start
     * @param end         the end
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end) {
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
        this.setPropertyRefs();
        this.setListeners();
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
        this.setPropertyRefs();
        this.setListeners();
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

    /**
     * Gets id property.
     *
     * @return the id property
     */
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
//        this.idProperty.set(this.id);
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets title property.
     *
     * @return the title property
     */
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
        this.titleProperty.set(this.title);
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets description property.
     *
     * @return the description property
     */
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
        this.descriptionProperty.set(this.description);
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets location property.
     *
     * @return the location property
     */
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

    /**
     * Gets type property.
     *
     * @return the type property
     */
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
        this.typeProperty.set(this.type);
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Gets start property.
     *
     * @return the start property
     */
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

    /**
     * Gets end property.
     *
     * @return the end property
     */
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

    /**
     * Gets customer id property.
     *
     * @return the customer id property
     */
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
        this.customerIdProperty.set(this.customerId);
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets user id property.
     *
     * @return the user id property
     */
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
        this.userIdProperty.set(this.userId);
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Gets contact id property.
     *
     * @return the contact id property
     */
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
        this.contactIdProperty.set(this.contactId);
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets customer property.
     *
     * @return the customer property
     */
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
        this.customerProperty.set(this.customer);
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets user property.
     *
     * @return the user property
     */
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
        this.userProperty.set(this.user);
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Gets contact property.
     *
     * @return the contact property
     */
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
        this.contactProperty.set(this.contact);
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

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return this.startTime;
    }

    /**
     * Gets start time property.
     *
     * @return the start time property
     */
    public ObjectProperty<LocalTime> getStartTimeProperty() {
        return startTimeProperty;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        this.startTimeProperty.set(this.startTime);
        if (this.startDate != null) {
            this.setStart(this.startDate.atTime(this.startTime));
        }
        this.setListeners();
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Gets start date property.
     *
     * @return the start date property
     */
    public ObjectProperty<LocalDate> getStartDateProperty() {
        return this.startDateProperty;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.startDateProperty.set(this.startDate);
        if (this.startTime != null) {
                this.setStart(this.startDate.atTime(this.startTime));
        }
        this.setListeners();
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return this.endTime;
    }

    /**
     * Gets end time property.
     *
     * @return the end time property
     */
    public ObjectProperty<LocalTime> getEndTimeProperty() {
        return endTimeProperty;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        this.endTimeProperty.set(this.endTime);
        if (this.endDate != null ) {
            this.setEnd(this.endDate.atTime(this.endTime));
        }
        this.setListeners();
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Gets end date property.
     *
     * @return the end date property
     */
    public ObjectProperty<LocalDate> getEndDateProperty() {
        return endDateProperty;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        this.endDateProperty.set(this.endDate);
        if (this.endTime != null) {
            this.setEnd(this.endDate.atTime(this.endTime));
        }
        this.setListeners();
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
        this.startProperty.set(this.start);
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
        this.endProperty.set(this.end);
    }


    //endregion
}
